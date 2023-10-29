package com.study.dongamboard

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.study.dongamboard.api.ApiObject
import com.study.dongamboard.db.PostDB
import com.study.dongamboard.model.request.PostRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostCreateActivity : AppCompatActivity() {

    lateinit var postDB : PostDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        postDB = PostDB.getInstance(this)!!

        val etPostCreateTitle = findViewById<EditText>(R.id.etPostCreateTitle)
        val etPostCreateContent = findViewById<EditText>(R.id.etPostCreateContent)

        val ivCreatePostBtn = findViewById<ImageView>(R.id.ivCreatePostBtn)
        ivCreatePostBtn.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                val postRequest = PostRequest(
                    etPostCreateTitle.text.toString(),
                    etPostCreateContent.text.toString(),
                    "자유게시판")
                ApiObject.getRetrofitAPIService.createPost(postRequest)
            }
            finish()
        }
    }

    override fun onDestroy() {
        PostDB.destroyInstance()
        super.onDestroy()
    }
}