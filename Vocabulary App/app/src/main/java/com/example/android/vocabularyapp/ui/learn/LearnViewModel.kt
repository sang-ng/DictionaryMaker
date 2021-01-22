package com.example.android.vocabularyapp.ui.learn

import android.util.Log
import androidx.lifecycle.*
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.Word
import com.example.android.vocabularyapp.repository.WordsRepository
import com.example.android.vocabularyapp.ui.words.WordsViewModel
import com.example.android.vocabularyapp.utils.CardStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LearnViewModel(private val repository: WordsRepository) : ViewModel(), DefaultLifecycleObserver{


    val category: LiveData<Category>
        get() = _category

    val badWords: LiveData<List<Word>>
        get() = _badWords

    val currentWord: LiveData<Word>
        get() = _currentWord

    val cardStatus: LiveData<CardStatus>
        get() = _cardStatus

    private var _category = MutableLiveData<Category>()
    private var _badWords = MutableLiveData<List<Word>>()
    private var _currentWord = MutableLiveData<Word>()
    private var _cardStatus = MutableLiveData<CardStatus>()


    init {
        getBadWords()
        getRandomWord()
        _cardStatus.value = CardStatus.NAME
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)

        getBadWords()
        getRandomWord()
        _cardStatus.value = CardStatus.NAME
    }


    fun setSelectedCategory(category: Category) {
        _category.value = category
    }

//    private fun getWords() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val words = _category.value?.id?.let { repository.getFilteredWords(it) }
//
//            _words.postValue(words)
//        }
//    }

    private fun getBadWords(){
        viewModelScope.launch(Dispatchers.IO) {
            val words = _category.value?.id?.let { repository.getFilteredBadWords(it) }

            _badWords.postValue(words)
            Log.i("TEST","bad words: " + words.toString())
        }
    }

     fun onYesButtonClicked(){
        _cardStatus.value = CardStatus.YES
    }

    fun onCardClicked(){
        _cardStatus.value = CardStatus.TRANSLATION
    }

    private fun getRandomWord() {

        viewModelScope.launch(Dispatchers.IO) {
            val words = _category.value?.id?.let { repository.getFilteredBadWords(it) }

            _currentWord.postValue(words?.first())
        }
    }
}