package com.study.dongamboard

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.study.dongamboard.db.PostDB
import com.study.dongamboard.model.PostData

class PostCreateActivity : AppCompatActivity() {

    var postDB : PostDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        this.window?.apply {
            this.statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        postDB = PostDB.getInstance(this)

        val createPostRunnable = Runnable {
            val newPost = PostData(
                5,
                20200982,
                findViewById<EditText>(R.id.etPostCreateTitle).text.toString(),
                findViewById<EditText>(R.id.etPostCreateContent).text.toString(),
                "일반",
                0,
                0)
            postDB?.postDao()?.insertPost(newPost)
            Log.d("InsertedPostList", postDB?.postDao()?.findAll().toString())
        }

        val ivCreatePostBtn = findViewById<ImageView>(R.id.ivCreatePostBtn)
        ivCreatePostBtn.setOnClickListener{
            val createPostThread = Thread(createPostRunnable)
            createPostThread.start()
            finish()
        }

    }

    override fun onDestroy() {
        PostDB.destroyInstance()
        super.onDestroy()
    }

}