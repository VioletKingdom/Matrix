public class RegisterFragment extends OnBoardingBaseFragment {

   public static RegisterFragment newInstance() {
      
       Bundle args = new Bundle();
      
       RegisterFragment fragment = new RegisterFragment();
       fragment.setArguments(args);
       return fragment;
   }

   public RegisterFragment() {
       // Required empty public constructor
   }

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                        Bundle savedInstanceState) {
   // Inflate the layout for this fragment
   View view = super.onCreateView(inflater, container, savedInstanceState);
   submitButton.setText(getString(R.string.register));

   // register the account to firebase
   submitButton.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View v) {
       final String username = usernameEditText.getText().toString();
       final String password = passwordEditText.getText().toString();

       database.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if (dataSnapshot.hasChild(username)) {
                   Toast.makeText(getContext(), "username is already registered, please change one",
                           Toast.LENGTH_LONG).show();
               } else if (!username.isEmpty() && !password.isEmpty()) {
                   final User user = new User();
                   user.setUser_account(username);
                   user.setUser_password(Utils.md5Encryption(password));
                   user.setUser_timestamp(System.currentTimeMillis());
                   database.child("user").child(user.getUser_account()).setValue(user);
                   Toast.makeText(getContext(), "user has successfully registered",
                           Toast.LENGTH_LONG).show();
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
   }
});

AdView mAdView = (AdView) view.findViewById(R.id.adView);
AdRequest adRequest = new AdRequest.Builder().build();
mAdView.loadAd(adRequest);


   return view;
}


   @Override
   protected int getLayout() {
       return R.layout.fragment_register;
   }

}

