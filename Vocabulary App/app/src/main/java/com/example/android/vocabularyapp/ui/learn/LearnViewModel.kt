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