package com.calyrsoft.ucbp1.features.profile.domain.repository

import com.calyrsoft.ucbp1.features.profile.domain.model.ProfileModel

interface IProfileRepository {
    fun fetchData(): Result<ProfileModel>
}