package com.example.android.vocabularyapp.ui.addWord

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.android.vocabularyapp.R
import com.example.android.vocabularyapp.databinding.ActivityAddWordBinding
import com.example.android.vocabularyapp.databinding.ActivityCategoryBinding
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.ui.category.CategoryViewModel
import com.example.android.vocabularyapp.ui.words.WordsActivity
import org.koin.android.viewmodel.ext.android.viewModel

class AddWordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddWordBinding
    private val viewModel by viewModel<AddWordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWordBinding.inflate(layoutInflater)

        getCategoryFromIntent()
        initOnClick()

        setContentView(binding.root)
    }

    private fun getCategoryFromIntent() {
        val category: Category? = intent.getParcelableExtra(CATEGORY)

        category?.run {
            viewModel.setSelectedCategory(this)
        }
    }

    private fun initOnClick() {

        binding.addWordBtn.setOnClickListener {
            val name = binding.addWordName.text.toString()
            val translation = binding.addWordTrans.text.toString()

            viewModel.addWord(name, translation)
            finish()
        }
    }

    companion object {
        private const val CATEGORY = "category_arg"

        fun startActivity(context: Context, category: Category) {
            val intent = Intent(context, AddWordActivity::class.java)

            intent.putExtra(CATEGORY, category)

            context.startActivity(intent)
        }
    }
}