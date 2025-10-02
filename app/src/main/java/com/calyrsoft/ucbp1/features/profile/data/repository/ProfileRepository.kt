package com.calyrsoft.ucbp1.features.profile.data.repository

import com.calyrsoft.ucbp1.features.github.domain.model.UrlPath
import com.calyrsoft.ucbp1.features.login.domain.model.Email
import com.calyrsoft.ucbp1.features.profile.domain.model.ProfileModel
import com.calyrsoft.ucbp1.features.profile.domain.model.vo.Cellphone
import com.calyrsoft.ucbp1.features.profile.domain.model.vo.Name
import com.calyrsoft.ucbp1.features.profile.domain.model.vo.Summary
import com.calyrsoft.ucbp1.features.profile.domain.repository.IProfileRepository

class ProfileRepository: IProfileRepository {
    override fun fetchData(): Result<ProfileModel> {
        return Result.success(
            ProfileModel(
                name = Name.create("Homero J. Simpson"),
                email = Email.create("homero.simpson@springfieldmail.com"),
                cellphone = Cellphone("+1 (939) 555‑7422"),
                pathUrl = UrlPath("https://www.viaempresa.cat/uploads/s1/43/99/69/homer.pg"),
                summary = Summary.create("Ciudadano de Springfield y dedicado inspector de seguridad en la Planta Nuclear.")
            )
        )
    }
}