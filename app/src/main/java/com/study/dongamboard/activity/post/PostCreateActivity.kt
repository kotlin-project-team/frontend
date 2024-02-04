package com.study.dongamboard.activity.post

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
import com.study.dongamboard.model.request.PostRequest
import com.study.dongamboard.type.BoardCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostCreateActivity : AppCompatActivity() {

    private val utils: Utils by lazy {
        Utils(this)
    }

    private lateinit var category: BoardCategory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        category = intent.getSerializableExtra("postCategory") as BoardCategory

        val etPostCreateTitle = findViewById<EditText>(R.id.etPostCreateTitle)
        val etPostCreateContent = findViewById<EditText>(R.id.etPostCreateContent)

        val ivCreatePostBtn = findViewById<ImageView>(R.id.ivCreatePostBtn)
        ivCreatePostBtn.setOnClickListener{
            val postRequest = PostRequest(
                etPostCreateTitle.text.toString(),
                etPostCreateContent.text.toString(),
                category
            )
            CoroutineScope(Dispatchers.IO).launch {
                val response = APIObject.getRetrofitAPIService.createPost(postRequest)
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