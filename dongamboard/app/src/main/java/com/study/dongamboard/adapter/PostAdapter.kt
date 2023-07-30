package com.study.dongamboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import com.study.dongamboard.model.PostData

class PostAdapter(private val context: Context, val resId: Int, val datas: MutableList<PostData>) :
ArrayAdapter<PostData>(context, resId) {

    override fun getCount(): Int {
        return datas.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(resId, null)

            val holder = PostHolder(convertView)
            convertView!!.tag = holder
        }
        val holder = convertView.getTag() as PostHolder
        val tvPostTitle: TextView = holder.tvPostTitle
        val tvCmtcnt: TextView = holder.tvCmtCnt


        val (title, cmtCnt) = arrayOf(datas[position].title, 0)
        tvPostTitle.text = title.toString()
        tvCmtcnt.text = "[댓글 " + cmtCnt.toString() + "]"

        return convertView
    }

}


//class PostAdapter(private val context: Context, val postList: ArrayList<PostData>) : BaseAdapter() {
//
//    override fun getCount(): Int {
//        return postList.size
//    }
//
//    override fun getItem(position: Int): Any {
//        return postList[position]
//    }
//
//    override fun getItemId(position: Int): Long {
//        return postList[position].id.toLong()
//    }