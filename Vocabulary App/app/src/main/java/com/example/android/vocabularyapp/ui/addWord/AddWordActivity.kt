package com.example.android.vocabularyapp.ui.addWord

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.vocabularyapp.R
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.ui.words.WordsActivity

class AddWordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)
    }

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, AddWordActivity::class.java)

            context.startActivity(intent)
        }
    }
}