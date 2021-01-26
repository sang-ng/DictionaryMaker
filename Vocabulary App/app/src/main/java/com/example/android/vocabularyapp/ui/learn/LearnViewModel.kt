package com.example.android.vocabularyapp.ui.learn

import android.util.Log
import androidx.lifecycle.*
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.Word
import com.example.android.vocabularyapp.repository.WordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LearnViewModel(private val repository: WordsRepository) : ViewModel(),
    DefaultLifecycleObserver {

    //TODO: feat: learn session consists of 10 words, if less words available session = list.size

    val currentWord: LiveData<Word>
        get() = _currentWord

    val showTranslationEvent: LiveData<Boolean>
        get() = _showTranslationEvent

    val showSessionCompleteEvent: LiveData<Boolean>
        get() = _showSessionCompleteEvent

    private var _category = MutableLiveData<Category>()
    private var _currentWord = MutableLiveData<Word>()
    private var _showTranslationEvent = MutableLiveData<Boolean>()
    private var _showSessionCompleteEvent = MutableLiveData<Boolean>()

    private var itemPosCounter = 0
    private var sessionCounter = 0

    init {
        getCurrentWord()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)

        getCurrentWord()
    }


    fun setSelectedCategory(category: Category) {
        _category.value = category
    }

    fun getCurrentWord() {
        viewModelScope.launch(Dispatchers.IO) {

            val allWords = getAllWords()
            val badWords = getBadWords()

            //make sure that bad words are asked more often than well known ones
            if (!badWords.isNullOrEmpty() && badWords.size > 1) {

                if (itemPosCounter < badWords.size) {
                    _currentWord.postValue(badWords[getBadItemPosition()])
                } else {
                    itemPosCounter = 0
                    _currentWord.postValue(badWords[getBadItemPosition()])
                }

            } else {

                if (itemPosCounter < allWords!!.size) {
                    _currentWord.postValue(allWords[getBadItemPosition()])
                } else {
                    itemPosCounter = 0
                    _currentWord.postValue(allWords[getBadItemPosition()])
                }
            }
        }
    }

    private fun getAllWords() = _category.value?.id?.let { repository.getWordsOfCategory(it) }

    private fun getBadWords() = _category.value?.id?.let { repository.getBadWordsOfCategory(it) }

    fun onCardClicked() {
        _showTranslationEvent.value = _showTranslationEvent.value == false
    }

    fun onYesClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            _currentWord.value?.goodWord = 1

            repository.updateWord(_currentWord.value!!)
        }

        checkIfSessionCompleted()
        sessionCounter++
    }

    fun onNoClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            _currentWord.value?.goodWord = 0

            repository.updateWord(_currentWord.value!!)
        }

        checkIfSessionCompleted()
        sessionCounter++
    }

    private fun checkIfSessionCompleted() {
        _showSessionCompleteEvent.value = sessionCounter == 2
    }

    private fun getBadItemPosition() = itemPosCounter++

}
