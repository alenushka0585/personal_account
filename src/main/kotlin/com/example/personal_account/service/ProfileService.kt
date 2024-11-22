package com.example.personal_account.service

import com.example.personal_account.dto.ProfileDTO
import com.example.personal_account.errors.FileSizeExceededException
import com.example.personal_account.errors.InvalidFileFormatException
import com.example.personal_account.errors.ProfileNotFoundException
import com.example.personal_account.errors.ValidationException
import com.example.personal_account.repository.ProfileRepository
import com.example.personal_account.validation.ProfileValidator
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.util.UUID


@Service
class ProfileService(
        private val profileRepository: ProfileRepository,
        private val profileValidator: ProfileValidator
) {

    fun createProfile(profileDTO: ProfileDTO): ProfileDTO {
        val errors = profileValidator.validateProfileDTO(profileDTO)
        if (errors.isNotEmpty()) {
            throw ValidationException(errors.joinToString(", "))
        }
        val profile = profileDTO.toEntity()
        val savedProfile = profileRepository.save(profile)
        return ProfileDTO.fromEntity(savedProfile)
    }

    fun getProfile(id: Long): ProfileDTO {
        val profile = profileRepository.findById(id).orElseThrow {
            ProfileNotFoundException("Profile not found with id: $id")
        }
        return ProfileDTO.fromEntity(profile)
    }

    fun updateProfile(id: Long, profileDTO: ProfileDTO): ProfileDTO {
        val errors = profileValidator.validateProfileDTO(profileDTO)
        if (errors.isNotEmpty()) {
            throw ValidationException(errors.joinToString(", "))
        }
        val profile = profileRepository.findById(id).orElseThrow {
            ProfileNotFoundException("Profile not found with id: $id")
        }
        profile.updateFromDTO(profileDTO)
        val updatedProfile = profileRepository.save(profile)
        return ProfileDTO.fromEntity(updatedProfile)
    }


    fun uploadAvatar(id: Long, file: MultipartFile): String {

        println("File content type: ${file.contentType}, size: ${file.size} bytes")
        //File Type Check
        if (file.contentType !in listOf("image/jpeg", "image/png", "image/jpg")) {
            println("Invalid file format: ${file.contentType}")
            throw InvalidFileFormatException("Invalid file format")

        }

        // File Size Check (limit 5MB)
        val maxFileSize = 5 * 1024 * 1024 // 5 MB in bytes
        if (file.size > maxFileSize) {
            throw FileSizeExceededException("File size exceeds the maximum limit of 5MB")
        }

        //Download directory
        val uploadDir = File(System.getProperty("user.dir"),"uploads")

        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            throw IOException("Failed to create upload directory: ${uploadDir.absolutePath}")
        }

        // Create file with unique name
        val extension = file.originalFilename?.substringAfterLast('.', "") ?: ""
        val avatarFile = File(uploadDir, "${UUID.randomUUID()}.$extension")

        // Save file
        file.transferTo(avatarFile)

        // Generate URL
        val avatarUrl = "/uploads/${avatarFile.name}"

        // Update profile
        val profile = profileRepository.findById(id).orElseThrow { RuntimeException("Profile not found with id: $id") }

        profile.avatarUrl = avatarUrl
        profileRepository.save(profile)

        return avatarUrl
    }
}
