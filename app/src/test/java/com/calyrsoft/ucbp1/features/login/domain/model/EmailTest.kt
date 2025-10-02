package com.calyrsoft.ucbp1.features.login.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class EmailTest {

    @Test()
    fun `test input Data Email`() {
        //arrange
        val inputData = "RobertoCarlos.callisaya@test.COM"
        val expected = "robertocarlos.callisaya@test.com"
        //act
        val emailValueObject = Email.create(inputData)

        //assert
        assertEquals(expected, emailValueObject.value)
    }
}