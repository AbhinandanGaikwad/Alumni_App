package com.example.alumni.data

data class Story(
    val name: String = "",
    val story: String = "",
    val uid: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
