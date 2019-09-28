public class OnBoardingPageAdapter extends FragmentPagerAdapter {
   private static int NUM_ITEMS = 2;

   public OnBoardingPageAdapter(FragmentManager fragmentManager) {
       super(fragmentManager);
   }

   // Returns total number of pages
   @Override
   public int getCount() {
       return NUM_ITEMS;
   }

   // Returns the fragment to display for that page
   @Override
   public Fragment getItem(int position) {
       switch (position) {
           case 0:
               return LoginFragment.newInstance();
           case 1:
               return RegisterFragment.newInstance();
           default:
               return null;
       }
   }

   @Override
   public CharSequence getPageTitle(int position) {
       switch (position) {
           case 0:
               return "Login";
           case 1:
               return "Register";
       }
       return null;
   }
}

public class OnBoardingActivity extends AppCompatActivity {
   private ViewPager viewpage;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_on_boarding);

       // setup viewpager and tablayout
       viewpage = findViewById(R.id.viewpager);
       OnBoardingPageAdapter onBoardingPageAdapter = new OnBoardingPageAdapter(getSupportFragmentManager());
       viewpage.setAdapter(onBoardingPageAdapter);
       TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
       tabLayout.setupWithViewPager(viewpage);
       tabLayout.setTabMode(TabLayout.MODE_FIXED);
       tabLayout.setSelectedTabIndicatorColor(getColor(R.color.colorAccent));
   }
}
