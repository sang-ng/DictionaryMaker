package com.example.android.vocabularyapp.ui.category

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.vocabularyapp.R
import com.example.android.vocabularyapp.database.entities.CategoryDb
import com.example.android.vocabularyapp.database.entities.toDomainModel
import com.example.android.vocabularyapp.databinding.ActivityCategoryBinding
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.ui.addCategory.AddCatDialog
import com.example.android.vocabularyapp.ui.words.WordsActivity
import org.koin.android.ext.android.bind
import org.koin.android.viewmodel.ext.android.viewModel


class CategoryActivity : AppCompatActivity(), AddCatDialog.CategoryDialogListener,
    CategoryListAdapter.ItemClickListener,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private val viewModel by viewModel<CategoryViewModel>()
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var listAdapter: CategoryListAdapter
    private lateinit var fragmentManager: FragmentManager
    private lateinit var catDialogFragment: DialogFragment
    private val CATEGORY = "category_arg"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        lifecycle.addObserver(viewModel)
        setSupportActionBar(binding.topAppBar)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        initOnClick()
        initRecyclerView()
        initDialogFragment()
        observeCategories()
        observeTotalNumberOfWords()

        setContentView(binding.root)
    }

    private fun initOnClick() {
        binding.categoryAddButton.setOnClickListener {
            showAddDialog()
        }
    }

    private fun showAddDialog() {
        catDialogFragment.show(fragmentManager, "CatDialog")
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
        catDialogFragment = AddCatDialog()
    }

    private fun observeCategories() {
        viewModel.categories.observe(this, { categories ->
            if (categories.isNullOrEmpty()) {
                listAdapter.clear()
                binding.categoryNoWords.visibility = View.VISIBLE
            } else {
                listAdapter.setData(categories)
                binding.categoryNoWords.visibility = View.INVISIBLE
            }
        })
    }


    override fun onDialogPositiveClick(name: String) {
        val newCat = CategoryDb(0, name)
        viewModel.addCategory(newCat)
        catDialogFragment.dismiss()
    }

    override fun onItemClick(id: Long) {
        viewModel.categories.value?.find { it.id == id }?.let {
            startWordsActivity(it)
        }
    }

    private fun startWordsActivity(category: Category) {
        startActivity(Intent(this, WordsActivity::class.java).apply {
            putExtra(CATEGORY, category)
        })
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)

    }

    private fun observeTotalNumberOfWords() {
        viewModel.totalOfWords.observe(this, { totalNumberOfWords ->
            binding.topAppBar.title = "$totalNumberOfWords words"
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(this)

        return true
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%" // "%" required for custom sql query

        viewModel.searchDatabase(searchQuery).observe(this, { list ->
            list.let {
                listAdapter.setData(it.toDomainModel())
            }
        })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }
        return true
    }
}
















