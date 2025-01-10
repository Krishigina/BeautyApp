package com.example.beautyapp.model

import android.os.Parcel
import android.os.Parcelable

data class ProductCard constructor(
    var image: String = "",
    var product_name: String = "",
    var description: String = "",
    var isLiked: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
        parcel.writeString(product_name)
        parcel.writeString(description)
        parcel.writeByte(if (isLiked) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductCard> {
        override fun createFromParcel(parcel: Parcel): ProductCard {
            return ProductCard(parcel)
        }

        override fun newArray(size: Int): Array<ProductCard?> {
            return arrayOfNulls(size)
        }
    }
}