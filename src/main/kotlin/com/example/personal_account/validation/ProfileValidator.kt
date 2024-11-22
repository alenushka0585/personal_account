package com.example.personal_account.validation
import com.example.personal_account.dto.ProfileDTO
import org.springframework.stereotype.Component

@Component
class ProfileValidator {
    fun validateProfileDTO(profileDTO: ProfileDTO): List<String> {
        val errors = mutableListOf<String>()

        // Validate required fields
        if (profileDTO.name == null) {
            errors.add("Name is required")
        }
        if (profileDTO.surname == null) {
            errors.add("Surname is required")
        }
        if (profileDTO.phone == null) {
            errors.add("Phone is required")
        }
        if (profileDTO.isPublic == null) {
            errors.add("isPublic field is required")
        }

        // Validate name
        profileDTO.name?.let { name ->
            if (name.isBlank()) {
                errors.add("Name must not be blank")
            }
            if (name.length !in 2..50) {
                errors.add("Name must be between 2 and 50 characters")
            }
            if (!name.matches(Regex("^[A-Za-zА-Яа-я\\s\\-]+$"))) {
                errors.add("Name can only contain letters, spaces, and hyphens")
            }
        }

        // Validate surname
        profileDTO.surname?.let { surname ->
            if (surname.isBlank()) {
                errors.add("Surname must not be blank")
            }
            if (surname.length !in 2..50) {
                errors.add("Surname must be between 2 and 50 characters")
            }
            if (!surname.matches(Regex("^[A-Za-zА-Яа-я\\s\\-]+$"))) {
                errors.add("Surname can only contain letters, spaces, and hyphens")
            }
        }

        // Validate jobTitle
        profileDTO.jobTitle?.let {
            if (it.length > 100) {
                errors.add("Job title must be not longer than 100 characters")
            }
            if (!it.matches(Regex("^[A-Za-zА-Яа-я0-9\\s\\-]+$"))) {
                errors.add("Job title can only contain letters, numbers, spaces and hyphens")
            }
        }

        // Validate phone
        profileDTO.phone?.let { phone ->
            if (!phone.matches(Regex("\\+\\d{10,15}"))) {
                errors.add("Phone number should begin with '+' and can only contain numbers from 10 to 15 characters")
            }
        }

        // Validate address
        profileDTO.address?.let {
            if (it.length > 200) {
                errors.add("Address must be not longer than 200 characters")
            }
            if (!it.matches(Regex("^[a-zA-Zа-яА-Я0-9,.\\s\\-]*$"))) {
                errors.add("Address can only contain letters, numbers, commas, periods, hyphens, and spaces")
            }
        }

        // Validate interests
        profileDTO.interests?.let { interests ->
            if (interests.size > 10) {
                errors.add("Interests can contain only 10 tags")
            }
            interests.forEachIndexed { index, tag ->
                if (tag.length > 30) {
                    errors.add("Tag at index $index exceeds 30 characters")
                }
                if (!tag.matches(Regex("^[a-zA-Zа-яА-Я0-9,.\\s-]*$"))) {
                    errors.add("Tag at index $index can only contain letters, numbers, spaces, commas, periods, and hyphens")
                }
            }
        }

        // Validate links
        profileDTO.links?.let {
            if (it.length > 200) {
                errors.add("The link must not exceed 200 characters")
            }
            if (!it.matches(Regex("^(https?://).+"))) {
                errors.add("Links must start with http:// or https://")
            }
        }
        return errors
    }
}