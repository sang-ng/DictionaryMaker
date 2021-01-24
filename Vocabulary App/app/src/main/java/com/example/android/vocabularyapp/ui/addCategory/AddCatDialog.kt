package com.example.android.vocabularyapp.ui.addCategory

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.android.vocabularyapp.databinding.DialogCategoryBinding

class CategoryDialogFragment : DialogFragment() {

    private lateinit var listener: CategoryDialogListener
    private lateinit var binding: DialogCategoryBinding

    interface CategoryDialogListener {
        fun onDialogPositiveClick(name: String)
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = context as CategoryDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(
                (context.toString() +
                        " must implement NoticeDialogListener")
            )
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogCategoryBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(context)
        builder.setView(binding.root)

        binding.catAddBtn.setOnClickListener {

            val categoryName = binding.catName.text.toString()
            listener.onDialogPositiveClick(categoryName)
        }

        return builder.create()
    }
}












