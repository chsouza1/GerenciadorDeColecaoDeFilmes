package com.example.gerenciadordecolecaodefilmes

import CollectionDao
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.selects.SelectInstance

class MovieRegistrationActivity : AppCompatActivity() {

    private lateinit var dao: CollectionDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_registration)

        dao = CollectionDao(this)

    }


}