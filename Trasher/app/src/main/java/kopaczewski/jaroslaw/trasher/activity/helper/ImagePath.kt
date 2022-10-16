package kopaczewski.jaroslaw.trasher.activity.helper

object ImagePath {
    fun getImagePath(path: String): String{
        return "http://maluch.mikr.us:40244/" + path
    }
}