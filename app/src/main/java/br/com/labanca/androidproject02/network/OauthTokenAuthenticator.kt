package br.com.labanca.androidproject02.network

import android.util.Log
import br.com.labanca.androidproject02.util.SharedPreferenceUtils
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

private const val TAG = "OauthTokenAuthenticator"

class OauthTokenAuthenticator(): Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = retrieveNewToken()
        SharedPreferenceUtils.saveToken(token.accessToken, token.expiresIn)

        return response.request().newBuilder()
            .header("Authorization", "Bearer ${token.accessToken}")
            .build()
    }

    //After executes(), the response contains the http status code, isSucessfull, etc
    //because the getToken method is blocking we can do .body()
    private fun retrieveNewToken(): OauthTokenResponse {
        Log.i(TAG, "Retrieving new token")
        return SalesApi.retrofitService.getToken(
            "Basic c2llY29sYTptYXRpbGRl",
            "password",
            "lucasllabanca@dm125.com.br",
            "dm125"
        ).execute().body()!!
    }

}