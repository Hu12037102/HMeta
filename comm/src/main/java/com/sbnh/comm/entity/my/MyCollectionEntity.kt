package com.sbnh.comm.entity.my

import android.os.Parcel
import android.os.Parcelable

data class MyCollectionEntity(
    var id: String? = "",
    var count: Int? = 0,
    var header: String? = "",
    var merchandiseId: String? = "",
    var merchandiseName: String? = "",
    var nickname: String? = "",
    var resourceUrl: String? = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeValue(count)
        parcel.writeString(header)
        parcel.writeString(merchandiseId)
        parcel.writeString(merchandiseName)
        parcel.writeString(nickname)
        parcel.writeString(resourceUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyCollectionEntity> {
        override fun createFromParcel(parcel: Parcel): MyCollectionEntity {
            return MyCollectionEntity(parcel)
        }

        override fun newArray(size: Int): Array<MyCollectionEntity?> {
            return arrayOfNulls(size)
        }
    }
}
