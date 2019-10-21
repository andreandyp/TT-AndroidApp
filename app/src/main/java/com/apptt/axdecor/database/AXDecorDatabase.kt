package com.apptt.axdecor.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.apptt.axdecor.database.DAO.AXDecorDao
import com.apptt.axdecor.database.Entities.*

@Database(entities = arrayOf(Model::class, Provider::class, ARScene::class, SocialNetwork::class, Store::class, Color::class, Email::class, Phone::class, PredefinedStyle::class), version = 1)
public abstract class AXDecorDatabase : RoomDatabase() {
    abstract fun axdecorDao(): AXDecorDao

    companion object {
        @Volatile
        private var INSTANCE: AXDecorDatabase? = null

        fun getDatabase(context: Context): AXDecorDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AXDecorDatabase::class.java,
                    "axdecor_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}