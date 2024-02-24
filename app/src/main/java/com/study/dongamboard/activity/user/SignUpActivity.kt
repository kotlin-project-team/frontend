package com.study.dongamboard.activity.user

import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import com.study.dongamboard.R
import com.study.dongamboard.api.APIObject
import com.study.dongamboard.api.Utils
import com.study.dongamboard.model.request.UserRequest
import com.study.dongamboard.type.UserRole
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {

    private val utils: Utils by lazy {
        Utils(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val etStuId = findViewById<EditText>(R.id.etStuId)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etNickname = findViewById<EditText>(R.id.etNickname)
        val deviceId = Settings.Secure.getString(applicationContext.contentResolver, Settings.Secure.ANDROID_ID)

        val btnUserCreate = findViewById<Button>(R.id.btnUserCreate)
        btnUserCreate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val userRequest = UserRequest(
                    etStuId.text.toString(),
                    etPassword.text.toString(),
                    etNickname.text.toString(),
                    deviceId,
                    UserRole.ROLE_USER
                )
                //TODO: 입력값 Filtering

                val response = APIObject.getRetrofitAPIService.createUser(userRequest)
                response.onSuccess {
                    runOnUiThread { Toast.makeText(applicationContext, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show() }
                    finish()
                }.onError {
                    utils.logD(statusCode.code.toString() + " " + statusCode.toString())
                    // TODO: status code에 따른 처리
                }
                response.onSuccess {
                    utils.logD(statusCode)
                }.onError {
                    val errorMsg = utils.logE(statusCode)
                    Toast.makeText(applicationContext, errorMsg, Toast.LENGTH_SHORT).show()
                }.onFailure {
                    utils.logE(this)
                }
            }
        }
    }
}