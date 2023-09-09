package com.sumeyyesahin.firebasekotlin.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.sumeyyesahin.firebasekotlin.DetailActivity
import com.sumeyyesahin.firebasekotlin.models.FavoriteProduct
import com.sumeyyesahin.firebasekotlin.R
import com.sumeyyesahin.firebasekotlin.Singleton
import com.sumeyyesahin.firebasekotlin.databinding.RvItemBinding
import com.sumeyyesahin.retrofitkotlintekrartekrar.models.Product

class RvAdapter(private var productList: List<Product>) :
    RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    class ViewHolder(val binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {
    }
    fun setFilteredList(mList: List<Product>){ // SearchView için
        this.productList= mList
        notifyDataSetChanged() // SearchView için //anlamı: değişiklikleri bildir
    }     //  notifyDataSetChanged() ----> Crashlytics hatası için eklendi
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        // anlamı : RvItemBinding ile bağla, parent'ı inflate et, false yapma
    }

    override fun getItemCount(): Int {
        return productList.size // anlamı: ürün listesinin boyutu
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {// anlamı: tutucuyu bağla, pozisyonu al
        val currentItem = this.productList.get(position) // anlamı: bu ürün listesinin bu pozisyonundaki ürünü al
        val checkBox = holder.binding.cbHeart // anlamı: tutucudaki kalp kutusunu al



        holder.binding.apply { // anlamı: tutucuya uygula
            textView.text = currentItem.title // anlamı: tutucudaki metin alanına bu ürünün başlığını yaz
            Picasso.get().load(currentItem.thumbnail).error(R.drawable.noimage).into(imageView) // anlamı: Picasso ile bu ürünün resmini al, hata olursa noimage koy, imageview'e koy
            textView2.text = "Fiyat: ${currentItem.price.toString()} TL" // anlamı: tutucudaki metin alanına bu ürünün fiyatını yaz


        }


        val database = Firebase.database.reference // anlamı: veritabanı referansını al
        var auth = Firebase.auth // anlamı: auth'u al


        val currentUser = auth.currentUser // anlamı: şu anki kullanıcıyı al

        checkBox.setOnCheckedChangeListener { checkBox, isChecked -> // anlamı: kalp kutusuna tıklandığında
            if (isChecked) {
                // CheckBox işaretlendiğinde yapılacak işlemler

                currentItem.isFavorite = true // anlamı: bu ürün favori olarak işaretlendi


                database.child(currentItem.id.toString() + currentUser!!.uid) // anlamı: veritabanında bu id ile bu kullanıcıyı al
                    .setValue(FavoriteProduct(currentUser!!.uid, currentItem.id)) // anlamı: bu kullanıcı id'si ile bu ürün id'sini veritabanına ekle

            } else {
                // CheckBox işareti kaldırıldığında yapılacak işlemler

                currentItem.isFavorite = false

                // databesi bu id ile delete
                database.child(currentItem.id.toString() + currentUser!!.uid).removeValue()


            }
        }

        checkBox.isChecked = false // anlamı: kalp kutusu işaretli değil

        // veritabanında bu id eklimi
        var getData = database.child(currentItem.id.toString() + currentUser!!.uid) // anlamı: veritabanında bu id ile bu kullanıcıyı al

        getData.get().addOnSuccessListener { // anlamı: başarılı olursa
            if (it.exists()) { // anlamı: varsa
                checkBox.isChecked = true // anlamı: kalp kutusu işaretli
            }
        }
            .addOnFailureListener { // anlamı: başarısız olursa
                checkBox.isChecked = false // anlamı: kalp kutusu işaretli değil
            }




        holder.itemView.setOnClickListener { // anlamı: tutucuya tıklandığında
            val intent = Intent(holder.itemView.context, DetailActivity::class.java) // anlamı: intent ile bu tutucunun bağlı olduğu context'te DetailActivity'e git
            Singleton.chosenProduct = currentItem // anlamı: Singleton ile seçilen ürün bu ürün
            holder.itemView.context.startActivity(intent) // anlamı: intenti başlat
        }


    }


}
