package com.example.comicslibrary.model.api

import com.example.comicslibrary.BuildConfig
import com.example.comicslibrary.getHash
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val BASE_URL = "http://gateaway.marvel.com/v1/public/"

    private fun getRetrofit(): Retrofit {
        val timestampForEachRequest = System.currentTimeMillis().toString()
        val apiSecret = BuildConfig.MARVEL_SECRET
        val apiKey = BuildConfig.MARVEL_KEY

        //md5 hash(kryptografikos algorithmos), google it and built it
        /*
        einai to hash poy xreiazetai gia kathe retrofit request, den eimai
        sigourh an einai gia to sygekrimeno service h an to theloume genika sto retrofit
        * */
        val hash = getHash(timestampForEachRequest, apiSecret, apiKey)

        /*
        *Gia ton kwdika:
        * */
        val clientInterceptor = Interceptor { chain ->
            var request: Request = chain.request() //kanoume retrieve to request
            val url: HttpUrl = request.url.newBuilder() //toy prosthetoume kapoies parametrous
                .addQueryParameter("timestamp", timestampForEachRequest)
                .addQueryParameter("apikey", apiKey)
                .addQueryParameter("hash", hash)
                .build() //kai sunexizoume to requset me to chain
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }

        val client = OkHttpClient.Builder().addInterceptor(clientInterceptor).build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) //auto metatrepei th json apanthsh toy service sto object p theloume
            .client(client)
            .build()
    }

    val api: MarvelApi = getRetrofit().create(MarvelApi:: class.java)
}