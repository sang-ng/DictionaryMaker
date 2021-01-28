package com.example.android.vocabularyapp.ui.category

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.vocabularyapp.database.entities.CategoryDb
import com.example.android.vocabularyapp.databinding.ActivityCategoryBinding
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.service.WordsService
import com.example.android.vocabularyapp.ui.addCategory.AddCatDialog
import com.example.android.vocabularyapp.ui.words.WordsActivity
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.collections.ArrayList

class CategoryActivity : AppCompatActivity(), AddCatDialog.CategoryDialogListener,
    CategoryListAdapter.ItemClickListener {

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

        initOnClick()
        initRecyclerView()
        initDialogFragment()
        observeCategories()
        createNotificationChannel()

        startService()

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
                displayNoDataMessage()
                listAdapter.clear()
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
        catDialogFragment.dismiss()
    }

    override fun onItemClick(position: Int) {
        viewModel.categories.value?.get(position)?.let {
            startWordsActivity(it)
        }
    }

    private fun startWordsActivity(category: Category) {
        startActivity(Intent(this, WordsActivity::class.java).apply {
            putExtra(CATEGORY, category)
        })
    }

    private fun startService() {
        val input = arrayListOf<String>()

        val serviceIntent = Intent(this, WordsService::class.java)
        serviceIntent.putStringArrayListExtra("inputExtra", input)
        ContextCompat.startForegroundService(this, serviceIntent)
    }

    private fun stopService() {
        val serviceIntent = Intent(this, WordsService::class.java)
        stopService(serviceIntent)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                "CHANNEL_ID",
                "Example Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}