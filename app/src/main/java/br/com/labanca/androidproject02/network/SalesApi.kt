package br.com.labanca.androidproject02.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://sales-provider.appspot.com"

//allows json manipulation with tags and more, with JsonAdaptor
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//client http with configs
//the interceptor holds the requests and adds headers, like the access token, and then continue with it
//the authenticator executes when the request runs and returns an authentication error
private val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(60, TimeUnit.SECONDS)
    .addInterceptor(OauthTokenInterceptor())
    .authenticator(OauthTokenAuthenticator())
    .build()

//retrofit client with the configs previously added
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .client(okHttpClient)
    .build()

//Methods should be async and non-blocking, then we use Deferred from coroutines
interface SalesApiService {

    @GET("api/products")
    fun getProducts(): Deferred<List<Product>>

    @GET("api/products/{code}")
    fun getProductByCode(@Path("code") code: String): Deferred<Product>

    //client authentication with blocking request using Call
    @POST("oauth/token")
    @FormUrlEncoded
    fun getToken(
        @Header("Authorization") basicAuthentication: String,
        @Field("grant_type") grantType: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<OauthTokenResponse>
}

object SalesApi {
    val retrofitService: SalesApiService by lazy {
        retrofit.create(SalesApiService::class.java)
    }
}