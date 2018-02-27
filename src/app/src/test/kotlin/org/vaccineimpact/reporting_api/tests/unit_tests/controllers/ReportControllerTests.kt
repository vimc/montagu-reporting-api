package org.vaccineimpact.reporting_api.tests.unit_tests.controllers

import com.google.gson.JsonParser
import com.nhaarman.mockito_kotlin.*
import khttp.responses.Response
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import org.vaccineimpact.api.models.Report
import org.vaccineimpact.api.models.Scope
import org.vaccineimpact.api.models.permissions.PermissionSet
import org.vaccineimpact.api.models.permissions.ReifiedPermission
import org.vaccineimpact.reporting_api.ActionContext
import org.vaccineimpact.reporting_api.OrderlyServerAPI
import org.vaccineimpact.reporting_api.ZipClient
import org.vaccineimpact.reporting_api.controllers.ReportController
import org.vaccineimpact.reporting_api.db.Config
import org.vaccineimpact.reporting_api.db.OrderlyClient
import org.vaccineimpact.reporting_api.errors.MissingRequiredPermissionError

class ReportControllerTests : ControllerTest()
{
    private val mockConfig = mock<Config> {
        on { this.get("orderly.root") } doReturn "root/"
    }

    private val reportName = "report1"

    private val permissionSetForSingleReport = PermissionSet(
            setOf(ReifiedPermission("reports.read", Scope.Specific("report", reportName))))

    private val permissionSetGlobal = PermissionSet(
            setOf(ReifiedPermission("reports.read", Scope.Global())))

    @Test
    fun `runs a report`()
    {
        val actionContext = mock<ActionContext> {
            on { this.params(":name") } doReturn reportName
            on { this.permissions } doReturn PermissionSet()
        }

        val mockAPIResponse = mock<Response>() {
            on { this.text } doReturn "okayresponse"
        }

        val apiClient = mock<OrderlyServerAPI>() {
            on { this.post(any(), any()) } doReturn mockAPIResponse
        }

        val sut = ReportController(actionContext, mock<OrderlyClient>(),
                mock<ZipClient>(), apiClient, mockConfig)

        val result = sut.run()

        assertThat(result).isEqualTo("okayresponse")
    }

    @Test
    fun `getReports returns report names user is authorized to see`()
    {
        val reports = listOf(Report(reportName, "test full name 1", "v1", true),
                Report("testname2", "test full name 2", "v1", true))

        val orderly = mock<OrderlyClient> {
            on { this.getAllReports() } doReturn reports
        }

        val mockContext = mock<ActionContext> {
            on { it.permissions } doReturn permissionSetForSingleReport
        }

        val sut = ReportController(mockContext, orderly, mock<ZipClient>(),
                mock<OrderlyServerAPI>(),
                mockConfig)

        val result = sut.getAllNames()
        assertThat(result).hasSize(1)
        assertThat(result[0].name).isEqualTo(reportName)
    }


    @Test
    fun `getReports returns all report names if user has global read permissions`()
    {
        val reports = listOf(Report(reportName, "test full name 1", "v1", true),
                Report("testname2", "test full name 2", "v1", true))

        val orderly = mock<OrderlyClient> {
            on { this.getAllReports() } doReturn reports
        }

        val mockContext = mock<ActionContext> {
            on { it.permissions } doReturn permissionSetGlobal
        }

        val sut = ReportController(mockContext, orderly, mock<ZipClient>(),
                mock<OrderlyServerAPI>(),
                mockConfig)

        val result = sut.getAllNames()
        assertThat(result).hasSameElementsAs(reports)
    }

    @Test
    fun `getByName returns all reports versions by name`()
    {
        val reportVersions = listOf("version1", "version2")

        val orderly = mock<OrderlyClient> {
            on { this.getReportsByName(reportName) } doReturn reportVersions
        }

        val mockContext = mock<ActionContext> {
            on { it.params(":name") } doReturn reportName
            on { it.permissions } doReturn permissionSetGlobal
        }

        val sut = ReportController(mockContext, orderly, mock<ZipClient>(),
                mock<OrderlyServerAPI>(),
                mockConfig)

        assertThat(sut.getVersionsByName()).isEqualTo(reportVersions)
    }

    @Test
    fun `getByName throws MissingRequiredPermissionError if user not authorized to read report`()
    {
        val reportVersions = listOf("version1", "version2")

        val orderly = mock<OrderlyClient> {
            on { this.getReportsByName(reportName) } doReturn reportVersions
        }

        val actionContext = mock<ActionContext> {
            on { this.params(":name") } doReturn "badreportname"
            on { this.permissions } doReturn permissionSetForSingleReport
        }

        val sut = ReportController(actionContext, orderly, mock<ZipClient>(),
                mock<OrderlyServerAPI>(),
                mockConfig)

        assertThrowsMissingPermissionError("badreportname",
                { sut.getVersionsByName() })
    }

    @Test
    fun `getByNameAndVersion returns report metadata`()
    {

        val reportName = "reportName"
        val reportVersion = "reportVersion"

        val report = JsonParser().parse("{\"key\":\"value\"}")

        val orderly = mock<OrderlyClient> {
            on { this.getReportsByNameAndVersion(reportName, reportVersion) } doReturn report.asJsonObject
        }

        val actionContext = mock<ActionContext> {
            on { this.params(":version") } doReturn reportVersion
            on { this.params(":name") } doReturn reportName
            on { this.permissions } doReturn permissionSetGlobal
        }

        val sut = ReportController(actionContext, orderly, mock<ZipClient>(),
                mock<OrderlyServerAPI>(),
                mockConfig)

        assertThat(sut.getByNameAndVersion()).isEqualTo(report)
    }

    @Test
    fun `getByNameAndVersion throws MissingRequiredPermissionError if user not authorized`()
    {
        val reportVersion = "reportVersion"

        val report = JsonParser().parse("{\"key\":\"value\"}")

        val orderly = mock<OrderlyClient> {
            on { this.getReportsByNameAndVersion(reportName, reportVersion) } doReturn report.asJsonObject
        }

        val actionContext = mock<ActionContext> {
            on { this.params(":version") } doReturn reportVersion
            on { this.params(":name") } doReturn "badreportname"
            on { this.permissions } doReturn permissionSetForSingleReport
        }

        val sut = ReportController(actionContext, orderly, mock<ZipClient>(),
                mock<OrderlyServerAPI>(),
                mockConfig)

        assertThrowsMissingPermissionError("badreportname",
                { sut.getByNameAndVersion() })
    }

    @Test
    fun `getZippedByNameAndVersion returns zip file`()
    {

        val reportName = "reportName"
        val reportVersion = "reportVersion"

        val actionContext = mock<ActionContext> {
            on { this.params(":version") } doReturn reportVersion
            on { this.params(":name") } doReturn reportName
            on { this.getSparkResponse() } doReturn mockSparkResponse
            on { this.permissions } doReturn permissionSetGlobal
        }

        val mockZipClient = mock<ZipClient>()

        val sut = ReportController(actionContext, mock<OrderlyClient>(), mockZipClient, mock<OrderlyServerAPI>(),
                mockConfig)

        sut.getZippedByNameAndVersion()

        verify(mockZipClient, times(1)).zipIt("root/archive/$reportName/$reportVersion/"
                , mockOutputStream)
    }

    @Test
    fun `getZippedByNameAndVersion throws MissingRequiredPermission error if user not authorized`()
    {
        val reportVersion = "reportVersion"

        val actionContext = mock<ActionContext> {
            on { this.params(":version") } doReturn reportVersion
            on { this.params(":name") } doReturn "badreportname"
            on { this.getSparkResponse() } doReturn mockSparkResponse
            on { this.permissions } doReturn permissionSetForSingleReport
        }

        val mockZipClient = mock<ZipClient>()

        val sut = ReportController(actionContext, mock<OrderlyClient>(), mockZipClient, mock<OrderlyServerAPI>(),
                mockConfig)

        assertThrowsMissingPermissionError("badreportname",
                { sut.getZippedByNameAndVersion() })
    }

}
