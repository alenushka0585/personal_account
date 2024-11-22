package com.example.personal_account.controller

import com.example.personal_account.dto.ProfileDTO
import com.example.personal_account.errors.ErrorResponse
import com.example.personal_account.errors.ValidationException
import com.example.personal_account.service.ProfileService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class ProfileController(
        private val profileService: ProfileService
) {

    @PostMapping("/profile")
    @Operation(
            summary = "Create a new profile",
            description = "Creates a new profile"
    )
    fun createProfile(@Valid @RequestBody profileDTO: ProfileDTO): ResponseEntity<Any> {
        return try {
            val createdProfile = profileService.createProfile(profileDTO)
            ResponseEntity.status(HttpStatus.CREATED).body(createdProfile)
        } catch (ex: ValidationException) {
            ResponseEntity.badRequest().body(ErrorResponse(
                    status = HttpStatus.BAD_REQUEST.value(),
                    error = "Validation Error",
                    message = ex.message ?: "Validation failed"
            ))
        }
    }


    @GetMapping("/profile/{id}")
    @Operation(
            summary = "Get profile by its id",
            description = "Get profile by its id")
    fun getProfile(@PathVariable id: Long): ResponseEntity<ProfileDTO> {
        return ResponseEntity.ok(profileService.getProfile(id))
    }

    @PutMapping("/profile/{id}")
    @Operation(
            summary = "Update profile by its id",
            description = "Updates profile by its id"
    )
    fun updateProfile(
            @PathVariable id: Long,
            @Valid @RequestBody profileDTO: ProfileDTO
    ): ResponseEntity<Any> {
        return try {
            val updatedProfile = profileService.updateProfile(id, profileDTO)
            ResponseEntity.ok(updatedProfile)
        } catch (ex: ValidationException) {
            ResponseEntity.badRequest().body(ErrorResponse(
                    status = HttpStatus.BAD_REQUEST.value(),
                    error = "Validation Error",
                    message = ex.message ?: "Validation failed"
            ))
        }
    }

    @PostMapping("/upload-avatar/{id}")
    @Operation(
            summary = "Upload profile avatar by its id",
            description = "Uploads profile avatar by its id")
    fun uploadAvatar(
            @PathVariable id: Long,
            @RequestParam("file") file: MultipartFile
    ): ResponseEntity<String>{
        val avatarUrl = profileService.uploadAvatar(id, file)
        return ResponseEntity.ok(avatarUrl)
    }
}