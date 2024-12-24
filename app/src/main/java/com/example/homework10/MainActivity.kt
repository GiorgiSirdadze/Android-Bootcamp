package com.example.homework10

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.products)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val recyclerView2: RecyclerView = findViewById(R.id.filters)

        recyclerView2.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val categories = listOf(

            Filters("All"),
            Filters("Party"),
            Filters("Camping"),
            Filters("Category1"),
            Filters("Category2"),
            Filters("Category3"),
        )

        val products = listOf(
            Products(R.drawable.image1, "Pink hoodie", "$120", "Camping"),
            Products(R.drawable.image1, "Black hoodie", "$120", "Party"),
            Products(R.drawable.image1, "Yellow hoodie", "$120", "Category2"),
            Products(R.drawable.image1, "Green hoodie", "$120", "Camping"),
            Products(R.drawable.image1, "Belt suit blazer", "$120", "Party"),
            Products(R.drawable.image1, "Yellow hoodie", "$120", "Category2"),
            Products(R.drawable.image1, "Mint hoodie", "$120", "Party"),
            Products(R.drawable.image1, "Belt suit blazer", "$120", "Party"),
            Products(R.drawable.image1, "Yellow hoodie", "$120", "Category3"),
            Products(R.drawable.image1, "Mint hoodie", "$120", "Category3"),
            Products(R.drawable.image1, "Belt suit blazer", "$120", "Party"),
            Products(R.drawable.image1, "Yellow hoodie", "$120", "Category1"),
            Products(R.drawable.image1, "Mint hoodie", "$120", "Camping"),
            Products(R.drawable.image1, "Red hoodie", "$120", "Camping")
        )

        val adapter = ProductAdapter(products)
        recyclerView.adapter = adapter

        val adapter2 = FilterAdapter(categories)
        recyclerView2.adapter = adapter2
        adapter2.setOnItemClickListener(object : FilterAdapter.OnItemClickListener {
            override fun onItemLick(position: Int) {
                val selectedCategory = categories[position].categoryType
                val filteredProducts = if (selectedCategory == "All") {
                    products
                } else {
                    products.filter { it.category == selectedCategory }
                }
                val newAdapter = ProductAdapter(filteredProducts)
                recyclerView.adapter = newAdapter
            }
        })
    }
}