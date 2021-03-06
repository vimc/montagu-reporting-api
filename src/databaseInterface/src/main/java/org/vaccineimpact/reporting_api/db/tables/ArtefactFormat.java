/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.reporting_api.db.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.reporting_api.db.DefaultSchema;
import org.vaccineimpact.reporting_api.db.Keys;
import org.vaccineimpact.reporting_api.db.tables.records.ArtefactFormatRecord;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ArtefactFormat extends TableImpl<ArtefactFormatRecord> {

    private static final long serialVersionUID = 2125689824;

    /**
     * The reference instance of <code>artefact_format</code>
     */
    public static final ArtefactFormat ARTEFACT_FORMAT = new ArtefactFormat();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ArtefactFormatRecord> getRecordType() {
        return ArtefactFormatRecord.class;
    }

    /**
     * The column <code>artefact_format.name</code>.
     */
    public final TableField<ArtefactFormatRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>artefact_format</code> table reference
     */
    public ArtefactFormat() {
        this("artefact_format", null);
    }

    /**
     * Create an aliased <code>artefact_format</code> table reference
     */
    public ArtefactFormat(String alias) {
        this(alias, ARTEFACT_FORMAT);
    }

    private ArtefactFormat(String alias, Table<ArtefactFormatRecord> aliased) {
        this(alias, aliased, null);
    }

    private ArtefactFormat(String alias, Table<ArtefactFormatRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return DefaultSchema.DEFAULT_SCHEMA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ArtefactFormatRecord> getPrimaryKey() {
        return Keys.PK_ARTEFACT_FORMAT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ArtefactFormatRecord>> getKeys() {
        return Arrays.<UniqueKey<ArtefactFormatRecord>>asList(Keys.PK_ARTEFACT_FORMAT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArtefactFormat as(String alias) {
        return new ArtefactFormat(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ArtefactFormat rename(String name) {
        return new ArtefactFormat(name, null);
    }
}
