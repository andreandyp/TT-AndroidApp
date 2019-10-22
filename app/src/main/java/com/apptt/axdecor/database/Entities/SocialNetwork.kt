package com.apptt.axdecor.database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class SocialNetwork(
    @ColumnInfo(name = "id_social_network") @PrimaryKey @NotNull val idSocialNetwork: Int,
    @ColumnInfo(name = "social_network_url") val socialNetworkURL:String,
    @ColumnInfo(name = "id_provider")
    @ForeignKey(
        entity = Provider::class,
        parentColumns = ["id_provider"],
        childColumns = ["id_provider"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    ) val idProvider: Int
)