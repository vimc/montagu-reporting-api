package org.vaccineimpact.reporting_api.errors

import org.vaccineimpact.api.models.ErrorInfo

class InvalidOneTimeLinkToken(code: String, message: String) : MontaguError(400, listOf(
        ErrorInfo("invalid-token-$code", message)
))