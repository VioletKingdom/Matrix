public class MainFragment extends Fragment implements OnMapReadyCallback, ReportDialog.DialogCallBack{
private MapView mapView;
private View view;
private GoogleMap googleMap;
private LocationTracker locationTracker;
private FloatingActionButton fabReport;
private ReportDialog dialog;
private FloatingActionButton fabFocus;
  
//event information part
private BottomSheetBehavior bottomSheetBehavior;
private ImageView mEventImageLike;
private ImageView mEventImageComment;
private ImageView mEventImageType;
private TextView mEventTextLike;
private TextView mEventTextType;
private TextView mEventTextLocation;
private TextView mEventTextTime;
private TrafficEvent mEvent;





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


private void askSpeechInput(String string) {
   Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
   intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
           RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
   intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
   intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 2000);
   intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
           string);
   try {
       startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
   } catch (ActivityNotFoundException a) {

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
}


//Store the image into local disk
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
   switch (requestCode) {
       case REQUEST_CAPTURE_IMAGE: {
           if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
               Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
               if (dialog != null && dialog.isShowing()) {
                    dialog.updateImage(imageBitmap);
               }
               //Compress the image, this is optional
               ByteArrayOutputStream bytes = new ByteArrayOutputStream();
               imageBitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes);


               File destination = new File(Environment.getExternalStorageDirectory(), "temp.png");
               if (!destination.exists()) {
                   try {
                       destination.createNewFile();
                   } catch (IOException ex) {
                       ex.printStackTrace();
                   }
               }
               FileOutputStream fo;
               try {
                   fo = new FileOutputStream(destination);
                   fo.write(bytes.toByteArray());
                   fo.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
           break;
       }
       default:
   }
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

