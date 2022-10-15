package kopaczewski.jaroslaw.trasher.activity.ui.map

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kopaczewski.jaroslaw.trasher.R
import kopaczewski.jaroslaw.trasher.activity.api.DataLoader.getItems
import kopaczewski.jaroslaw.trasher.activity.data.Item
import kopaczewski.jaroslaw.trasher.databinding.FragmentMapBinding
import kotlin.concurrent.thread


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapBinding
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var itemList: ArrayList<Item>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(layoutInflater)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        supportMapFragment.getMapAsync(this)
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
            val marker = mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(item.latitude.toDouble(), item.longitude.toDouble()))
                    .title(item.name)
                    .snippet(item.category)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.trash))
            )
            if (marker != null) {
                marker.tag = item.id
            }
        }

        mMap.setOnMarkerClickListener { marker ->
            println("Tag")
            println(marker.tag)
            false
        }
    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val user = LatLng(location.latitude, location.longitude)
                    val zoomLevel = 13f
                    mMap.addMarker(
                        MarkerOptions()
                            .position(user)
                            .title("User")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.me))
                    )
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user, zoomLevel))
                    loadItems()
                }
            }
    }
}