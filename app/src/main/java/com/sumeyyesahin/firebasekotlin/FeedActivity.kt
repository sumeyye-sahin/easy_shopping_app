package com.sumeyyesahin.firebasekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sumeyyesahin.firebasekotlin.databinding.ActivityFeedBinding
import com.sumeyyesahin.firebasekotlin.adapter.RvAdapter
import com.sumeyyesahin.retrofitkotlintekrartekrar.models.Product
//import com.sumeyyesahin.firebasekotlin.models.Product
//import com.sumeyyesahin.firebasekotlin.utils.RetrofitInstance
import com.sumeyyesahin.retrofitkotlintekrartekrar.utils.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class FeedActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var binding: ActivityFeedBinding
    private lateinit var rvAdapter: RvAdapter
    private lateinit var productList : List<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= Firebase.auth

        GlobalScope.launch (Dispatchers.IO) {

            val response = try {
                RetrofitInstance.api.getProducts()
            } catch (e: Exception) {
                Toast.makeText(applicationContext,"app error ${e.message}", Toast.LENGTH_SHORT).show()
                return@launch
            }catch (e: HttpException){
                Toast.makeText(applicationContext,"http error ${e.message}", Toast.LENGTH_SHORT).show()
                return@launch
            }
            if(response.isSuccessful && response.body() != null){
                withContext(Dispatchers.Main){
                    productList = response.body()!!.products
                    binding.apply {
                        progressBar.visibility = View.GONE
                        rvAdapter = RvAdapter(productList)
                        recyclerView.adapter = rvAdapter
                        recyclerView.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)

                    }
                }
            }
            productList.forEach {
                println(productList[0].title)
            }



        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.logoutmenu, menu)
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.logoutmenu){
            auth.signOut()

            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()


        }
        return super.onOptionsItemSelected(item)
    }
}