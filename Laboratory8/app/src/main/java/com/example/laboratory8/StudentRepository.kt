package com.example.laboratory8

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class StudentRepository(private val studentDao: StudentDao) {

    val allStudents: Flow<List<Student>> = studentDao.getAllStudents()

    // Correction ici : on force l'insertion en arrière-plan
    suspend fun insert(student: Student) {
        withContext(Dispatchers.IO) {
            studentDao.insertStudent(student)
        }
    }

    // Correction ici : on force la suppression en arrière-plan
    suspend fun delete(student: Student) {
        withContext(Dispatchers.IO) {
            studentDao.deleteStudent(student)
        }
    }
}