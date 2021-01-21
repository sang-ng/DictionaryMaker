package com.example.android.vocabularyapp.ui.category

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.vocabularyapp.model.Category

class CategoryListAdapter(private val categories: ArrayList<Category>) :
    RecyclerView.Adapter<CategoryListAdapter.CategoryHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): CategoryHolder {

        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = categories.size

    //1
    class CategoryHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        //2
        private var view: View = v
        private var category: Category? = null

        //3
        init {
            v.setOnClickListener(this)
        }

        //4
        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }

        companion object {
            //5
            private val PHOTO_KEY = "PHOTO"
        }
    }
}

