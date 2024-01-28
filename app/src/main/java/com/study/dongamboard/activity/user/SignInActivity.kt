package com.study.dongamboard.activity.user

import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onSuccess
import com.study.dongamboard.R
import com.study.dongamboard.activity.MainActivity.Companion.tokenDataStore
import com.study.dongamboard.api.APIObject
import com.study.dongamboard.api.TokenAuthenticator
import com.study.dongamboard.model.request.SignInRequest
import com.study.dongamboard.type.ResponseStatusType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInActivity : AppCompatActivity() {

    companion object{
        lateinit var signInActivity: SignInActivity
        fun isInitialized() = ::signInActivity.isInitialized
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        signInActivity = this

        val etSignInStuId = findViewById<EditText>(R.id.etSignInStuId)
        val etSignInPwd = findViewById<EditText>(R.id.etSignInPwd)
        etSignInStuId.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        val btnSignIn = findViewById<Button>(R.id.btnSignIn)
        btnSignIn.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val signInReq = SignInRequest(etSignInStuId.text.toString(), etSignInPwd.text.toString())
                val response = APIObject.getRetrofitAPIService.signIn(signInReq)
                response.onSuccess {
                    CoroutineScope(Dispatchers.IO).launch {
                        saveAccessToken(data.accessToken)
                        saveRefreshToken(data.refreshToken)
                    }
                    val intent = Intent(applicationContext, UserActivity::class.java)
                    startActivity(intent)
                }.onError {
                    Log.d("statusCode", statusCode.toString())
                    Log.d("error", errorBody.toString())
                    if (statusCode.code == ResponseStatusType.BAD_REQUEST.code) {
                        Toast.makeText(applicationContext, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                        etSignInPwd.setText("")
                    }
                }
            }
        }

        val btnSignUp = findViewById<Button>(R.id.btnSignUp)
        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private suspend fun saveAccessToken(accessToken: String) {
        tokenDataStore.edit { preferences ->
            preferences[TokenAuthenticator.PreferenceKeys.ACCESS_TOKEN] = accessToken
        }
    }

    private suspend fun saveRefreshToken(refreshToken: String) {
        tokenDataStore.edit { preferences ->
            preferences[TokenAuthenticator.PreferenceKeys.REFRESH_TOKEN] = refreshToken
        }
    }
}