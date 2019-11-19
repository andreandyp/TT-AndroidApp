package com.apptt.axdecor.utilities

import com.apptt.axdecor.db.Entities.ARSceneHasModelModel
import com.apptt.axdecor.db.Entities.ARSceneModel
import com.apptt.axdecor.db.Entities.ModelModel
import com.apptt.axdecor.network.NetworkARScene
import com.apptt.axdecor.network.NetworkModelScene

object ARSceneUtils {
    fun convertToARSceneModel(escenas: List<NetworkARScene>): Array<ARSceneModel> {
        return escenas.map {
            ARSceneModel(
                idARScene = it.idARScene,
                name = it.name,
                imagen = it.imagen,
                idProvider = it.Provider_idProvider,
                idType = it.type.idType,
                idPredefinedStyle = it.style.idPredefinedStyle
            )
        }.toTypedArray()

    }

    fun extractModelsFromScene(models: List<NetworkModelScene>, idARScene: Int): Array<ARSceneHasModelModel> {
        return models.map {
            ARSceneHasModelModel(
                idARSceneModel = 0,
                idModel = it.idModel,
                idARScene = idARScene
            )
        }.toTypedArray()
    }
}