package com.sumeyyesahin.firebasekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View

import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sumeyyesahin.firebasekotlin.databinding.ActivityFeedBinding
import com.sumeyyesahin.firebasekotlin.adapter.RvAdapter
import com.sumeyyesahin.firebasekotlin.adapter.RvFavAdapter
import com.sumeyyesahin.retrofitkotlintekrartekrar.models.Product
//import com.sumeyyesahin.firebasekotlin.models.Product
//import com.sumeyyesahin.firebasekotlin.utils.RetrofitInstance
import com.sumeyyesahin.retrofitkotlintekrartekrar.utils.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.Locale

class FeedActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityFeedBinding
    private lateinit var rvAdapter: RvAdapter
    private lateinit var productList: ArrayList<Product>
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth // anlamı: auth, Firebase.auth ile oluşturuldu (Firebase kimlik doğrulama)



        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener { // anlamı: searchView, SearchView.OnQueryTextListener ile oluşturuldu  (arama görünümü)
            override fun onQueryTextSubmit(query: String?): Boolean { // anlamı: onQueryTextSubmit, arama sorgusu gönderildiğinde çalışır (metin gönderildi)
                return false // anlamı: false, döndürür
            }

            override fun onQueryTextChange(newText: String?): Boolean { // anlamı: onQueryTextChange, arama sorgusu değiştirildiğinde çalışır (yeni metin)
                filterList(newText) // anlamı: filterList, newText ile filtrelenir (yeni metin)
                return true // anlamı: true, döndürür
            }

        })
    }

    private fun filterList(query: String?) { // anlamı: filterList, sorgu ile filtrelenir (sorgu)

        if (query != null) { // anlamı: sorgu null değilse
            val filteredList = ArrayList<Product>() // anlamı: filteredList, ArrayList ile oluşturuldu (filtrelenmiş liste)
            for (i in productList) { // anlamı: i, productList ile oluşturuldu (ürün listesi)
                if (i.title.lowercase(Locale.ROOT).contains(query)) {
                    // anlamı: i.title.lowercase(Locale.ROOT).contains(query), i.title.lowercase(Locale.ROOT) ile oluşturuldu (i.title.lowercase(Locale.ROOT) içerir (sorgu))

                    filteredList.add(i) // anlamı: filteredList.add(i), filteredList.add ile oluşturuldu (filtrelenmiş liste ekle)
                }
            }

            if (filteredList.isEmpty()) { // anlamı: filteredList.isEmpty(), filteredList.isEmpty ile oluşturuldu (filtrelenmiş liste boş)
                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show() // anlamı: Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show() ile oluşturuldu (Veri bulunamadı)

            } else { // anlamı: değilse
                rvAdapter.setFilteredList(filteredList)
            // anlamı: rvAdapter.setFilteredList(filteredList), rvAdapter.setFilteredList ile oluşturuldu (filtrelenmiş liste ayarla)
            }
        }
    }

    override fun onResume() { // anlamı: onResume, devam et (devam et)
        super.onResume() // anlamı: super.onResume(), super.onResume ile oluşturuldu (devam et)
        GlobalScope.launch(Dispatchers.IO) { // anlamı: GlobalScope.launch(Dispatchers.IO), GlobalScope.launch ile oluşturuldu (GlobalScope başlat)
        //GlobalScope, bir uygulamanın yaşam döngüsü boyunca yaşar ve uygulama kapatıldığında sona erer.
            val response = try { // anlamı: response, try ile oluşturuldu (yanıt)
                RetrofitInstance.api.getProducts() // anlamı: RetrofitInstance.api.getProducts ile oluşturuldu (ürünleri al)
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "app error ${e.message}", Toast.LENGTH_SHORT)
                    .show()
                return@launch
            } catch (e: HttpException) {
                Toast.makeText(applicationContext, "http error ${e.message}", Toast.LENGTH_SHORT)
                    .show()
                return@launch
            }

            if (response.isSuccessful && response.body() != null) { // anlamı: (başarılı ise ve yanıt boş değilse)
                withContext(Dispatchers.Main) { // açıklaması: Dispatchers.Main, ana iş parçacığı için bir bağlamdır.
                    productList = response.body()!!.products
                    Singleton.allProducts = productList
                    dataEdit()
                    binding.apply {
                        progressBar.visibility = View.GONE //görünürlük: GONE, görünmez
                        rvAdapter = RvAdapter(productList) // anlamı: rvAdapter, RvAdapter ile oluşturuldu (ürün listesi)
                        recyclerView.adapter = rvAdapter // anlamı: recyclerView.adapter, recyclerView.adapter ile oluşturuldu (recyclerView ayarla)
                        recyclerView.setHasFixedSize(true) // setHasFixedSize, sabit boyutlu ayarla
                        recyclerView.layoutManager =
                            StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)//StringgeredGridLayoutManager: Sütun sayısı 2, dikey yönde düzenle
                    }
                }
            }
        }
    }

    fun dataEdit() {

        Singleton.favoriteProduct = ArrayList<Product>()

        for (item in Singleton.allProducts!!) {
            val database = Firebase.database.reference
            var auth = Firebase.auth
            val currentUser = auth.currentUser

            var getData = database.child(item.id.toString() + currentUser!!.uid)

            getData.get().addOnSuccessListener { it ->
                if (it.exists()) {
                    Singleton.favoriteProduct?.add(item)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.logoutmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.logoutmenu) {
            auth.signOut()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        if (item.itemId == R.id.favoritemenu) {
            val intent = Intent(this, FavoriteProductsActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}