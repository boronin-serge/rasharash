package com.boronin.rasharash.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.boronin.rasharash.R
import com.boronin.rasharash.models.song.SongInfo
import com.bumptech.glide.Glide

class VendorsAdapter(
    private val listener: ItemClickListener
) : RecyclerView.Adapter<VendorsAdapter.ViewHolder>() {

    private val data: MutableList<SongInfo> = mutableListOf()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: AppCompatTextView = itemView.findViewById(R.id.tvTitle)
        val vendorName: AppCompatTextView = itemView.findViewById(R.id.tvVendorName)
        val logo: AppCompatImageView = itemView.findViewById(R.id.ivLogo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_vendor_item, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val song = data[position]

        with(viewHolder) {
            name.text = "${song.artistName} - ${song.name}"
            vendorName.text = song.vendorMetaData?.title
            itemView.setOnClickListener {
                listener.resultItemClicked(song.url)
            }

            Glide.with(itemView.context).load(song.logoUrl).into(viewHolder.logo)
        }
    }

    fun update(vendors: List<SongInfo>) {
        this.data.clear()
        this.data.addAll(vendors)
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun resultItemClicked(outputUrl: String?)
    }
}