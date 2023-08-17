package com.sumeyyesahin.firebasekotlin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.squareup.picasso.Picasso
import com.sumeyyesahin.firebasekotlin.adapter.RvAdapter
import com.sumeyyesahin.firebasekotlin.databinding.ActivityDetailBinding
import com.sumeyyesahin.firebasekotlin.Singleton.chosenProduct
import com.sumeyyesahin.retrofitkotlintekrartekrar.models.Product

class DetailActivity : AppCompatActivity() {
    private lateinit var rvAdapter: RvAdapter
    private lateinit var binding: ActivityDetailBinding


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)




        val selectedProduct = chosenProduct //Singleton{


        selectedProduct?.let {


               binding.textView4.text = it.title
               // binding.titleDetail.text = it.title
                binding.textView5.text = it.description

                binding.textView6.text = "Fiyat: ${it.price.toString()} TL"


              //  Picasso.get().load(it.image).into(scrollView.addView(binding.scrollView))

            val imageList = ArrayList<SlideModel>() // Create image list

// imageList.add(SlideModel("String Url" or R.drawable)
// imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title


            for (i in it.images){
                imageList.add(SlideModel(i))
            }


            val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
            imageSlider.setImageList(imageList)


        }
        }
    }


