public class LoginFragment extends OnBoardingBaseFragment {

   public static LoginFragment newInstance() {
       Bundle args = new Bundle();

       LoginFragment fragment = new LoginFragment();
       fragment.setArguments(args);
       return fragment;
   }

   public LoginFragment() {
       // Required empty public constructor
   }


@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                        Bundle savedInstanceState) {
   // Inflate the layout for this fragment
   View view = super.onCreateView(inflater, container, savedInstanceState);
   submitButton.setText(getString(R.string.login));

   // test database connection
   DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("message");
   myRef.setValue("Hello, World!");

   // login the submitButton and register
   submitButton.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           final String username = usernameEditText.getText().toString();
           final String password = Utils.md5Encryption(passwordEditText.getText().toString());

           database.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   if (dataSnapshot.hasChild(username) && (password.equals(dataSnapshot.child(username).child("user_password").getValue()))) {
                       Config.username = username;
                       startActivity(new Intent(getActivity(), ControlPanel.class));
                   } else {
                       Toast.makeText(getActivity(),"Please try to login again", Toast.LENGTH_SHORT).show();
                   }
               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           });
       }
   });


   return view;
}


}

