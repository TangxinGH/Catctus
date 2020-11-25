package com.zhu.nav.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ramotion.foldingcell.FoldingCell
import com.zhu.nav.R

class  myAdapter (private val myDataset: Array<String>): RecyclerView.Adapter<myAdapter.MyViewHolder>(){

    class MyViewHolder(cell: FoldingCell) : RecyclerView.ViewHolder(cell)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // create a new view
        val fc = LayoutInflater.from(parent.context)
            .inflate(R.layout.foldingcell, parent, false) as FoldingCell
        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(fc)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
         holder.itemView.setOnClickListener { (it as FoldingCell).toggle(false) }
    }

    override fun getItemCount(): Int {
       return myDataset.size
    }

}