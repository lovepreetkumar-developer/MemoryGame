package com.ehaque.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.ehaque.util.Constants

class PreferenceProvider(context: Context) {

    private val appContext = context.applicationContext

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun clear(): Boolean {
        return preferences.edit().clear().commit()
    }

    fun setRateCount(count: Int) {
        preferences.edit().putInt(
            Constants.RATE_COUNT,
            count
        ).apply()
    }

    fun getRateCount(): Int {
        return preferences.getInt(Constants.RATE_COUNT, 0)
    }

    fun setWonCount(count: Int) {
        preferences.edit().putInt(
            Constants.WON_COUNT,
            count
        ).apply()
    }

    fun getWonCount(): Int {
        return preferences.getInt(Constants.WON_COUNT, 0)
    }

    fun setLoseCount(count: Int) {
        preferences.edit().putInt(
            Constants.LOSE_COUNT,
            count
        ).apply()
    }

    fun getLoseCount(): Int {
        return preferences.getInt(Constants.LOSE_COUNT, 0)
    }

    fun setAttemptsCount(count: Int) {
        preferences.edit().putInt(
            Constants.ATTEMPTS_COUNT,
            count
        ).apply()
    }

    fun getAttemptsCount(): Int {
        return preferences.getInt(Constants.ATTEMPTS_COUNT, 0)
    }


    fun setDeviceId(language: String?): Boolean {
        return preferences.edit().putString(
            Constants.DEVICE_ID,
            language
        ).commit()
    }

    fun getDeviceId(): String? {
        return preferences.getString(Constants.DEVICE_ID, null)
    }


    fun setOnGoingOrderId(id: Int) {
        preferences.edit().putInt(
            Constants.ORDER_ID,
            id
        ).apply()
    }

    fun getOnGoingOrderId(): Int {
        return preferences.getInt(Constants.ORDER_ID, 0)
    }

    fun setUserLoginType(type: String?) {
        preferences.edit().putString(
            Constants.USER_LOGIN_TYPE,
            type
        ).apply()
    }

    fun getUserLoginType(): String? {
        return preferences.getString(Constants.USER_LOGIN_TYPE, "1")
    }

    /*fun setUser(userModel: UserModel): Boolean {
        return preferences.edit().putString(
            Constants.USER, Gson().toJson(userModel)
        ).commit()
    }

    fun getUser(): UserModel? {
        return try {
            val s: String = preferences.getString(Constants.USER, null)
                ?: return null
            Gson().fromJson(s, UserModel::class.java)
        } catch (e: Exception) {
            null
        }
    }*/

    /*fun setStoreList(thoughtBeanMain: ArrayList<StoreListDataModel>) {
        preferences.edit().putString(
                Constants.THOUGHTS_LIST,
                Gson().toJson(thoughtBeanMain)
        ).apply()
    }

    fun getThoughtsList(): ArrayList<StoreListDataModel?>? {
        return try {
            val s: String = preferences.getString(Constants.THOUGHTS_LIST, null) ?: return null
            val type = object : TypeToken<List<StoreListDataModel?>?>() {}.type
            Gson().fromJson<ArrayList<StoreListDataModel?>>(s, type)
        } catch (e: Exception) {
            null
        }
    }*/

    fun setLanguage(language: String?): Boolean {
        return preferences.edit().putString(
            Constants.LANGUAGE,
            language
        ).commit()
    }

    fun getLanguage(): String? {
        return preferences.getString(Constants.LANGUAGE, "en")
    }

    fun setSoundEnabled(value:Boolean) {
        return preferences.edit().putBoolean(
            Constants.SOUND_ENABLED,
            value
        ).apply()
    }

    fun getSoundEnabled(): Boolean {
        return preferences.getBoolean(Constants.SOUND_ENABLED, false)
    }
}