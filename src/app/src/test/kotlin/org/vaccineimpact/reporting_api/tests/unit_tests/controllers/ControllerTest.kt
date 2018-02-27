package org.vaccineimpact.reporting_api.tests.unit_tests.controllers

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.assertj.core.api.Assertions
import org.vaccineimpact.api.models.Scope
import org.vaccineimpact.api.models.permissions.PermissionSet
import org.vaccineimpact.api.models.permissions.ReifiedPermission
import org.vaccineimpact.reporting_api.db.Config
import org.vaccineimpact.reporting_api.errors.MissingRequiredPermissionError
import org.vaccineimpact.reporting_api.test_helpers.MontaguTests
import spark.Response
import javax.servlet.ServletOutputStream
import javax.servlet.http.HttpServletResponse

abstract class ControllerTest : MontaguTests()
{
    protected val mockOutputStream = mock<ServletOutputStream>()

    protected val servletResponse = mock<HttpServletResponse>() {
        on { this.outputStream } doReturn mockOutputStream
        on { this.contentType } doReturn ""
    }

    protected val mockSparkResponse = mock<Response>() {
        on { this.raw() } doReturn servletResponse
    }

    protected val mockConfig = mock<Config> {
        on { this.get("orderly.root") } doReturn "root/"
    }

    protected fun assertThrowsMissingPermissionError(reportName: String, work: () -> Any)
    {
        Assertions.assertThatThrownBy { work() }
                .isInstanceOf(MissingRequiredPermissionError::class.java)
                .hasMessageContaining("report:$reportName/reports.read")
    }

    protected val reportName = "report1"

    protected val permissionSetForSingleReport = PermissionSet(
            setOf(ReifiedPermission("reports.read", Scope.Specific("report", reportName))))

    protected val permissionSetGlobal = PermissionSet(
            setOf(ReifiedPermission("reports.read", Scope.Global())))

}