package org.vaccineimpact.reporting_api.tests

import org.vaccineimpact.api.models.ArtefactFormat
import org.vaccineimpact.api.models.FilePurpose
import org.vaccineimpact.reporting_api.db.Config
import org.vaccineimpact.reporting_api.db.AppConfig
import org.vaccineimpact.reporting_api.db.JooqContext
import org.vaccineimpact.reporting_api.db.Tables.*
import java.io.File
import java.sql.Timestamp
import kotlin.streams.asSequence

data class InsertableChangelog
constructor(val id: String,
            val reportVersion: String,
            val label: String,
            val value: String,
            val fromFile: Boolean,
            val ordering: Int,
            val reportVersionPublic: String? = null)

fun insertReport(name: String,
                 version: String,
                 published: Boolean = true,
                 date: Timestamp = Timestamp(System.currentTimeMillis()),
                 author: String = "author authorson",
                 requester: String = "requester mcfunder",
                 changelog: List<InsertableChangelog> = listOf())
{

    JooqContext().use {

        val displayname = "display name $name"

        //Does the report already exist in the REPORT table?
        val rows = it.dsl.select(REPORT.NAME)
                .from(REPORT)
                .where(REPORT.NAME.eq(name))
                .fetch()

        if (rows.isEmpty())
        {

            val reportRecord = it.dsl.newRecord(REPORT)
                    .apply {
                        this.name = name
                        this.latest = version
                    }
            reportRecord.store()
        }
        else
        {
            //Update latest version of Report
            it.dsl.update(REPORT)
                    .set(REPORT.LATEST, version)
                    .where(REPORT.NAME.eq(name))
                    .execute()
        }


        val reportVersionRecord = it.dsl.newRecord(REPORT_VERSION)
                .apply {
                    this.id = version
                    this.report = name
                    this.date = date
                    this.displayname = displayname
                    this.description = "description $name"
                    this.requester = requester
                    this.author = author
                    this.published = published
                    this.connection = false
                }
        reportVersionRecord.store()

        //Check if we need to add changelog labels
        val labels = it.dsl.select(CHANGELOG_LABEL.ID)
                .from(CHANGELOG_LABEL)
                .fetch()

        if (labels.isEmpty())
        {
             val publicRecord = it.dsl.newRecord(CHANGELOG_LABEL)
                     .apply{
                         this.id = "public"
                         this.public = true
                     }
             publicRecord.store();

            val internalRecord = it.dsl.newRecord(CHANGELOG_LABEL)
                    .apply{
                        this.id = "internal"
                        this.public = false
                    }
            internalRecord.store();
        }

        for (entry in changelog)
        {
            it.dsl.insertInto(CHANGELOG)
                    .set(CHANGELOG.ID, entry.id)
                    .set(CHANGELOG.LABEL, entry.label)
                    .set(CHANGELOG.VALUE, entry.value)
                    .set(CHANGELOG.FROM_FILE, entry.fromFile)
                    .set(CHANGELOG.REPORT_VERSION, entry.reportVersion)
                    .set(CHANGELOG.ORDERING, entry.ordering)
                    .set(CHANGELOG.REPORT_VERSION_PUBLIC, entry.reportVersionPublic)
                    .execute()

        }

    }

}

fun insertArtefact(reportVersionId: String,
                   description: String = "description",
                   format: ArtefactFormat = ArtefactFormat.REPORT,
                   fileNames: List<String>)
{

    JooqContext().use { it ->

        val lastId = it.dsl.select(REPORT_VERSION_ARTEFACT.ID.max())
                .from(REPORT_VERSION_ARTEFACT)
                .fetchAnyInto(Int::class.java)
                ?: 0

        it.dsl.insertInto(REPORT_VERSION_ARTEFACT)
                .set(REPORT_VERSION_ARTEFACT.DESCRIPTION, description)
                .set(REPORT_VERSION_ARTEFACT.FORMAT, format.toString().toLowerCase())
                .set(REPORT_VERSION_ARTEFACT.REPORT_VERSION, reportVersionId)
                .set(REPORT_VERSION_ARTEFACT.ORDER, lastId + 1)
                .set(REPORT_VERSION_ARTEFACT.ID, lastId + 1)
                .execute()

        fileNames.map { f ->
            it.dsl.insertInto(FILE_ARTEFACT)
                    .set(FILE_ARTEFACT.FILENAME, f)
                    .set(FILE_ARTEFACT.ARTEFACT, lastId + 1)
                    .set(FILE_ARTEFACT.FILE_HASH, generateRandomString())
                    .execute()
        }
    }
}

fun insertData(reportVersionId: String,
               name: String,
               query: String ,
               database: String,
               hash: String)
{
    JooqContext().use {
        it.dsl.insertInto(REPORT_VERSION_DATA)
                .set(REPORT_VERSION_DATA.REPORT_VERSION, reportVersionId)
                .set(REPORT_VERSION_DATA.NAME, name)
                .set(REPORT_VERSION_DATA.QUERY, query)
                .set(REPORT_VERSION_DATA.DATABASE, database)
                .set(REPORT_VERSION_DATA.HASH, hash)
                .execute()
    }
}

private fun generateRandomString(len: Long = 10): String
{
    val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    return java.util.Random().ints(len, 0, source.length)
            .asSequence()
            .map(source::get)
            .joinToString("")
}

fun insertFileInput(reportVersion: String, fileName: String, purpose: FilePurpose)
{
    JooqContext().use {
        it.dsl.insertInto(FILE_INPUT)
                .set(FILE_INPUT.FILE_PURPOSE, purpose.toString())
                .set(FILE_INPUT.FILENAME, fileName)
                .set(FILE_INPUT.FILE_HASH, generateRandomString())
                .set(FILE_INPUT.REPORT_VERSION, reportVersion)
                .execute()
    }
}


fun getArchiveFolder(reportName: String, reportVersion: String, config: Config): String
{
    return "${config["orderly.root"]}archive/$reportName/$reportVersion/"
}

fun createArchiveFolder(reportName: String, reportVersion: String, config: Config = AppConfig())
{
    val folderName = getArchiveFolder(reportName, reportVersion, config)
    val folder = File(folderName)
    if (!folder.exists())
    {
        println("creating archive folder $folderName")
        folder.mkdirs()
    }
}

fun deleteArchiveFolder(reportName: String, reportVersion: String, config: Config = AppConfig())
{
    val folderName = getArchiveFolder(reportName, reportVersion, config)
    val folder = File(folderName)
    if (folder.exists())
    {
        val reportFolder = folder.parentFile
        folder.delete()
        if (reportFolder.exists() && reportFolder.list().count() == 0)
        {
            reportFolder.delete()
        }
    }
}