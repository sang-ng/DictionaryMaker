package com.example.android.vocabularyapp.ui.learn

import androidx.lifecycle.*
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.Word
import com.example.android.vocabularyapp.repository.WordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LearnViewModel(private val repository: WordsRepository) : ViewModel(),
    DefaultLifecycleObserver {

    val currentWord: LiveData<Word>
        get() = _currentWord

    val showTranslationEvent: LiveData<Boolean>
        get() = _showTranslationEvent

    val showSessionCompleteEvent: LiveData<Boolean>
        get() = _showSessionCompleteEvent


    val category: LiveData<Category>
        get() = _category

    private var _category = MutableLiveData<Category>()
    private var _currentWord = MutableLiveData<Word>()
    private var _showTranslationEvent = MutableLiveData<Boolean>()
    private var _showSessionCompleteEvent = MutableLiveData<Boolean>()


    private var _itemPosCounter = 0
    private var _randomWords = listOf<Word>()

    init {
        getCurrentWord()
        _showTranslationEvent.value = true
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

            allWords?.let {

                if (_itemPosCounter == 0) {
                    _randomWords = it.shuffled()
                }

                if (_itemPosCounter < _randomWords.size) {

                    _currentWord.postValue(_randomWords[_itemPosCounter])

                } else if (_itemPosCounter == _randomWords.size) {

                    _itemPosCounter = 0
                    _showSessionCompleteEvent.postValue(true)
                }
            }
        }
    }

    private fun getAllWords() = _category.value?.id?.let { repository.getWordsOfCategory(it) }

    fun onCardClicked() {
        _showTranslationEvent.value = _showTranslationEvent.value == false
    }

    fun onYesClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            _currentWord.value?.goodWord = 1

            _currentWord.value?.let { repository.updateWord(it) }
        }

        _itemPosCounter++
    }

    fun onNoClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            _currentWord.value?.goodWord = 0

            _currentWord.value?.let { repository.updateWord(it) }
        }

        _itemPosCounter++
    }

    fun showSessionCompleteDone() {
        _itemPosCounter = 0
        _showSessionCompleteEvent.value = false
    }
}
