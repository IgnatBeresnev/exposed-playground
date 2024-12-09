package com.example.pc

import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.*

/**
 * You can find the description of the schema in [PcCompanySchema]
 *
 * You can find vanilla SQL solutions in `resources/sql-solutions/pc`,
 * they might give you an idea of how to implement it with Exposed.
 *
 * Run the tests to find out if you got it right!
 */
class PcCompanyExercises {

    /**
     * Not actually an exercise, but a simple starter example and a sanity check
     * that everything is set up correctly and works.
     */
    fun exercise00(): Query {
        return PC.select(PC.model, PC.ram)
    }

    /**
     * Find the model number, speed and hard drive capacity for all the PCs with prices below $500.
     *
     * Result set: model, speed, hd
     */
    fun exercise01(): Query {
        return TODO()
    }

    /**
     * List all printer makers.
     *
     * Result set: maker
     */
    fun exercise02(): Query {
        return TODO()
    }

    /**
     * Find the model number, RAM and screen size of the laptops with prices over $1000.
     *
     * Result set: model, ram, screen
     */
    fun exercise03(): Query {
        return TODO()
    }

    /**
     * Get the models and prices for all commercially available products (of any type) produced by maker B.
     *
     * Result set: model, price
     */
    fun exercise07(): Query {
        return TODO()
    }

    /**
     * Find the makers producing PCs but not laptops
     *
     * Result set: maker
     */
    fun exercise08(): Query {
        return TODO()
    }

    /**
     * Find the printer models having the highest price
     *
     * Result set: model, price
     */
    fun exercise10(): Query {
        return TODO()
    }

    /**
     * Get hard drive capacities that are identical for two or more PCs.
     *
     * Result set: hd
     */
    fun exercise15(): Query {
        return TODO()
    }

    /**
     * Using Product table, find out the number of makers who produce only one model.
     *
     * Result set: one number labeled as "count"
     */
    fun exercise28(): Query {
        return TODO()
    }
}
