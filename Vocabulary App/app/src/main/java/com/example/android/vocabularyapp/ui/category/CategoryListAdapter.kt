package com.example.android.vocabularyapp.ui.category

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.vocabularyapp.R
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.utils.inflate

class CategoryListAdapter(private val categories: ArrayList<Category>) :
    RecyclerView.Adapter<CategoryListAdapter.CategoryHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {

        val inflatedView = parent.inflate(R.layout.category_list_item, false)
        return CategoryHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val itemCategory = categories[position]
        holder.bind(itemCategory)
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

//            val context = itemView.context
//            val showPhotoIntent = Intent(context, PhotoActivity::class.java)
//            showPhotoIntent.putExtra(PHOTO_KEY, photo)
//            context.startActivity(showPhotoIntent)
        }

        fun bind(category: Category)   {

        }

        companion object {
            //5
            private val PHOTO_KEY = "PHOTO"
        }
    }
}

