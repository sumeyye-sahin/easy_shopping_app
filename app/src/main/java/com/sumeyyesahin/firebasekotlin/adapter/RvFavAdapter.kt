package com.sumeyyesahin.firebasekotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sumeyyesahin.firebasekotlin.Singleton
import com.sumeyyesahin.firebasekotlin.databinding.RvFavItemBinding
import com.sumeyyesahin.retrofitkotlintekrartekrar.models.Product

class RvFavAdapter(private var productList: List<Product>) :
    RecyclerView.Adapter<RvFavAdapter.ViewHolder>() { //anlamı : ürün listesini al, tutucu al

    class ViewHolder(val binding: RvFavItemBinding) : RecyclerView.ViewHolder(binding.root) {
// anlamı: tutucu al, bağla, root'u al
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvFavAdapter.ViewHolder { // anlamı: tutucu oluştur
        return RvFavAdapter.ViewHolder( // anlamı: tutucu al
            RvFavItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ) // anlamı: RvFavItemBinding ile bağla, parent'ı inflate et, false yapma

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { // anlamı: tutucuyu bağla, pozisyonu al
        holder.binding.apply {
            textViewFavTitle.text = productList.get(position).title // anlamı: tutucudaki metin alanına bu ürünün başlığını yaz
            Picasso.get().load(productList.get(position).thumbnail).into(imageViewFav) // anlamı: Picasso ile bu ürünün resmini al, imageview'e koy
        }
    }

    override fun getItemCount(): Int {
        return productList.size // anlamı: ürün listesinin boyutu
    }
}