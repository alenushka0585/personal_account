package com.example.personal_account.dto

import com.example.personal_account.entity.Profile
import com.fasterxml.jackson.annotation.JsonInclude



data class ProfileDTO(

        val name: String?,

        val surname: String?,

        @field:JsonInclude(JsonInclude.Include.NON_NULL)
        val jobTitle: String?,

        val phone: String?,

        @field:JsonInclude(JsonInclude.Include.NON_NULL)
        val address: String?,

        @field:JsonInclude(JsonInclude.Include.NON_NULL)
        val interests: List<
                String>? = null,

        val isPublic: Boolean? = false,


        @field:JsonInclude(JsonInclude.Include.NON_NULL)
        val avatarUrl: String?,

        @field:JsonInclude(JsonInclude.Include.NON_NULL)
        val links: String? = null
) {
    companion object {
        fun fromEntity(profile: Profile): ProfileDTO {
            return ProfileDTO(
                    name = profile.name,
                    surname = profile.surname,
                    jobTitle = profile.jobTitle,
                    phone = profile.phone,
                    address = profile.address,
                    interests = profile.interests,
                    isPublic = profile.isPublic,
                    avatarUrl = profile.avatarUrl
            )
        }
    }

    fun toEntity(): Profile {
        return Profile(
                name = this.name!!,
                surname = this.surname!!,
                jobTitle = this.jobTitle,
                phone = this.phone!!,
                address = this.address,
                interests = this.interests,
                isPublic = this.isPublic!!,
                avatarUrl = this.avatarUrl
        )
    }
}



