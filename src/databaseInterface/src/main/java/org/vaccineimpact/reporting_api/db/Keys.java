/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.reporting_api.db;


import javax.annotation.Generated;

import org.jooq.ForeignKey;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;
import org.vaccineimpact.reporting_api.db.tables.ArtefactFormat;
import org.vaccineimpact.reporting_api.db.tables.Changelog;
import org.vaccineimpact.reporting_api.db.tables.ChangelogLabel;
import org.vaccineimpact.reporting_api.db.tables.CustomFields;
import org.vaccineimpact.reporting_api.db.tables.Data;
import org.vaccineimpact.reporting_api.db.tables.Depends;
import org.vaccineimpact.reporting_api.db.tables.File;
import org.vaccineimpact.reporting_api.db.tables.FileArtefact;
import org.vaccineimpact.reporting_api.db.tables.FileInput;
import org.vaccineimpact.reporting_api.db.tables.FilePurpose;
import org.vaccineimpact.reporting_api.db.tables.OrderlySchema;
import org.vaccineimpact.reporting_api.db.tables.OrderlySchemaTables;
import org.vaccineimpact.reporting_api.db.tables.Parameters;
import org.vaccineimpact.reporting_api.db.tables.ParametersType;
import org.vaccineimpact.reporting_api.db.tables.Report;
import org.vaccineimpact.reporting_api.db.tables.ReportVersion;
import org.vaccineimpact.reporting_api.db.tables.ReportVersionArtefact;
import org.vaccineimpact.reporting_api.db.tables.ReportVersionCustomFields;
import org.vaccineimpact.reporting_api.db.tables.ReportVersionData;
import org.vaccineimpact.reporting_api.db.tables.ReportVersionPackage;
import org.vaccineimpact.reporting_api.db.tables.ReportVersionView;
import org.vaccineimpact.reporting_api.db.tables.records.ArtefactFormatRecord;
import org.vaccineimpact.reporting_api.db.tables.records.ChangelogLabelRecord;
import org.vaccineimpact.reporting_api.db.tables.records.ChangelogRecord;
import org.vaccineimpact.reporting_api.db.tables.records.CustomFieldsRecord;
import org.vaccineimpact.reporting_api.db.tables.records.DataRecord;
import org.vaccineimpact.reporting_api.db.tables.records.DependsRecord;
import org.vaccineimpact.reporting_api.db.tables.records.FileArtefactRecord;
import org.vaccineimpact.reporting_api.db.tables.records.FileInputRecord;
import org.vaccineimpact.reporting_api.db.tables.records.FilePurposeRecord;
import org.vaccineimpact.reporting_api.db.tables.records.FileRecord;
import org.vaccineimpact.reporting_api.db.tables.records.OrderlySchemaRecord;
import org.vaccineimpact.reporting_api.db.tables.records.OrderlySchemaTablesRecord;
import org.vaccineimpact.reporting_api.db.tables.records.ParametersRecord;
import org.vaccineimpact.reporting_api.db.tables.records.ParametersTypeRecord;
import org.vaccineimpact.reporting_api.db.tables.records.ReportRecord;
import org.vaccineimpact.reporting_api.db.tables.records.ReportVersionArtefactRecord;
import org.vaccineimpact.reporting_api.db.tables.records.ReportVersionCustomFieldsRecord;
import org.vaccineimpact.reporting_api.db.tables.records.ReportVersionDataRecord;
import org.vaccineimpact.reporting_api.db.tables.records.ReportVersionPackageRecord;
import org.vaccineimpact.reporting_api.db.tables.records.ReportVersionRecord;
import org.vaccineimpact.reporting_api.db.tables.records.ReportVersionViewRecord;


/**
 * A class modelling foreign key relationships between tables of the <code></code> 
 * schema
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ArtefactFormatRecord> PK_ARTEFACT_FORMAT = UniqueKeys0.PK_ARTEFACT_FORMAT;
    public static final UniqueKey<ChangelogRecord> PK_CHANGELOG = UniqueKeys0.PK_CHANGELOG;
    public static final UniqueKey<ChangelogLabelRecord> PK_CHANGELOG_LABEL = UniqueKeys0.PK_CHANGELOG_LABEL;
    public static final UniqueKey<CustomFieldsRecord> PK_CUSTOM_FIELDS = UniqueKeys0.PK_CUSTOM_FIELDS;
    public static final UniqueKey<DataRecord> PK_DATA = UniqueKeys0.PK_DATA;
    public static final UniqueKey<DependsRecord> PK_DEPENDS = UniqueKeys0.PK_DEPENDS;
    public static final UniqueKey<FileRecord> PK_FILE = UniqueKeys0.PK_FILE;
    public static final UniqueKey<FileArtefactRecord> PK_FILE_ARTEFACT = UniqueKeys0.PK_FILE_ARTEFACT;
    public static final UniqueKey<FileInputRecord> PK_FILE_INPUT = UniqueKeys0.PK_FILE_INPUT;
    public static final UniqueKey<FilePurposeRecord> PK_FILE_PURPOSE = UniqueKeys0.PK_FILE_PURPOSE;
    public static final UniqueKey<OrderlySchemaRecord> PK_ORDERLY_SCHEMA = UniqueKeys0.PK_ORDERLY_SCHEMA;
    public static final UniqueKey<OrderlySchemaTablesRecord> PK_ORDERLY_SCHEMA_TABLES = UniqueKeys0.PK_ORDERLY_SCHEMA_TABLES;
    public static final UniqueKey<ParametersRecord> PK_PARAMETERS = UniqueKeys0.PK_PARAMETERS;
    public static final UniqueKey<ParametersTypeRecord> PK_PARAMETERS_TYPE = UniqueKeys0.PK_PARAMETERS_TYPE;
    public static final UniqueKey<ReportRecord> PK_REPORT = UniqueKeys0.PK_REPORT;
    public static final UniqueKey<ReportVersionRecord> PK_REPORT_VERSION = UniqueKeys0.PK_REPORT_VERSION;
    public static final UniqueKey<ReportVersionArtefactRecord> PK_REPORT_VERSION_ARTEFACT = UniqueKeys0.PK_REPORT_VERSION_ARTEFACT;
    public static final UniqueKey<ReportVersionCustomFieldsRecord> PK_REPORT_VERSION_CUSTOM_FIELDS = UniqueKeys0.PK_REPORT_VERSION_CUSTOM_FIELDS;
    public static final UniqueKey<ReportVersionDataRecord> PK_REPORT_VERSION_DATA = UniqueKeys0.PK_REPORT_VERSION_DATA;
    public static final UniqueKey<ReportVersionPackageRecord> PK_REPORT_VERSION_PACKAGE = UniqueKeys0.PK_REPORT_VERSION_PACKAGE;
    public static final UniqueKey<ReportVersionViewRecord> PK_REPORT_VERSION_VIEW = UniqueKeys0.PK_REPORT_VERSION_VIEW;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<ChangelogRecord, ReportVersionRecord> FK_CHANGELOG_REPORT_VERSION_2 = ForeignKeys0.FK_CHANGELOG_REPORT_VERSION_2;
    public static final ForeignKey<ChangelogRecord, ReportVersionRecord> FK_CHANGELOG_REPORT_VERSION_1 = ForeignKeys0.FK_CHANGELOG_REPORT_VERSION_1;
    public static final ForeignKey<ChangelogRecord, ChangelogLabelRecord> FK_CHANGELOG_CHANGELOG_LABEL_1 = ForeignKeys0.FK_CHANGELOG_CHANGELOG_LABEL_1;
    public static final ForeignKey<DependsRecord, ReportVersionRecord> FK_DEPENDS_REPORT_VERSION_1 = ForeignKeys0.FK_DEPENDS_REPORT_VERSION_1;
    public static final ForeignKey<DependsRecord, FileArtefactRecord> FK_DEPENDS_FILE_ARTEFACT_1 = ForeignKeys0.FK_DEPENDS_FILE_ARTEFACT_1;
    public static final ForeignKey<FileArtefactRecord, ReportVersionArtefactRecord> FK_FILE_ARTEFACT_REPORT_VERSION_ARTEFACT_1 = ForeignKeys0.FK_FILE_ARTEFACT_REPORT_VERSION_ARTEFACT_1;
    public static final ForeignKey<FileArtefactRecord, FileRecord> FK_FILE_ARTEFACT_FILE_1 = ForeignKeys0.FK_FILE_ARTEFACT_FILE_1;
    public static final ForeignKey<FileInputRecord, ReportVersionRecord> FK_FILE_INPUT_REPORT_VERSION_1 = ForeignKeys0.FK_FILE_INPUT_REPORT_VERSION_1;
    public static final ForeignKey<FileInputRecord, FileRecord> FK_FILE_INPUT_FILE_1 = ForeignKeys0.FK_FILE_INPUT_FILE_1;
    public static final ForeignKey<FileInputRecord, FilePurposeRecord> FK_FILE_INPUT_FILE_PURPOSE_1 = ForeignKeys0.FK_FILE_INPUT_FILE_PURPOSE_1;
    public static final ForeignKey<ParametersRecord, ReportVersionRecord> FK_PARAMETERS_REPORT_VERSION_1 = ForeignKeys0.FK_PARAMETERS_REPORT_VERSION_1;
    public static final ForeignKey<ParametersRecord, ParametersTypeRecord> FK_PARAMETERS_PARAMETERS_TYPE_1 = ForeignKeys0.FK_PARAMETERS_PARAMETERS_TYPE_1;
    public static final ForeignKey<ReportRecord, ReportVersionRecord> FK_REPORT_REPORT_VERSION_1 = ForeignKeys0.FK_REPORT_REPORT_VERSION_1;
    public static final ForeignKey<ReportVersionRecord, ReportRecord> FK_REPORT_VERSION_REPORT_1 = ForeignKeys0.FK_REPORT_VERSION_REPORT_1;
    public static final ForeignKey<ReportVersionArtefactRecord, ReportVersionRecord> FK_REPORT_VERSION_ARTEFACT_REPORT_VERSION_1 = ForeignKeys0.FK_REPORT_VERSION_ARTEFACT_REPORT_VERSION_1;
    public static final ForeignKey<ReportVersionArtefactRecord, ArtefactFormatRecord> FK_REPORT_VERSION_ARTEFACT_ARTEFACT_FORMAT_1 = ForeignKeys0.FK_REPORT_VERSION_ARTEFACT_ARTEFACT_FORMAT_1;
    public static final ForeignKey<ReportVersionCustomFieldsRecord, ReportVersionRecord> FK_REPORT_VERSION_CUSTOM_FIELDS_REPORT_VERSION_1 = ForeignKeys0.FK_REPORT_VERSION_CUSTOM_FIELDS_REPORT_VERSION_1;
    public static final ForeignKey<ReportVersionCustomFieldsRecord, CustomFieldsRecord> FK_REPORT_VERSION_CUSTOM_FIELDS_CUSTOM_FIELDS_1 = ForeignKeys0.FK_REPORT_VERSION_CUSTOM_FIELDS_CUSTOM_FIELDS_1;
    public static final ForeignKey<ReportVersionDataRecord, ReportVersionRecord> FK_REPORT_VERSION_DATA_REPORT_VERSION_1 = ForeignKeys0.FK_REPORT_VERSION_DATA_REPORT_VERSION_1;
    public static final ForeignKey<ReportVersionDataRecord, DataRecord> FK_REPORT_VERSION_DATA_DATA_1 = ForeignKeys0.FK_REPORT_VERSION_DATA_DATA_1;
    public static final ForeignKey<ReportVersionPackageRecord, ReportVersionRecord> FK_REPORT_VERSION_PACKAGE_REPORT_VERSION_1 = ForeignKeys0.FK_REPORT_VERSION_PACKAGE_REPORT_VERSION_1;
    public static final ForeignKey<ReportVersionViewRecord, ReportVersionRecord> FK_REPORT_VERSION_VIEW_REPORT_VERSION_1 = ForeignKeys0.FK_REPORT_VERSION_VIEW_REPORT_VERSION_1;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<ArtefactFormatRecord> PK_ARTEFACT_FORMAT = createUniqueKey(ArtefactFormat.ARTEFACT_FORMAT, "pk_artefact_format", ArtefactFormat.ARTEFACT_FORMAT.NAME);
        public static final UniqueKey<ChangelogRecord> PK_CHANGELOG = createUniqueKey(Changelog.CHANGELOG, "pk_changelog", Changelog.CHANGELOG.ID);
        public static final UniqueKey<ChangelogLabelRecord> PK_CHANGELOG_LABEL = createUniqueKey(ChangelogLabel.CHANGELOG_LABEL, "pk_changelog_label", ChangelogLabel.CHANGELOG_LABEL.ID);
        public static final UniqueKey<CustomFieldsRecord> PK_CUSTOM_FIELDS = createUniqueKey(CustomFields.CUSTOM_FIELDS, "pk_custom_fields", CustomFields.CUSTOM_FIELDS.ID);
        public static final UniqueKey<DataRecord> PK_DATA = createUniqueKey(Data.DATA, "pk_data", Data.DATA.HASH);
        public static final UniqueKey<DependsRecord> PK_DEPENDS = createUniqueKey(Depends.DEPENDS, "pk_depends", Depends.DEPENDS.ID);
        public static final UniqueKey<FileRecord> PK_FILE = createUniqueKey(File.FILE, "pk_file", File.FILE.HASH);
        public static final UniqueKey<FileArtefactRecord> PK_FILE_ARTEFACT = createUniqueKey(FileArtefact.FILE_ARTEFACT, "pk_file_artefact", FileArtefact.FILE_ARTEFACT.ID);
        public static final UniqueKey<FileInputRecord> PK_FILE_INPUT = createUniqueKey(FileInput.FILE_INPUT, "pk_file_input", FileInput.FILE_INPUT.ID);
        public static final UniqueKey<FilePurposeRecord> PK_FILE_PURPOSE = createUniqueKey(FilePurpose.FILE_PURPOSE, "pk_file_purpose", FilePurpose.FILE_PURPOSE.NAME);
        public static final UniqueKey<OrderlySchemaRecord> PK_ORDERLY_SCHEMA = createUniqueKey(OrderlySchema.ORDERLY_SCHEMA, "pk_orderly_schema", OrderlySchema.ORDERLY_SCHEMA.SCHEMA_VERSION);
        public static final UniqueKey<OrderlySchemaTablesRecord> PK_ORDERLY_SCHEMA_TABLES = createUniqueKey(OrderlySchemaTables.ORDERLY_SCHEMA_TABLES, "pk_orderly_schema_tables", OrderlySchemaTables.ORDERLY_SCHEMA_TABLES.NAME);
        public static final UniqueKey<ParametersRecord> PK_PARAMETERS = createUniqueKey(Parameters.PARAMETERS, "pk_parameters", Parameters.PARAMETERS.ID);
        public static final UniqueKey<ParametersTypeRecord> PK_PARAMETERS_TYPE = createUniqueKey(ParametersType.PARAMETERS_TYPE, "pk_parameters_type", ParametersType.PARAMETERS_TYPE.NAME);
        public static final UniqueKey<ReportRecord> PK_REPORT = createUniqueKey(Report.REPORT, "pk_report", Report.REPORT.NAME);
        public static final UniqueKey<ReportVersionRecord> PK_REPORT_VERSION = createUniqueKey(ReportVersion.REPORT_VERSION, "pk_report_version", ReportVersion.REPORT_VERSION.ID);
        public static final UniqueKey<ReportVersionArtefactRecord> PK_REPORT_VERSION_ARTEFACT = createUniqueKey(ReportVersionArtefact.REPORT_VERSION_ARTEFACT, "pk_report_version_artefact", ReportVersionArtefact.REPORT_VERSION_ARTEFACT.ID);
        public static final UniqueKey<ReportVersionCustomFieldsRecord> PK_REPORT_VERSION_CUSTOM_FIELDS = createUniqueKey(ReportVersionCustomFields.REPORT_VERSION_CUSTOM_FIELDS, "pk_report_version_custom_fields", ReportVersionCustomFields.REPORT_VERSION_CUSTOM_FIELDS.ID);
        public static final UniqueKey<ReportVersionDataRecord> PK_REPORT_VERSION_DATA = createUniqueKey(ReportVersionData.REPORT_VERSION_DATA, "pk_report_version_data", ReportVersionData.REPORT_VERSION_DATA.ID);
        public static final UniqueKey<ReportVersionPackageRecord> PK_REPORT_VERSION_PACKAGE = createUniqueKey(ReportVersionPackage.REPORT_VERSION_PACKAGE, "pk_report_version_package", ReportVersionPackage.REPORT_VERSION_PACKAGE.ID);
        public static final UniqueKey<ReportVersionViewRecord> PK_REPORT_VERSION_VIEW = createUniqueKey(ReportVersionView.REPORT_VERSION_VIEW, "pk_report_version_view", ReportVersionView.REPORT_VERSION_VIEW.ID);
    }

    private static class ForeignKeys0 extends AbstractKeys {
        public static final ForeignKey<ChangelogRecord, ReportVersionRecord> FK_CHANGELOG_REPORT_VERSION_2 = createForeignKey(org.vaccineimpact.reporting_api.db.Keys.PK_REPORT_VERSION, Changelog.CHANGELOG, "fk_changelog_report_version_2", Changelog.CHANGELOG.REPORT_VERSION);
        public static final ForeignKey<ChangelogRecord, ReportVersionRecord> FK_CHANGELOG_REPORT_VERSION_1 = createForeignKey(org.vaccineimpact.reporting_api.db.Keys.PK_REPORT_VERSION, Changelog.CHANGELOG, "fk_changelog_report_version_1", Changelog.CHANGELOG.REPORT_VERSION_PUBLIC);
        public static final ForeignKey<ChangelogRecord, ChangelogLabelRecord> FK_CHANGELOG_CHANGELOG_LABEL_1 = createForeignKey(org.vaccineimpact.reporting_api.db.Keys.PK_CHANGELOG_LABEL, Changelog.CHANGELOG, "fk_changelog_changelog_label_1", Changelog.CHANGELOG.LABEL);
        public static final ForeignKey<DependsRecord, ReportVersionRecord> FK_DEPENDS_REPORT_VERSION_1 = createForeignKey(org.vaccineimpact.reporting_api.db.Keys.PK_REPORT_VERSION, Depends.DEPENDS, "fk_depends_report_version_1", Depends.DEPENDS.REPORT_VERSION);
        public static final ForeignKey<DependsRecord, FileArtefactRecord> FK_DEPENDS_FILE_ARTEFACT_1 = createForeignKey(org.vaccineimpact.reporting_api.db.Keys.PK_FILE_ARTEFACT, Depends.DEPENDS, "fk_depends_file_artefact_1", Depends.DEPENDS.USE);
        public static final ForeignKey<FileArtefactRecord, ReportVersionArtefactRecord> FK_FILE_ARTEFACT_REPORT_VERSION_ARTEFACT_1 = createForeignKey(org.vaccineimpact.reporting_api.db.Keys.PK_REPORT_VERSION_ARTEFACT, FileArtefact.FILE_ARTEFACT, "fk_file_artefact_report_version_artefact_1", FileArtefact.FILE_ARTEFACT.ARTEFACT);
        public static final ForeignKey<FileArtefactRecord, FileRecord> FK_FILE_ARTEFACT_FILE_1 = createForeignKey(org.vaccineimpact.reporting_api.db.Keys.PK_FILE, FileArtefact.FILE_ARTEFACT, "fk_file_artefact_file_1", FileArtefact.FILE_ARTEFACT.FILE_HASH);
        public static final ForeignKey<FileInputRecord, ReportVersionRecord> FK_FILE_INPUT_REPORT_VERSION_1 = createForeignKey(org.vaccineimpact.reporting_api.db.Keys.PK_REPORT_VERSION, FileInput.FILE_INPUT, "fk_file_input_report_version_1", FileInput.FILE_INPUT.REPORT_VERSION);
        public static final ForeignKey<FileInputRecord, FileRecord> FK_FILE_INPUT_FILE_1 = createForeignKey(org.vaccineimpact.reporting_api.db.Keys.PK_FILE, FileInput.FILE_INPUT, "fk_file_input_file_1", FileInput.FILE_INPUT.FILE_HASH);
        public static final ForeignKey<FileInputRecord, FilePurposeRecord> FK_FILE_INPUT_FILE_PURPOSE_1 = createForeignKey(org.vaccineimpact.reporting_api.db.Keys.PK_FILE_PURPOSE, FileInput.FILE_INPUT, "fk_file_input_file_purpose_1", FileInput.FILE_INPUT.FILE_PURPOSE);
        public static final ForeignKey<ParametersRecord, ReportVersionRecord> FK_PARAMETERS_REPORT_VERSION_1 = createForeignKey(org.vaccineimpact.reporting_api.db.Keys.PK_REPORT_VERSION, Parameters.PARAMETERS, "fk_parameters_report_version_1", Parameters.PARAMETERS.REPORT_VERSION);
        public static final ForeignKey<ParametersRecord, ParametersTypeRecord> FK_PARAMETERS_PARAMETERS_TYPE_1 = createForeignKey(org.vaccineimpact.reporting_api.db.Keys.PK_PARAMETERS_TYPE, Parameters.PARAMETERS, "fk_parameters_parameters_type_1", Parameters.PARAMETERS.TYPE);
        public static final ForeignKey<ReportRecord, ReportVersionRecord> FK_REPORT_REPORT_VERSION_1 = createForeignKey(org.vaccineimpact.reporting_api.db.Keys.PK_REPORT_VERSION, Report.REPORT, "fk_report_report_version_1", Report.REPORT.LATEST);
        public static final ForeignKey<ReportVersionRecord, ReportRecord> FK_REPORT_VERSION_REPORT_1 = createForeignKey(org.vaccineimpact.reporting_api.db.Keys.PK_REPORT, ReportVersion.REPORT_VERSION, "fk_report_version_report_1", ReportVersion.REPORT_VERSION.REPORT);
        public static final ForeignKey<ReportVersionArtefactRecord, ReportVersionRecord> FK_REPORT_VERSION_ARTEFACT_REPORT_VERSION_1 = createForeignKey(org.vaccineimpact.reporting_api.db.Keys.PK_REPORT_VERSION, ReportVersionArtefact.REPORT_VERSION_ARTEFACT, "fk_report_version_artefact_report_version_1", ReportVersionArtefact.REPORT_VERSION_ARTEFACT.REPORT_VERSION);
        public static final ForeignKey<ReportVersionArtefactRecord, ArtefactFormatRecord> FK_REPORT_VERSION_ARTEFACT_ARTEFACT_FORMAT_1 = createForeignKey(org.vaccineimpact.reporting_api.db.Keys.PK_ARTEFACT_FORMAT, ReportVersionArtefact.REPORT_VERSION_ARTEFACT, "fk_report_version_artefact_artefact_format_1", ReportVersionArtefact.REPORT_VERSION_ARTEFACT.FORMAT);
        public static final ForeignKey<ReportVersionCustomFieldsRecord, ReportVersionRecord> FK_REPORT_VERSION_CUSTOM_FIELDS_REPORT_VERSION_1 = createForeignKey(org.vaccineimpact.reporting_api.db.Keys.PK_REPORT_VERSION, ReportVersionCustomFields.REPORT_VERSION_CUSTOM_FIELDS, "fk_report_version_custom_fields_report_version_1", ReportVersionCustomFields.REPORT_VERSION_CUSTOM_FIELDS.REPORT_VERSION);
        public static final ForeignKey<ReportVersionCustomFieldsRecord, CustomFieldsRecord> FK_REPORT_VERSION_CUSTOM_FIELDS_CUSTOM_FIELDS_1 = createForeignKey(org.vaccineimpact.reporting_api.db.Keys.PK_CUSTOM_FIELDS, ReportVersionCustomFields.REPORT_VERSION_CUSTOM_FIELDS, "fk_report_version_custom_fields_custom_fields_1", ReportVersionCustomFields.REPORT_VERSION_CUSTOM_FIELDS.KEY);
        public static final ForeignKey<ReportVersionDataRecord, ReportVersionRecord> FK_REPORT_VERSION_DATA_REPORT_VERSION_1 = createForeignKey(org.vaccineimpact.reporting_api.db.Keys.PK_REPORT_VERSION, ReportVersionData.REPORT_VERSION_DATA, "fk_report_version_data_report_version_1", ReportVersionData.REPORT_VERSION_DATA.REPORT_VERSION);
        public static final ForeignKey<ReportVersionDataRecord, DataRecord> FK_REPORT_VERSION_DATA_DATA_1 = createForeignKey(org.vaccineimpact.reporting_api.db.Keys.PK_DATA, ReportVersionData.REPORT_VERSION_DATA, "fk_report_version_data_data_1", ReportVersionData.REPORT_VERSION_DATA.HASH);
        public static final ForeignKey<ReportVersionPackageRecord, ReportVersionRecord> FK_REPORT_VERSION_PACKAGE_REPORT_VERSION_1 = createForeignKey(org.vaccineimpact.reporting_api.db.Keys.PK_REPORT_VERSION, ReportVersionPackage.REPORT_VERSION_PACKAGE, "fk_report_version_package_report_version_1", ReportVersionPackage.REPORT_VERSION_PACKAGE.REPORT_VERSION);
        public static final ForeignKey<ReportVersionViewRecord, ReportVersionRecord> FK_REPORT_VERSION_VIEW_REPORT_VERSION_1 = createForeignKey(org.vaccineimpact.reporting_api.db.Keys.PK_REPORT_VERSION, ReportVersionView.REPORT_VERSION_VIEW, "fk_report_version_view_report_version_1", ReportVersionView.REPORT_VERSION_VIEW.REPORT_VERSION);
    }
}
