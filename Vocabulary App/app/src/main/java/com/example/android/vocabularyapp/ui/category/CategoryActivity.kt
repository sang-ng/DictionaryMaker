package com.example.android.vocabularyapp.ui.category

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.vocabularyapp.database.entities.CategoryDb
import com.example.android.vocabularyapp.databinding.ActivityCategoryBinding
import com.example.android.vocabularyapp.ui.addCategory.CategoryDialogFragment
import org.koin.android.viewmodel.ext.android.viewModel

class CategoryActivity : AppCompatActivity(), CategoryDialogFragment.CategoryDialogListener {

    private val viewModel by viewModel<CategoryViewModel>()
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var listAdapter: CategoryListAdapter
    private lateinit var fragmentManager: FragmentManager
    private lateinit var addDialogFragment: DialogFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        lifecycle.addObserver(viewModel)

        initOnClick()
        initRecyclerView()
        initDialogFragment()
        observeCategories()

        setContentView(binding.root)
    }


    private fun initOnClick() {
        binding.categoryAddButton.setOnClickListener {
            showAddDialog()
        }
    }

    private fun showAddDialog() {
        addDialogFragment.show(fragmentManager, "AddCatDialog")
    }

    private fun initRecyclerView() {
        listAdapter = CategoryListAdapter()

        binding.categoryRecyclerview.apply {
            layoutManager = LinearLayoutManager(this.context)
            itemAnimator = DefaultItemAnimator()
            adapter = listAdapter
        }
    }

    private fun initDialogFragment() {
        fragmentManager = supportFragmentManager
        addDialogFragment = CategoryDialogFragment()
    }

    private fun observeCategories() {
        viewModel.categories.observe(this, { categories ->
            if (categories.isNullOrEmpty()) {
                displayNoDataMessage()
            } else {
                listAdapter.setData(categories)
            }
        })
    }

    private fun displayNoDataMessage(){
        Toast.makeText(this, "Please add a category", Toast.LENGTH_LONG).show()
    }

    override fun onDialogPositiveClick(name: String) {
        val newCat = CategoryDb(0, name)
        viewModel.addCategory(newCat)
        addDialogFragment.dismiss()
    }
}