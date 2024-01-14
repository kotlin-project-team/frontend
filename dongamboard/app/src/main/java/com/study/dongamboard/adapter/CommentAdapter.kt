package com.study.dongamboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.study.dongamboard.model.response.CommentResponse

class CommentAdapter(private val context: Context, private val resId: Int, private val data: MutableList<CommentResponse>) :
    ArrayAdapter<CommentResponse>(context, resId) {

    override fun getCount(): Int {
        return data.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(resId, null)

            val holder = CommentHolder(convertView)
            convertView!!.tag = holder
        }
        val holder = convertView.getTag() as CommentHolder
        val tvCmtNickname: TextView = holder.tvCmtNickname
        val tvCmtContent: TextView = holder.tvCmtContent


        //TODO: 추후 userId -> nickname 변경
        val (nickname, content) = arrayOf(data[position].userId, data[position].content)
        tvCmtNickname.text = nickname.toString()
        tvCmtContent.text = content.toString()

        return convertView
    }
}