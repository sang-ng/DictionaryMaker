package com.example.android.vocabularyapp.ui.category

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.vocabularyapp.R
import com.example.android.vocabularyapp.databinding.CategoryListItemBinding
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.ui.words.WordsActivity

class CategoryListAdapter(private val categoryList: ArrayList<Category> = ArrayList()) :
    RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemBinding =
            CategoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    class CategoryViewHolder(private val itemBinding: CategoryListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(category: Category) = with(itemView) {

            itemBinding.categoryListName.text = category.name

            itemBinding.categoryListLayout.setOnClickListener { WordsActivity.startActivity(itemView.context) }
        }
    }

    fun setData(list: List<Category>) {
        categoryList.clear()
        categoryList.addAll(list)
        notifyDataSetChanged()
    }
}