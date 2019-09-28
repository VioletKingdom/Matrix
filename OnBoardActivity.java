public class OnBoardingActivity extends AppCompatActivity {
   private ViewPager viewpage;
   private FirebaseAuth mAuth;
   private final static String TAG = OnBoardingActivity.class.getSimpleName();
   private FirebaseAuth.AuthStateListener mAuthListener;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_on_boarding);
       mAuth = FirebaseAuth.getInstance();

   //Add listener to check sign in status
   mAuthListener = new FirebaseAuth.AuthStateListener() {
       @Override
       public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
           FirebaseUser user = firebaseAuth.getCurrentUser();
           if (user != null) {
               Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
           } else {
               Log.d(TAG, "onAuthStateChanged:signed_out");
           }
       }
   };

  //sign in anonymously
   mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
       @Override
       public void onComplete(@NonNull Task<AuthResult> task) {
           Log.d(TAG, "signInAnonymously:onComplete:" + task.isSuccessful());
           if (!task.isSuccessful()) {
               Log.w(TAG, "signInAnonymously", task.getException());
           }
       }
   });


       // setup viewpager and tablayout
       viewpage = findViewById(R.id.viewpager);
       OnBoardingPageAdapter onBoardingPageAdapter = new OnBoardingPageAdapter(getSupportFragmentManager());
       viewpage.setAdapter(onBoardingPageAdapter);
       TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
       tabLayout.setupWithViewPager(viewpage);
       tabLayout.setTabMode(TabLayout.MODE_FIXED);
       tabLayout.setSelectedTabIndicatorColor(getColor(R.color.colorAccent));
   }
  
   // switch viewpage to #page
   public void setCurrentPage(int page) {
       viewpage.setCurrentItem(page);
   }
   
   //Add authentification listener when activity starts
@Override
public void onStart() {
   super.onStart();
   mAuth.addAuthStateListener(mAuthListener);
}

//Remove authentification listener when activity starts
@Override
public void onStop() {
   super.onStop();
   if (mAuthListener != null) {
       mAuth.removeAuthStateListener(mAuthListener);
   }
}


}
