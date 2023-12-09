package com.github.adoptiveparent

import android.content.Context
import android.content.SharedPreferences

private const val DB = "Adoptive_parent_db"
const val NIGHT_MODE = "NIGHT_MODE"
const val NO_FIRST_RUN_APP = "NO_FIRST_RUN_APP"

class SharedPref(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(DB, Context.MODE_PRIVATE)
    private val editSharedPreferences: SharedPreferences.Editor = sharedPreferences.edit()

    fun setValue(key: String, value: Boolean) {
        editSharedPreferences.putBoolean(key, value)
        editSharedPreferences.apply()
    }

    fun getValue(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }
}