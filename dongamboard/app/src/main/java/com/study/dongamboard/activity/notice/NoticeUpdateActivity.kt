package com.study.dongamboard.activity.notice

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.study.dongamboard.R
import com.study.dongamboard.api.APIObject
import com.study.dongamboard.model.request.NoticeRequest
import com.study.dongamboard.model.response.NoticeResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoticeUpdateActivity : AppCompatActivity() {

    private lateinit var notice: NoticeResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        notice = intent.getSerializableExtra("noticeData") as NoticeResponse

        // TODO: title 추가 시 설정
//        val etPostCreateTitle = findViewById<EditText>(R.id.etPostCreateTitle)
        val etPostCreateContent = findViewById<EditText>(R.id.etPostCreateContent)

//        etPostCreateTitle.setText(notice.title)
        etPostCreateContent.setText(notice.content)

        val ivCreatePostBtn = findViewById<ImageView>(R.id.ivCreatePostBtn)
        ivCreatePostBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val noticeRequest = NoticeRequest(
                    etPostCreateContent.text.toString()
                )
                APIObject.getRetrofitAPIService.updateNotice(notice.id, noticeRequest)
                finish()
            }
        }
    }
}