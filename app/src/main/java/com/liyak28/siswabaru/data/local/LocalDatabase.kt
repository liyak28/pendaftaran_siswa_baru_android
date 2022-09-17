package com.liyak28.siswabaru.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.liyak28.siswabaru.data.local.dao.StudentsDao
import com.liyak28.siswabaru.data.local.entity.StudentsEntity

@Database(
    entities = [
        StudentsEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentsDao

}