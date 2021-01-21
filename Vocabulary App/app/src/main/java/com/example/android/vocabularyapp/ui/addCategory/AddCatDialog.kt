package com.example.android.vocabularyapp.ui.addCategory

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.android.vocabularyapp.R
import java.util.*

class CategoryDialogFragment : DialogFragment() {

    private lateinit var listener: CategoryDialogListener

    interface CategoryDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
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
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;

            builder.setView(inflater.inflate(R.layout.dialog_category, null))
                // Add action buttons
                .setPositiveButton("SAVE",
                    DialogInterface.OnClickListener { _, _ ->
                        listener.onDialogPositiveClick(this)
                    })
                .setNegativeButton("CANCEL",
                    DialogInterface.OnClickListener { _, _ ->
                        dialog?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}