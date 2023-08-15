package com.sumeyyesahin.firebasekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.squareup.picasso.Picasso
import com.sumeyyesahin.firebasekotlin.adapter.RvAdapter
import com.sumeyyesahin.firebasekotlin.databinding.ActivityDetailBinding
import com.sumeyyesahin.retrofitkotlintekrartekrar.models.Product
import com.sumeyyesahin.retrofitkotlintekrartekrar.utils.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import com.sumeyyesahin.firebasekotlin.Singleton.chosenProduct
class DetailActivity : AppCompatActivity() {
    private lateinit var rvAdapter: RvAdapter
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val selectedProduct = chosenProduct //Singleton{

            selectedProduct?.let {
                binding.detailText.text = it.description
                binding.nameDetailText.text = it.brand
                //   binding.imageDitail.setImageResource(it.thumbnail)
                Picasso.get().load(it.thumbnail).into(binding.imageDitail)

                val scrollView = ScrollView(this@DetailActivity)
              //  Picasso.get().load(it.image).into(scrollView.addView(binding.scrollView))


            }
        }
    }


