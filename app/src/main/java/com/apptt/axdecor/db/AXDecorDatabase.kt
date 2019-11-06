package com.apptt.axdecor.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.apptt.axdecor.db.DAO.DataDAO
import com.apptt.axdecor.db.DAO.ModelDAO
import com.apptt.axdecor.db.DAO.ProviderDAO
import com.apptt.axdecor.db.Entities.*

@Database(
    entities = [
        ARScene::class,
        CategoryModel::class,
        ModelHasCategoryModel::class,
        ModelHasPredefinedStyleModel::class,
        ModelModel::class,
        PaintModel::class,
        PredefinedStyleModel::class,
        ProviderHasCategoryModel::class,
        ProviderModel::class,
        SocialNetworkModel::class,
        StoreModel::class,
        TypeModel::class,
        ModelHasTypeModel::class
    ], version = 1, exportSchema = false
)
abstract class AXDecorDatabase : RoomDatabase() {
    abstract fun modelDAO(): ModelDAO
    abstract fun providerDAO(): ProviderDAO
    abstract fun dataDAO(): DataDAO

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