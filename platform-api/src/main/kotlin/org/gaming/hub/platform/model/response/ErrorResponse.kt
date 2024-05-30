package org.gaming.hub.platform.model.response

data class ErrorResponse(
    val code: Int,
    val message: String,
    val status: Int
)