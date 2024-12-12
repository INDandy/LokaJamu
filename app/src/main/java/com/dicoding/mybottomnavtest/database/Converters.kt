package com.dicoding.mybottomnavtest.database

import androidx.room.TypeConverter
import com.dicoding.mybottomnavtest.NewsResponse.ContentItem
import com.dicoding.mybottomnavtest.Response.IngredientsItem
import com.dicoding.mybottomnavtest.Response.StepsItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    // Convert List<String> to String
    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromContentItemList(value: List<ContentItem>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toContentItemList(value: String?): List<ContentItem>? {
        val listType = object : TypeToken<List<ContentItem>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromIngredientsList(value: List<IngredientsItem?>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toIngredientsList(value: String?): List<IngredientsItem?>? {
        val type = object : TypeToken<List<IngredientsItem?>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromStepsList(value: List<StepsItem?>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStepsList(value: String?): List<StepsItem?>? {
        val type = object : TypeToken<List<StepsItem?>>() {}.type
        return gson.fromJson(value, type)
    }
    @TypeConverter
    fun fromTipsList(value: List<String?>?): String? {
        return Gson().toJson(value)
    }
    @TypeConverter
    fun toTipsList(value: String?): List<String?>? {
        val type = object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson(value, type)
    }

}
