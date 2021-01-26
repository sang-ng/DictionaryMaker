package com.example.android.vocabularyapp.ui.words

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
<<<<<<< HEAD
import android.view.MenuItem
=======
>>>>>>> 8f6f6ec158046e5b269395beb7c0d27954fbd180
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.vocabularyapp.R
import com.example.android.vocabularyapp.databinding.ActivityWordsBinding
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.Word
import com.example.android.vocabularyapp.ui.addWord.AddWordActivity
import com.example.android.vocabularyapp.ui.category.CategoryListAdapter
import com.example.android.vocabularyapp.ui.learn.LearnActivity
import org.koin.android.viewmodel.ext.android.viewModel

class WordsActivity : AppCompatActivity(), WordListAdapter.ItemClickListener {

    private lateinit var binding: ActivityWordsBinding
    private val viewModel by viewModel<WordsViewModel>()
    private lateinit var listAdapter: WordListAdapter
    private val CATEGORY = "category_arg"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordsBinding.inflate(layoutInflater)
        lifecycle.addObserver(viewModel)

        getCategoryFromIntent()
        initOnClick()
        initRecyclerView()
        observeWords()

        setContentView(binding.root)
    }

    private fun getCategoryFromIntent() {
        val category: Category? = intent.getParcelableExtra(CATEGORY)

        category?.run {
            viewModel.setSelectedCategory(this)
        }
    }

    private fun initOnClick() {

        binding.wordsAddBtn.setOnClickListener {
            viewModel.category.value?.let {
                startAddWordActivity(category = it)
            }
        }

        binding.wordsStartBtn.setOnClickListener {
            viewModel.category.value?.let {
                startLearnActivity(category = it)
            }
        }

        binding.wordsSetting.setOnClickListener {
            showPopUp(binding.wordsSetting)
        }
    }

    private fun showPopUp(view: View) {
        val popUp = PopupMenu(this, view)
        popUp.inflate(R.menu.menu_category)
        popUp.show()

        popUp.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_cat_delete -> {
                    viewModel.deleteCategory()
                    finish()
                }
                R.id.menu_cat_rename -> Log.i("TEST", "rename")
                else -> TODO()
            }
            true
        }
    }


    private fun initRecyclerView() {
        listAdapter = WordListAdapter(ArrayList(), this)

        binding.wordsRecyclerview.apply {
            layoutManager = LinearLayoutManager(this.context)
            itemAnimator = DefaultItemAnimator()
            adapter = listAdapter
        }
    }

    private fun observeWords() {
        viewModel.words.observe(this, { words ->
            if (words.isNullOrEmpty()) {
                Toast.makeText(this, "Please add some words to start!", Toast.LENGTH_LONG).show()
            } else {
                renderUI(listItems = words)
            }
        })
    }

    private fun renderUI(listItems: List<Word>) {
        listAdapter.setData(listItems)
        binding.wordsStartBtn.visibility = View.VISIBLE
    }

    private fun startLearnActivity(category: Category) {
        startActivity(Intent(this, LearnActivity::class.java).apply {
            
            putExtra(CATEGORY, category)

        })
    }

    private fun startAddWordActivity(category: Category) {
        startActivity(Intent(this, AddWordActivity::class.java).apply {
            putExtra(CATEGORY, category)
        })
    }

    override fun onItemClick(position: Int) {
        Log.i("TEST", "clicked")
    }

    override fun onItemLongClick(position: Int) {
        Log.i("TEST", "Clicked")
    }
}