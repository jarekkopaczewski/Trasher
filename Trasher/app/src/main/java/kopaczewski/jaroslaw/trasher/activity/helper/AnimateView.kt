package kopaczewski.jaroslaw.trasher.activity.helper

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils

object AnimateView {
    fun singleAnimation(view: View, context: Context?, id: Int) =
        view.startAnimation(AnimationUtils.loadAnimation(context, id))
}