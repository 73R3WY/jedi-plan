package jeremypacabis.ingenuity.jediplanagency;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Jeremy Patrick on 8/16/2017.
 * Author: Jeremy Patrick G. Pacabis
 * for jeremypacabis.ingenuity.jediplanagency @ JediPlanAgency
 */

public class ProfileFragment extends Fragment {
    private TextView tv_name, tv_username, tv_type, tv_position, tv_rate;
    private User mUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mUser = ((HomePage) getActivity()).getUser();
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        tv_name = (TextView) view.findViewById(R.id.tv_profile_name);
        tv_username = (TextView) view.findViewById(R.id.tv_profile_username);
        tv_type = (TextView) view.findViewById(R.id.tv_profile_type);
        tv_position = (TextView) view.findViewById(R.id.tv_profile_position);
        tv_rate = (TextView) view.findViewById(R.id.tv_profile_rate);

        tv_name.setText(mUser.getFirst_name() + " " + mUser.getLast_name());
        tv_username.setText(mUser.getUsername());
        tv_type.setText(mUser.getType());
        tv_position.setText(mUser.getPosition());
        tv_rate.setText(mUser.getRate());



        return view;
    }
}
