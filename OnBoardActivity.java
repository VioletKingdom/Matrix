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
  
   // switch viewpage to #page
   public void setCurrentPage(int page) {
       viewpage.setCurrentItem(page);
   }

}
