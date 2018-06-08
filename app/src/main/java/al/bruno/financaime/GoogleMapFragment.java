package al.bruno.financaime;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import al.bruno.financaime.model.ExpenseLocation;

public class GoogleMapFragment extends Fragment implements OnMapReadyCallback {

    private  MapView mapView;
    private List<ExpenseLocation> expenseLocations;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expenseLocations = new ArrayList<>();
        expenseLocations.add(new ExpenseLocation("TEG - Tirana East Gate", 41.282985, 19.857024000000024));
        expenseLocations.add(new ExpenseLocation("Ring Center", 41.332466, 19.803945));
        expenseLocations.add(new ExpenseLocation("Toptani Shopping Center", 41.3270802, 19.82254230000001));
        expenseLocations.add(new ExpenseLocation("COIN Tirana", 41.320151, 19.823104000000058));
        expenseLocations.add(new ExpenseLocation("City Park Albania", 41.3684889, 19.68699449999997));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        for(ExpenseLocation expenseLocation : expenseLocations) {
            LatLng latLng = new LatLng(expenseLocation.getLatitude(), expenseLocation.getLongitude());
            CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15f).bearing(20f).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            googleMap.addMarker(new MarkerOptions().title(expenseLocation.getDescription()).position(latLng));
            googleMap.addMarker(new MarkerOptions().position(latLng));
        }
    }


}
