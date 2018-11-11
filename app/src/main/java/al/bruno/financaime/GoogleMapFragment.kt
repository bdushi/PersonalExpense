package al.bruno.financaime

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import java.util.ArrayList

import al.bruno.financaime.model.ExpenseLocation

class GoogleMapFragment : Fragment(), OnMapReadyCallback {

    private var mapView: MapView? = null
    private var expenseLocations: MutableList<ExpenseLocation>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        expenseLocations = ArrayList()
        expenseLocations!!.add(ExpenseLocation("TEG - Tirana East Gate", 41.282985, 19.857024000000024))
        expenseLocations!!.add(ExpenseLocation("Ring Center", 41.332466, 19.803945))
        expenseLocations!!.add(ExpenseLocation("Toptani Shopping Center", 41.3270802, 19.82254230000001))
        expenseLocations!!.add(ExpenseLocation("COIN Tirana", 41.320151, 19.823104000000058))
        expenseLocations!!.add(ExpenseLocation("City Park Albania", 41.3684889, 19.68699449999997))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.findViewById(R.id.map)
        mapView!!.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView!!.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        mapView!!.onResume()
        mapView!!.getMapAsync(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView!!.onLowMemory()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.uiSettings.isScrollGesturesEnabled = true
        googleMap.uiSettings.isZoomControlsEnabled = true
        for (expenseLocation in expenseLocations!!) {
            val latLng = LatLng(expenseLocation.latitude, expenseLocation.longitude)
            val cameraPosition = CameraPosition.Builder().target(latLng).zoom(15f).bearing(20f).build()
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            googleMap.addMarker(MarkerOptions().title(expenseLocation.description).position(latLng))
            googleMap.addMarker(MarkerOptions().position(latLng))
        }
    }


}
