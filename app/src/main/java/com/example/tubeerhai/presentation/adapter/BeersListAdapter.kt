package com.example.tubeerhai.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tubeerhai.R
import com.example.tubeerhai.data.model.BeerModel

class BeersListAdapter(
    private val context: Context,
    private var list: List<BeerModel>
): RecyclerView.Adapter<BeersListAdapter.BeersViewHolder>() {

    inner class BeersViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val beerImage: ImageView = view.findViewById(R.id.iv_beer_image)
        val beerName: TextView = view.findViewById(R.id.tv_title)
        val beerTagLine: TextView = view.findViewById(R.id.tv_tag_line)
        val beerShare: ImageView = view.findViewById(R.id.iv_share)
    }

    private var onLongClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeersViewHolder {
        return BeersViewHolder(LayoutInflater.from(context).inflate(R.layout.item_beer_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BeersViewHolder, position: Int) {
        val model = list[position]
        Glide.with(context).load(model.imageUrl).centerInside().into(holder.beerImage)
        holder.beerName.text = model.name
        holder.beerTagLine.text = model.tagline
        holder.beerShare.setOnClickListener {
            onLongClickListener?.onClick(position, model)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<BeerModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onClick(position: Int, model: BeerModel)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onLongClickListener = onClickListener
    }
}