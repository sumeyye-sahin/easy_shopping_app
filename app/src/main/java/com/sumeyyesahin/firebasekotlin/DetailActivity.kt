package com.sumeyyesahin.firebasekotlin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sumeyyesahin.firebasekotlin.adapter.RvAdapter
import com.sumeyyesahin.firebasekotlin.databinding.ActivityDetailBinding
import com.sumeyyesahin.firebasekotlin.Singleton.chosenProduct
import com.sumeyyesahin.firebasekotlin.models.FavoriteProduct

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    @SuppressLint("SuspiciousIndentation") // anlamı : şüpheli girinti (kodda boşluklar varsa kaldırır)
    override fun onCreate(savedInstanceState: Bundle?) { // onCreate aktivite ilk oluşturulduğunda çalışır
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //tooolbarda geri gitmek için kullanılır (manifestte de ayar yapılmalı)
        supportActionBar!!.title="" // toolbar başlığı boş bırakıldı


        val selectedProduct = chosenProduct // seçilen ürünü alır (singletondan)
        val currentItem = selectedProduct // seçilen ürünü alır (singletondan)
        val checkBox = binding.detailCheckBox // checkbox tanımlandı
        val database = Firebase.database.reference // database referansı tanımlandı
        var auth = Firebase.auth // firebase auth nesnesi oluşturuldu


        val currentUser = auth.currentUser // eğer kullanıcı varsa currentUser değişkenine atar


        selectedProduct?.let { // seçilen ürün boş değilse aşağıdaki işlemleri yapar (let)


            binding.textView4.text = it.title  // anlamı: textView4 ün texti it.title olsun
            binding.textView5.text = it.description // anlamı: textView5 in texti it.description olsun
            binding.textView6.text = "Fiyat: ${it.price.toString()} TL" // anlamı: textView6 nın texti Fiyat: it.price.toString() TL olsun
            binding.textViewBrand.text = "${it.brand}" // anlamı: textViewBrand in texti it.brand olsun

            val imageList = ArrayList<SlideModel>() // Create image list

            if (it.images != null || it.images.size != 0  ) { // eğer ürünün resmi varsa veya resim sayısı 0 dan büyükse aşağıdaki işlemleri yapar
                for (i in it.images) { // ürünün resimlerini imageList e ekler
                    imageList.add(SlideModel(i))
                }
            } else { // eğer ürünün resmi yoksa noimage resmini imageList e ekler
                imageList.add(SlideModel(R.drawable.noimage))
            }

            val imageSlider = findViewById<ImageSlider>(R.id.image_slider) // image_slider tanımlandı (xml deki)
            imageSlider.setImageList(imageList) // imageList i imageSlider a atar (resimleri gösterir)


        }

        checkBox.setOnCheckedChangeListener { checkBox, isChecked -> // checkbox işaretlendiğinde yapılacak işlemler (checked)
            if (isChecked) {
                // CheckBox işaretlendiğinde yapılacak işlemler

                currentItem!!.isFavorite = true // seçilen ürün favoriye eklendi

                //database işlemleri
                // databesi bu id ile insert

                database.child(currentItem!!.id.toString()+currentUser!!.uid) // databasede id ve kullanıcı id ile kayıt açar
                    .setValue(FavoriteProduct(currentUser!!.uid, currentItem.id)) // databasede id ve kullanıcı id ile kayıt açar (kullanıcı id ve ürün id ile kayıt açar)


            } else {
                // CheckBox işareti kaldırıldığında yapılacak işlemler

                currentItem!!.isFavorite = false // seçilen ürün favoriden çıkarıldı

                // databesi bu id ile delete
                database.child(currentItem.id.toString()+currentUser!!.uid).removeValue() // databasede id ve kullanıcı id ile kayıt siler (kullanıcı id ve ürün id ile kayıt siler)


            }
        }


        val favoriteProductRef = database.child(currentItem!!.id.toString() + currentUser!!.uid) // databasede id ve kullanıcı id ile kayıt açar (kullanıcı id ve ürün id ile kayıt açar)

        favoriteProductRef.get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) { // eğer kayıt varsa checkbox işaretli olur

                checkBox.isChecked = true // checkbox işaretli olur
            } else { // eğer kayıt yoksa checkbox işaretsiz olur

                checkBox.isChecked = false // checkbox işaretsiz olur
            }
        }.addOnFailureListener { // eğer hata olursa checkbox işaretsiz olur
            checkBox.isChecked = false // checkbox işaretsiz olur
        }
    }
}


