package jeremypacabis.ingenuity.jediplanagency;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Jeremy Patrick on 8/16/2017.
 * Author: Jeremy Patrick G. Pacabis
 * for jeremypacabis.ingenuity.jediplanagency @ JediPlanAgency
 */

public class HomeFragment extends Fragment {
    private ButtonClicked buttonClicked;
    private TextView tv_welcome_user;
    private User mUser;
    private Button btn_view_report, btn_view_payslip, btn_view_employees, btn_view_payroll;
    private LinearLayout ll_management;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mUser = ((HomePage) getActivity()).getUser();
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        tv_welcome_user = (TextView) view.findViewById(R.id.tv_welcome_user);
        tv_welcome_user.setText("Welcome " + mUser.getFirst_name() + "!");
        btn_view_report = (Button) view.findViewById(R.id.btn_view_report);
        btn_view_payslip = (Button) view.findViewById(R.id.btn_view_payslip);
        btn_view_employees = (Button) view.findViewById(R.id.btn_view_employees);
        btn_view_payroll = (Button) view.findViewById(R.id.btn_view_payroll);

        if (isManagement(mUser.getType())) {
            btn_view_employees.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClicked.ButtonClicked(C.CLICKED_VIEW_EMPLOYEES);
                }
            });

            btn_view_payroll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClicked.ButtonClicked(C.CLICKED_VIEW_PAYROLL);
                }
            });
        } else {
            ll_management = (LinearLayout) view.findViewById(R.id.ll_management);
            ll_management.setVisibility(View.GONE);
        }

        btn_view_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked.ButtonClicked(C.CLICKED_VIEW_REPORT);
            }
        });

        btn_view_payslip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked.ButtonClicked(C.CLICKED_VIEW_PAYSLIP);
            }
        });

        return view;
    }

    private boolean isManagement(String type) {
        return type.equals(C.TYPE_MANAGEMENT);
    }

    public interface ButtonClicked {
        public void ButtonClicked(String string);
    }

    public void setButtonClicked(ButtonClicked buttonClicked) {
        this.buttonClicked = buttonClicked;
    }
}
