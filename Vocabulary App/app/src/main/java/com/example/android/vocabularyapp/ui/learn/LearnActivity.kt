package com.example.android.vocabularyapp.ui.learn

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android.vocabularyapp.databinding.ActivityLearnBinding
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.Word
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class LearnActivity : AppCompatActivity() {

    private val CATEGORY = "category_arg"

    private val viewModel by viewModel<LearnViewModel>()
    private lateinit var binding: ActivityLearnBinding
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnBinding.inflate(layoutInflater)
        lifecycle.addObserver(viewModel)

        getCategoryFromIntent()
        observeCurrentWord()
        initOnClick()
        observeShowTranslationEvent()
        observeSessionsEvent()
        observeNumberOGoodWords()
        initTextToSpeech()


        setContentView(binding.root)
    }

    private fun speakWord() {
        val wordToSpeech = binding.learnTranslation.text.toString()
        textToSpeech.speak(wordToSpeech, TextToSpeech.QUEUE_FLUSH, null)
    }

    private fun initTextToSpeech() {
        textToSpeech = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR) {
                textToSpeech.language = Locale.UK
            }
        }
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

    private fun observeSessionsEvent() {
        viewModel.showSessionCompleteEvent.observe(this, { sessionCompleted ->
            if (sessionCompleted) {
                viewModel.showSessionCompleteDone()
                Toast.makeText(this, "Session completed!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun observeNumberOGoodWords() {
        viewModel.numberOfGoodWords.observe(this, { numberOfGoodWords ->
            Log.i("TEST", numberOfGoodWords.toString())
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

        binding.learnPronunciation.setOnClickListener {
            speakWord()
        }
    }

    private fun renderUI(word: Word) {
        binding.learnWord.text = word.name
        binding.learnTranslation.text = word.translation
    }

    override fun onPause() {

        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }

        super.onPause()
    }
}