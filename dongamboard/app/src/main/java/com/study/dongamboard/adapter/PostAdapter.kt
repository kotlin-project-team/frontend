package com.study.dongamboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.study.dongamboard.model.response.PostResponse

class PostAdapter(private val context: Context, private val resId: Int, private val data: MutableList<PostResponse>) :
ArrayAdapter<PostResponse>(context, resId) {

    override fun getCount(): Int {
        return data.size
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
        val tvLikes: TextView = holder.tvLikes

        val (title, likes) = arrayOf(data[position].title, data[position].likes)
        tvPostTitle.text = title.toString()
        tvLikes.text = "[댓글 " + likes.toString() + "]"

        return convertView
    }
}