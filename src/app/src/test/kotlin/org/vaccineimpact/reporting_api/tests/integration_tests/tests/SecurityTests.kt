package org.vaccineimpact.reporting_api.tests.integration_tests.tests

import org.assertj.core.api.Assertions
import org.junit.Test
import org.vaccineimpact.reporting_api.ContentTypes
import org.vaccineimpact.reporting_api.tests.insertReport
import org.vaccineimpact.reporting_api.tests.integration_tests.helpers.RequestHelper

class SecurityTests : IntegrationTest()
{

    @Test
    fun `returns 401 if token missing`()
    {

        val response = RequestHelper().getNoAuth("/reports")

        Assertions.assertThat(response.headers["content-type"]).isEqualTo("application/json")
        Assertions.assertThat(response.statusCode).isEqualTo(401)
        JSONValidator.validateError(response.text, "bearer-token-invalid",
                "Bearer token not supplied in Authorization header, or bearer token was invalid")

    }

    @Test
    fun `returns 401 if token not valid`()
    {

        val response = RequestHelper().getWrongAuth("/reports")

        Assertions.assertThat(response.headers["content-type"]).isEqualTo("application/json")
        Assertions.assertThat(response.statusCode).isEqualTo(401)
        JSONValidator.validateError(response.text, "bearer-token-invalid",
                "Bearer token not supplied in Authorization header, or bearer token was invalid")

    }

    @Test
    fun `returns 403 if missing permissions`()
    {
        val response = RequestHelper().getWrongPermissions("/reports")

        Assertions.assertThat(response.headers["content-type"]).isEqualTo("application/json")
        Assertions.assertThat(response.statusCode).isEqualTo(403)
        JSONValidator.validateError(response.text, "forbidden",
                "You do not have sufficient permissions to access this resource. Missing these permissions: */can-login")

    }

    // TODO: We can't test this until there is an endpoint that requires more permissions than the user has.
    // This should be fixed as part of the work on VIMC-410
//    @Test
//    fun `returns 403 if missing permissions with access token`()
//    {
//        val response = RequestHelper().getWrongPermissionsWithAccessToken("/reports/testname/versions/testversion/artefacts/someartefact/", ContentTypes.binarydata)
//
//        Assertions.assertThat(response.headers["content-type"]).isEqualTo("application/json")
//        Assertions.assertThat(response.statusCode).isEqualTo(403)
//        JSONValidator.validateError(response.text, "forbidden",
//                "You do not have sufficient permissions to access this resource. Missing these permissions: */reports.read")
//    }

    @Test
    fun `returns 403 if access token url is wrong`()
    {
        insertReport("testname", "testversion")

        val token = requestHelper.generateOnetimeToken("/reports/testname/versions/testversion/artefacts/someartefact/")
        val response = RequestHelper().getNoAuth("/reports/testname/versions/testversion/artefacts/someotherartefact/?access_token=$token", ContentTypes.binarydata)

        Assertions.assertThat(response.headers["content-type"]).isEqualTo("application/json")
        Assertions.assertThat(response.statusCode).isEqualTo(403)
        JSONValidator.validateError(response.text, "forbidden",
                "This token is issued for /v1/reports/testname/versions/testversion/artefacts/someartefact/ but the current request is for /v1/reports/testname/versions/testversion/artefacts/someotherartefact/")

    }


}