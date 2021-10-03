package com.gits.mywishlist.auth

import android.content.Context
import android.provider.SyncStateContract
import androidx.core.content.edit

class SharePreference(context: Context) {
    private val sharePref = context.getSharedPreferences("My Wishlist", Context.MODE_PRIVATE)

    companion object {
        @Volatile
        private var instance: SharePreference? = null

        fun getInstance(context: Context): SharePreference =
            instance ?: synchronized(this) {
                instance ?: SharePreference(context)
            }
    }

    fun setApiKey(apiKey : String){
        sharePref.edit{
            putString(Constants.SHAREPREF_API_KEY,apiKey)
        }
    }

    fun getApiKey()=sharePref.getString(Constants.SHAREPREF_API_KEY,"")

    fun setIntro(boolean: Boolean){
        sharePref.edit{
            putBoolean(SyncStateContract.Constants.SHAREPREF_INTRO_KEY,boolean)
        }
    }

    fun getIntro()=sharePref.getBoolean(Constants.SHAREPREF_INTRO_KEY,false)
}