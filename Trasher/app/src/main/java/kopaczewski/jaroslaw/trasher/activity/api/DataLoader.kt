package kopaczewski.jaroslaw.trasher.activity.api

import com.google.gson.Gson
import kopaczewski.jaroslaw.trasher.activity.data.Item
import kopaczewski.jaroslaw.trasher.activity.data.ItemSend
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject


object DataLoader {
    private val GET_ITEMS = "http://maluch.mikr.us:40243/api/item/"
    private val AUTHORIZATION = "Authorization"
    private val TOKEN = "Token 12d2308f8b3bc3494fe9b9d0d900aeb94238f25d"
    private val JSON: MediaType? = "application/json; charset=utf-8".toMediaTypeOrNull()

    fun getItems(): ArrayList<Item> {
        val client = OkHttpClient()
        val request =
            Request.Builder().url(GET_ITEMS).get().addHeader(AUTHORIZATION, TOKEN).build()
        val response = client.newCall(request).execute().body!!.string()
        println(response)
        val jsonArray = JSONArray(response)
        val stringArray = ArrayList<JSONObject>()
        val items = arrayListOf<Item>()

        for (i in 0 until jsonArray.length())
            stringArray.add(jsonArray.get(i) as JSONObject)

        for ((i, _) in stringArray.withIndex()) {
            items.add(Gson().fromJson(stringArray[i].toString(), Item::class.java))
        }

        return items
    }

    fun addItem(){
        val item = ItemSend("test", 43.4f, 43.4f, 11, false, "Inne", 21, 123)
        val client = OkHttpClient()
        println(Gson().toJson(item))
        val request =
            Request.Builder().url(GET_ITEMS).post(RequestBody.create(JSON, Gson().toJson(item))).addHeader(AUTHORIZATION, TOKEN).build()
        val response = client.newCall(request).execute().body!!.string()
        println(response)
    }
}