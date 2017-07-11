package org.vaccineimpact.reporting_api.db

class UnableToConnectToDatabase(val url: String) : Exception(
        "Unable to connect to database at $url"
)