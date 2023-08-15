package com.sumeyyesahin.firebasekotlin.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sumeyyesahin.firebasekotlin.DetailActivity
import com.sumeyyesahin.firebasekotlin.R
import com.sumeyyesahin.firebasekotlin.Singleton
import com.sumeyyesahin.firebasekotlin.databinding.RvItemBinding
import com.sumeyyesahin.retrofitkotlintekrartekrar.models.Product

class RvAdapter (private val productList: List<Product>) : RecyclerView.Adapter<RvAdapter.ViewHolder>(){

    class ViewHolder (val binding: RvItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = productList[position]
        holder.binding.apply {
            textView.text = currentItem.title
            Picasso.get().load(currentItem.thumbnail).into(imageView)
            textView2.text = "Fiyat: ${currentItem.price.toString()} TL"
        }

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context,DetailActivity::class.java)
            Singleton.chosenProduct = currentItem


            holder.itemView.context.startActivity(intent)
        }



    }



}


