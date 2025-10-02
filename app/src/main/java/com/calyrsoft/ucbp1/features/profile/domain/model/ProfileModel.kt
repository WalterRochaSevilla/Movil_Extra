package com.calyrsoft.ucbp1.features.profile.domain.model

import com.calyrsoft.ucbp1.features.github.domain.model.UrlPath
import com.calyrsoft.ucbp1.features.login.domain.model.Email
import com.calyrsoft.ucbp1.features.profile.domain.model.vo.Cellphone
import com.calyrsoft.ucbp1.features.profile.domain.model.vo.Name
import com.calyrsoft.ucbp1.features.profile.domain.model.vo.Summary

data class ProfileModel(
    val pathUrl: UrlPath,
    val name: Name,
    val email: Email,
    val cellphone: Cellphone,
    val summary: Summary
)
