package com.example.android.vocabularyapp.ui.category

import android.content.Intent
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
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.ui.addCategory.CategoryDialogFragment
import com.example.android.vocabularyapp.ui.addWord.AddWordActivity
import com.example.android.vocabularyapp.ui.words.WordsActivity
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

class CategoryActivity : AppCompatActivity(), CategoryDialogFragment.CategoryDialogListener,
    CategoryListAdapter.ItemClickListener {

    private val viewModel by viewModel<CategoryViewModel>()
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var listAdapter: CategoryListAdapter
    private lateinit var fragmentManager: FragmentManager
    private lateinit var addDialogFragment: DialogFragment
    private val CATEGORY = "category_arg"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)

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
        listAdapter = CategoryListAdapter(ArrayList(), this)

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

    private fun displayNoDataMessage() {
        Toast.makeText(this, "Please add a category", Toast.LENGTH_LONG).show()
    }

    override fun onDialogPositiveClick(name: String) {
        val newCat = CategoryDb(0, name)
        viewModel.addCategory(newCat)
        addDialogFragment.dismiss()
    }

    override fun onItemClick(position: Int) {
        viewModel.categories.value?.get(position)?.let {
            startWordsActivity(it)
        }
    }

    override fun onItemLongClick(position: Int) {
        TODO("Not yet implemented")
    }

    private fun startWordsActivity(category: Category) {
        startActivity(Intent(this, WordsActivity::class.java).apply {
            putExtra(CATEGORY, category)
        })
    }
}