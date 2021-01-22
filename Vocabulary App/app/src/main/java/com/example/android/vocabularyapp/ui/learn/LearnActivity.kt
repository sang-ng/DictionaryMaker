package com.example.android.vocabularyapp.ui.learn

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.android.vocabularyapp.databinding.ActivityLearnBinding
import com.example.android.vocabularyapp.model.Category
import org.koin.android.ext.android.bind
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
            binding.learnWord.text = word.name
            binding.learnTranslation.text = word.translation
        })
    }

    private fun observeShowTranslationEvent() {
        viewModel.showTranslationEvent.observe(this, { showTranslationEvent ->

            if (showTranslationEvent) {
                binding.learnTranslation.visibility = View.VISIBLE
                binding.learnWord.visibility = View.INVISIBLE
            }else{
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
            viewModel.getCurrentWord()
        }
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