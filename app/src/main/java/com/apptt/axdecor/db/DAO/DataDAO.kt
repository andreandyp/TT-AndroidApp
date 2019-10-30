package com.apptt.axdecor.db.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.apptt.axdecor.db.Entities.CategoryModel
import com.apptt.axdecor.db.Entities.PredefinedStyleModel
import com.apptt.axdecor.db.Entities.TypeModel

@Dao
interface DataDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTypes(vararg typeModel: TypeModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStyles(vararg styleModel: PredefinedStyleModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(vararg categoryModel: CategoryModel)

    @Query("SELECT * FROM categorymodel")
    fun getAll(): List<CategoryModel>
}