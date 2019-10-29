package com.apptt.axdecor.Network

import com.apptt.axdecor.db.Entities.ModelModel
import com.apptt.axdecor.db.Entities.Paint
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "http://axdecor.herokuapp.com"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface APIAxDecorService {
    //Obtiene Modelos
    @GET("/api/v1.0/models")
    fun obtenerModelos(): Deferred<List<ModelModel>>

    //Obtiene Proveedores
    @GET("/api/v1.0/providers")
    fun obtenerProveedoresAsync(): Deferred<List<NetworkProvider>>

    //Obtiene Modelos
    @GET("/api/v1.0/paint")
    fun obtenerPinturas(): Deferred<List<Paint>>

}

object AXDecorAPI {
    val retrofitService: APIAxDecorService by lazy {
        retrofit.create(APIAxDecorService::class.java)
    }
}