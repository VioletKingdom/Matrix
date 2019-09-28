public class MainFragment extends Fragment implements OnMapReadyCallback {
private MapView mapView;
private View view;
private GoogleMap googleMap;
private LocationTracker locationTracker;
private FloatingActionButton fabReport;
private ReportDialog dialog;
private FloatingActionButton fabFocus;




public static MainFragment newInstance() {
  
   Bundle args = new Bundle();
  
   MainFragment fragment = new MainFragment();
   fragment.setArguments(args);
   return fragment;
}

public MainFragment() {
   // Required empty public constructor
}


   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_main, container,
               false);
       return view;
   }
@Override
public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
   super.onViewCreated(view, savedInstanceState);
   mapView = (MapView) this.view.findViewById(R.id.event_map_view);

   fabReport = (FloatingActionButton)view.findViewById(R.id.fab);
   fabReport.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           //show dialog
           showDialog(null, null);
       }
   });

   fabFocus = (FloatingActionButton) view.findViewById(R.id.fab_focus);

   fabFocus.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           mapView.getMapAsync(MainFragment.this);
       }
   });


   if (mapView != null) {
       mapView.onCreate(null);
       mapView.onResume();// needed to get the map to display immediately
       mapView.getMapAsync(this);
   }
}



   if (mapView != null) {
       mapView.onCreate(null);
       mapView.onResume();// needed to get the map to display immediately
       mapView.getMapAsync(this);
   }
}





   @Override
   public void onResume() {
       super.onResume();
       mapView.onResume();
   }

   @Override
   public void onPause() {
       super.onPause();
       mapView.onPause();
   }

   @Override
   public void onDestroy() {
       super.onDestroy();
       mapView.onDestroy();
   }

@Override
public void onLowMemory() {
   super.onLowMemory();
   mapView.onLowMemory();
}


private void showDialog(String label, String prefillText) {
   dialog = new ReportDialog(getContext());
   dialog.show();
}


@Override
public void onMapReady(GoogleMap googleMap) {
   MapsInitializer.initialize(getContext());
   
   this.googleMap = googleMap;
   this.googleMap.setMapStyle(
           MapStyleOptions.loadRawResourceStyle(
                   getActivity(), R.raw.style_json));
   double latitude = 17.385044;
   double longitude = 78.486671;

   // Create marker on google map
   MarkerOptions marker = new MarkerOptions().position(
           new LatLng(latitude, longitude)).title("This is your focus");

   // Change marker Icon on google map
   marker.icon(BitmapDescriptorFactory
           .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

   // Add marker to google map
   googleMap.addMarker(marker);


   // Set up camera configuration, set camera to latitude = 17.385044, longitude = 78.486671, and set Zoom to 12
   CameraPosition cameraPosition = new CameraPosition.Builder()
           .target(new LatLng(latitude, longitude)).zoom(12).build();

   // Animate the zoom process
   googleMap.animateCamera(CameraUpdateFactory
           .newCameraPosition(cameraPosition));
}
@Override
public void onMapReady(GoogleMap googleMap) {
   MapsInitializer.initialize(getContext());

   this.googleMap = googleMap;
   this.googleMap.setMapStyle(
           MapStyleOptions.loadRawResourceStyle(
                   getActivity(), R.raw.style_json));

   locationTracker = new LocationTracker(getActivity());
   locationTracker.getLocation();

   LatLng latLng = new LatLng(locationTracker.getLatitude(), locationTracker.getLongitude());

   CameraPosition cameraPosition = new CameraPosition.Builder()
           .target(latLng)      // Sets the center of the map to Mountain View
           .zoom(16)// Sets the zoom
           .bearing(90)           // Sets the orientation of the camera to east
           .tilt(30)                   // Sets the tilt of the camera to 30 degrees
           .build();                   // Creates a CameraPosition from the builder

   googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

   MarkerOptions marker = new MarkerOptions().position(latLng).
           title("You");

   // Changing marker icon
   marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.boy));

   // adding marker
   googleMap.addMarker(marker);


   double latitude = 17.385044;
   double longitude = 78.486671;

   // Create marker on google map
   MarkerOptions marker = new MarkerOptions().position(
           new LatLng(latitude, longitude)).title("This is your focus");

   // Change marker Icon on google map
   marker.icon(BitmapDescriptorFactory
           .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

   // Add marker to google map
   googleMap.addMarker(marker);


   // Set up camera configuration, set camera to latitude = 17.385044, longitude = 78.486671, and set Zoom to 12
   CameraPosition cameraPosition = new CameraPosition.Builder()
           .target(new LatLng(latitude, longitude)).zoom(12).build();

   // Animate the zoom process
   googleMap.animateCamera(CameraUpdateFactory
           .newCameraPosition(cameraPosition));
}

