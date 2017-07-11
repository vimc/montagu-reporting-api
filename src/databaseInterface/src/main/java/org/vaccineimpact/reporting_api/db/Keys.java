/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.reporting_api.db;


import javax.annotation.Generated;

import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;
import org.vaccineimpact.reporting_api.db.tables.Orderly;
import org.vaccineimpact.reporting_api.db.tables.records.OrderlyRecord;


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

    public static final UniqueKey<OrderlyRecord> PK_ORDERLY = UniqueKeys0.PK_ORDERLY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<OrderlyRecord> PK_ORDERLY = createUniqueKey(Orderly.ORDERLY, "pk_orderly", Orderly.ORDERLY.ID);
    }
}
