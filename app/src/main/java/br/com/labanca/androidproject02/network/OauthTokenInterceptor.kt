package br.com.labanca.androidproject02.network

import android.util.Log
import br.com.labanca.androidproject02.util.SharedPreferenceUtils
import okhttp3.Interceptor
import okhttp3.Response

private const val TAG = "OauthTokenInterceptor"

class OauthTokenInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        Log.i(TAG, "Fetching access token from shared preference")
        val accessToken = SharedPreferenceUtils.getAccessToken()

        if (accessToken != null) {
            Log.i(TAG, "Using the existing token")
            request = request.newBuilder()
                .addHeader("Authorization", "Bearer ${accessToken}")
                .build()
        }

        return chain.proceed(request)
    }

}