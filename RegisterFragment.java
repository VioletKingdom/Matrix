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
       return view;
   }

   @Override
   protected int getLayout() {
       return R.layout.fragment_register;
   }

}

