package kopaczewski.jaroslaw.trasher.activity.api

import com.google.gson.Gson
import kopaczewski.jaroslaw.trasher.activity.data.Item
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject


object DataLoader {
    private val GET_ITEMS = "http://maluch.mikr.us:40243/api/item/"
    private val AUTHORIZATION = "Authorization"
    private val TOKEN = "Token 12d2308f8b3bc3494fe9b9d0d900aeb94238f25d"

    fun getItems(): ArrayList<Item> {
        val client = OkHttpClient()
        val request =
            Request.Builder().url(GET_ITEMS).get().addHeader(AUTHORIZATION, TOKEN).build()
        val response = client.newCall(request).execute().body!!.string()
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
}