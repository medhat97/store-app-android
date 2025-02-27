package com.example.storeapp.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.storeapp.model.MovementRecord
import com.example.storeapp.model.StoreRecord
import com.example.storeapp.network.StoreApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        StoreEntity::class,
        MovementEntity::class,
        StoreListEntity::class,
        UserEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class StoreDatabase : RoomDatabase() {
    abstract fun storeDao(): StoreDao
    abstract fun movementDao(): MovementDao
    abstract fun storeListDao(): StoreListDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: StoreDatabase? = null
        private const val TAG = "StoreDatabase"

        fun getDatabase(context: Context, scope: CoroutineScope): StoreDatabase {
            Log.i(TAG, "Fetching the database instance")

            return INSTANCE ?: synchronized(this) {
//                context.deleteDatabase("store_database")
                Log.i(TAG, "Creating new database instance")
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StoreDatabase::class.java,
                    "store_database"
                )
//                    .addCallback(StoreDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()  // This will trigger `onCreate()`
                    .build()

                INSTANCE = instance
                Log.i(TAG, "Database instance created successfully 52")
                instance

            }
        }

        private class StoreDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.i("StoreDatabase", "onCreate callback triggered")

                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        Log.i("StoreDatabase", "Starting database population")
                        populateDatabase(
                            database.storeDao(),
                            database.movementDao(),
                        )
                        Log.i("StoreDatabase", "Database population finished 72")
                    }
                } ?: Log.e("StoreDatabase", "Database instance is null in onCreate!")
            }
        }

        suspend fun populateDatabase(
            storeDao: StoreDao,
            movementDao: MovementDao
        ) {
            try {
                Log.i(TAG, "Fetching data from API to populate the database")

                val api = StoreApi.retrofitService

                val stores = api.getAllData()
                Log.i(TAG, "${stores[0]}")
                storeDao.insertAll(stores.map { StoreRecord.toStoreEntity(it) })
                Log.i(TAG, "${StoreRecord.toStoreEntity(stores[0])}")
                Log.i(TAG, "Inserted stores into database")

                val movements = api.getMovements()
                Log.i(TAG, "Fetched ${movements.size} movements from API")
                movementDao.insertAll(movements.map { MovementRecord.toMovementEntity(it) })
                Log.i(TAG, "Inserted movements into database")

            } catch (e: Exception) {
                Log.e(TAG, "Error populating database: ${e.message}", e)
            }
        }
    }
}
