package com.example.android.vocabularyapp.ui.learn

import androidx.lifecycle.*
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.Word
import com.example.android.vocabularyapp.repository.WordsRepository
import com.example.android.vocabularyapp.utils.CardStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LearnViewModel(private val repository: WordsRepository) : ViewModel(),
    DefaultLifecycleObserver {

    //TODO: make sure that each word is displayed only once


    //TODO: bad list: every item with goodWord = false
    //TODO: get random item of badlist
    //TODO: When correct answer set goodWord = true of current word
    //TODO: display next word of bad list

    //TODO: start displaying word from good list only when bad list is empty
    //TODO: when word of good list was not correct set goodWord to false
    //TODO: when bad list has item start displaying item from there again

    val currentWord: LiveData<Word>
        get() = _currentWord

    val showTranslationEvent : LiveData<Boolean>
    get() = _showTranslationEvent

    private var _category = MutableLiveData<Category>()
    private var _currentWord = MutableLiveData<Word>()
    private var _showTranslationEvent = MutableLiveData<Boolean>()

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
            val words = _category.value?.id?.let { repository.getFilteredWords(it) }

            _currentWord.postValue(words?.random())
        }
    }

     fun onCardClicked() {
         _showTranslationEvent.value = _showTranslationEvent.value == false
    }
}