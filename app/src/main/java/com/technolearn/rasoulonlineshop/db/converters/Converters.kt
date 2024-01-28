package com.technolearn.rasoulonlineshop.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.technolearn.rasoulonlineshop.vo.res.CategoryRes


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

    @TypeConverter
    fun fromCategoryRes(categoryRes: CategoryRes?): String? {
        // Convert CategoryRes to String (you can use a Gson converter or any other method)
        return Gson().toJson(categoryRes)
    }

    @TypeConverter
    fun toCategoryRes(categoryResString: String?): CategoryRes? {
        // Convert String to CategoryRes (you can use a Gson converter or any other method)
        return Gson().fromJson(categoryResString, CategoryRes::class.java)
    }
}
