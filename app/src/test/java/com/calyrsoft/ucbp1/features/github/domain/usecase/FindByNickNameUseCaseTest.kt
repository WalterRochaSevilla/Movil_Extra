package com.calyrsoft.ucbp1.features.github.domain.usecase

import androidx.annotation.OptIn
import com.calyrsoft.ucbp1.features.github.domain.model.NickName
import com.calyrsoft.ucbp1.features.github.domain.model.UrlPath
import com.calyrsoft.ucbp1.features.github.domain.model.UserModel
import com.calyrsoft.ucbp1.features.github.domain.repository.IGithubRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FindByNickNameUseCaseTest {
    private val repository = mockk<IGithubRepository>()
    private val useCase = FindByNickNameUseCase(repository)

    @Test
    fun `should return success when repository returns user`() = runTest {
        // Arrange
        val expected = UserModel(nickname = NickName("juan"), pathUrl = UrlPath("https://test.url"))
        coEvery { repository.findByNick("juan") } returns Result.success(expected)

        // Act
        val result = useCase.invoke("juan")

        // Assert
        assert(result.isSuccess)
        assertEquals(expected, result.getOrNull())
    }

    @Test
    fun `should return failure when repository returns error`() = runTest {
        // Arrange
        val exception = Exception("User not found")
        coEvery { repository.findByNick("nonexistent") } returns Result.failure(exception)

        // Act
        val result = useCase.invoke("nonexistent")

        // Assert
        assert(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}