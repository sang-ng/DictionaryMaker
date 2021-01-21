package com.example.android.vocabularyapp.ui.category

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.vocabularyapp.database.entities.CategoryDb
import com.example.android.vocabularyapp.databinding.ActivityCategoryBinding
import org.koin.android.viewmodel.ext.android.viewModel

class CategoryActivity : AppCompatActivity() {

    private val viewModel by viewModel<CategoryViewModel>()
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)

        initOnClick()

        setContentView(binding.root)
    }

    private fun initOnClick() {
        binding.categoryAddButton.setOnClickListener {

            val cat = CategoryDb(0, "Basic")

            viewModel.addCategory(cat)
        }
    }

    private fun initRecyclerView() {
        linearLayoutManager = LinearLayoutManager(this)
        binding.categoryRecyclerview.layoutManager = linearLayoutManager
    }
}