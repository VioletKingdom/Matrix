public class MainActivity extends AppCompatActivity {
   private EventFragment listFragment;
   private CommentFragment gridFragment;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);

       // Show different fragments based on screen size.
       if (savedInstanceState == null) {
           Fragment fragment = isTablet() ? new  CommentFragment() : new EventFragment();
           getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
       }

       //add list view
      if (getSupportFragmentManager().findFragmentById(R.id.event_container) == null) {
             listFragment = new EventFragment();
             getSupportFragmentManager().beginTransaction().add(R.id.event_container, listFragment).commit();
      }

       //add Gridview
       if (getSupportFragmentManager().findFragmentById(R.id.comment_container) == null && isTablet()) {
           gridFragment = new CommentFragment();
           getSupportFragmentManager().beginTransaction().add(R.id.comment_container, gridFragment).commit();
       }
   }
   
@Override
public void onItemSelected(int position) {
   if (!isTablet()) {
       Fragment fragment = CommentFragment.newInstance(position);
       getSupportFragmentManager().beginTransaction().replace(R.id.event_container, fragment).addToBackStack(null).commit();
       Intent intent = new Intent(this, EventGridActivity.class);
       intent.putExtra("position", position);
       startActivity(intent);
   } else {
       gridFragment.onItemSelected(position);
   }
}





private boolean isTablet() {
   return (getApplicationContext().getResources().getConfiguration().screenLayout &
           Configuration.SCREENLAYOUT_SIZE_MASK) >=
           Configuration.SCREENLAYOUT_SIZE_LARGE;
}

@Override
public void onCommentSelected(int position) {
   listFragment.onItemSelected(position);
}



/**
* A dummy function to get fake event names.
*/
private String[] getEventNames() {
   String[] names = {
           "Event1", "Event2", "Event3",
           "Event4", "Event5", "Event6",
           "Event7", "Event8", "Event9",
           "Event10", "Event11", "Event12"};
   return names;
}


/**
* A dummy function to get fake event names.
*/
private String[] getEventNames() {
   String[] names = {
           "Event1", "Event2", "Event3",
           "Event4", "Event5", "Event6",
           "Event7", "Event8", "Event9",
           "Event10", "Event11", "Event12"};
   return names;
}


@Override
protected void onStart() {
   super.onStart();
   Log.e("Life cycle test", "We are at onStart()");
}

@Override
protected void onResume() {
   super.onResume();
   Log.e("Life cycle test", "We are at onResume()");
}

@Override
protected void onPause() {
   super.onPause();
   Log.e("Life cycle test", "We are at onPause()");
}

@Override
protected void onStop() {
   super.onStop();
   Log.e("Life cycle test", "We are at onStop()");
}

@Override
protected void onDestroy() {
   super.onDestroy();
   Log.e("Life cycle test", "We are at onDestroy()");
}
