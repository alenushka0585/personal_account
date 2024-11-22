package com.example.personal_account.entity

import com.example.personal_account.dto.ProfileDTO
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "profile")
class Profile(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        var name: String,

        @field:NotNull(message = "Surname must not be blank")
        var surname: String,

        var jobTitle: String?,

        var phone: String,

        var address: String?,

        @ElementCollection
        @CollectionTable(name = "profile_interests", joinColumns = [JoinColumn(name = "profile_id")])
        @Column(name = "interest")
        var interests: List<String>? = emptyList(),

        var isPublic: Boolean = false,

        var avatarUrl: String?,

        var links: String? = null
) {
    fun updateFromDTO(profileDTO: ProfileDTO) {
        this.name = profileDTO.name?: this.name
        this.surname = profileDTO.surname?: this.surname
        this.jobTitle = profileDTO.jobTitle
        this.phone = profileDTO.phone?:this.phone
        this.address = profileDTO.address
        this.interests = profileDTO.interests
        this.isPublic = profileDTO.isPublic?:this.isPublic
        this.links = profileDTO.links
    }
}
