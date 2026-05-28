package com.example.laboratory7

import java.util.UUID

data class Student(
    val id: String = UUID.randomUUID().toString(),
    val firstName: String,
    val lastName: String
)