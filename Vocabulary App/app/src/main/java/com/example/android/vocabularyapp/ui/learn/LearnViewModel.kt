package com.example.android.vocabularyapp.ui.learn

import androidx.lifecycle.*
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.Word
import com.example.android.vocabularyapp.repository.WordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LearnViewModel(private val repository: WordsRepository) : ViewModel(),
    DefaultLifecycleObserver {

    //TODO: make sure that each word is displayed only once
    //TODO: decide if word was known and append to appropriate list (good/bad)


// get list from
//     bad list: every item with goodWord = false
//    get random item of badlist
//   When correct answer set goodWord = true of current word
//     display next word of bad list
//
//     start displaying word from good list only when bad list is empty
//    when word of good list was not correct set goodWord to false
//     when bad list has item start displaying item from there again

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
            val words = _category.value?.id?.let { repository.getWordsOfCategory(it) }

            _currentWord.postValue(words?.random())
        }
    }

     fun onCardClicked() {
         _showTranslationEvent.value = _showTranslationEvent.value == false
    }
}