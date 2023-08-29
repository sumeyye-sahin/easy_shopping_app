package com.sumeyyesahin.firebasekotlin.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FieldValue
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.sumeyyesahin.firebasekotlin.DetailActivity
import com.sumeyyesahin.firebasekotlin.FavoriteProduct
import com.sumeyyesahin.firebasekotlin.R
import com.sumeyyesahin.firebasekotlin.Singleton
import com.sumeyyesahin.firebasekotlin.databinding.RvItemBinding
import com.sumeyyesahin.retrofitkotlintekrartekrar.models.Product

class RvAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    class ViewHolder(val binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = Singleton.allProducts!!.get(position)
        val checkBox = holder.binding.cbHeart



        //  notifyDataSetChanged() ----> Crashlytics hatası için eklendi
        holder.binding.apply {
            textView.text = currentItem.title
            Picasso.get().load(currentItem.thumbnail).error(R.drawable.noimage).into(imageView)
            textView2.text = "Fiyat: ${currentItem.price.toString()} TL"

        }


        val database = Firebase.database.reference

        var auth = Firebase.auth


        val currentUser = auth.currentUser

        checkBox.setOnCheckedChangeListener { checkBox, isChecked ->
            if (isChecked) {
                // CheckBox işaretlendiğinde yapılacak işlemler

                currentItem.isFavorite = true

                //database işlemleri
                // databesi bu id ile insert

                database.child(currentItem.id.toString()+currentUser!!.uid)
                    .setValue(FavoriteProduct(currentUser!!.uid, currentItem.id))

            } else {
                // CheckBox işareti kaldırıldığında yapılacak işlemler

                currentItem.isFavorite = false

                // databesi bu id ile delete
                database.child(currentItem.id.toString()+currentUser!!.uid).removeValue()


            }
        }

        checkBox.isChecked = false

        // veritabanında bu id eklimi
        var getData = database.child(currentItem.id.toString()+currentUser!!.uid)

        getData.get().addOnSuccessListener {
            if (it.exists())             {
                checkBox.isChecked = true
            }
        }
            .addOnFailureListener {
                checkBox.isChecked = false
            }




        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            Singleton.chosenProduct = currentItem
            holder.itemView.context.startActivity(intent)
        }


    }


}




