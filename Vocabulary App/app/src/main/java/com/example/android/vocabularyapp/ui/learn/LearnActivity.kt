package com.example.android.vocabularyapp.ui.learn

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.example.android.vocabularyapp.R
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
    private lateinit var soundCorrect: MediaPlayer
    private lateinit var soundCompleted: MediaPlayer
    private lateinit var soundWrong: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnBinding.inflate(layoutInflater)
        lifecycle.addObserver(viewModel)

        initToolbar()
        getCategoryFromIntent()
        initOnClick()
        addObservers()
        initTextToSpeech()
        initSoundEffects()

        setContentView(binding.root)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.learnToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initSoundEffects() {
        soundCorrect = MediaPlayer.create(this, R.raw.correct)
        soundCompleted = MediaPlayer.create(this, R.raw.success)
        soundWrong = MediaPlayer.create(this, R.raw.wrong)
    }

    override fun onSupportNavigateUp(): Boolean {
        navigateUp()
        return super.onSupportNavigateUp()
    }

    private fun navigateUp() {
        finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
    }

    private fun getCategoryFromIntent() {
        val category: Category? = intent.getParcelableExtra(CATEGORY)

        category?.run {
            viewModel.setSelectedCategory(this)
        }
    }

    private fun initOnClick() {

        binding.learnCard.setOnClickListener {
            initFlipCardAnim()
            viewModel.onCardClicked()
        }

        binding.learnYesBtn.setOnClickListener {
            viewModel.onYesClicked()
            viewModel.getCurrentWord()
            soundCorrect.start()
        }

        binding.learnNoBtn.setOnClickListener {
            viewModel.onNoClicked()
            viewModel.getCurrentWord()
            soundWrong.start()
        }

        binding.learnPronunciation.setOnClickListener {
            speakWord()
        }
    }

    private fun initFlipCardAnim() {
        val oa1 = ObjectAnimator.ofFloat(binding.learnCard, "scaleX", 1f, 0f)
        val oa2 = ObjectAnimator.ofFloat(binding.learnCard, "scaleX", 0f, 1f)
        oa1.interpolator = DecelerateInterpolator()
        oa2.interpolator = AccelerateDecelerateInterpolator()
        oa1.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                oa2.start()
            }
        })
        oa1.start()
    }

    private fun addObservers() {
        observeCurrentWord()
        observeShowTranslationEvent()
        observeSessionsEvent()
    }

    private fun initTextToSpeech() {
        textToSpeech = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR) {
                textToSpeech.language = Locale.UK
            }
        }
    }

    private fun observeCurrentWord() {
        viewModel.currentWord.observe(this, { word ->
            renderUI(word)
        })
    }

    private fun observeShowTranslationEvent() {
        viewModel.showTranslationEvent.observe(this, { showTranslationEvent ->
            showTranslationOrWord(showTranslationEvent)
        })
    }

    private fun showTranslationOrWord(showTranslationEvent: Boolean) {
        if (showTranslationEvent) {
            binding.learnTranslation.visibility = View.VISIBLE
            binding.learnWord.visibility = View.INVISIBLE
        } else {
            binding.learnTranslation.visibility = View.INVISIBLE
            binding.learnWord.visibility = View.VISIBLE
        }
    }

    private fun observeSessionsEvent() {
        viewModel.showSessionCompleteEvent.observe(this, { sessionCompleted ->
            if (sessionCompleted) {
                viewModel.showSessionCompleteDone()
                startSuccessAnim()
                soundCompleted.start()
            }
        })
    }

    private fun startSuccessAnim() {
        binding.learnAnimation.visibility = View.VISIBLE
        binding.learnWord.visibility = View.INVISIBLE
        binding.learnTranslation.visibility = View.INVISIBLE
        binding.learnCard.visibility = View.INVISIBLE
        binding.learnNoBtn.visibility = View.INVISIBLE
        binding.learnYesBtn.visibility = View.INVISIBLE
        binding.learnPronunciation.visibility = View.INVISIBLE
        binding.learnFlip.visibility = View.INVISIBLE
    }

    private fun speakWord() {
        val wordToSpeech = binding.learnTranslation.text.toString()
        textToSpeech.speak(wordToSpeech, TextToSpeech.QUEUE_FLUSH, null)
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