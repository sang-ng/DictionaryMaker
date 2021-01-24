package com.example.android.vocabularyapp.ui.words

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.vocabularyapp.databinding.ActivityWordsBinding
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.Word
import com.example.android.vocabularyapp.ui.addWord.AddWordActivity
import com.example.android.vocabularyapp.ui.learn.LearnActivity
import org.koin.android.viewmodel.ext.android.viewModel

class WordsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWordsBinding
    private val viewModel by viewModel<WordsViewModel>()
    private lateinit var listAdapter: WordListAdapter


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
            AddWordActivity.startActivity(this, viewModel.category.value!!)
        }

        binding.wordsStartBtn.setOnClickListener {
            LearnActivity.startActivity(this, viewModel.category.value!!)
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
            if (words.isNullOrEmpty()) {
                Toast.makeText(this, "Please add some words to start!", Toast.LENGTH_LONG).show()
            } else {
               renderUI(listItems = words)
            }
        })
    }

    private fun renderUI(listItems : List<Word>){
        listAdapter.setData(listItems)
        binding.wordsStartBtn.visibility = View.VISIBLE
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