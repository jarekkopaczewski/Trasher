package kopaczewski.jaroslaw.trasher.activity.data

data class Item(
    val id: Long,
    val name: String,
    val latitude: Float,
    val longitude: Float,
    val user: Int,
    val status: Boolean,
    val category: String,
    val image: String,
    val likes: Int,
    val views: Int,
    val creation_date: String, //TODO
    val last_updated: String //TODO
)