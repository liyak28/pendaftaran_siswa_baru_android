package com.liyak28.siswabaru.data.local.dao

import androidx.room.*
import com.liyak28.siswabaru.data.local.entity.StudentsEntity

@Dao
interface StudentsDao {

    @Insert
    suspend fun insertStudents(studentsEntity: StudentsEntity)

    @Query("SELECT * FROM students ORDER BY id DESC")
    suspend fun getStudentAll(): List<StudentsEntity>

    @Query("SELECT * FROM students WHERE id = :id")
    suspend fun getStudentDetail(id: Long): StudentsEntity?

    @Update
    suspend fun updateStudent(studentsEntity: StudentsEntity)

    @Query("DELETE FROM students WHERE id = :id")
    suspend fun deleteStudentById(id: Long)

    @Delete
    fun delete(studentsEntity: StudentsEntity)

}