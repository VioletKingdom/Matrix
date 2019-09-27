public class RegisterFragment extends Fragment {

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
       return inflater.inflate(R.layout.fragment_register, container, false);
   }

}
