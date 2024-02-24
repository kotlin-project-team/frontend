package com.study.dongamboard.fragment.post

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lakue.pagingbutton.OnPageSelectListener
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import com.study.dongamboard.R
import com.study.dongamboard.activity.NaviActivity
import com.study.dongamboard.activity.post.PostActivity
import com.study.dongamboard.activity.post.PostCreateActivity
import com.study.dongamboard.adapter.PostAdapter
import com.study.dongamboard.api.APIObject
import com.study.dongamboard.databinding.FragmentPostListBinding
import com.study.dongamboard.model.response.PostResponse
import com.study.dongamboard.type.BoardCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostListFragment : Fragment() {

    private lateinit var binding: FragmentPostListBinding

    private lateinit var postAdapter: PostAdapter
    private lateinit var postList: ArrayList<PostResponse>
    private val displayPageItemSize = 6
    private var maxPageSize = 1
    private var nowPage = 1
    private var category = BoardCategory.자유게시판

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostListBinding.inflate(inflater, container, false)

        postList = arrayListOf<PostResponse>()

        setPager()
        readAllPostsByPage()

        binding.tvPostListCategory.text = category.toString()
        binding.postListLayout.setOnTouchListener { view, motionEvent ->
            var touchPoint = 0f
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    touchPoint = motionEvent.x
                }
                MotionEvent.ACTION_UP -> {
                    touchPoint -= motionEvent.x
                    if (Math.abs(touchPoint) > 100) {
                        if (touchPoint > 0) {
                            category = category.next()
                        } else {
                            category = category.previous()
                        }
                        binding.tvPostListCategory.text = category.toString()
                        readAllPostsByPage()
                    }
                }
            }
            true
        }

        binding.lvPost.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(NaviActivity.applicationContext(), PostActivity::class.java)
            intent.putExtra("postData", postList[i])
            intent.putExtra("category", category)
            startActivity(intent)
        }

        binding.ivPostCreate.setOnClickListener {
            val intent = Intent(NaviActivity.applicationContext(), PostCreateActivity::class.java)
            intent.putExtra("postCategory", category)
            startActivity(intent)
            nowPage = 1
        }

        val swipe = binding.swipePostlistLayout
        swipe.setOnRefreshListener {
            setPager()
            readAllPostsByPage()
            swipe.isRefreshing = false
        }

        return binding.root
    }

    private fun setPager() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = APIObject.getRetrofitAPIService.getAllPost(0, 0, category)
            response.onSuccess {
                if (data.result.postCount == 0) {
                    maxPageSize = 1
                } else {
                    maxPageSize = data.result.postCount / displayPageItemSize
                }
                if (data.result.postCount % displayPageItemSize > 0) {
                    maxPageSize++
                }
                paging()

                NaviActivity.utils.logD("size" + data.result.postCount.toString() + " " + displayPageItemSize)
                NaviActivity.utils.logD("maxPageSize: $maxPageSize")
                NaviActivity.utils.logD(statusCode)
            }.onError {
                val errorMsg = NaviActivity.utils.logE(statusCode)
                Toast.makeText(NaviActivity.applicationContext(), errorMsg, Toast.LENGTH_SHORT).show()
            }.onFailure {
                NaviActivity.utils.logE(this)
            }
        }
    }

    private fun readAllPostsByPage() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = APIObject.getRetrofitAPIService.getAllPost(displayPageItemSize, nowPage - 1, category)
            response.onSuccess {
                postList = data.result.posts as ArrayList<PostResponse>
                postAdapter = PostAdapter(NaviActivity.applicationContext(), R.layout.post_adapter_view,
                    postList as MutableList<PostResponse>
                )
                postAdapter.notifyDataSetChanged()
                binding.lvPost.adapter = postAdapter

                NaviActivity.utils.logD(statusCode)
            }.onError {
                val errorMsg = NaviActivity.utils.logE(statusCode)
                Toast.makeText(NaviActivity.applicationContext(), errorMsg, Toast.LENGTH_SHORT).show()
            }.onFailure {
                NaviActivity.utils.logE(this)
            }
        }
    }

    private fun paging() {
        var displayPageSize = 5

        if (maxPageSize < 5) {
            displayPageSize = maxPageSize
        }
        binding.btnLpbPager.setPageItemCount(displayPageSize)
        binding.btnLpbPager.addBottomPageButton(maxPageSize, nowPage)

        binding.btnLpbPager.setOnPageSelectListener(object : OnPageSelectListener {
            override fun onPageBefore(clicked: Int) {
                nowPage = clicked
                binding.btnLpbPager.addBottomPageButton(maxPageSize, clicked)
                readAllPostsByPage()
            }

            override fun onPageCenter(clicked: Int) {
                nowPage = clicked
                readAllPostsByPage()
            }

            override fun onPageNext(clicked: Int) {
                nowPage = clicked
                binding.btnLpbPager.addBottomPageButton(maxPageSize, clicked)
                readAllPostsByPage()
            }
        })
    }

    inline fun <reified T: Enum<T>> T.next(): T {
        val values = enumValues<T>()
        val nextOrdinal = (ordinal + 1) % values.size
        return values[nextOrdinal]
    }

    inline fun <reified T: Enum<T>> T.previous(): T {
        val values = enumValues<T>()
        var nextOrdinal = (ordinal - 1) % values.size
        if(nextOrdinal < 0) {
            nextOrdinal = values.size - 1
        }
        return values[nextOrdinal]
    }

    override fun onResume() {
        super.onResume()
        setPager()
        readAllPostsByPage()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PostListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}