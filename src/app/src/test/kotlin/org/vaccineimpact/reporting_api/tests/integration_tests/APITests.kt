package org.vaccineimpact.reporting_api.tests.integration_tests

import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.vaccineimpact.reporting_api.app_start.main
import org.vaccineimpact.reporting_api.tests.integration_tests.helpers.TestTokenGenerator
import org.vaccineimpact.reporting_api.tests.integration_tests.tests.*
import spark.Spark

@RunWith(Suite::class)
@Suite.SuiteClasses(ArtefactTests::class,
        ReportTests::class,
        ResourceTests::class,
        DataTests::class,
        SecurityTests::class)
class APITests
{
    companion object
    {
        // Use a single TestTokenGenerator for the whole suite. This
        // ensures that the same keypair is used throughout.
        val tokenHelper = TestTokenGenerator()

        @BeforeClass @JvmStatic
        fun startApp()
        {
            appStarted = true
            main(emptyArray())
        }

        @AfterClass @JvmStatic
        fun stopApp()
        {

            Spark.stop()
        }

        @JvmStatic
        var appStarted = false
    }
}