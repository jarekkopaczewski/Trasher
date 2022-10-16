package kopaczewski.jaroslaw.trasher.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kopaczewski.jaroslaw.trasher.R
import kopaczewski.jaroslaw.trasher.activity.DetailActivity
import kopaczewski.jaroslaw.trasher.activity.api.DataLoader.currentItem
import kopaczewski.jaroslaw.trasher.activity.data.Item
import kopaczewski.jaroslaw.trasher.activity.helper.AnimateView.singleAnimation
import kopaczewski.jaroslaw.trasher.activity.helper.ImagePath

class ListItemViewHolder(
    private val view: View,
    private val context: Context,
    private val titleItem: TextView = view.findViewById(R.id.titleItem),
    private val categoryItem: TextView = view.findViewById(R.id.categoryItem),
    private val image: ImageView = view.findViewById(R.id.itemImageList)
) : RecyclerView.ViewHolder(view) {

    fun setEverything(item: Item) {
        titleItem.text = item.name
        categoryItem.text = item.category
        if (item.image != null)
            Picasso.get().load(ImagePath.getImagePath(item.image)).fit().into(image)

        view.setOnClickListener {
            singleAnimation(view, context, R.anim.zoomout)
            currentItem = item
            context.startActivity(Intent(context, DetailActivity::class.java))
        }
    }
}