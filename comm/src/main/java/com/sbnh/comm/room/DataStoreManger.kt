package com.sbnh.comm.room

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.compat.DataCompat
import kotlinx.coroutines.flow.first

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/13 20:06
 * 更新时间: 2022/6/13 20:06
 * 描述:
 */
class DataStoreManger private constructor() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "datastore")

    companion object {
        private val mInstance: DataStoreManger by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { DataStoreManger() }
        fun get(): DataStoreManger {
            return mInstance
        }
    }

    suspend fun putObject(key: String, value: Any = "") {
        DataCompat.getContext().dataStore.edit {
            try {
                when (value) {
                    is Boolean -> {
                        val preferences = booleanPreferencesKey(key)
                        it[preferences] = value
                    }
                    is Int -> {
                        val preferences = intPreferencesKey(key)
                        it[preferences] = value
                    }
                    is Float -> {
                        val preferences = floatPreferencesKey(key)
                        it[preferences] = value
                    }
                    is Set<*> -> {
                        val set = HashSet<String>()
                        value.forEach { data ->
                            if (data is String) {
                                set.add(data)
                            }
                        }
                        val preferences = stringSetPreferencesKey(key)
                        it[preferences] = set
                    }
                    is String -> {
                        val preferences = stringPreferencesKey(key)
                        it[preferences] = value
                    }
                    is ArrayList<*> -> {
                        val preferences = stringPreferencesKey(key)
                        val gson = Gson()
                        val json = gson.toJson(value)
                        it[preferences] = json
                    }
                    is Double -> {
                        val preferences = doublePreferencesKey(key)
                        it[preferences] = value
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }

    suspend fun getString(key: String, defaultValue: String = ""): String {
        return DataCompat.getContext().dataStore.data.first()[stringPreferencesKey(key)]
            ?: defaultValue
    }

    suspend fun getInt(key: String, defaultValue: Int = -1): Int {
        return DataCompat.getContext().dataStore.data.first()[intPreferencesKey(key)]
            ?: defaultValue
    }

    suspend fun getFloat(key: String, defaultValue: Float = -1.0F): Float {
        return DataCompat.getContext().dataStore.data.first()[floatPreferencesKey(key)]
            ?: defaultValue
    }

    suspend fun getDouble(key: String, defaultValue: Double = (-1).toDouble()): Double {
        return DataCompat.getContext().dataStore.data.first()[doublePreferencesKey(key)]
            ?: defaultValue
    }

    suspend fun getStringSet(
        key: String,
        defaultValue: Set<String> = HashSet()
    ): Set<String> {
        return DataCompat.getContext().dataStore.data.first()[stringSetPreferencesKey(key)]
            ?: defaultValue
    }

    suspend fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return DataCompat.getContext().dataStore.data.first()[booleanPreferencesKey(key)]
            ?: defaultValue
    }

    suspend fun <T> getArrayList(key: String): List<T> {
        val list = ArrayList<T>()
        try {
            val preferences = stringPreferencesKey(key)
            val json = DataCompat.getContext().dataStore.data.first()[preferences] ?: ""
            val type = object : TypeToken<ArrayList<T>>() {}.type
            val gson = Gson()
            val data = gson.fromJson<List<T>>(json, type)
            if (CollectionCompat.notEmptyList(data)) {
                list.addAll(data)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    suspend fun clear() {
        DataCompat.getContext().dataStore.edit {
            it.clear()
        }
    }

}