package io.soboro.supreme.web.api.web.controller

import io.soboro.supreme.web.api.support.error.ApiException
import io.soboro.supreme.web.api.support.error.ErrorType
import io.soboro.supreme.web.api.support.response.ApiResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.logging.LogLevel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiControllerAdvice {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(ApiException::class)
    fun handleCoreApiException(e: ApiException): ResponseEntity<ApiResponse<Any>> {
        when (e.errorType.logLevel) {
            LogLevel.ERROR -> log.error("CoreApiException : {}", e.message, e)
            LogLevel.WARN -> log.warn("CoreApiException : {}", e.message, e)
            else -> log.info("CoreApiException : {}", e.message, e)
        }
        return ResponseEntity(ApiResponse.error(e.errorType, e.data), e.errorType.status)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<Any>> {
        log.error("Exception : {}", e.message, e)
        return ResponseEntity(ApiResponse.error(ErrorType.DEFAULT_ERROR), ErrorType.DEFAULT_ERROR.status)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleNotFoundException(e: IllegalArgumentException): ResponseEntity<ApiResponse<Any>> {
        log.error("Exception : {}", e.message, e)
        return ResponseEntity(ApiResponse.error(ErrorType.INVALID_ARG_ERROR), ErrorType.INVALID_ARG_ERROR.status)
    }
}
