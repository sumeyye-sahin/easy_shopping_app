package com.sumeyyesahin.firebasekotlin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sumeyyesahin.firebasekotlin.adapter.RvAdapter
import com.sumeyyesahin.firebasekotlin.databinding.ActivityDetailBinding
import com.sumeyyesahin.firebasekotlin.Singleton.chosenProduct

class DetailActivity : AppCompatActivity() {
    private lateinit var rvAdapter: RvAdapter
    private lateinit var binding: ActivityDetailBinding
    // Firebase Referansları
    private lateinit var database: FirebaseDatabase
    private lateinit var favoriteRef: DatabaseReference

    // CheckBox
    private lateinit var checkBox: CheckBox

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //tooolbarda geri gitmek için
        supportActionBar!!.title=""


        val selectedProduct = chosenProduct //Singleton
        val currentItem = selectedProduct
        val checkBox = binding.detailCheckBox
        val database = Firebase.database.reference
        var auth = Firebase.auth


        val currentUser = auth.currentUser


        selectedProduct?.let {


            binding.textView4.text = it.title
            // binding.titleDetail.text = it.title
            binding.textView5.text = it.description

            binding.textView6.text = "Fiyat: ${it.price.toString()} TL"


            //  Picasso.get().load(it.image).into(scrollView.addView(binding.scrollView))

            val imageList = ArrayList<SlideModel>() // Create image list

// imageList.add(SlideModel("String Url" or R.drawable)
// imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title

            if (it.images != null || it.images.size != 0  ) {
                for (i in it.images) {
                    imageList.add(SlideModel(i))
                }
            } else {
                imageList.add(SlideModel(R.drawable.noimage))
            }

            val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
            imageSlider.setImageList(imageList)


        }

        checkBox.setOnCheckedChangeListener { checkBox, isChecked ->
            if (isChecked) {
                // CheckBox işaretlendiğinde yapılacak işlemler

                currentItem!!.isFavorite = true

                //database işlemleri
                // databesi bu id ile insert

                database.child(currentItem!!.id.toString()+currentUser!!.uid)
                    .setValue(FavoriteProduct(currentUser!!.uid, currentItem.id))

            } else {
                // CheckBox işareti kaldırıldığında yapılacak işlemler

                currentItem!!.isFavorite = false

                // databesi bu id ile delete
                database.child(currentItem.id.toString()+currentUser!!.uid).removeValue()


            }
        }

// CheckBox başlangıç durumunu false olarak ayarla


// Veritabanından ürünün favori olarak işaretlenip işaretlenmediğini kontrol et
        val favoriteProductRef = database.child(currentItem!!.id.toString() + currentUser!!.uid)

        favoriteProductRef.get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                // Veritabanında bu ürün varsa, CheckBox'ı işaretleyin
                checkBox.isChecked = true
            } else {
                // Veritabanında bu ürün yoksa, CheckBox'ı boş bırakın
                checkBox.isChecked = false
            }
        }.addOnFailureListener {
            // Veritabanı hatası durumunda CheckBox'ı boş bırakın
            checkBox.isChecked = false
        }
    }
}


