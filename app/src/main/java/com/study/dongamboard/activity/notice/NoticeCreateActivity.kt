package com.study.dongamboard.activity.notice

import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import com.study.dongamboard.R
import com.study.dongamboard.api.APIObject
import com.study.dongamboard.api.Utils
import com.study.dongamboard.model.request.NoticeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoticeCreateActivity : AppCompatActivity() {

    private val utils: Utils by lazy {
        Utils(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        val etPostCreateTitle = findViewById<EditText>(R.id.etPostCreateTitle)
        val etPostCreateContent = findViewById<EditText>(R.id.etPostCreateContent)

        etPostCreateTitle.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS

        val ivCreatePostBtn = findViewById<ImageView>(R.id.ivCreatePostBtn)
        ivCreatePostBtn.setOnClickListener{
            val noticeRequest = NoticeRequest(
                etPostCreateTitle.text.toString(),
                etPostCreateContent.text.toString()
            )
            CoroutineScope(Dispatchers.IO).launch {
                val response = APIObject.getRetrofitAPIService.createNotice(noticeRequest)
                response.onSuccess {
                    utils.logD(statusCode)
                    finish()
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