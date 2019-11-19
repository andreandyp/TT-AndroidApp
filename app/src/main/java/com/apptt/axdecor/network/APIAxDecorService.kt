package com.apptt.axdecor.network

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
    fun obtenerModelosAsync(): Deferred<List<NetworkModel>>

    //Obtiene Proveedores
    @GET("/api/v1.0/providers")
    fun obtenerProveedoresAsync(): Deferred<List<NetworkProvider>>

    //Obtiene Modelos
    @GET("/api/v1.0/paints")
    fun obtenerPinturasAsync(): Deferred<List<NetworkPaint>>

    @GET("/api/v1.0/data")
    fun obtenerDatosAsync(): Deferred<NetworkDataContainer>

}

object AXDecorAPI {
    val retrofitService: APIAxDecorService by lazy {
        retrofit.create(APIAxDecorService::class.java)
    }
}