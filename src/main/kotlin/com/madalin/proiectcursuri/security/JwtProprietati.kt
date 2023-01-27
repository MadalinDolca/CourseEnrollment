package com.madalin.proiectcursuri.security

object JwtProperties {
    const val SECRET = "PROIECT_JAVA"
    const val TIMP_EXPIRARE = 3_600_000 // 1 ora
    const val TOKEN_PREFIX = "Bearer "
    const val HEADER = "Authorization"
}