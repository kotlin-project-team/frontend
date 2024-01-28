package com.study.dongamboard.activity.user

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onSuccess
import com.study.dongamboard.R
import com.study.dongamboard.activity.user.SignInActivity.Companion.signInActivity
import com.study.dongamboard.api.APIObject
import com.study.dongamboard.model.response.MyInformationResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserActivity : AppCompatActivity() {

    private lateinit var myInfo: MyInformationResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        loadMyInfo()

        val btnUpdateMyInfo = findViewById<Button>(R.id.btnUpdateMyInfo)
        btnUpdateMyInfo.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                PasswordDialog({ Toast.makeText(applicationContext, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show() }, applicationContext)
                    .show(supportFragmentManager, "")
            }
        }

        val btnFinMyInfo = findViewById<Button>(R.id.btnFinMyInfo)
        btnFinMyInfo. setOnClickListener{
            if (SignInActivity.isInitialized()) {
                signInActivity.finish()
            }
            finish()
        }

        val btnDeleteAccount = findViewById<Button>(R.id.btnDeleteAccount)
        btnDeleteAccount.setOnClickListener {
            deleteUser()
        }
    }

    private fun loadMyInfo() {
        val tvPostTitle = findViewById<TextView>(R.id.tvInfoStuID)
        val tvPostContent = findViewById<TextView>(R.id.tvInfoNickname)

        CoroutineScope(Dispatchers.Main).launch {
            val response = APIObject.getRetrofitAPIService.getMyInformation()
            response.onSuccess {
                myInfo = data
                tvPostTitle.text = myInfo.studentId
                tvPostContent.text = myInfo.nickname
                Log.d("loadMyInfo", myInfo.nickname)
            }.onError {
                Log.d("statusCode", statusCode.toString())
                Log.d("error", errorBody.toString())
            }
        }
    }

    private fun deleteUser() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = APIObject.getRetrofitAPIService.deleteUser()
            response.onSuccess {
                finish()
            }.onError {
                Log.d("statusCode", statusCode.toString())
                Log.d("error", errorBody.toString())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadMyInfo()
    }
}