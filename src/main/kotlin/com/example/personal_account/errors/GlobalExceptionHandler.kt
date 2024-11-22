package com.example.personal_account.errors

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(FileSizeExceededException::class)
    fun handleFileSizeExceededException(ex: FileSizeExceededException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                error = "File Size Exceeded",
                message = ex.message ?: "The uploaded file exceeds the maximum allowed size."
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidFileFormatException::class)
    fun handleInvalidFileFormatException(ex: InvalidFileFormatException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                error = "Invalid File Format",
                message = ex.message ?: "The uploaded file format is not supported."
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ProfileNotFoundException::class)
    fun handleProfileNotFoundException(ex: ProfileNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
                status = HttpStatus.NOT_FOUND.value(),
                error = "Profile Not Found",
                message = ex.message ?: "The requested profile could not be found."
        )
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(ex: ValidationException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                error = "Validation Error",
                message = ex.message ?: "A validation error occurred."
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                error = "Internal Server Error",
                message = "An unexpected error occurred."
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

}

// A class for a structured error response
data class ErrorResponse(
        val status: Int,
        val error: String,
        val message: String
)

