package kopaczewski.jaroslaw.trasher.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kopaczewski.jaroslaw.trasher.R
import kopaczewski.jaroslaw.trasher.activity.api.DataLoader.currentItem
import kopaczewski.jaroslaw.trasher.activity.helper.ImagePath
import kopaczewski.jaroslaw.trasher.databinding.ActivityDetailBinding
import kopaczewski.jaroslaw.trasher.databinding.FragmentMapBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailedImage: ImageView
    private lateinit var itemDetailName: TextView
    private lateinit var itemDetailCategory: TextView
    private lateinit var itemDetailLikes: TextView
    private lateinit var itemDetailsViews: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        detailedImage = binding.detailedImage
        itemDetailName = binding.itemDetailName
        itemDetailCategory = binding.itemDetailCategory
        itemDetailLikes = binding.itemDetailLikes
        itemDetailsViews = binding.itemDetailsViews

        if(currentItem!=null){
            itemDetailName.text = currentItem?.name
            itemDetailCategory.text = currentItem?.category
            itemDetailLikes.text = currentItem?.likes.toString()
            itemDetailsViews.text = currentItem?.views.toString()
            if (currentItem?.image != null)
                Picasso.get().load(ImagePath.getImagePath(currentItem?.image!!)).fit().into(detailedImage)
        }
    }
}