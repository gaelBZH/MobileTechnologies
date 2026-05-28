package com.example.laboratory7

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class StudentViewModel : ViewModel() {

    // StateFlow to hold the list of students
    private val _students = MutableStateFlow<List<Student>>(emptyList())
    val students: StateFlow<List<Student>> = _students.asStateFlow()

    fun addStudent(firstName: String, lastName: String) {
        if (firstName.isNotBlank() && lastName.isNotBlank()) {
            _students.update { currentList ->
                currentList + Student(firstName = firstName, lastName = lastName)
            }
        }
    }

    fun removeStudent(student: Student) {
        _students.update { currentList ->
            currentList.filterNot { it.id == student.id }
        }
    }
}