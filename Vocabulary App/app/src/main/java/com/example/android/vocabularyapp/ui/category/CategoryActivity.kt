package com.example.android.vocabularyapp.ui.category

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.vocabularyapp.database.entities.CategoryDb
import com.example.android.vocabularyapp.databinding.ActivityCategoryBinding
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.ui.addCategory.CategoryDialogFragment
import org.koin.android.ext.android.get
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

        initDialogFragment()
        initOnClick()
        initRecyclerView()
        observeCategories()

        setContentView(binding.root)
    }


    private fun initOnClick() {
        binding.categoryAddButton.setOnClickListener {

            showAddDialog()
        }
    }

    private fun initRecyclerView() {
        listAdapter = CategoryListAdapter()

        binding.categoryRecyclerview.apply {
            layoutManager = LinearLayoutManager(this.context)
            itemAnimator = DefaultItemAnimator()
            adapter = listAdapter
        }
    }

    private fun initDialogFragment(){
        fragmentManager = supportFragmentManager
        addDialogFragment = CategoryDialogFragment()
    }

    private fun showAddDialog() {
        addDialogFragment.show(fragmentManager, "AddCatDialog")
    }

    private fun observeCategories() {
        viewModel.categories.observe(this, { categories ->
            listAdapter.setData(categories)
        })
    }

    override fun onDialogPositiveClick(name: String) {
        val newCat = CategoryDb(0, name)
        viewModel.addCategory(newCat)

        addDialogFragment.dismiss()
    }
}