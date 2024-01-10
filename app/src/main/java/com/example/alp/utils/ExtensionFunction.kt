package com.example.alp.utils

import com.example.alp.core.source.remote.response.BaseApiResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

inline fun <reified T> Gson.fromJson(json:String): T =
    this.fromJson(json,object : TypeToken<T>(){}.type)


val baseApiResponseType: Type = object : TypeToken<BaseApiResponse<Any, Any>>() {}.type
