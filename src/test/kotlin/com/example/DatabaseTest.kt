package com.example

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.fail

/**
 * An ugly helper that sets up H2 in PostgreSQL mode,
 * runs migrations and does asserts.
 */
open class DatabaseTest(
    testDataFilePath: String,
    tables: List<Table>,
) {
    init {
        prepareTestData(testDataFilePath, tables)
    }

    companion object {
        private val REGEX_WHITESPACE = Regex("\\s+")

        private val appliedTestDataFiles = mutableSetOf<String>()

        private lateinit var testDatabase: Database

        @JvmStatic
        @BeforeAll
        fun initTestDatabase() {
            testDatabase = createTestDatabase()
        }

        @JvmStatic
        @AfterAll
        fun closeTestDatabase() {
            TransactionManager.closeAndUnregister(testDatabase)
        }

        private fun createTestDatabase(): Database {
            val keepOpenAfterLastConnection = "DB_CLOSE_DELAY=-1" // to reuse the same DB between tests
            val h2PostgresMode = "MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH"
            return Database.connect(
                url = "jdbc:h2:mem:test;$keepOpenAfterLastConnection;$h2PostgresMode",
                driver = "org.h2.Driver"
            )
        }

        private fun prepareTestData(testDataFilePath: String, tables: List<Table>) {
            if (appliedTestDataFiles.contains(testDataFilePath)) return

            val testDataSqlFile = DatabaseTest::class.java.getResource(testDataFilePath).file?.let { File(it) }
            require(testDataSqlFile?.exists() == true) {
                "Test data file not found: $testDataFilePath"
            }

            val sqlLines = requireNotNull(testDataSqlFile)
                .readLines()
                .filterNot { it.isBlank() }

            transaction(testDatabase) {
                SchemaUtils.create(*tables.toTypedArray())
                TransactionManager.current().connection.executeInBatch(sqlLines)
            }
            appliedTestDataFiles.add(testDataFilePath)
        }

        fun assertQuery(queryBuilder: () -> Query, expectedResult: () -> String) {
            transaction(testDatabase) {
                addLogger(StdOutSqlLogger)

                val query = queryBuilder()

                if (query.empty()) {
                    fail("The query returned no data.")
                }

                val headerValues = query.first().fieldIndex.entries.map { it.key.toString() }
                val headerCommonPrefix = findCommonHeaderPrefix(headerValues)
                val headerWithoutCommonPrefix = headerValues.map { it.removePrefix(headerCommonPrefix).trim() }

                val values = query.map { result ->
                    result.fieldIndex.entries.map { result[it.key].toString() }
                }

                val actual = prettyPrintResults(listOf(headerWithoutCommonPrefix) + values)
                val expected = expectedResult()
                    .lines()
                    .filterNot { it.isBlank() }
                    .map { it.trim().split(REGEX_WHITESPACE) }
                    .let { prettyPrintResults(it) }

                if (expected == actual) {
                    println("\nCorrect! Query results:")
                    println(actual)
                } else {
                    println("\nResults of your query:")
                    println(actual)
                    println("\nExpected results:")
                    println(expected)

                    assertEquals(expected, actual) // so that IDEA gives the option to view the diff
                }
            }
        }

        /**
         * If all values begin with `com.example.pc.PC`, return it as a common prefix
         */
        private fun findCommonHeaderPrefix(headerValues: List<String>): String {
            return when (headerValues.size) {
                1 -> {
                    val headerValue = headerValues.single()
                    when {
                        headerValue.contains(" ") -> headerValue.substringBeforeLast(" ") // hack for single val aliases
                        headerValue.contains(".") -> headerValue.substringBeforeLast(".") + "."
                        else -> ""
                    }
                }

                else -> headerValues.reduce { acc, str ->
                    acc.commonPrefixWith(str)
                }
            }
        }

        /**
         * Pretty prints values (with a header) in the following format:
         *
         * ```
         * +-------+--------+
         * | model | price  |
         * +-------+--------+
         * | 1276  | 400.00 |
         * | 1288  | 400.00 |
         * +-------+--------+
         * ```
         *
         * Constructed from:
         *
         * ```kotlin
         * listOf(
         *     listOf("model", "price"),
         *     listOf("1276", "400.00"),
         *     listOf("1288", "400.00")
         * )
         * ```
         *
         * The first value must contain the column names (header values).
         */
        private fun prettyPrintResults(values: List<List<String>>): String {
            if (values.isEmpty()) return ""

            val columnWidths = values.first().indices.map { columnIndex ->
                values.maxOf { it[columnIndex].length }
            }

            val separator = columnWidths.joinToString("+") { "-".repeat(it + 2) }.let { "+$it+" }

            fun formatRow(row: List<String>): String {
                return row.mapIndexed { index, cell ->
                    // yes this is confusing, chatgpt wrote this
                    "%-${columnWidths[index]}s".format(cell.replace("-", " "))
                }.joinToString(" | ", prefix = "| ", postfix = " |")
            }

            return buildString {
                appendLine(separator)
                val header = values.first()
                appendLine(formatRow(header))

                appendLine(separator)
                values.drop(1).forEach { row ->
                    appendLine(formatRow(row))
                }
                appendLine(separator)
            }
        }
    }
}
