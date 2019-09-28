public class MainFragment extends Fragment implements OnMapReadyCallback {
private MapView mapView;
private View view;
private GoogleMap googleMap;
private LocationTracker locationTracker;
private FloatingActionButton fabReport;
private ReportDialog dialog;
private FloatingActionButton fabFocus;
private DatabaseReference database;
  
   private static final int REQUEST_EXTERNAL_STORAGE = 1;
   private static String[] PERMISSIONS_STORAGE = {
           Manifest.permission.READ_EXTERNAL_STORAGE,
           Manifest.permission.WRITE_EXTERNAL_STORAGE
   };


...
public static void verifyStoragePermissions(Activity activity) {
   // Check if we have write permission
   int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

   if (permission != PackageManager.PERMISSION_GRANTED) {
       // We don't have permission so prompt the user
       ActivityCompat.requestPermissions(
               activity,
               PERMISSIONS_STORAGE,
               REQUEST_EXTERNAL_STORAGE
       );
   }
}

//get center coordinate
private void loadEventInVisibleMap() {
   database.child("events").addListenerForSingleValueEvent(new ValueEventListener() {
       @Override
       public void onDataChange(DataSnapshot dataSnapshot) {
           for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
               TrafficEvent event = noteDataSnapshot.getValue(TrafficEvent.class);
               double eventLatitude = event.getEvent_latitude();
               double eventLongitude = event.getEvent_longitude();

               LatLng center = googleMap.getCameraPosition().target;
               double centerLatitude = center.latitude;
               double centerLongitude = center.longitude;

               int distance = Utils.distanceBetweenTwoLocations(centerLatitude, centerLongitude,
                       eventLatitude, eventLongitude);

               if (distance < 20) {
                   LatLng latLng = new LatLng(eventLatitude, eventLongitude);
                   MarkerOptions marker = new MarkerOptions().position(latLng);

                   // Changing marker icon
                   String type = event.getEvent_type();
                   Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),
                           Config.trafficMap.get(type));

                   Bitmap resizeBitmap = Utils.getResizedBitmap(icon, 130, 130);

                   marker.icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap));

                   // adding marker
                   Marker mker = googleMap.addMarker(marker);
                   mker.setTag(event);
               }
           }
       }

       @Override
       public void onCancelled(DatabaseError databaseError) {
           //TODO: do something
       }
   });
}






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
       database = FirebaseDatabase.getInstance().getReference();

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

private String uploadEvent(String user_id, String editString, String event_type) {
   TrafficEvent event = new TrafficEvent();

   event.setEvent_type(event_type);
   event.setEvent_description(editString);
   event.setEvent_reporter_id(user_id);
   event.setEvent_timestamp(System.currentTimeMillis());
   event.setEvent_latitude(locationTracker.getLatitude());
   event.setEvent_longitude(locationTracker.getLongitude());
   event.setEvent_like_number(0);
   event.setEvent_comment_number(0);

   String key = database.child("events").push().getKey();
   event.setId(key);
   database.child("events").child(key).setValue(event, new DatabaseReference.CompletionListener() {
       @Override
       public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
           if (databaseError != null) {
               Toast toast = Toast.makeText(getContext(),
                       "The event is failed, please check your network status.", Toast.LENGTH_SHORT);
               toast.show();
               dialog.dismiss();
           } else {
               Toast toast = Toast.makeText(getContext(), "The event is reported", Toast.LENGTH_SHORT);
               toast.show();
               //TODO: update map fragment
           }
       }
   });

   return key;
}

private void setupBottomBehavior() {
   //set up bottom up slide
   final View nestedScrollView = (View) view.findViewById(R.id.nestedScrollView);
   bottomSheetBehavior = BottomSheetBehavior.from(nestedScrollView);

   //set hidden initially
   bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

   //set expansion speed
   bottomSheetBehavior.setPeekHeight(1000);

   mEventImageLike = (ImageView) view.findViewById(R.id.event_info_like_img);
   mEventImageComment = (ImageView) view.findViewById(R.id.event_info_comment_img);
   mEventImageType = (ImageView) view.findViewById(R.id.event_info_type_img);
   mEventTextLike = (TextView) view.findViewById(R.id.event_info_like_text);
   mEventTextType = (TextView) view.findViewById(R.id.event_info_type_text);
   mEventTextLocation = (TextView) view.findViewById(R.id.event_info_location_text);
   mEventTextTime = (TextView) view.findViewById(R.id.event_info_time_text);

   mEventImageLike.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           int number = Integer.parseInt(mEventTextLike.getText().toString());
           database.child("events").child(mEvent.getId()).child("event_like_number").setValue(number + 1);
           mEventTextLike.setText(String.valueOf(number + 1));
       }
   });

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


@Override
public void onSubmit(String editString, String event_type){
   String key = uploadEvent(Config.username, editString, event_type);
  
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

