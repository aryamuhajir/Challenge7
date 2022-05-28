package com.example.challenge6

import org.junit.Assert.*

import org.junit.Test

class DataUmurTest {
    val dataUmur = DataUmur()
    @Test
    fun getDataUmur() {
        val result = dataUmur.getDataUmur(2000, 2022)
    }
}