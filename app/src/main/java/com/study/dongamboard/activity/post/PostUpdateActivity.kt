package com.study.dongamboard.activity.post

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import com.study.dongamboard.R
import com.study.dongamboard.api.APIObject
import com.study.dongamboard.model.response.PostResponse
import com.study.dongamboard.model.request.PostRequest
import com.study.dongamboard.type.BoardCategoryType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostUpdateActivity : AppCompatActivity() {

    private lateinit var post: PostResponse
    private lateinit var category: BoardCategoryType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        post = intent.getSerializableExtra("postData") as PostResponse
        category = intent.getSerializableExtra("category") as BoardCategoryType

        val etPostCreateTitle = findViewById<EditText>(R.id.etPostCreateTitle)
        val etPostCreateContent = findViewById<EditText>(R.id.etPostCreateContent)

        etPostCreateTitle.setText(post.title)
        etPostCreateContent.setText(post.content)

        val ivCreatePostBtn = findViewById<ImageView>(R.id.ivCreatePostBtn)
        ivCreatePostBtn.setOnClickListener {
            val postRequest = PostRequest(
                etPostCreateTitle.text.toString(),
                etPostCreateContent.text.toString(),
                category
            )
            CoroutineScope(Dispatchers.IO).launch {
                val response = APIObject.getRetrofitAPIService.updatePost(post.id, postRequest)
                response.onSuccess {
                    finish()
                }.onError {
                    Log.e("statusCode", statusCode.code.toString() + " " + statusCode.toString())
                    // TODO: status code에 따른 처리
                }.onFailure {
                    Log.e("failed",  this)
                }
            }
        }
    }
}