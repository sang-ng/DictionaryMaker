package com.example.android.vocabularyapp.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.vocabularyapp.databinding.CategoryListItemBinding
import com.example.android.vocabularyapp.model.Category

class CategoryListAdapter(
    private val categoryList: ArrayList<Category> = ArrayList(),
    val clickListener: ItemClickListener
) : RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>() {

    //TODO: implement search filter
    //TODO: show good and total words for each list item

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
