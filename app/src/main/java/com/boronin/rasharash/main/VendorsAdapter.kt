package com.boronin.rasharash.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.boronin.rasharash.R
import com.boronin.rasharash.models.SearchResult

class VendorsAdapter(
    private val listener: ItemClickListener
) : RecyclerView.Adapter<VendorsAdapter.ViewHolder>() {

    private val vendors: MutableList<SearchResult> = mutableListOf()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.result_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_vendor_item, parent, false))
    }

    override fun getItemCount(): Int {
        return vendors.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val vendor = vendors[position]

        viewHolder.name.text = "${vendor.songInfo.name}\n${vendor.vendorMetaData.title}"
        viewHolder.itemView.setOnClickListener {
            listener.resultItemClicked(vendor.songInfo.url)
        }
    }

    fun update(vendors: List<SearchResult>) {
        this.vendors.clear()
        this.vendors.addAll(vendors)
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun resultItemClicked(outputUrl: String)
    }
}