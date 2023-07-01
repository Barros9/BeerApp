package com.barros.beerapp.libraries.beer.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.barros.beerapp.libraries.beer.data.database.BeerAppDatabase
import com.barros.beerapp.libraries.beer.data.database.BeerDao
import com.barros.beerapp.libraries.beer.data.database.DatabaseWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class DatabaseModule {

    companion object {
        const val beerAppDatabaseName = "BeerAppDatabase"
        const val beerDatabaseWorkerName = "DatabaseWorker"
    }

    @Provides
    @Singleton
    fun provideRoomDb(
        @ApplicationContext context: Context,
        databaseCallback: RoomDatabase.Callback
    ): BeerAppDatabase =
        Room.databaseBuilder(context, BeerAppDatabase::class.java, beerAppDatabaseName)
            .fallbackToDestructiveMigration()
            .addCallback(databaseCallback)
            .build()

    @Provides
    @Singleton
    fun provideBeerDao(beerAppDatabase: BeerAppDatabase): BeerDao =
        beerAppDatabase.beerDao()

    @Provides
    @Singleton
    fun provideDatabaseCallback(
        @ApplicationContext context: Context
    ) = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            WorkManager
                .getInstance(context)
                .enqueueUniqueWork(
                    beerDatabaseWorkerName,
                    ExistingWorkPolicy.REPLACE,
                    OneTimeWorkRequestBuilder<DatabaseWorker>().build()
                )
        }
    }
}
