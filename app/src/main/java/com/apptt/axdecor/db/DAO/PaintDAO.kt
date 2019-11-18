package com.apptt.axdecor.db.DAO
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.apptt.axdecor.db.Entities.PaintModel
import com.apptt.axdecor.db.queries.PaintsWithProviderModel

@Dao
interface PaintDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaint(vararg paintModel: PaintModel)

    @Query("""SELECT pa.*, p.id_provider, p.name AS proveedor
            FROM PaintModel pa, ProviderModel p
            WHERE pa.idProvider = p.id_provider""")
    suspend fun getPaints(): List<PaintsWithProviderModel>
}