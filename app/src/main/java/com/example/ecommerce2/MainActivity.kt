package com.example.ecommerce2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var rv: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var productAdapter: ProductAdapter
    private val listOfProducts = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv = findViewById(R.id.rv)
        fab = findViewById(R.id.fab)

        FirebaseDatabase.getInstance().getReference("Products")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    listOfProducts.clear()
                    for(dataSnapshot in snapshot.children){
                        val prod = dataSnapshot.getValue(Product::class.java)
                        listOfProducts.add(prod!!)
                    }

                    productAdapter = ProductAdapter(listOfProducts, this@MainActivity)
                    rv.adapter = productAdapter
                    rv.layoutManager = GridLayoutManager(this@MainActivity, 2)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })



        fab.setOnClickListener{
            startActivity(
                Intent(this, LoginPage::class.java)
            )
        }
    }
}