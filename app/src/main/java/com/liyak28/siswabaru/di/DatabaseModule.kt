package com.liyak28.siswabaru.di

import android.content.Context
import androidx.room.Room
import com.liyak28.siswabaru.data.local.LocalDatabase
import com.liyak28.siswabaru.repo.StudentsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesRoom(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            LocalDatabase::class.java,
            "local_database.db"
        ).build()
    }

}