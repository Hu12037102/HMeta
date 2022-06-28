package com.sbnh.my.repo

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.my.CollectionNumDetailsEntity
import com.sbnh.comm.entity.my.GiveCollectionEntity
import com.sbnh.comm.entity.my.MyCollectionEntity
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.room.DataStoreManger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyCollectionRepository {

    companion object {
        const val KEY_CACHE_MY_COLLECTION_LIST = "cache_my_collection_list"
        const val KEY_CACHE_COLLECTION_NUM_DETAILS_LIST = "cache_collection_num_details_list"
        const val KEY_CACHE_GIVE_COLLECTION_LIST = "cache_give_collection_list"
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

    suspend fun cacheCollectionNumDetailsPagerEntity(id: String, data: BasePagerEntity<List<CollectionNumDetailsEntity>>?) {
        withContext(Dispatchers.IO) {
            val cache = data?.let {
                Gson().toJson(it)
            }
            DataStoreManger.get().putObject("${KEY_CACHE_COLLECTION_NUM_DETAILS_LIST}_${id}_${UserInfoStore.get().getId()}", cache?: "")
        }
    }

    suspend fun loadCachedCollectionNumDetailsPagerEntity(id: String): BasePagerEntity<List<CollectionNumDetailsEntity>>? {
        val result: BasePagerEntity<List<CollectionNumDetailsEntity>>? = withContext(Dispatchers.IO) {
            DataStoreManger.get().getString("${KEY_CACHE_COLLECTION_NUM_DETAILS_LIST}_${id}_${UserInfoStore.get().getId()}").let {
                Gson().fromJson(it, object : TypeToken<BasePagerEntity<List<CollectionNumDetailsEntity>>>() {}.type)
            }
        }
        return result
    }

    suspend fun cacheGiveCollectionPagerEntity(giveType: Int, data: BasePagerEntity<List<GiveCollectionEntity>>?) {
        withContext(Dispatchers.IO) {
            val cache = data?.let {
                Gson().toJson(it)
            }
            DataStoreManger.get().putObject("${KEY_CACHE_GIVE_COLLECTION_LIST}_${giveType}_${UserInfoStore.get().getId()}", cache?: "")
        }
    }

    suspend fun loadCachedGiveCollectionPagerEntity(giveType: Int): BasePagerEntity<List<GiveCollectionEntity>>? {
        val result: BasePagerEntity<List<GiveCollectionEntity>>? = withContext(Dispatchers.IO) {
            DataStoreManger.get().getString("${KEY_CACHE_GIVE_COLLECTION_LIST}_${giveType}_${UserInfoStore.get().getId()}").let {
                Gson().fromJson(it, object : TypeToken<BasePagerEntity<List<GiveCollectionEntity>>>() {}.type)
            }
        }
        return result
    }

    /**
     * 联动更新已缓存的我的收藏列表
     */
    suspend fun unionUpdateCachedMyCollection(collectionNumDetailsId: String, merchandiseId: String) {
        withContext(Dispatchers.IO) {
            val collectionNumDetailsPagerEntity = loadCachedCollectionNumDetailsPagerEntity(merchandiseId)
            collectionNumDetailsPagerEntity?.let {
                var cndList = BasePagerEntity.getData(it)
                if (CollectionCompat.notEmptyList(cndList)) {
                    // 删除这个收藏数量明细项
                    cndList = cndList!!.filterNot { cnd ->
                        cnd.id == collectionNumDetailsId
                    }
                    cacheCollectionNumDetailsPagerEntity(merchandiseId, it.copy(cndList))

                    // 更新我的收藏列表中对应项的数量
                    var myCollectionList: List<MyCollectionEntity> = loadCachedMyCollectionList()
                    if (CollectionCompat.notEmptyList(myCollectionList)) {
                        myCollectionList.map { myCollection ->
                            if (myCollection.merchandiseId == merchandiseId) {
                                myCollection.count = myCollection.count?.let { count ->
                                    count - 1
                                }?: 0
                            }
                        }
                        // 删除数量为0的项
                        myCollectionList = myCollectionList.filterNot { mc ->
                            mc.count == 0
                        }
                        cacheMyCollectionList(myCollectionList)
                    }
                }
            }
        }
    }

}