package com.sbnh.my.repo

import com.google.gson.reflect.TypeToken
import com.sbnh.comm.entity.my.MyCollectionEntity
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.room.DataStoreManger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyCollectionRepository {

    companion object {
        const val KEY_CACHE_MY_COLLECTION_LIST = "cache_my_collection_list"
    }

    suspend fun cacheMyCollectionList(list: List<MyCollectionEntity>?) {
        withContext(Dispatchers.IO) {
            DataStoreManger.get().putObject("${KEY_CACHE_MY_COLLECTION_LIST}_${UserInfoStore.get().getId()}", list?: "")
        }
    }

    suspend fun loadCachedMyCollectionList(): List<MyCollectionEntity> {
        val result: List<MyCollectionEntity> = withContext(Dispatchers.IO) {
            DataStoreManger.get().getArrayList(
                "${KEY_CACHE_MY_COLLECTION_LIST}_${UserInfoStore.get().getId()}",
                object : TypeToken<ArrayList<MyCollectionEntity>>() {}.type
            )
        }
        return result
    }

}