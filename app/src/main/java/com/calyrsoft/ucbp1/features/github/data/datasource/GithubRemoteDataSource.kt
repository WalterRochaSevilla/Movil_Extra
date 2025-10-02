package com.calyrsoft.ucbp1.features.github.data.datasource

import com.calyrsoft.ucbp1.features.github.data.api.GithubService
import com.calyrsoft.ucbp1.features.github.data.api.dto.GithubDto
import com.calyrsoft.ucbp1.features.github.data.error.DataException
import com.calyrsoft.ucbp1.features.github.domain.model.NickName
import com.calyrsoft.ucbp1.features.github.domain.model.UrlPath
import com.calyrsoft.ucbp1.features.github.domain.model.UserModel

class GithubRemoteDataSource(
    val githubService: GithubService
) {
    suspend fun getUser(nick: String): Result<UserModel> {
        val response = githubService.getInfoAvatar(nick)
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                try {
                    return Result.success(UserModel(
                        NickName(body.login),
                        UrlPath(body.url)
                    ))
                } catch (e: Exception) {
                    return Result.failure(
                        DataException.Unknown(
                            e.message.toString()))
                }

            } else {
                return Result.failure(DataException.NoContent)
            }
        } else if (response.code() == 404) {
            return Result.failure(DataException.HttpNotFound)
        } else {
            return Result.failure(DataException.Unknown(response.message()))
        }
    }
}