package jeremypacabis.ingenuity.jediplanagency;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.codecrafters.tableview.TableView;

/**
 * Created by Jeremy Patrick on 8/16/2017.
 * Author: Jeremy Patrick G. Pacabis
 * for jeremypacabis.ingenuity.jediplanagency @ JediPlanAgency
 */

public class EmployeesFragment extends Fragment {
    TableView tableView;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employees_fragment, container, false);
        mContext = ((HomePage) getActivity()).getBaseContext();
        tableView = (TableView) view.findViewById(R.id.tbl_employees);

//        new EmployeesDataLoader().execute();
        return view;
    }

    private class EmployeesDataLoader extends AsyncTask<String, String, String> {
        private HttpHandler httpHandler;
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(mContext, "", "Loading employees data...", true, false);
        }

        @Override
        protected String doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
        }
    }
}
