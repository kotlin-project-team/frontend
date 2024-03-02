package com.study.dongamboard.activity.user

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import com.study.dongamboard.R
import com.study.dongamboard.api.APIObject
import com.study.dongamboard.api.Utils
import com.study.dongamboard.model.request.UpdateNicknameRequest
import com.study.dongamboard.model.request.UpdatePasswordRequest
import com.study.dongamboard.type.ResponseStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserUpdateActivity : AppCompatActivity() {

    private val utils: Utils by lazy {
        Utils(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_update)

        loadMyInfo()

        val btnUpdatePwd = findViewById<Button>(R.id.btnUpdatePwd)
        btnUpdatePwd.setOnClickListener {
            updatePassword()
        }

        val btnUpdateNick = findViewById<Button>(R.id.btnUpdateNick)
        btnUpdateNick.setOnClickListener {
            updateNickname()
        }
    }

    private fun loadMyInfo() {
        val tvStuId = findViewById<TextView>(R.id.tvStuId)
        val etNewNickname = findViewById<EditText>(R.id.etNewNickname)
        CoroutineScope(Dispatchers.Main).launch {
            val response = APIObject.getRetrofitAPIService.getMyInformation()
            response.onSuccess {
                tvStuId.text = data.result.studentId
                etNewNickname.setText(data.result.nickname)

                utils.logD(statusCode)
            }.onError {
                val errorMsg = utils.logE(statusCode)
                Toast.makeText(applicationContext, errorMsg, Toast.LENGTH_SHORT).show()
            }.onFailure {
                utils.logE(this)
            }
        }
    }

    private fun updatePassword() {
        val etOldPassword = findViewById<EditText>(R.id.etOldPassword)
        val etNewPassword = findViewById<EditText>(R.id.etNewPassword)

        CoroutineScope(Dispatchers.Main).launch {
            val updatePasswordReq = UpdatePasswordRequest(etOldPassword.text.toString(), etNewPassword.text.toString())
            val response = APIObject.getRetrofitAPIService.updatePassword(updatePasswordReq)
            response.onSuccess {
                Toast.makeText(applicationContext, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }.onError {
                utils.logE(statusCode)
                if (statusCode.code == ResponseStatus.BAD_REQUEST.code) {
                    Toast.makeText(applicationContext, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                    etOldPassword.setText("")
                    etNewPassword.setText("")
                }
            }

            response.onSuccess {
                Toast.makeText(applicationContext, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show()
                utils.logD(statusCode)
                finish()
            }.onError {
                utils.logE(statusCode)
                if (statusCode.code == ResponseStatus.BAD_REQUEST.code) {
                    Toast.makeText(applicationContext, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                    etOldPassword.setText("")
                    etNewPassword.setText("")
                }
            }.onFailure {
                utils.logE(this)
            }
        }
    }

    private fun updateNickname() {
        val etNewNickname = findViewById<EditText>(R.id.etNewNickname)

        CoroutineScope(Dispatchers.Main).launch {
            val updateNicknameReq = UpdateNicknameRequest(etNewNickname.text.toString())
            val response = APIObject.getRetrofitAPIService.updateNickname(updateNicknameReq)
            response.onSuccess {
                Toast.makeText(applicationContext, "닉네임이 변경되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }.onError {
                utils.logD(statusCode.toString())
                utils.logD(errorBody.toString())
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