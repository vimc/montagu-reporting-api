package org.vaccineimpact.reporting_api.security

data class MontaguUser(
        val username: String,
        val roles: String,
        val permissions: String
)