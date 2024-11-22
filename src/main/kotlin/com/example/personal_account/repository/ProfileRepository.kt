package com.example.personal_account.repository

import com.example.personal_account.entity.Profile
import org.springframework.data.jpa.repository.JpaRepository

interface ProfileRepository : JpaRepository<Profile, Long>