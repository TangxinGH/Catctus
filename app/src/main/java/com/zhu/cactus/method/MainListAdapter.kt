package com.zhu.cactus.method

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zhu.cactus.R


data class MainListModel(val id: Int)

class MainListAdapter(context: Context) : RecyclerView.Adapter<MainListAdapter.ListViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    // filteredItems is a static field to simulate filtering of random items
    private val filteredItems = intArrayOf(2, 5, 6, 8, 12)
    private val modelList = List(20) { MainListModel(it) }
    private val modelListFiltered = modelList.filter { it.id !in filteredItems }
    private val adapterList: List<MainListModel> get() = if (isFiltered) modelListFiltered else modelList

    private lateinit var recyclerView: RecyclerView

    /** Variable used to filter adapter items. 'true' if filtered and 'false' if not */
    var isFiltered = false

    ///////////////////////////////////////////////////////////////////////////
    // Methods
    ///////////////////////////////////////////////////////////////////////////

    override fun getItemCount(): Int = adapterList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(inflater.inflate(R.layout.item_list, parent, false))

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }



    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val model = adapterList[position]

    }


    ///////////////////////////////////////////////////////////////////////////
    // ViewHolder
    ///////////////////////////////////////////////////////////////////////////

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}