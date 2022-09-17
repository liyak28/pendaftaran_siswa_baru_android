package com.liyak28.siswabaru.repo

import com.liyak28.siswabaru.data.local.LocalDatabase
import com.liyak28.siswabaru.data.local.entity.StudentsEntity
import com.liyak28.siswabaru.repo.source.StudentDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StudentsRepo @Inject constructor(
    val database: LocalDatabase
): StudentDataSource {

    override suspend fun insertStudents(studentEntity: StudentsEntity) {
        return withContext(Dispatchers.IO) {
            database.studentDao().insertStudents(studentEntity)
        }
    }

    override suspend fun getStudentAll(): List<StudentsEntity> {
        return withContext(Dispatchers.IO) {
            database.studentDao().getStudentAll()
        }
    }

    override suspend fun getStudentDetail(id: Long): StudentsEntity? {
        return withContext(Dispatchers.IO) {
            database.studentDao().getStudentDetail(id)
        }
    }

    override suspend fun updateStudent(studentEntity: StudentsEntity) {
        return withContext(Dispatchers.IO) {
            database.studentDao().updateStudent(studentEntity)
        }
    }

    override suspend fun deleteStudentById(id: Long) {
        return withContext(Dispatchers.IO) {
            database.studentDao().deleteStudentById(id)
        }
    }

    override suspend fun deleteStudent(studentEntity: StudentsEntity) {
        return withContext(Dispatchers.IO) {
            database.studentDao().delete(studentEntity)
        }
    }
}