/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.reporting_api.db.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;
import org.vaccineimpact.reporting_api.db.tables.FileInput;


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
public class FileInputRecord extends UpdatableRecordImpl<FileInputRecord> implements Record5<Integer, String, String, String, String> {

    private static final long serialVersionUID = 2072829404;

    /**
     * Setter for <code>file_input.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>file_input.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>file_input.report_version</code>.
     */
    public void setReportVersion(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>file_input.report_version</code>.
     */
    public String getReportVersion() {
        return (String) get(1);
    }

    /**
     * Setter for <code>file_input.file_hash</code>.
     */
    public void setFileHash(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>file_input.file_hash</code>.
     */
    public String getFileHash() {
        return (String) get(2);
    }

    /**
     * Setter for <code>file_input.filename</code>.
     */
    public void setFilename(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>file_input.filename</code>.
     */
    public String getFilename() {
        return (String) get(3);
    }

    /**
     * Setter for <code>file_input.file_purpose</code>.
     */
    public void setFilePurpose(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>file_input.file_purpose</code>.
     */
    public String getFilePurpose() {
        return (String) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, String, String, String, String> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, String, String, String, String> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return FileInput.FILE_INPUT.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return FileInput.FILE_INPUT.REPORT_VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return FileInput.FILE_INPUT.FILE_HASH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return FileInput.FILE_INPUT.FILENAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return FileInput.FILE_INPUT.FILE_PURPOSE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getReportVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getFileHash();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getFilename();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getFilePurpose();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileInputRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileInputRecord value2(String value) {
        setReportVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileInputRecord value3(String value) {
        setFileHash(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileInputRecord value4(String value) {
        setFilename(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileInputRecord value5(String value) {
        setFilePurpose(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileInputRecord values(Integer value1, String value2, String value3, String value4, String value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached FileInputRecord
     */
    public FileInputRecord() {
        super(FileInput.FILE_INPUT);
    }

    /**
     * Create a detached, initialised FileInputRecord
     */
    public FileInputRecord(Integer id, String reportVersion, String fileHash, String filename, String filePurpose) {
        super(FileInput.FILE_INPUT);

        set(0, id);
        set(1, reportVersion);
        set(2, fileHash);
        set(3, filename);
        set(4, filePurpose);
    }
}
