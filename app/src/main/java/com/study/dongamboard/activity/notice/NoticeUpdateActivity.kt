package com.study.dongamboard.activity.notice

import android.os.Bundle
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
import com.study.dongamboard.model.response.NoticeResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoticeUpdateActivity : AppCompatActivity() {

    private lateinit var notice: NoticeResponse
    private val utils: Utils by lazy {
        Utils(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        notice = intent.getSerializableExtra("noticeData") as NoticeResponse

        val etPostCreateTitle = findViewById<EditText>(R.id.etPostCreateTitle)
        val etPostCreateContent = findViewById<EditText>(R.id.etPostCreateContent)

        etPostCreateTitle.setText(notice.title)
        etPostCreateContent.setText(notice.content)

        val ivCreatePostBtn = findViewById<ImageView>(R.id.ivCreatePostBtn)
        ivCreatePostBtn.setOnClickListener {
            val noticeRequest = NoticeRequest(
                etPostCreateTitle.text.toString(),
                etPostCreateContent.text.toString()
            )
            CoroutineScope(Dispatchers.IO).launch {
                val response = APIObject.getRetrofitAPIService.updateNotice(notice.id, noticeRequest)
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