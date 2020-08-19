package cn.com.finaldemo.utils

import android.content.Context
import android.content.SharedPreferences
import cn.com.finaldemo.utils.GmConstans

/**
 * 缓存处理(不支持序列化，对象请存储为json串)
 *
 * apply：无返回值，异步存储，不会出现ANR
 * commit：有返回值，同步存储，数据量大且频繁时会出现ANR
 */

object GmPlayPreferenceUtil {
    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var map = mutableMapOf<String, Any>()

    fun saveStringData(key: String, value: String) {
        initSharePreference()
        map[key] = value
        editor!!.putString(key, value).apply()
    }

    fun saveIntData(key: String, value: Int) {
        initSharePreference()
        map[key] = value
        editor!!.putInt(key, value).apply()
    }

    fun saveBooleanData(key: String, value: Boolean) {
        initSharePreference()
        map[key] = value
        editor!!.putBoolean(key, value).apply()
    }

    fun getBooleanData(key: String): Boolean {
        return getBoolean(key, false)
    }

    fun getBooleanData(key: String, defaultValue: Boolean): Boolean {
        initSharePreference()

        return getBoolean(key, defaultValue)
    }

    fun getStringData(key: String): String {
        initSharePreference()

        return getString(key, "")
    }

    fun getStringData(key: String, defaultValue: String): String {
        initSharePreference()
        return getString(key, defaultValue)
    }

    fun getIntData(key: String): Int {
        initSharePreference()

        return getInt(key, 0)
    }

    fun getIntData(key: String, defaultValue: Int): Int {
        initSharePreference()

        return getInt(key, defaultValue)
    }

    fun deleteData(key: String) {
        initSharePreference()

        map.remove(key)
        editor!!.remove(key).apply()
    }

    fun clearAllData() {
        initSharePreference()

        map.clear()
        editor!!.clear().apply()
    }

    private fun getInt(key: String, defaultValue: Int): Int {
        return if (map.containsKey(key)) {
            map[key].toString().toInt()
        } else
            sharedPreferences!!.getInt(key, defaultValue)
    }

    private fun getString(key: String, defaultValue: String): String {
        return if (map.containsKey(key)) {
            map[key].toString()
        } else
            sharedPreferences!!.getString(key, defaultValue) ?: ""
    }

    private fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return if (map.containsKey(key)) {
            map[key].toString().toBoolean()
        } else
            sharedPreferences!!.getBoolean(key, defaultValue)
    }

    private fun initSharePreference() {
        if (sharedPreferences == null) {
            try {
                sharedPreferences = GmConstans.mInstance?.getSharedPreferences("gmmusic", Context.MODE_PRIVATE)
                map = sharedPreferences?.all as MutableMap<String, Any>
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (editor == null) {
            editor = sharedPreferences!!.edit()
        }
    }
}