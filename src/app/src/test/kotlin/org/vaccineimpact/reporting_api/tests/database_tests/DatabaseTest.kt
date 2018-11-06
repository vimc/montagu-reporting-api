package org.vaccineimpact.reporting_api.tests.database_tests

import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.vaccineimpact.reporting_api.db.AppConfig
import org.vaccineimpact.reporting_api.db.JooqContext
import org.vaccineimpact.reporting_api.db.Tables.ORDERLY
import org.vaccineimpact.reporting_api.db.Tables.REPORT
import org.vaccineimpact.reporting_api.db.Tables.REPORT_VERSION
import org.vaccineimpact.reporting_api.test_helpers.MontaguTests
import java.io.File

abstract class DatabaseTests : MontaguTests()
{
    companion object
    {

        @BeforeClass
        @JvmStatic
        fun createDatabase()
        {
            println("Looking for sqlite database at path: ${AppConfig()["db.template"]}")
            println("Working directory: ${System.getProperty("user.dir")}")

            val newDbFile = File(AppConfig()["db.location"])
            val source = File(AppConfig()["db.template"])

            source.copyTo(newDbFile, true)
        }

        @AfterClass
        @JvmStatic
        fun dropDatabase()
        {
            File(AppConfig()["db.location"]).delete()
        }

    }

    @Before
    fun clearDatabase()
    {
        JooqContext().use {
            it.dsl.deleteFrom(ORDERLY)
                    .execute()

            it.dsl.deleteFrom(REPORT)
                    .execute()

            it.dsl.deleteFrom(REPORT_VERSION)
                    .execute()
        }
    }

}