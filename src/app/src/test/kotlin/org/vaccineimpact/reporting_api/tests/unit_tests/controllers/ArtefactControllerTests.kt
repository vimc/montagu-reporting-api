package org.vaccineimpact.reporting_api.tests.unit_tests.controllers

import com.google.gson.JsonParser
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.mock
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import org.vaccineimpact.api.models.Scope
import org.vaccineimpact.api.models.permissions.PermissionSet
import org.vaccineimpact.api.models.permissions.ReifiedPermission
import org.vaccineimpact.reporting_api.ActionContext
import org.vaccineimpact.reporting_api.FileSystem
import org.vaccineimpact.reporting_api.controllers.ArtefactController
import org.vaccineimpact.reporting_api.db.Config
import org.vaccineimpact.reporting_api.db.OrderlyClient
import org.vaccineimpact.reporting_api.errors.UnknownObjectError

class ArtefactControllerTests : ControllerTest()
{
    @Test
    fun `gets artefacts for report`()
    {
        val version = "testversion"

        val artefacts = JsonParser().parse("{ \"test.png\" : \"hjkdasjkldas6762i1j\"}")

        val orderly = mock<OrderlyClient> {
            on { this.getArtefacts(reportName, version) } doReturn artefacts.asJsonObject
        }

        val actionContext = mock<ActionContext> {
            on { this.params(":name") } doReturn reportName
            on { this.params(":version") } doReturn version
            on { this.permissions } doReturn permissionSetForSingleReport
        }

        val sut = ArtefactController(actionContext, orderly, mock<FileSystem>(), mockConfig)

        assertThat(sut.get()).isEqualTo(artefacts)
    }

    @Test
    fun `can't get artefact for report if not authorized`()
    {
        val name = "testname"
        val version = "testversion"

        val actionContext = mock<ActionContext> {
            on { this.params(":name") } doReturn name
            on { this.params(":version") } doReturn version
            on { this.permissions } doReturn permissionSetForSingleReport
        }

        val sut = ArtefactController(actionContext, mock(), mock<FileSystem>(), mockConfig)

        assertThrowsMissingPermissionError(name, { sut.get() })
    }

    @Test
    fun `downloads artefact for report`()
    {
        val version = "testversion"
        val artefact = "testartefact"

        val orderly = mock<OrderlyClient> {
            on { this.getArtefact(reportName, version, artefact) } doReturn ""
        }

        val actionContext = mock<ActionContext> {
            on { this.params(":name") } doReturn reportName
            on { this.params(":version") } doReturn version
            on { this.params(":artefact") } doReturn artefact
            on { this.getSparkResponse() } doReturn mockSparkResponse
            on { this.permissions } doReturn permissionSetForSingleReport
        }


        val fileSystem = mock<FileSystem>() {
            on { this.fileExists("root/archive/$reportName/$version/$artefact") } doReturn true
        }

        val sut = ArtefactController(actionContext, orderly, fileSystem, mockConfig)

        sut.download()
    }

    @Test
    fun `can't download artefact for report if unauthorized`()
    {
        val name = "anothername"
        val version = "testversion"
        val artefact = "testartefact"

        val actionContext = mock<ActionContext> {
            on { this.params(":name") } doReturn name
            on { this.params(":version") } doReturn version
            on { this.params(":artefact") } doReturn artefact
            on { this.getSparkResponse() } doReturn mockSparkResponse
            on { this.permissions } doReturn permissionSetForSingleReport
        }


        val fileSystem = mock<FileSystem>() {
            on { this.fileExists("root/archive/$name/$version/$artefact") } doReturn true
        }

        val sut = ArtefactController(actionContext, mock(), fileSystem, mockConfig)

        assertThrowsMissingPermissionError(name, { sut.download() })
    }

    @Test
    fun `throws unknown object error if artefact does not exist for report`()
    {
        val name = "testname"
        val version = "testversion"
        val artefact = "test.png"

        val orderly = mock<OrderlyClient> {
            on { this.getArtefact(name, version, artefact) } doThrow UnknownObjectError("", "")
        }

        val actionContext = mock<ActionContext> {
            on { this.params(":name") } doReturn name
            on { this.params(":version") } doReturn version
            on { this.params(":artefact") } doReturn artefact
            on { this.permissions } doReturn permissionSetGlobal
        }

        val sut = ArtefactController(actionContext, orderly, mock<FileSystem>(), mockConfig)

        assertThatThrownBy { sut.download() }
                .isInstanceOf(UnknownObjectError::class.java)
    }

}