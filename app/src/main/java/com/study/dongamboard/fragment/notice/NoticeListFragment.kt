package com.study.dongamboard.fragment.notice

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.lakue.pagingbutton.OnPageSelectListener
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import com.study.dongamboard.R
import com.study.dongamboard.activity.NaviActivity
import com.study.dongamboard.activity.notice.NoticeActivity
import com.study.dongamboard.activity.notice.NoticeCreateActivity
import com.study.dongamboard.adapter.NoticeAdapter
import com.study.dongamboard.api.APIObject
import com.study.dongamboard.databinding.FragmentNoticeListBinding
import com.study.dongamboard.model.response.NoticeResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NoticeListFragment : Fragment() {

    private lateinit var binding: FragmentNoticeListBinding

    private lateinit var noticeAdapter: NoticeAdapter
    private lateinit var noticeList : ArrayList<NoticeResponse>
    private val displayPageItemSize = 6
    private var maxPageSize = 1
    private var nowPage = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoticeListBinding.inflate(inflater, container, false)

        noticeList = arrayListOf<NoticeResponse>()

        setPager()
        readAllNoticesByPage()

        binding.lvNotice.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(NaviActivity.applicationContext(), NoticeActivity::class.java)
            intent.putExtra("noticeData", noticeList[i])
            startActivity(intent)
        }

        binding.ivNoticeCreate.setOnClickListener {
            val intent = Intent(NaviActivity.applicationContext(), NoticeCreateActivity::class.java)
            startActivity(intent)
        }

        var swipe = binding.swipeNoticelistLayout
        swipe.setOnRefreshListener {
            readAllNoticesByPage()
            swipe.isRefreshing = false
        }

        return binding.root
    }

    private fun setPager() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = APIObject.getRetrofitAPIService.getAllNotice(0, 0)
            response.onSuccess {
                if (data.result.isEmpty()) {
                    maxPageSize = 1
                } else {
                    maxPageSize = data.result.size / displayPageItemSize
                }
                if (data.result.size % displayPageItemSize > 0) {
                    maxPageSize++
                }
                paging()

                NaviActivity.utils.logD(statusCode)
                NaviActivity.utils.logD("size" + data.result.size.toString() + " " + displayPageItemSize)
                NaviActivity.utils.logD("maxPageSize: $maxPageSize")
            }.onError {
                val errorMsg = NaviActivity.utils.logE(statusCode)
                Toast.makeText(NaviActivity.applicationContext(), errorMsg, Toast.LENGTH_SHORT).show()
            }.onFailure {
                NaviActivity.utils.logE(this)
            }
        }
    }

    private fun readAllNoticesByPage() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = APIObject.getRetrofitAPIService.getAllNotice(displayPageItemSize, nowPage - 1)
            response.onSuccess {
                noticeList = data.result as ArrayList<NoticeResponse>
                noticeAdapter = NoticeAdapter(NaviActivity.applicationContext(), R.layout.notice_adapter_view,
                    noticeList as MutableList<NoticeResponse>
                )
                noticeAdapter.notifyDataSetChanged()
                binding.lvNotice.adapter = noticeAdapter

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
        val btnLpbPager = binding.btnLpbPager
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
                readAllNoticesByPage()
            }

            override fun onPageCenter(clicked: Int) {
                nowPage = clicked
                readAllNoticesByPage()
            }

            override fun onPageNext(clicked: Int) {
                nowPage = clicked
                btnLpbPager.addBottomPageButton(maxPageSize, clicked)
                readAllNoticesByPage()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        setPager()
        readAllNoticesByPage()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NoticeListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}