package kopaczewski.jaroslaw.trasher.activity.data

data class ItemSend(
    val name: String,
    val latitude: Float,
    val longitude: Float,
    val user: Int,
    val status: Boolean,
    val category: String,
    val likes: Int,
    val views: Int
)