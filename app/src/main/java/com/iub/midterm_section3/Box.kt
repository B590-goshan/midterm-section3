package com.iub.midterm_section3

import android.graphics.PointF
import android.os.Parcel
import android.os.Parcelable

data class Box(val start: PointF) : Parcelable {
    var end: PointF = start

    val left: Float
        get() = Math.min(start.x, end.x)

    val right: Float
        get() = Math.max(start.x, end.x)

    val top: Float
        get() = Math.min(start.y, end.y)

    val bottom: Float
        get() = Math.max(start.y, end.y)

    constructor(parcel: Parcel) : this(
        PointF(parcel.readFloat(), parcel.readFloat())
    ) {
        end = PointF(parcel.readFloat(), parcel.readFloat())
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(start.x)
        parcel.writeFloat(start.y)
        parcel.writeFloat(end.x)
        parcel.writeFloat(end.y)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Box> {
        override fun createFromParcel(parcel: Parcel): Box {
            return Box(parcel)
        }

        override fun newArray(size: Int): Array<Box?> {
            return arrayOfNulls(size)
        }
    }
}