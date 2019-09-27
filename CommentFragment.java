@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                        Bundle savedInstanceState) {
   // Inflate the layout for this fragment
   View view = inflater.inflate(R.layout.fragment_comment, container, false);
   GridView gridView = (GridView) view.findViewById(R.id.comment_grid);
   gridView.setAdapter(new EventAdapter(getActivity()));
   return view;
}
