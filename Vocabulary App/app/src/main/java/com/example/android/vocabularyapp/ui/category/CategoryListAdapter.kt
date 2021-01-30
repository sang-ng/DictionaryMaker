package com.example.android.vocabularyapp.ui.category

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.vocabularyapp.database.dao.WordDao
import com.example.android.vocabularyapp.databinding.CategoryListItemBinding
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.repository.CategoryRepository
import com.example.android.vocabularyapp.repository.WordsRepository

class CategoryListAdapter(
    private val categoryList: ArrayList<Category> = ArrayList(),
    val clickListener: ItemClickListener,
    private val repository: WordDao
) : RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>() {

    interface ItemClickListener {
        fun onItemClick(position: Int)
    }

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

    inner class CategoryViewHolder(private val itemBinding: CategoryListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemView.setOnClickListener { clickListener.onItemClick(adapterPosition) }
        }

        fun bind(category: Category) {
            itemBinding.categoryListName.text = category.name
            itemBinding.categoryWordsNumber.text =
                repository.getTotalWordsOfCategory(category.id).toString()
        }
    }

    fun setData(list: List<Category>) {
        categoryList.clear()
        categoryList.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        categoryList.clear()
        notifyDataSetChanged()
    }
}
