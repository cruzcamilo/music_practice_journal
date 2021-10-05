package com.example.musicpracticejournal.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "musical_fragment_table")
data class MusicFragment(
    val type:String,
    val author:String,
    val name:String,
    val practiceTime:String,
    val practiceDate:String,
    val targetTempo: Int? = null,
    val currentTempo: Int?= null,
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Long::class.java.classLoader) as? Long
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(author)
        parcel.writeString(name)
        parcel.writeString(practiceTime)
        parcel.writeString(practiceDate)
        parcel.writeValue(targetTempo)
        parcel.writeValue(currentTempo)
        parcel.writeValue(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MusicFragment> {
        override fun createFromParcel(parcel: Parcel): MusicFragment {
            return MusicFragment(parcel)
        }

        override fun newArray(size: Int): Array<MusicFragment?> {
            return arrayOfNulls(size)
        }
    }
}