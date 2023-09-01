package com.sumeyyesahin.firebasekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sumeyyesahin.firebasekotlin.adapter.RvFavAdapter
import com.sumeyyesahin.firebasekotlin.databinding.ActivityFavoriteProductsBinding

class FavoriteProductsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteProductsBinding
    private lateinit var rvFavAdapter: RvFavAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //tooolbarda geri gitmek için
        supportActionBar!!.title = "My Favorite Products"

        rvFavAdapter = RvFavAdapter(Singleton.favoriteProduct!!)
        binding.recyclerView.adapter = rvFavAdapter
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
    }

    override fun onResume() {
        super.onResume()
    }
}