package com.gmail.mukatdisovilyas.easycalculator.utils


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gmail.mukatdisovilyas.easycalculator.R

class GraphColorsAdapter(
    private val list: MutableList<GraphColors>,
) :
    RecyclerView.Adapter<GraphColorsAdapter.ViewHolder>()
{
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val tvColor: TextView = itemView.findViewById(R.id.tv_color_item)
        val btnClose: ImageButton = itemView.findViewById(R.id.btn_delete_graph)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.graph_color_rv_item, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.tvColor.text = list[position].title
        holder.tvColor.setTextColor(list[position].color)
        holder.btnClose.setOnClickListener {
            list.removeAt(position)
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int
    {
        return list.size
    }
}