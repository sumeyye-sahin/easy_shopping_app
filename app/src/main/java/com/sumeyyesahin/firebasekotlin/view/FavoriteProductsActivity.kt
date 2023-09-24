package com.sumeyyesahin.firebasekotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sumeyyesahin.firebasekotlin.Singleton
import com.sumeyyesahin.firebasekotlin.adapter.RvFavAdapter
import com.sumeyyesahin.firebasekotlin.databinding.ActivityFavoriteProductsBinding

class FavoriteProductsActivity : AppCompatActivity() { // anlamı: favori ürünler aktivitesi (uygulama çubuğu)

    private lateinit var binding: ActivityFavoriteProductsBinding
    private lateinit var rvFavAdapter: RvFavAdapter  // anlamı: tutucu adaptörü

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //toolbarda geri gitmek için kullanılır (manifestte de ayar yapılmalı)
        supportActionBar!!.title = "My Favorite Products" // toolbar başlığı

        rvFavAdapter = RvFavAdapter(Singleton.favoriteProduct!!) // anlamı: rvFavAdapter, Singleton.favoriteProduct!! ile oluşturuldu (favori ürünler)
        binding.recyclerView.adapter = rvFavAdapter // anlamı: recyclerView adapteri rvFavAdapter ile oluşturuldu (favori ürünler)
        binding.recyclerView.setHasFixedSize(true) // anlamı: recyclerView sabit boyutlu (performans için)
        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(1, RecyclerView.VERTICAL) // anlamı: recyclerView düzeni StaggeredGridLayoutManager ile oluşturuldu (performans için)
    }

    override fun onResume() { // anlamı: uygulama yeniden başlatıldığında çalışır (favori ürünler)
        super.onResume()  // onResume aktivite yeniden başlatıldığında çalışır
    }
}