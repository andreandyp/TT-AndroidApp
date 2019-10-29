package com.apptt.axdecor.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.apptt.axdecor.db.DAO.ModelDAO
import com.apptt.axdecor.db.DAO.ProviderDAO
import com.apptt.axdecor.db.Entities.*

@Database(entities = arrayOf(ModelModel::class, ProviderModel::class, ARScene::class, SocialNetworkModel::class, StoreModel::class, Paint::class, PredefinedStyle::class), version = 1)
abstract class AXDecorDatabase : RoomDatabase() {
    abstract fun modelDAO(): ModelDAO
    abstract fun providerDAO(): ProviderDAO

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