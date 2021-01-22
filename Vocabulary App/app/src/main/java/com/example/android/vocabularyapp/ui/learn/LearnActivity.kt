package com.example.android.vocabularyapp.ui.learn

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.android.vocabularyapp.R
import com.example.android.vocabularyapp.databinding.ActivityLearnBinding
import com.example.android.vocabularyapp.databinding.ActivityWordsBinding
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.ui.words.WordsActivity
import com.example.android.vocabularyapp.ui.words.WordsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class LearnActivity : AppCompatActivity() {

    //TODO: on card click -> display translation of word
    //TODO: on yes clicked -> display new word

    private val viewModel by viewModel<LearnViewModel>()
    private lateinit var binding: ActivityLearnBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnBinding.inflate(layoutInflater)

        getCategoryFromIntent()
        observeWords()

        setContentView(binding.root)
    }

    private fun getCategoryFromIntent() {
        val category: Category? = intent.getParcelableExtra(CATEGORY)

        category?.run {
            viewModel.setSelectedCategory(this)
        }
    }

    private fun observeWords(){
        viewModel.words.observe(this, {words ->
            Log.i("TEST", words.first().name)
        })
    }

    companion object {
        private const val CATEGORY = "category_arg"

        fun startActivity(context: Context, category: Category) {
            val intent = Intent(context, LearnActivity::class.java)

            intent.putExtra(CATEGORY, category)

            context.startActivity(intent)
        }
    }
}