package com.example.gerenciadordecolecaodefilmes

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)   {

    companion object {
        private const val DATABASE_NAME = "MovieDB"
        private const val DATABASE_VERSION = 1

        const val TABLE_COLLECTIONS = "collections"
        const val COL_C_ID = "id"
        const val COL_C_NAME = "name"

        const val TABLE_MOVIES = "movies"
        const val COL_M_ID = "id"
        const val COL_M_TITLE = "title"
        const val COL_M_YEAR = "year"
        const val COL_M_GENRE = "genre"
        const val COL_M_COLLECTION_ID = "collection_id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_COLLECTIONS_TABLE = ("CREATE TABLE $TABLE_COLLECTIONS ("
                + "$COL_C_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COL_C_NAME TEXT)")
        db.execSQL(CREATE_COLLECTIONS_TABLE)

        val CREATE_MOVIES_TABLE = ("CREATE TABLE $TABLE_MOVIES ("
                + "$COL_M_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COL_M_TITLE TEXT,"
                + "$COL_M_YEAR INTEGER,"
                + "$COL_M_GENRE TEXT,"
                + "$COL_M_COLLECTION_ID INTEGER,"
                + "FOREIGN KEY($COL_M_COLLECTION_ID) REFERENCES $TABLE_COLLECTIONS($COL_C_ID))")
        db.execSQL(CREATE_MOVIES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MOVIES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_COLLECTIONS")
        onCreate(db)
    }

}