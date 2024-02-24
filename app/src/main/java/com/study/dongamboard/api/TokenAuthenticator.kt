package com.study.dongamboard.api

import android.content.Intent
import android.util.Log
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.study.dongamboard.activity.MainActivity
import com.study.dongamboard.activity.MainActivity.Companion.tokenDataStore
import com.study.dongamboard.activity.user.SignInActivity
import com.study.dongamboard.api.TokenAuthenticator.PreferenceKeys.ACCESS_TOKEN
import com.study.dongamboard.api.TokenAuthenticator.PreferenceKeys.REFRESH_TOKEN
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.io.IOException
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
) : Authenticator {

    object PreferenceKeys {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val isPathRefresh = response.request.url.toString() == (route?.address?.url ?: String)
        Log.d("route address", route?.address?.url.toString())
        Log.d("url", response.request.url.toString())
        Log.d("isPathRefresh", isPathRefresh.toString())

        if (response.code >= 401) { // && !isPathRefresh) {   // UNAUTHORIZED 발생 시 재요청
            val tokenRefreshSuccess = runBlocking { fetchUpdateToken() }

            return if (tokenRefreshSuccess) {
                Log.d("tokenRefreshSuccess", tokenRefreshSuccess.toString())
                val newToken = runBlocking { fetchAccessToken() }
                response.request.newBuilder().apply {
                    removeHeader("Authorization")
                    addHeader("Authorization", "Bearer $newToken")
                }.build()
            } else {    // RefreshToken 만료
                Log.d("tokenRefreshFail", tokenRefreshSuccess.toString())
                val intent = Intent(MainActivity.applicationContext(), SignInActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                MainActivity.applicationContext().startActivity(intent)
                null
            }
        }
        return null
    }

    private suspend fun fetchAccessToken(): String {
        val flow = MainActivity.applicationContext().tokenDataStore.data
            .catch { exception ->
                when (exception) {
                    is IOException -> emit(emptyPreferences())
                    else -> throw exception
                }
            }
            .map { preferences ->
                preferences[ACCESS_TOKEN]
            }
        return flow.firstOrNull().orEmpty()
    }

    private suspend fun fetchUpdateToken(): Boolean {
        val flow = MainActivity.applicationContext().tokenDataStore.data
            .catch { exception ->
                when (exception) {
                    is IOException -> emit(emptyPreferences())
                    else -> throw exception
                }
            }
            .map { preferences ->
                preferences[REFRESH_TOKEN]
            }
        val request = runBlocking {
            // TODO: refresh token 사용하여 access token 받아와서 저장
            val refreshToken = flow.firstOrNull().orEmpty()

        }
        return false
    }
}