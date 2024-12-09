package com.example.pc

import org.jetbrains.exposed.sql.Table

/**
 * The database schema consists of four tables:
 *
 * * Product(maker, model, type)
 * * PC(code, model, speed, ram, hd, cd, price)
 * * Laptop(code, model, speed, ram, hd, screen, price)
 * * Printer(code, model, color, type, price)
 *
 * The `Product` table contains data on the `maker`, `model` number, and `type` of product ('PC', 'Laptop', or 'Printer').
 * It is assumed that `model` numbers in the `Product` table are unique for all makers and product types.
 *
 * Each personal computer in the `PC` table is unambiguously identified by a unique code, and is additionally
 * characterized by its `model` (foreign key referring to the `Product` table), processor speed (in MHz) – `speed`,
 * RAM capacity (in Mb) - `ram`, hard disk drive capacity (in Gb) – `hd`, CD-ROM speed (e.g, '4x') - `cd`,
 * and its `price`.
 *
 * The `Laptop` table is similar to the `PC` table, except that instead of the CD-ROM speed, it contains the screen size
 * (in inches) – `screen`.
 *
 * For each printer model in the `Printer` table, its output type (‘y’ for color and ‘n’ for monochrome) – `color` field,
 * printing technology ('Laser', 'Jet', or 'Matrix') – `type`, and `price` are specified.
 */
class PcCompanySchema

// ----------------------------------------------------

object Product : Table("Product") {

    val maker = varchar("maker", 10)
    val model = varchar("model", 50) // Model numbers are unique for all makers and product types
    val type = varchar("type", 50) // Possible values: `PC`, `Laptop`, or `Printer`

    override val primaryKey = PrimaryKey(model)
}

object PC : Table("PC") {

    val code = integer("code")
    val model = reference("model", Product.model)
    val speed = short("speed") // in MHz
    val ram = short("ram") // in Mb
    val hd = float("hd") // in Gb
    val cd = varchar("cd", 10) // CD-ROM speed (e.g '4x')
    val price = decimal("price", 12, 2).nullable()

    override val primaryKey = PrimaryKey(code)
}

object Laptop : Table("Laptop") {

    val code = integer("code")
    val model = reference("model", Product.model)
    val speed = short("speed") // in MHz
    val ram = short("ram") // in Mb
    val hd = float("hd") // in Gb
    val price = decimal("price", 12, 2).nullable()
    val screen = short("screen") // in inches

    override val primaryKey = PrimaryKey(code)
}

object Printer : Table("Printer") {

    val code = integer("code")
    val model = reference("model", Product.model)
    val color = char("color") // ‘y’ for color and ‘n’ for monochrome
    val type = varchar("type", 10) // Possible values: 'Laser', 'Jet', or 'Matrix'
    val price = decimal("price", 12, 2).nullable()

    override val primaryKey = PrimaryKey(code)
}
