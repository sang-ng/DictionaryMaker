package com.example.android.vocabularyapp.ui.learn

import androidx.lifecycle.*
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.Word
import com.example.android.vocabularyapp.repository.WordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LearnViewModel(private val repository: WordsRepository) : ViewModel(),
    DefaultLifecycleObserver {


    /*
    TODO: - getRandomWord -> only one word once per session
          - numberOfWords doesn't work properly yet
          - display goodWords, display word in sum
          - make progressbar out of it
     */

    val currentWord: LiveData<Word>
        get() = _currentWord

    val showTranslationEvent: LiveData<Boolean>
        get() = _showTranslationEvent

    val showSessionCompleteEvent: LiveData<Boolean>
        get() = _showSessionCompleteEvent

    val numberOfGoodWords: LiveData<Int>
        get() = _numberOfGoodWords

    private var _category = MutableLiveData<Category>()
    private var _currentWord = MutableLiveData<Word>()
    private var _numberOfGoodWords = MutableLiveData<Int>()
    private var _showTranslationEvent = MutableLiveData<Boolean>()
    private var _showSessionCompleteEvent = MutableLiveData<Boolean>()


    private var itemPosCounter = 0

    init {
        getCurrentWord()
        getNumberOfGoodWords()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)

        getCurrentWord()
        getNumberOfGoodWords()
    }


    fun setSelectedCategory(category: Category) {
        _category.value = category
    }

    fun getCurrentWord() {
        viewModelScope.launch(Dispatchers.IO) {

            val allWords = getAllWords()
            var randomWords = listOf<Word>()

            allWords?.let {
                randomWords = it.shuffled()

                if (itemPosCounter < randomWords.size) {
                    _currentWord.postValue(randomWords[itemPosCounter])
                } else if (itemPosCounter == randomWords.size) {
                    itemPosCounter = 0
                    _showSessionCompleteEvent.postValue(true)
                }
            }
        }
    }

    private fun getNumberOfGoodWords() {
        viewModelScope.launch(Dispatchers.IO) {
            val allWords = getAllWords()

            val goodWords = allWords?.filter { it.goodWord == 1 }

            goodWords?.let {
                var numberOfGoodWords = 0

                for ((i, word) in goodWords.withIndex()) {
                    numberOfGoodWords += i
                }

                _numberOfGoodWords.postValue(numberOfGoodWords)
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

        itemPosCounter++
        getNumberOfGoodWords()
    }

    fun onNoClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            _currentWord.value?.goodWord = 0

            _currentWord.value?.let { repository.updateWord(it) }
        }

        itemPosCounter++
        getNumberOfGoodWords()
    }

    fun showSessionCompleteDone() {
        itemPosCounter = 0
        _showSessionCompleteEvent.value = false
    }
}
