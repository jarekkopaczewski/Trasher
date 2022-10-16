package kopaczewski.jaroslaw.trasher.activity.ui.map

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kopaczewski.jaroslaw.trasher.R
import kopaczewski.jaroslaw.trasher.activity.AddItemActivity
import kopaczewski.jaroslaw.trasher.activity.DetailActivity
import kopaczewski.jaroslaw.trasher.activity.api.DataLoader.getItems
import kopaczewski.jaroslaw.trasher.activity.data.Item
import kopaczewski.jaroslaw.trasher.activity.helper.AnimateView.singleAnimation
import kopaczewski.jaroslaw.trasher.databinding.DetailedViewBinding
import kopaczewski.jaroslaw.trasher.databinding.FragmentMapBinding
import kotlinx.coroutines.coroutineScope
import kotlin.concurrent.thread
import kotlin.random.Random


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapBinding
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var itemList: ArrayList<Item>
    private lateinit var specificationView: ConstraintLayout
    private lateinit var exitFab: FloatingActionButton
    private lateinit var detailedViewBinding: DetailedViewBinding
    private lateinit var detailButton: Button
    private lateinit var myLocation: Location

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        specificationView = binding.includeSpecification.specificationView
        detailedViewBinding = binding.includeSpecification
        exitFab = binding.includeSpecification.exitFab
        detailButton = binding.includeSpecification.detailButton
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        supportMapFragment.getMapAsync(this)

        exitFab.setOnClickListener {
            if(specificationView.visibility == VISIBLE){
                specificationView.visibility = INVISIBLE
            }
        }

        detailButton.setOnClickListener {
            if(specificationView.visibility == VISIBLE){
                startActivity(Intent(requireActivity(), DetailActivity::class.java))
            }
        }

        return binding.root
    }

    private fun loadItems() {
        var itemz = arrayListOf<Item>()
        thread {
            itemz = getItems()
        }.join()
        itemList = itemz
        itemz.forEach { item ->
            println(itemz)
            val imageId = when(item.category){
                "AGD" -> R.drawable.agd
                "Meble" -> R.drawable.furniture
                "Ubrania" -> R.drawable.clothes
                "Inne" -> R.drawable.trash
                else -> R.drawable.trash
            }
            val marker = mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(item.latitude.toDouble(), item.longitude.toDouble()))
                    .title(item.name)
                    .snippet(item.category)
                    .icon(BitmapDescriptorFactory.fromResource(imageId))
            )
            if (marker != null) {
                marker.tag = item.id
            }
        }

        mMap.setOnMarkerClickListener { marker ->
            if( marker.tag != "user"){
                if(specificationView.visibility == VISIBLE){
                    specificationView.visibility = INVISIBLE
                }else{
                    specificationView.visibility = VISIBLE
                    val item = itemList.filter {
                        it.id == marker.tag
                    }.first()

                    detailedViewBinding.itemName.text = item.name
                    detailedViewBinding.categoryText.text = item.category
                    detailedViewBinding.viewsText.text = item.views.toString()
                    detailedViewBinding.likeNumberText.text = item.likes.toString()
                    singleAnimation(specificationView, context, R.anim.zoomin)
                }
            }
            else{
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 16f))
            }
            false
        }
    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    myLocation = location
                    val user = LatLng(location.latitude, location.longitude)
                    val zoomLevel = 13f
                    val marker = mMap.addMarker(
                        MarkerOptions()
                            .position(user)
                            .title("User")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.me))
                    )
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user, zoomLevel))
                    if (marker != null) {
                        marker.tag = "user"
                    }
                    loadItems()
                }
            }

        mMap.setOnMapLongClickListener { latLng ->
            if(myLocation != null){
                val intent = Intent(requireActivity(), AddItemActivity::class.java)
                intent.putExtra("latitude", latLng.latitude)
                intent.putExtra("longitude", latLng.longitude)
                startActivity(intent)
            }
        }
    }
}