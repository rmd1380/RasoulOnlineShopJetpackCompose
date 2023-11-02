package com.technolearn.rasoulonlineshop.db.converters

import androidx.room.TypeConverter


class Converters {
    @TypeConverter
    fun fromArrayList(list: ArrayList<Int>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toArrayList(data: String): ArrayList<Int> {
        return if (data.isEmpty()) {
            arrayListOf()
        } else {
            ArrayList(data.split(",").map { it.toInt() })
        }
    }

    @TypeConverter
    fun fromStringArrayList(list: ArrayList<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toStringArrayList(data: String): ArrayList<String> {
        return if (data.isEmpty()) {
            arrayListOf()
        } else {
            ArrayList(data.split(",").map { it })
        }
    }
}
