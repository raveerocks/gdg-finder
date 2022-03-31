package io.raveerocks.gdgfinder.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface GDGService {

    companion object {

        private lateinit var INSTANCE: GDGService
        private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"

        fun getGDGService(): GDGService {
            synchronized(GDGService::class.java) {
                if (!::INSTANCE.isInitialized) {
                    val moshi = Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                    val retrofit = Retrofit.Builder()
                        .addConverterFactory(MoshiConverterFactory.create(moshi))
                        .addCallAdapterFactory(CoroutineCallAdapterFactory())
                        .baseUrl(BASE_URL)
                        .build()
                    INSTANCE = retrofit.create(GDGService::class.java)
                }
            }
            return INSTANCE
        }

    }

    @GET("gdg-directory.json")
    fun getChaptersAsync(): Deferred<GdgResponse>


}