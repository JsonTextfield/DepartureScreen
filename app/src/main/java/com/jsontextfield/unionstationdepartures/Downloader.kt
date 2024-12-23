package com.jsontextfield.unionstationdepartures

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import org.json.JSONException
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object Downloader {
    private val client by lazy { OkHttpClient() }

    suspend fun download(apiKey: String): List<Train> {
        val url = "https://api.openmetrolinx.com/OpenDataAPI/api/V1/Stop/NextService/UN?key=$apiKey"
        Log.e("OkHttp", url)
        val request: Request = Request.Builder()
            .url(url)
            .build()
        try {
            val jsonString = client.newCall(request).execute().body?.string() ?: ""
            val trainsJson = JSONObject(jsonString)
                .getJSONObject("NextService")
                .getJSONArray("Lines")
            return (0 until trainsJson.length()).map {
                val destination =
                    trainsJson.getJSONObject(it).getString("DirectionName").split(" - ").last()
                val platform = trainsJson.getJSONObject(it).getString("ActualPlatform")
                    .ifBlank { trainsJson.getJSONObject(it).getString("ScheduledPlatform") }
                    .ifBlank { "-" }
                val departureTime = trainsJson.getJSONObject(it).getString("ComputedDepartureTime")
                    .ifBlank { trainsJson.getJSONObject(it).getString("ScheduledDepartureTime") }
                    .ifBlank { "-" }
                val inFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val outFormatter = DateTimeFormatter.ofPattern("HH:mm")
                val departureTimeString = LocalDateTime.parse(departureTime, inFormatter).format(outFormatter)
                val name = trainsJson.getJSONObject(it).getString("LineCode")
                val color : Int = when (name) {
                    "ST" -> 0xFF794500.toInt()
                    "RH" -> 0xFF0099c7.toInt()
                    "MI" -> 0xFFf57f25.toInt()
                    "LW" -> 0xFF98002e.toInt()
                    "LE" -> 0xFFff0d00.toInt()
                    "BR" -> 0xFF003767.toInt()
                    "GT", "KI" -> 0xFF00853e.toInt()
                    else -> 0xFF000000.toInt()
                }
                Train(
                    name = name,
                    destination = destination,
                    platform = platform,
                    departureTime = departureTimeString,
                    color = color
                )
            }.sortedWith(compareBy({ it.name }, { it.departureTime }))
        } catch (e: IOException) {
            Log.e("OkHttp", e.toString())
            return emptyList()
        } catch (e: JSONException) {
            Log.e("OkHttp", e.toString())
            return emptyList()
        }
    }
}