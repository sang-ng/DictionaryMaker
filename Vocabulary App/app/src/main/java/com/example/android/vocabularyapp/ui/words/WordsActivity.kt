package com.example.android.vocabularyapp.ui.words

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
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

class WordsActivity : AppCompatActivity(), WordListAdapter.ItemClickListener,
    AddCatDialog.CategoryDialogListener {

    private lateinit var binding: ActivityWordsBinding
    private val viewModel by viewModel<WordsViewModel>()
    private lateinit var listAdapter: WordListAdapter
    private lateinit var fragmentManager: FragmentManager
    private lateinit var catDialogFragment: DialogFragment
    private lateinit var recyclerView: RecyclerView
    private val CATEGORY = "category_arg"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordsBinding.inflate(layoutInflater)
        lifecycle.addObserver(viewModel)

        getCategoryFromIntent()
        initOnClick()
        initRecyclerView()
        initDialogFragment()
        observeWords()
        observeCategory()
        setRecyclerViewItemTouchListener()

        setContentView(binding.root)
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
                startAddWordActivity(category = it)
            }
        }

        binding.wordsStartBtn.setOnClickListener {
            viewModel.category.value?.let {
                startLearnActivity(category = it)
            }
        }

        binding.wordsSetting.setOnClickListener {
            showCatPopUp(binding.wordsSetting)
        }
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
                R.id.menu_cat_rename -> showDialog()
                else -> TODO()
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
        viewModel.words.observe(this, { words ->
            if (words.isNullOrEmpty()) {
                Toast.makeText(this, "Please add some words to start!", Toast.LENGTH_LONG).show()
            } else {
                renderUI(listItems = words)
            }
        })
    }

    private fun renderUI(listItems: List<Word>) {
        listAdapter.setData(listItems)
        binding.wordsStartBtn.visibility = View.VISIBLE
    }

    private fun startLearnActivity(category: Category) {
        startActivity(Intent(this, LearnActivity::class.java).apply {
            putExtra(CATEGORY, category)
        })
    }

    private fun startAddWordActivity(category: Category) {
        startActivity(Intent(this, AddWordActivity::class.java).apply {
            putExtra(CATEGORY, category)
        })
    }

    override fun onItemClick(position: Int) {
    }

    override fun onItemLongClick(position: Int) {
        Log.i("TEST", "Clicked")
    }

    override fun onDialogPositiveClick(newName: String) {

        viewModel.updateCategory(newName)
        catDialogFragment.dismiss()
    }

    private fun showDialog() {
        catDialogFragment.show(fragmentManager, "CatDialog")
    }

    private fun setRecyclerViewItemTouchListener() {

        val itemTouchCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
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