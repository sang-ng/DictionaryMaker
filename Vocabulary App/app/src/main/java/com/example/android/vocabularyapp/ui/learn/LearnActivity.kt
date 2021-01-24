package com.example.android.vocabularyapp.ui.learn

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.android.vocabularyapp.databinding.ActivityLearnBinding
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.Word
import org.koin.android.ext.android.bind
import org.koin.android.viewmodel.ext.android.viewModel

class LearnActivity : AppCompatActivity() {


    private val viewModel by viewModel<LearnViewModel>()
    private lateinit var binding: ActivityLearnBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnBinding.inflate(layoutInflater)
        lifecycle.addObserver(viewModel)

        getCategoryFromIntent()
        observeCurrentWord()
        initOnClick()
        observeShowTranslationEvent()

        setContentView(binding.root)
    }

    private fun getCategoryFromIntent() {
        val category: Category? = intent.getParcelableExtra(CATEGORY)

        category?.run {
            viewModel.setSelectedCategory(this)
        }
    }

    private fun observeCurrentWord() {
        viewModel.currentWord.observe(this, { word ->
            renderUI(word)
        })
    }

    private fun observeShowTranslationEvent() {
        viewModel.showTranslationEvent.observe(this, { showTranslationEvent ->

            if (showTranslationEvent) {
                binding.learnTranslation.visibility = View.VISIBLE
                binding.learnWord.visibility = View.INVISIBLE
            } else {
                binding.learnTranslation.visibility = View.INVISIBLE
                binding.learnWord.visibility = View.VISIBLE
            }
        })
    }

    private fun initOnClick() {

        binding.learnCard.setOnClickListener {
            viewModel.onCardClicked()
        }

        binding.learnYesBtn.setOnClickListener {
            viewModel.onYesClicked()
            viewModel.getCurrentWord()
        }

        binding.learnNoBtn.setOnClickListener {
            viewModel.onNoClicked()
            viewModel.getCurrentWord()
        }
    }

    fun startActivity() {
        startActivity(Intent(this, LearnActivity::class.java).apply {
            putExtra(CATEGORY, category)
        })
    }

    private fun renderUI(word: Word) {
        binding.learnWord.text = word.name
        binding.learnTranslation.text = word.translation
    }

    companion object {
        private const val CATEGORY = "category_arg"


    }
}