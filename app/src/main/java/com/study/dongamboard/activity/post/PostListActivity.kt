package com.study.dongamboard.activity.post

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lakue.pagingbutton.LakuePagingButton
import com.lakue.pagingbutton.OnPageSelectListener
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import com.study.dongamboard.R
import com.study.dongamboard.adapter.PostAdapter
import com.study.dongamboard.api.APIObject
import com.study.dongamboard.model.response.PostResponse
import com.study.dongamboard.type.BoardCategoryType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostListActivity : AppCompatActivity() {

    private lateinit var category: BoardCategoryType
    private lateinit var postAdapter: PostAdapter
    private lateinit var lvPost: ListView
    private lateinit var postList: ArrayList<PostResponse>

    private val displayPageItemSize = 6
    private var maxPageSize = 1
    private var nowPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_postlist)

        category = intent.getSerializableExtra("postCategory") as BoardCategoryType
        val tvPostListCategory = findViewById<TextView>(R.id.tvPostListCategory)
        tvPostListCategory.text = category.toString()

        lvPost = findViewById<ListView>(R.id.lvPost)
        postList = arrayListOf<PostResponse>()

        setPager()
        readAllPostsByPage()

        lvPost.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this, PostActivity::class.java)
            intent.putExtra("postData", postList[i])
            intent.putExtra("category", category)
            startActivity(intent)
        }

        val ivPostCreate = findViewById<ImageView>(R.id.ivPostCreate)
        ivPostCreate.setOnClickListener {
            val intent = Intent(this, PostCreateActivity::class.java)
            intent.putExtra("postCategory", category)
            startActivity(intent)
            nowPage = 1
        }

        val swipe = findViewById<SwipeRefreshLayout>(R.id.swipePostlistLayout)
        swipe.setOnRefreshListener {
            setPager()
            readAllPostsByPage()
            swipe.isRefreshing = false
        }
    }

    private fun setPager() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = APIObject.getRetrofitAPIService.getAllPost(0, 0, category)
            response.onSuccess {
                maxPageSize = data.postCount / displayPageItemSize
                Log.d("size", data.postCount.toString() + " " + displayPageItemSize)
                Log.d("maxPageSize", maxPageSize.toString())
                if (data.postCount % displayPageItemSize > 0) {
                    maxPageSize++
                }
                paging()
            }.onError {
                Log.e("statusCode", statusCode.code.toString() + " " + statusCode.toString())
                // TODO: status code에 따른 처리
            }
        }
    }

    private fun readAllPostsByPage() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = APIObject.getRetrofitAPIService.getAllPost(displayPageItemSize, nowPage - 1, category)
            response.onSuccess {
                postList = data.posts as ArrayList<PostResponse>
                postAdapter = PostAdapter(applicationContext, R.layout.post_adapter_view,
                    postList as MutableList<PostResponse>
                )
                postAdapter.notifyDataSetChanged()
                lvPost.adapter = postAdapter
            }.onError {
                Log.e("statusCode", statusCode.code.toString() + " " + statusCode.toString())
                // TODO: status code에 따른 처리
            }.onFailure {
                Log.e("failed",  this)
            }
        }
    }

    private fun paging() {
        val btnLpbPager = findViewById<LakuePagingButton>(R.id.btnLpbPager)
        var displayPageSize = 5

        if (maxPageSize < 5) {
            displayPageSize = maxPageSize
        }
        btnLpbPager.setPageItemCount(displayPageSize)
        btnLpbPager.addBottomPageButton(maxPageSize, nowPage)

        btnLpbPager.setOnPageSelectListener(object : OnPageSelectListener {
            override fun onPageBefore(clicked: Int) {
                nowPage = clicked
                btnLpbPager.addBottomPageButton(maxPageSize, clicked)
                readAllPostsByPage()
            }

            override fun onPageCenter(clicked: Int) {
                nowPage = clicked
                readAllPostsByPage()
            }

            override fun onPageNext(clicked: Int) {
                nowPage = clicked
                btnLpbPager.addBottomPageButton(maxPageSize, clicked)
                readAllPostsByPage()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        setPager()
        readAllPostsByPage()
    }
}