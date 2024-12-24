package com.jsontextfield.unionstationdepartures

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.jsontextfield.unionstationdepartures.ui.theme.Barrie
import com.jsontextfield.unionstationdepartures.ui.theme.Kitchener
import com.jsontextfield.unionstationdepartures.ui.theme.LakeshoreEast
import com.jsontextfield.unionstationdepartures.ui.theme.LakeshoreWest
import com.jsontextfield.unionstationdepartures.ui.theme.Milton
import com.jsontextfield.unionstationdepartures.ui.theme.RichmondHill
import com.jsontextfield.unionstationdepartures.ui.theme.Stouffville
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import org.json.JSONException
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Downloader {
    private val client by lazy { OkHttpClient() }


    suspend fun download(apiKey: String): List<Train> {
        val url = "https://api.openmetrolinx.com/OpenDataAPI/api/V1/ServiceUpdate/UnionDepartures/All?key=$apiKey"
        Log.e("OkHttp", url)
        val request: Request = Request.Builder()
            .url(url)
            .build()
        try {
            val jsonString = client.newCall(request).execute().body?.string() ?: ""
            val trainsJson = JSONObject(jsonString)
                .getJSONObject("AllDepartures")
                .getJSONArray("Trip")
            return (0 until trainsJson.length()).map {
                val destination =
                    trainsJson.getJSONObject(it).getString("Service").split(" - ").last()
                val platform = trainsJson.getJSONObject(it).getString("Platform")
                val departureTime = trainsJson.getJSONObject(it).getString("Time")
                val inFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val outFormatter = DateTimeFormatter.ofPattern("HH:mm")
                val departureTimeString = LocalDateTime.parse(departureTime, inFormatter).format(outFormatter)
                val color : Color = when (destination) {
                    "Stouffville" -> Stouffville
                    "Richmond Hill" -> RichmondHill
                    "Milton" -> Milton
                    "Lakeshore West" -> LakeshoreWest
                    "Lakeshore East" -> LakeshoreEast
                    "Barrie" -> Barrie
                    "Kitchener" -> Kitchener
                    else -> Color(0xFF000000)
                }
                Train(
                    name = "",
                    destination = destination,
                    platform = platform,
                    departureTime = departureTimeString,
                    color = color,
                )
            }.sortedWith(compareBy({ it.name }, { it.tripOrder }))
        } catch (e: IOException) {
            Log.e("OkHttp", e.toString())
            return emptyList()
        } catch (e: JSONException) {
            Log.e("OkHttp", e.toString())
            return emptyList()
        }
    }
}