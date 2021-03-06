package org.vaccineimpact.reporting_api.tests.integration_tests.tests

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.vaccineimpact.reporting_api.ContentTypes
import org.vaccineimpact.reporting_api.security.WebTokenHelper
import org.vaccineimpact.reporting_api.tests.insertReport
import org.vaccineimpact.reporting_api.tests.integration_tests.helpers.RequestHelper

class SecurityTests : IntegrationTest()
{

    /**  Bearer token tests */

    @Test
    fun `returns 401 if token missing`()
    {

        val response = RequestHelper().getNoAuth("/reports/")

        Assertions.assertThat(response.headers["content-type"]).isEqualTo("application/json")
        Assertions.assertThat(response.statusCode).isEqualTo(401)
        JSONValidator.validateError(response.text, "bearer-token-invalid",
                "Bearer token not supplied in Authorization header, or bearer token was invalid")

    }

    @Test
    fun `returns 200 if token is present in cookie`()
    {
        val response = RequestHelper().getWithCookie("/reports/")
        println(response.text)
        assertThat(response.statusCode).isEqualTo(200)
    }

    @Test
    fun `returns 401 if token not valid`()
    {

        val response = RequestHelper().getWrongAuth("/reports/")

        Assertions.assertThat(response.headers["content-type"]).isEqualTo("application/json")
        Assertions.assertThat(response.statusCode).isEqualTo(401)
        JSONValidator.validateError(response.text, "bearer-token-invalid",
                "Bearer token not supplied in Authorization header, or bearer token was invalid")

    }

    @Test
    fun `returns 403 if missing permissions`()
    {
        val response = RequestHelper().getWrongPermissions("/reports/")

        Assertions.assertThat(response.headers["content-type"]).isEqualTo("application/json")
        Assertions.assertThat(response.statusCode).isEqualTo(403)
        JSONValidator.validateError(response.text, "forbidden",
                "You do not have sufficient permissions to access this resource. Missing these permissions: */can-login")

    }

    /**  Access token tests */

    @Test
    fun `returns 403 if missing permissions with access token`()
    {
        val url = "/reports/testname/versions/testversion/artefacts/someartefact/"
        val token = requestHelper.generateOnetimeToken(url, fakeReportReader("wrongreport"))

        val response = requestHelper
                .getNoAuth("/reports/testname/versions/testversion/artefacts/someartefact/?access_token=$token",
                        ContentTypes.binarydata)

        Assertions.assertThat(response.headers["content-type"]).isEqualTo("application/json")
        Assertions.assertThat(response.statusCode).isEqualTo(403)
        JSONValidator.validateError(response.text, "forbidden",
                "You do not have sufficient permissions to access this resource." +
                        " Missing these permissions: report:testname/reports.read")
    }

    @Test
    fun `returns 403 if access token url is wrong`()
    {
        insertReport("testname", "testversion")

        val token = requestHelper
                .generateOnetimeToken("/reports/testname/versions/testversion/artefacts/someartefact/")
        val response = requestHelper
                .getNoAuth("/reports/testname/versions/testversion/artefacts/someotherartefact/?access_token=$token",
                        ContentTypes.binarydata)

        Assertions.assertThat(response.headers["content-type"]).isEqualTo("application/json")
        Assertions.assertThat(response.statusCode).isEqualTo(403)
        JSONValidator.validateError(response.text, "forbidden",
                "This token is issued for /v1/reports/testname/versions/testversion/artefacts/someartefact/ but the " +
                        "current request is for /v1/reports/testname/versions/testversion/artefacts/someotherartefact/")

    }

    @Test
    fun `return 401 if invalid access token`()
    {
        insertReport("testname", "testversion")
        val response = requestHelper
                .getNoAuth("/reports/testname/versions/testversion/artefacts/fakeartefact/?access_token=42678iwek",
                        ContentTypes.binarydata)

        Assertions.assertThat(response.headers["content-type"]).isEqualTo("application/json")
        Assertions.assertThat(response.statusCode).isEqualTo(401)
        JSONValidator.validateMultipleAuthErrors(response.text)

    }

    @Test
    fun `returns 401 if access token not in db`()
    {
        insertReport("testname", "testversion")
        val url = "/reports/testname/versions/testversion/artefacts/6943yhks/"
        val token = WebTokenHelper.oneTimeTokenHelper.issuer
                .generateOnetimeActionToken(requestHelper.fakeGlobalReportReader, url)
        val response = requestHelper
                .getNoAuth("$url?access_token=$token", ContentTypes.binarydata)

        Assertions.assertThat(response.headers["content-type"]).isEqualTo("application/json")
        Assertions.assertThat(response.statusCode).isEqualTo(401)
        JSONValidator.validateMultipleAuthErrors(response.text)
    }

    @Test
    fun `returns 401 if missing access token and no auth`()
    {
        insertReport("testname", "testversion")
        val fakeartefact = "hf647rhj"

        val url = "/reports/testname/versions/testversion/artefacts/$fakeartefact"
        val response = requestHelper.getNoAuth("$url/", ContentTypes.binarydata)

        Assertions.assertThat(response.headers["content-type"]).isEqualTo("application/json")
        Assertions.assertThat(response.statusCode).isEqualTo(401)
        JSONValidator.validateMultipleAuthErrors(response.text)
    }


}