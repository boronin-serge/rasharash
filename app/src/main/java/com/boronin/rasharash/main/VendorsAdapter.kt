package com.boronin.rasharash.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.boronin.rasharash.R
import com.boronin.rasharash.models.song.SongInfo
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import ru.boronin.common.view.base.RoundedFrameLayout


class VendorsAdapter(
    private val listener: ItemClickListener
) : RecyclerView.Adapter<VendorsAdapter.ViewHolder>() {

    private val data: MutableList<SongInfo> = mutableListOf()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: AppCompatTextView = itemView.findViewById(R.id.tvTitle)
        val vendorName: AppCompatTextView = itemView.findViewById(R.id.tvVendorName)
        val album: AppCompatTextView = itemView.findViewById(R.id.tvAlbumName)
        val logo: AppCompatImageView = itemView.findViewById(R.id.ivLogo)
        val vendorLayout: RoundedFrameLayout = itemView.findViewById(R.id.lVendorName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_result_item, parent, false))
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
            album.text = song.collectionName
            vendorLayout.setBackgroundColor(song.vendorMetaData?.color ?: Color.WHITE)
            itemView.setOnClickListener {
                listener.resultItemClicked(song.url)
            }

            Glide.with(itemView.context)
                .load(song.logoUrl)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(8)))
                .into(viewHolder.logo)
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