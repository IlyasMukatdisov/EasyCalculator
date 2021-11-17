package com.gmail.mukatdisovilyas.easycalculator.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gmail.mukatdisovilyas.easycalculator.R

class HistoryAdapter(private val listHistory: List<Expression>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>()
{

    private lateinit var currentDate: String

    init
    {
        if (listHistory.isNotEmpty())
        {
           currentDate = listHistory[0].date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_rv_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        if (position == 0)
        {
            holder.tvDate.text = listHistory[position].date
            holder.tvDate.visibility = View.VISIBLE
        }
        else
        {
            if (listHistory[position].date != currentDate)
            {
                holder.tvDate.text = listHistory[position].date
                currentDate = listHistory[position].date
                holder.tvDate.visibility = View.VISIBLE
            }
            else
            {
                holder.tvDate.visibility = View.GONE
            }
        }
        holder.tvPrevExp.text = listHistory[position].prevExp
        holder.tvResult.text = listHistory[position].result
    }

    override fun getItemCount(): Int = listHistory.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val tvDate: TextView = itemView.findViewById(R.id.history_rv_date)
        val tvPrevExp: TextView = itemView.findViewById(R.id.history_rv_prev_exp)
        val tvResult: TextView = itemView.findViewById(R.id.history_rv_result)
    }

}

