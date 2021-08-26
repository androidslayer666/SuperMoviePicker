package com.example.supermoviepicker.utils

import android.content.Context
import androidx.preference.PreferenceManager

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MySharedPreferencesWrapper @Inject constructor(@ApplicationContext context: Context) {

    val prefs = PreferenceManager.getDefaultSharedPreferences(context)

//    fun getIfNeedRandomMovie(): Boolean {return prefs.getString("GET_RANDOM_MOVIE", "true")!!.toBoolean()}
//    fun setIfNeedRandomMovie(boolean: Boolean) {prefs.edit().putString("GET_RANDOM_MOVIE", boolean.toString()).apply() }

}