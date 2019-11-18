package com.apptt.axdecor.utilities

import com.apptt.axdecor.db.Entities.PaintModel
import com.apptt.axdecor.network.NetworkPaint

object PaintNetworkUtils {
    fun convertToPaintModel(networkPaints: List<NetworkPaint>): Array<PaintModel> {
        return networkPaints.map {
            PaintModel(
                idPaint = it.idPaint,
                name = it.name,
                vendorCode = it.vendorCode,
                rgbCode = it.rgbCode,
                hexCode = it.hexCode,
                price = it.price,
                presentacion = it.presentacion,
                idProvider = it.Provider_idProvider
            )
        }.toTypedArray()
    }
}