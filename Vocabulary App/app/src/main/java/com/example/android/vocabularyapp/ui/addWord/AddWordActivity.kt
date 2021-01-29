package com.example.android.vocabularyapp.ui.addWord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.android.vocabularyapp.databinding.ActivityAddWordBinding
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.Word
import org.koin.android.viewmodel.ext.android.viewModel

class AddWordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddWordBinding
    private val viewModel by viewModel<AddWordViewModel>()
    private val CATEGORY = "category_arg"
    private val WORD = "word_arg"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWordBinding.inflate(layoutInflater)

        setSupportActionBar(binding.addToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        getDataFromIntent()
        initOnClick()
        observeSelectedWord()

        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()

        return super.onSupportNavigateUp()
    }

    private fun getDataFromIntent() {
        val category: Category? = intent.getParcelableExtra(CATEGORY)
        val word: Word? = intent.getParcelableExtra(WORD)

        category?.run { viewModel.setSelectedCategory(this) }

        word?.run { viewModel.setSelectedWord(this) }
    }

    private fun initOnClick() {

        binding.addWordBtn.setOnClickListener {
            val name = binding.addWordName.text.toString()
            val translation = binding.addWordTrans.text.toString()

            viewModel.addOrUpdate(name, translation)
            finish()
        }
    }

    private fun observeSelectedWord() {
        viewModel.word.observe(this, { word ->
            word?.let {
                binding.addWordName.setText(word.name)
                binding.addWordTrans.setText(word.translation)
            }
        })
    }
}