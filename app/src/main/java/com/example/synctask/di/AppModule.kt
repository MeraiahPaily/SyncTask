package com.example.synctask.di

import android.content.Context
import com.example.synctask.data.CategoryDao
import com.example.synctask.data.ProjectDao
import com.example.synctask.data.SyncTaskDatabase
import com.example.synctask.data.TaskDao
import com.example.synctask.data.TaskRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSyncTaskDatabase(@ApplicationContext context: Context): SyncTaskDatabase {
        return SyncTaskDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideTaskDao(database: SyncTaskDatabase): TaskDao {
        return database.taskDao()
    }

    @Singleton
    @Provides
    fun provideCategoryDao(database: SyncTaskDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Singleton
    @Provides
    fun provideProjectDao(database: SyncTaskDatabase): ProjectDao {
        return database.projectDao()
    }

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideTaskRepository(taskDao: TaskDao, categoryDao: CategoryDao, projectDao: ProjectDao, firestore: FirebaseFirestore): TaskRepository {
        return TaskRepository(taskDao, categoryDao, projectDao, firestore)
    }
}
