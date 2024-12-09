package com.example.pc

import com.example.DatabaseTest
import org.junit.jupiter.api.Test

/**
 * Tests queries implemented in [PcCompanyExercises]
 */
class PcCompanyExercisesTest : DatabaseTest(
    testDataFilePath = "/testdata/pc.sql",
    tables = listOf(Laptop, PC, Product, Printer),
) {
    private val pcCompanyExercises = PcCompanyExercises()

    /**
     * Run this to make sure everything is set up correctly and works.
     */
    @Test
    fun exercise00() {
        assertQuery(pcCompanyExercises::exercise00) {
            """
                model   ram
                1232    64
                1121    128
                1233    64
                1121    128
                1121    128
                1233    128
                1232    32
                1232    64
                1232    32
                1260    32
                1233    128
                1233    128
            """
        }
    }

    @Test
    fun exercise01() {
        assertQuery(pcCompanyExercises::exercise01) {
            """
                model	speed	hd
                1232	500     10.0
                1232	450	    8.0
                1232	450	    10.0
                1260	500	    10.0
            """
        }
    }

    @Test
    fun exercise02() {
        assertQuery(pcCompanyExercises::exercise02) {
            """
                maker
                A
                D
                E
            """
        }
    }

    @Test
    fun exercise03() {
        assertQuery(pcCompanyExercises::exercise03) {
            """
                model	ram	screen
                1750	128	14
                1298	64	15
                1752	128	14
            """
        }
    }

    @Test
    fun exercise07() {
        assertQuery(pcCompanyExercises::exercise07) {
            """
                model	price
                1121	850.00
                1750	1200.00
            """
        }
    }

    @Test
    fun exercise08() {
        assertQuery(pcCompanyExercises::exercise08) {
            """
                maker
                E
            """
        }
    }

    @Test
    fun exercise10() {
        assertQuery(pcCompanyExercises::exercise10) {
            """
                model	price
                1276	400.00
                1288	400.00
            """
        }
    }

    @Test
    fun exercise15() {
        assertQuery(pcCompanyExercises::exercise15) {
            """
                hd
                5.0
                8.0
                10.0
                14.0
                20.0
            """
        }
    }

    @Test
    fun exercise28() {
        assertQuery(pcCompanyExercises::exercise28) {
            """
                count
                1            
            """
        }
    }
}
