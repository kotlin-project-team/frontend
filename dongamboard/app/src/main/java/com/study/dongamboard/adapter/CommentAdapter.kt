package com.study.dongamboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.study.dongamboard.model.CommentData

class CommentAdapter(private val context: Context, val resId: Int, val datas: MutableList<CommentData>) :
    ArrayAdapter<CommentData>(context, resId) {

    override fun getCount(): Int {
        return datas.size
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


        val (nickname, content) = arrayOf(datas[position].nickname, datas[position].content)
        tvCmtNickname.text = nickname.toString()
        tvCmtContent.text = content.toString()

        return convertView
    }

}