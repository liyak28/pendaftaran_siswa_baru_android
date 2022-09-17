package com.liyak28.siswabaru.repo.source

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.liyak28.siswabaru.data.local.entity.StudentsEntity

interface StudentDataSource {

    suspend fun insertStudents(studentEntity: StudentsEntity)

    suspend fun getStudentAll(): List<StudentsEntity>

    suspend fun getStudentDetail(id: Long): StudentsEntity?

    suspend fun updateStudent(studentEntity: StudentsEntity)

    suspend fun deleteStudentById(id: Long)

    suspend fun deleteStudent(studentEntity: StudentsEntity)

}