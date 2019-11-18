package com.apptt.axdecor.db.DAO
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.apptt.axdecor.db.Entities.PaintModel

@Dao
interface PaintDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaint(vararg paintModel: PaintModel)
}