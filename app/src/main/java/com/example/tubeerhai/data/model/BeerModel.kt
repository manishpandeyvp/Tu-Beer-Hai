package com.example.tubeerhai.data.model

import android.os.Parcel
import android.os.Parcelable

data class BeerModel (
    val id: Int,
    val imageUrl: String,
    val name: String,
    val tagline: String,
    val description: String,
    val brewersTips: String,
    val firstBrewed: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(imageUrl)
        parcel.writeString(name)
        parcel.writeString(tagline)
        parcel.writeString(description)
        parcel.writeString(brewersTips)
        parcel.writeString(firstBrewed)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BeerModel> {
        override fun createFromParcel(parcel: Parcel): BeerModel {
            return BeerModel(parcel)
        }

        override fun newArray(size: Int): Array<BeerModel?> {
            return arrayOfNulls(size)
        }
    }
}