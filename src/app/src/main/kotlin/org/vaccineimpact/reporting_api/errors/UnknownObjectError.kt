package org.vaccineimpact.reporting_api.errors

class UnknownObjectError(id: Any, typeName: Any) : MontaguError(404, listOf(
        org.vaccineimpact.api.models.ErrorInfo("unknown-${mangleTypeName(typeName)}", "Unknown ${mangleTypeName(typeName)} : '$id'")
))
{
    companion object
    {
        fun mangleTypeName(typeName: Any) = typeName
                .toString()
                .replace(Regex("[A-Z]"), { "-" + it.value.toLowerCase() })
                .trim('-')
    }
}
