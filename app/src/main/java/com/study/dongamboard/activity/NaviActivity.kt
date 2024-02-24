package com.study.dongamboard.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.study.dongamboard.R
import com.study.dongamboard.api.Utils
import com.study.dongamboard.databinding.ActivityNaviBinding
import com.study.dongamboard.fragment.MainFragment
import com.study.dongamboard.fragment.notice.NoticeListFragment
import com.study.dongamboard.fragment.post.PostListFragment
import com.study.dongamboard.fragment.user.UserFragment

private const val TAG_NOTICE = "notice_fragment"
private const val TAG_MAIN = "main_fragment"
private const val TAG_POST = "post_fragment"
private const val TAG_USER = "user_fragment"

class NaviActivity : AppCompatActivity() {

    init { instance = this }
    private lateinit var binding: ActivityNaviBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNaviBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFragment(TAG_MAIN, MainFragment())

        binding.navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.noticeFragment -> setFragment(TAG_NOTICE, NoticeListFragment())
                R.id.mainFragment -> setFragment(TAG_MAIN, MainFragment())
                R.id.postFragment -> setFragment(TAG_POST, PostListFragment())
                R.id.userFragment-> setFragment(TAG_USER, UserFragment())
            }
            true
        }
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null) {
            fragTransaction.add(R.id.mainFrameLayout, fragment, tag)
        }

        val main = manager.findFragmentByTag(TAG_MAIN)
        val notice = manager.findFragmentByTag(TAG_NOTICE)
        val post = manager.findFragmentByTag(TAG_POST)
        val user = manager.findFragmentByTag(TAG_USER)

        if (main != null) {
            fragTransaction.hide(main)
        }
        if (notice != null) {
            fragTransaction.hide(notice)
        }
        if (post != null) {
            fragTransaction.hide(post)
        }
        if (user != null) {
            fragTransaction.hide(user)
        }

        when (tag) {
            TAG_MAIN -> {
                if (main != null) {
                    fragTransaction.show(main)
                }
            }
            TAG_NOTICE -> {
                if (notice != null) {
                    fragTransaction.show(notice)
                }
            }
            TAG_POST -> {
                if (post != null) {
                    fragTransaction.show(post)
                }
            }
            TAG_USER -> {
                if (user != null) {
                    fragTransaction.show(user)
                }
            }
        }

        fragTransaction.commitAllowingStateLoss()
    }

    companion object {
        val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(
            name = "TOKEN_DATA_STORE"
        )
        val utils: Utils by lazy {
            Utils(applicationContext())
        }
        lateinit var instance: NaviActivity
        fun applicationContext() : Context {
            return instance.applicationContext
        }
    }
}