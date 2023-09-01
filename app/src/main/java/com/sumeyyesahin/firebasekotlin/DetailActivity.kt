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
            binding.textView5.text = it.description
            binding.textView6.text = "Fiyat: ${it.price.toString()} TL"
            binding.textViewBrand.text = "${it.brand}"

            val imageList = ArrayList<SlideModel>() // Create image list

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


        val favoriteProductRef = database.child(currentItem!!.id.toString() + currentUser!!.uid)

        favoriteProductRef.get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {

                checkBox.isChecked = true
            } else {

                checkBox.isChecked = false
            }
        }.addOnFailureListener {

            checkBox.isChecked = false
        }
    }
}


