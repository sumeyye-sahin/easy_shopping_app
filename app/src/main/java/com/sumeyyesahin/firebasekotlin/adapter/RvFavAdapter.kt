package com.sumeyyesahin.firebasekotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sumeyyesahin.firebasekotlin.Singleton
import com.sumeyyesahin.firebasekotlin.databinding.RvFavItemBinding
import com.sumeyyesahin.retrofitkotlintekrartekrar.models.Product

class RvFavAdapter(private var productList: List<Product>) :
    RecyclerView.Adapter<RvFavAdapter.ViewHolder>() {

    class ViewHolder(val binding: RvFavItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvFavAdapter.ViewHolder {
        return RvFavAdapter.ViewHolder(
            RvFavItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            textViewFavTitle.text = productList.get(position).title
            Picasso.get().load(productList.get(position).thumbnail).into(imageViewFav)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}