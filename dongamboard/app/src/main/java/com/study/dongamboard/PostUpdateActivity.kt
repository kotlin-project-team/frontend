package com.study.dongamboard

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.study.dongamboard.api.APIObject
import com.study.dongamboard.db.PostDB
import com.study.dongamboard.model.PostResponse
import com.study.dongamboard.model.request.PostRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostUpdateActivity : AppCompatActivity() {

    lateinit var post: PostResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        post = intent.getSerializableExtra("postData") as PostResponse

        val etPostCreateTitle = findViewById<EditText>(R.id.etPostCreateTitle)
        val etPostCreateContent = findViewById<EditText>(R.id.etPostCreateContent)

        etPostCreateTitle.setText(post.title)
        etPostCreateContent.setText(post.content)

        val ivCreatePostBtn = findViewById<ImageView>(R.id.ivCreatePostBtn)
        ivCreatePostBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val postRequest = PostRequest(
                    etPostCreateTitle.text.toString(),
                    etPostCreateContent.text.toString(),
                    post.category!!
                )
                APIObject.getRetrofitAPIService.updatePost(post.id, postRequest)
                finish()
            }
        }
    }

    override fun onDestroy() {
        PostDB.destroyInstance()
        super.onDestroy()
    }
}