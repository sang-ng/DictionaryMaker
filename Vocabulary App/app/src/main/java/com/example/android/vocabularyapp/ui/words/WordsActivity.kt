package com.example.android.vocabularyapp.ui.words

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.vocabularyapp.R
import com.example.android.vocabularyapp.databinding.ActivityCategoryBinding
import com.example.android.vocabularyapp.databinding.ActivityWordsBinding
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.Word
import com.example.android.vocabularyapp.ui.addWord.AddWordActivity
import com.example.android.vocabularyapp.ui.category.CategoryListAdapter
import com.example.android.vocabularyapp.ui.category.CategoryViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class WordsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWordsBinding
    private val viewModel by viewModel<WordsViewModel>()
    private lateinit var listAdapter: WordListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordsBinding.inflate(layoutInflater)

        getCategoryFromIntent()
        initOnClick()
        observeSelectedCategory()
        initRecyclerView()
        observeWords()
        lifecycle.addObserver(viewModel)

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
            AddWordActivity.startActivity(this, viewModel.category.value!!)
        }
    }

    private fun initRecyclerView() {
        listAdapter = WordListAdapter()

        binding.wordsRecyclerview.apply {
            layoutManager = LinearLayoutManager(this.context)
            itemAnimator = DefaultItemAnimator()
            adapter = listAdapter
        }
    }

    private fun observeWords() {
        viewModel.words.observe(this, { words ->
            listAdapter.setData(words)
        })
    }

    private fun observeSelectedCategory() {
        viewModel.category.observe(this, { selectedCategory ->
            Log.i("TEST", selectedCategory.name)
        })
    }

    companion object {
        private const val CATEGORY = "category_arg"

        fun startActivity(context: Context, category: Category) {
            val intent = Intent(context, WordsActivity::class.java)

            intent.putExtra(CATEGORY, category)

            context.startActivity(intent)
        }
    }
}