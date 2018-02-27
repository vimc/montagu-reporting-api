package org.vaccineimpact.reporting_api.tests.unit_tests.controllers

import com.google.gson.JsonParser
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import org.vaccineimpact.reporting_api.ActionContext
import org.vaccineimpact.reporting_api.FileSystem
import org.vaccineimpact.reporting_api.controllers.DataController
import org.vaccineimpact.reporting_api.db.Config
import org.vaccineimpact.reporting_api.db.OrderlyClient
import org.vaccineimpact.reporting_api.errors.OrderlyFileNotFoundError

class DataControllerTests : ControllerTest()
{

    @Test
    fun `gets data for report`()
    {
        val name = "testname"
        val version = "testversion"

        val data = JsonParser().parse("{ \"test.csv\" : \"hjkdasjkldas6762i1j\"}")

        val orderly = mock<OrderlyClient> {
            on { this.getData(name, version) } doReturn data.asJsonObject
        }

        val actionContext = mock<ActionContext> {
            on { this.params(":name") } doReturn name
            on { this.params(":version") } doReturn version
            on { this.permissions } doReturn permissionSetGlobal
        }

        val sut = DataController(actionContext, orderly, mock<FileSystem>(), mockConfig)

        assertThat(sut.get()).isEqualTo(data)
    }

    @Test
    fun `can't get data for unauthorized report`()
    {
        val name = "testname"

        val actionContext = mock<ActionContext> {
            on { this.params(":name") } doReturn name
            on { this.permissions } doReturn permissionSetForSingleReport
        }

        val sut = DataController(actionContext, mock(), mock<FileSystem>(), mockConfig)

        assertThrowsMissingPermissionError(name, { sut.get() })
    }

    @Test
    fun `gets csv file for report by name if type not specified`()
    {

        val name = "testname"
        val version = "testversion"
        val datumname = "testname"
        val hash = "hjkdasjkldas6762i1j"

        val orderly = mock<OrderlyClient> {
            on { this.getDatum(name, version, datumname) } doReturn hash
        }

        val fileSystem = mock<FileSystem>() {
            on { this.fileExists("root/data/csv/$hash.csv") } doReturn true
        }

        val actionContext = mock<ActionContext> {
            on { this.params(":name") } doReturn name
            on { this.params(":version") } doReturn version
            on { this.params(":data") } doReturn datumname
            on { this.getSparkResponse() } doReturn mockSparkResponse
            on { this.permissions } doReturn permissionSetGlobal
        }

        val sut = DataController(actionContext, orderly, fileSystem, mockConfig)
        sut.downloadData()

        verify(fileSystem, times(1)).writeFileToOutputStream("root/data/csv/$hash.csv", mockOutputStream)
    }

    @Test
    fun `can't download data for unauthorized report`()
    {
        val name = "testname"

        val actionContext = mock<ActionContext> {
            on { this.params(":name") } doReturn name
            on { this.permissions } doReturn permissionSetForSingleReport
        }

        val sut = DataController(actionContext, mock(), mock(), mockConfig)

        assertThrowsMissingPermissionError(name, { sut.downloadData() })
    }


    @Test
    fun `gets rds data file for report by name`()
    {

        val name = "testname"
        val version = "testversion"
        val datumname = "testname"
        val hash = "hjkdasjkldas6762i1j"

        val orderly = mock<OrderlyClient> {
            on { this.getDatum(name, version, datumname) } doReturn hash
        }

        val fileSystem = mock<FileSystem>() {
            on { this.fileExists("root/data/rds/$hash.rds") } doReturn true
        }

        val actionContext = mock<ActionContext> {
            on { this.params(":name") } doReturn name
            on { this.params(":version") } doReturn version
            on { this.params(":data") } doReturn datumname
            on { this.getSparkResponse() } doReturn mockSparkResponse
            on { this.permissions } doReturn permissionSetGlobal
            on { this.queryParams("type") } doReturn "rds"
        }

        val sut = DataController(actionContext, orderly, fileSystem, mockConfig)
        sut.downloadData()

        verify(fileSystem, times(1)).writeFileToOutputStream("root/data/rds/$hash.rds", mockOutputStream)
    }

    @Test
    fun `gets csv file by id`()
    {

        val hash = "hjkdasjkldas6762i1j"

        val fileSystem = mock<FileSystem>() {
            on { this.fileExists("root/data/csv/$hash.csv") } doReturn true
        }

        val actionContext = mock<ActionContext> {
            on { this.getSparkResponse() } doReturn mockSparkResponse
            on { this.permissions } doReturn permissionSetGlobal
            on { this.params(":id") } doReturn hash
        }

        val sut = DataController(actionContext, mock<OrderlyClient>(), fileSystem, mockConfig)
        sut.downloadCSV()

        verify(fileSystem, times(1)).writeFileToOutputStream("root/data/csv/$hash.csv", mockOutputStream)
    }

// TODO
//    @Test
//    fun `can't download csv for unauthorized report`()
//    {
//        val hash = "hjkdasjkldas6762i1j"
//
//        val actionContext = mock<ActionContext> {
//            on { this.getSparkResponse() } doReturn mockSparkResponse
//            on { this.permissions } doReturn permissionSetForSingleReport
//            on { this.params(":id") } doReturn hash
//        }
//
//        val sut = DataController(actionContext, mock<OrderlyClient>(), mock(), mockConfig)
//        assertThrowsMissingPermissionError(reportName, { sut.downloadCSV() })
//    }

    @Test
    fun `gets rds file by id`()
    {

        val hash = "hjkdasjkldas6762i1j"

        val fileSystem = mock<FileSystem>() {
            on { this.fileExists("root/data/rds/$hash.rds") } doReturn true
        }

        val actionContext = mock<ActionContext> {
            on { this.getSparkResponse() } doReturn mockSparkResponse
            on { this.permissions } doReturn permissionSetGlobal
            on { this.params(":id") } doReturn hash
        }

        val sut = DataController(actionContext, mock<OrderlyClient>(), fileSystem, mockConfig)
        sut.downloadRDS()

        verify(fileSystem, times(1)).writeFileToOutputStream("root/data/rds/$hash.rds", mockOutputStream)
    }

//    TODO
//    @Test
//    fun `can't download rds for unauthorized report`()
//    {
//        val hash = "hjkdasjkldas6762i1j"
//
//        val actionContext = mock<ActionContext> {
//            on { this.getSparkResponse() } doReturn mockSparkResponse
//            on { this.permissions } doReturn permissionSetForSingleReport
//            on { this.params(":id") } doReturn hash
//        }
//
//        val sut = DataController(actionContext, mock<OrderlyClient>(), mock(), mockConfig)
//        assertThrowsMissingPermissionError(reportName, { sut.downloadRDS() })
//    }

    @Test
    fun `throws unknown object error if data does not exist for report`()
    {
        val name = "testname"
        val version = "testversion"

        val data = JsonParser().parse("{ \"test.csv\" : \"hjkdasjkldas6762i1j\"}")

        val orderly = mock<OrderlyClient> {
            on { this.getData(name, version) } doReturn data.asJsonObject
        }

        val actionContext = mock<ActionContext> {
            on { this.params(":name") } doReturn name
            on { this.params(":version") } doReturn version
            on { this.permissions } doReturn permissionSetGlobal
        }

        val sut = DataController(actionContext, orderly, mock<FileSystem>(), mockConfig)

        assertThat(sut.get()).isEqualTo(data)
    }

    @Test
    fun `throws file not found error if file does not exist for report`()
    {
        val name = "testname"
        val version = "testversion"
        val data = "testdata"

        val orderly = mock<OrderlyClient> {
            on { this.getDatum(name, version, data) } doReturn "64387yhfdjsbc"
        }

        val actionContext = mock<ActionContext> {
            on { this.params(":name") } doReturn name
            on { this.params(":version") } doReturn version
            on { this.params(":data") } doReturn data
            on { this.queryParams("type") } doReturn "csv"
            on { this.getSparkResponse() } doReturn mockSparkResponse
            on { this.permissions } doReturn permissionSetGlobal
        }

        val sut = DataController(actionContext, orderly, mock<FileSystem>(), mockConfig)

        assertThatThrownBy { sut.downloadData() }
                .isInstanceOf(OrderlyFileNotFoundError::class.java)
    }

}