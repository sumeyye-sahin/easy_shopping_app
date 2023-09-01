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

        auth = Firebase.auth



        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
    }

    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = ArrayList<Product>()
            for (i in productList) {
                if (i.title.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                rvAdapter.setFilteredList(filteredList)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        GlobalScope.launch(Dispatchers.IO) {

            val response = try {
                RetrofitInstance.api.getProducts()
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "app error ${e.message}", Toast.LENGTH_SHORT)
                    .show()
                return@launch
            } catch (e: HttpException) {
                Toast.makeText(applicationContext, "http error ${e.message}", Toast.LENGTH_SHORT)
                    .show()
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    productList = response.body()!!.products
                    Singleton.allProducts = productList
                    dataEdit()
                    binding.apply {
                        progressBar.visibility = View.GONE
                        rvAdapter = RvAdapter(productList)
                        recyclerView.adapter = rvAdapter
                        recyclerView.setHasFixedSize(true)
                        recyclerView.layoutManager =
                            StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
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