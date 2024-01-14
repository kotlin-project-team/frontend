package com.study.dongamboard.activity.notice

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.study.dongamboard.R
import com.study.dongamboard.api.APIObject
import com.study.dongamboard.model.request.NoticeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoticeCreateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        noticeDB = NoticeDB.getInstance(this)!!

        // TODO: 추후 title 추가
//        val etPostCreateTitle = findViewById<EditText>(R.id.etPostCreateTitle)
        val etPostCreateContent = findViewById<EditText>(R.id.etPostCreateContent)

        val ivCreatePostBtn = findViewById<ImageView>(R.id.ivCreatePostBtn)
        ivCreatePostBtn.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                val noticeRequest = NoticeRequest(
                    etPostCreateContent.text.toString())
                APIObject.getRetrofitAPIService.createNotice(noticeRequest)
            }
            finish()
        }
    }
}