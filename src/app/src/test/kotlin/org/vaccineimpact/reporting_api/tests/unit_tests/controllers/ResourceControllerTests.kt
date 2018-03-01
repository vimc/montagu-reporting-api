package org.vaccineimpact.reporting_api.tests.unit_tests.controllers

import com.google.gson.JsonParser
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.mock
import org.assertj.core.api.Assertions
import org.junit.Test
import org.vaccineimpact.reporting_api.ActionContext
import org.vaccineimpact.reporting_api.FileSystem
import org.vaccineimpact.reporting_api.controllers.ResourceController
import org.vaccineimpact.reporting_api.db.Config
import org.vaccineimpact.reporting_api.db.OrderlyClient
import org.vaccineimpact.reporting_api.errors.OrderlyFileNotFoundError
import org.vaccineimpact.reporting_api.errors.UnknownObjectError

class ResourceControllerTests : ControllerTest()
{
    @Test
    fun `gets resources for report`()
    {
        val version = "testversion"

        val resources = JsonParser().parse("{ \"test\" : \"hjkdasjkldas6762i1j\"}")

        val orderly = mock<OrderlyClient> {
            on { this.getResources(reportName, version) } doReturn resources.asJsonObject
        }

        val actionContext = mock<ActionContext> {
            on { this.params(":name") } doReturn reportName
            on { this.params(":version") } doReturn version
            on { this.permissions } doReturn permissionSetForSingleReport
        }

        val sut = ResourceController(actionContext,
                orderly, mock<FileSystem>(),
                mockConfig)

        Assertions.assertThat(sut.get()).isEqualTo(resources)
    }

    @Test
    fun `can't get resources for unauthorized report`()
    {
        val name = "name"

        val actionContext = mock<ActionContext> {
            on { this.params(":name") } doReturn name
            on { this.permissions } doReturn permissionSetForSingleReport
        }

        val sut = ResourceController(actionContext,
                mock(), mock<FileSystem>(),
                mockConfig)

        assertThrowsMissingPermissionError(name, { sut.get() })
    }

    @Test
    fun `downloads resource for report`()
    {
        val version = "testversion"
        val resource = "testresource"

        val orderly = mock<OrderlyClient> {
            on { this.getResource(reportName, version, resource) } doReturn ""
        }

        val actionContext = mock<ActionContext> {
            on { this.params(":name") } doReturn reportName
            on { this.params(":version") } doReturn version
            on { this.params(":resource") } doReturn resource
            on { this.getSparkResponse() } doReturn mockSparkResponse
            on { this.permissions } doReturn permissionSetForSingleReport
        }

        val fileSystem = mock<FileSystem>() {
            on { this.fileExists("root/archive/$reportName/$version/$resource") } doReturn true
        }

        val sut = ResourceController(actionContext, orderly, fileSystem, mockConfig)

        sut.download()
    }

    @Test
    fun `can't download resource for unauthorized report`()
    {
        val name = "testname"

        val actionContext = mock<ActionContext> {
            on { this.params(":name") } doReturn name
            on { this.permissions } doReturn permissionSetForSingleReport
        }

        val sut = ResourceController(actionContext, mock(), mock(), mockConfig)

        assertThrowsMissingPermissionError(name, { sut.download() })
    }

    @Test
    fun `throws unknown object error if artefact does not exist for report`()
    {
        val name = "testname"
        val version = "testversion"
        val resource = "testresource"

        val orderly = mock<OrderlyClient> {
            on { this.getResource(name, version, resource) } doThrow UnknownObjectError("", "")
        }

        val actionContext = mock<ActionContext> {
            on { this.params(":name") } doReturn name
            on { this.params(":version") } doReturn version
            on { this.params(":resource") } doReturn resource
            on { this.permissions } doReturn permissionSetGlobal
        }

        val sut = ResourceController(actionContext, orderly, mock<FileSystem>(), mockConfig)

        Assertions.assertThatThrownBy { sut.download() }
                .isInstanceOf(UnknownObjectError::class.java)
    }

    @Test
    fun `throws file not found error if file does not exist for report`()
    {
        val name = "testname"
        val version = "testversion"
        val resource = "testresource"

        val orderly = mock<OrderlyClient> {
            on { this.getResource(name, version, resource) } doReturn ""
        }

        val actionContext = mock<ActionContext> {
            on { this.params(":name") } doReturn name
            on { this.params(":version") } doReturn version
            on { this.params(":resource") } doReturn resource
            on { this.getSparkResponse() } doReturn mockSparkResponse
            on { this.permissions } doReturn permissionSetGlobal
        }

        val fileSystem = mock<FileSystem>() {
            on { this.fileExists("root/archive/$name/$version/$resource") } doReturn false
        }

        val sut = ResourceController(actionContext, orderly, fileSystem, mockConfig)

        Assertions.assertThatThrownBy { sut.download() }
                .isInstanceOf(OrderlyFileNotFoundError::class.java)
    }


}