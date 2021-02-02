package com.example.android.vocabularyapp.ui.addWord

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.android.vocabularyapp.R
import com.example.android.vocabularyapp.databinding.ActivityAddWordBinding
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.Word
import com.example.android.vocabularyapp.ui.words.WordsActivity
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class AddWordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddWordBinding
    private val viewModel by viewModel<AddWordViewModel>()
    private val CATEGORY = "category_arg"
    private val WORD = "word_arg"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWordBinding.inflate(layoutInflater)

        initToolbar()
        getDataFromIntent()
        initOnClick()
        observeSelectedWord()
        observeInputData()

        setContentView(binding.root)
    }

    private fun initToolbar(){
        setSupportActionBar(binding.addToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        overridePendingTransition(R.anim.hold, R.anim.slide_out_bottom)
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
        }
    }

    private fun observeInputData() {
        viewModel.dataIsValid.observe(this, { dataIsValid ->
            if (!dataIsValid) {
                Snackbar.make(binding.root, "Please fill out all fields", Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                finish()
                overridePendingTransition(R.anim.hold, R.anim.slide_out_bottom)
            }
        })
    }

    private fun observeSelectedWord() {
        viewModel.word.observe(this, { word ->
            word?.let {
                binding.addWordName.setText(word.name)
                binding.addWordTrans.setText(word.translation)
            }
        })
    }

    private fun startWordsActivity(category: Category?) {
        startActivity(Intent(this, WordsActivity::class.java).apply {
            putExtra(CATEGORY, category)
        })
        overridePendingTransition(R.anim.slide_out_bottom, R.anim.hold)
    }
}




















