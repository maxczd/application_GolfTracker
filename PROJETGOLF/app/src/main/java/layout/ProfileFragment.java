package layout;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cazade.golf.projetgolf.R;


public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    TextView textPseudo, textEmail, textHandicap;

    // TODO: Rename and change types of parameters
    private String email;
    private String username;
    private String handicap;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2, String param3) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString(ARG_PARAM1);
            username = getArguments().getString(ARG_PARAM2);
            handicap = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        textPseudo = (TextView) v.findViewById(R.id.textPseudo);
        textPseudo.setText(username);
        textEmail = (TextView) v.findViewById(R.id.textEmail);
        textEmail.setText(email);
        textHandicap = (TextView) v.findViewById(R.id.textHandicap);
        if(handicap.equals("null")){
            textHandicap.setText("Handicap : non défini");
        }else{
            textHandicap.setText("Handicap : " + handicap);
        }
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onProfileFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onProfileFragmentInteraction(Uri uri);
    }

    public void setPseudo(String pseudo){
        textPseudo.setText(pseudo);
    }
    public void setEmail(String email){
        textEmail.setText(email);
    }
}
