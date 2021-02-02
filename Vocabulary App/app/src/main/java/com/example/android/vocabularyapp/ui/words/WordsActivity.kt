package com.example.android.vocabularyapp.ui.words

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.vocabularyapp.R
import com.example.android.vocabularyapp.databinding.ActivityWordsBinding
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.Word
import com.example.android.vocabularyapp.ui.addCategory.AddCatDialog
import com.example.android.vocabularyapp.ui.addWord.AddWordActivity
import com.example.android.vocabularyapp.ui.learn.LearnActivity
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList


class WordsActivity : AppCompatActivity(), WordListAdapter.ItemClickListener,
    AddCatDialog.CategoryDialogListener {

    private lateinit var textToSpeech: TextToSpeech
    private lateinit var binding: ActivityWordsBinding
    private val viewModel by viewModel<WordsViewModel>()
    private lateinit var listAdapter: WordListAdapter
    private lateinit var fragmentManager: FragmentManager
    private lateinit var catDialogFragment: DialogFragment
    private lateinit var recyclerView: RecyclerView
    private val CATEGORY = "category_arg"
    private val WORD = "word_arg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordsBinding.inflate(layoutInflater)
        lifecycle.addObserver(viewModel)

        initToolbar()
        getCategoryFromIntent()
        initOnClick()
        initRecyclerView()
        initDialogFragment()
        observeWords()
        observeCategory()
        setRecyclerViewItemTouchListener()
        initTextToSpeech()

        setContentView(binding.root)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.wordsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun observeCategory() {
        viewModel.category.observe(this, { category ->
            binding.wordsCategory.text = category.name
        })
    }

    private fun getCategoryFromIntent() {
        val category: Category? = intent.getParcelableExtra(CATEGORY)

        category?.run {
            viewModel.setSelectedCategory(this)
            binding.wordsCategory.text = category.name
        }
    }

    private fun initOnClick() {

        binding.wordsAddBtn.setOnClickListener {
            viewModel.category.value?.let {
                startAddWordActivity(category = it, null)
            }
        }

        binding.wordsStartBtn.setOnClickListener {
            viewModel.category.value?.let {
                startLearnActivity(category = it)
            }
        }

        binding.wordsCatEdit.setOnClickListener {
            showCatPopUp(binding.wordsCatEdit)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        startCategoryActivity()

        return super.onSupportNavigateUp()
    }

    private fun showCatPopUp(view: View) {
        val popUp = PopupMenu(this, view)
        popUp.inflate(R.menu.menu_category)
        popUp.show()

        popUp.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_cat_delete -> {
                    viewModel.deleteCategory()
                    finish()
                }
                R.id.menu_cat_rename -> showRenameDialog()
            }
            true
        }
    }

    private fun initRecyclerView() {
        listAdapter = WordListAdapter(ArrayList(), this)

        recyclerView = binding.wordsRecyclerview.apply {
            layoutManager = LinearLayoutManager(this.context)
            itemAnimator = DefaultItemAnimator()
            adapter = listAdapter
        }
    }

    private fun initDialogFragment() {
        fragmentManager = supportFragmentManager
        catDialogFragment = AddCatDialog()
    }

    private fun observeWords() {
        viewModel.wordsOfCategory.observe(this, { words ->
            if (words.isNullOrEmpty()) {
                binding.wordsAddImage.visibility = View.VISIBLE
                binding.wordsAddText.visibility = View.VISIBLE
                binding.wordsNumber.text = getString(R.string.zero_words)
            } else {
                renderUI(listItems = words)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun renderUI(listItems: List<Word>) {
        listAdapter.setData(listItems)

        binding.wordsStartBtn.visibility = View.VISIBLE

        binding.wordsNumber.text =
            "(" + listItems.filter { it.goodWord == 1 }.count()
                .toString() + " /" + listItems.count()
                .toString() + ")"

        binding.wordsAddImage.visibility = View.INVISIBLE
        binding.wordsAddText.visibility = View.INVISIBLE

        binding.wordsStartBtn.isEnabled = true
        binding.wordsStartBtn.isClickable = true
    }

    private fun startLearnActivity(category: Category) {
        startActivity(Intent(this, LearnActivity::class.java).apply {
            putExtra(CATEGORY, category)
        })
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
    }

    private fun startAddWordActivity(category: Category, word: Word?) {
        startActivity(Intent(this, AddWordActivity::class.java).apply {
            putExtra(CATEGORY, category)
            putExtra(WORD, word)
        })
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.hold)
    }

    private fun startCategoryActivity() {
        finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
    }

    override fun onItemClick(position: Int) {
        val word = viewModel.wordsOfCategory.value?.get(position)
        viewModel.category.value?.let { startAddWordActivity(it, word) }
    }

    override fun onListeningClick(position: Int) {
        val word = viewModel.wordsOfCategory.value?.get(position)?.translation
        word?.let { speakWord(it) }
    }

    private fun initTextToSpeech() {
        textToSpeech = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR) {
                textToSpeech.language = Locale.UK
            }
        }
    }

    private fun speakWord(word: String) {
        textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null)
    }

    override fun onDialogPositiveClick(name: String) {

        viewModel.updateCategory(name)
        binding.wordsCategory.text = name
        catDialogFragment.dismiss()
    }

    private fun showRenameDialog() {
        catDialogFragment.show(fragmentManager, getString(R.string.cat_dialog))
    }

    private fun setRecyclerViewItemTouchListener() {

        val itemTouchCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                viewHolder1: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition

                viewModel.deleteWord(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}