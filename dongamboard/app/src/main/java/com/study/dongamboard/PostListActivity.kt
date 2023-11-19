package com.study.dongamboard

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.study.dongamboard.adapter.PostAdapter
import com.study.dongamboard.api.APIObject
import com.study.dongamboard.db.PostDB
import com.study.dongamboard.model.response.PostResponse
import com.study.dongamboard.type.BoardCategoryType
import com.study.dongamboard.type.ResponseStatusType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostListActivity : AppCompatActivity() {

    lateinit var category: BoardCategoryType
    lateinit var postAdapter: PostAdapter
    lateinit var lvPost: ListView
    lateinit var postDB: PostDB
    lateinit var postList: ArrayList<PostResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_postlist)

        category = intent.getSerializableExtra("postCategory") as BoardCategoryType
        val tvPostListCategory = findViewById<TextView>(R.id.tvPostListCategory)
        tvPostListCategory.setText(category.toString())

        postDB = PostDB.getInstance(this)!!

        lvPost = findViewById<ListView>(R.id.lvPost)
        postList = arrayListOf<PostResponse>()
        readAllPosts()

        lvPost.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this, PostActivity::class.java)
            intent.putExtra("postData", postList.get(i))
            startActivity(intent)
        }

        val ivPostCreate = findViewById<ImageView>(R.id.ivPostCreate)
        ivPostCreate.setOnClickListener {
            val intent = Intent(this, PostCreateActivity::class.java)
            intent.putExtra("postCategory", category)
            startActivity(intent)
        }

        var swipe = findViewById<SwipeRefreshLayout>(R.id.swipePostlistLayout)
        swipe.setOnRefreshListener {
            readAllPosts()
            swipe.isRefreshing = false
        }
    }

    private fun readAllPosts() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = APIObject.getRetrofitAPIService.getAllPost(displayPageItemSize, nowPage - 1, category)
            if (response.code == ResponseStatusType.SUCCESS.code) {
                postList = response.result as ArrayList<PostResponse>
                postAdapter = PostAdapter(applicationContext, R.layout.post_adapter_view,
                    postList as MutableList<PostResponse>
                )
                postAdapter.notifyDataSetChanged()
                lvPost.adapter = postAdapter
            }
        }
    }

    override fun onResume() {
        readAllPosts()
        super.onResume()
    }

    override fun onDestroy() {
        PostDB.destroyInstance()
        super.onDestroy()
    }
}