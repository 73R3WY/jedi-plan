package jeremypacabis.ingenuity.jediplanagency;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jeremy Patrick on 8/16/2017.
 * Author: Jeremy Patrick G. Pacabis
 * for jeremypacabis.ingenuity.jediplanagency @ JediPlanAgency
 */

public class MenuAdapter extends ArrayAdapter<String> {
    private LayoutInflater inflater;
    private static final String[] menuItems = {
            "Home", "Profile", "Logout"
    };

    private static final int[] menuIcons = {
            R.drawable.ic_home_white_24dp,
            R.drawable.ic_account_circle_white_24dp,
            R.drawable.ic_exit_to_app_white_24dp
    };

    public MenuAdapter(@NonNull Context context) {
        super(context, R.layout.sidebar_menu, menuItems);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.sidebar_menu, parent, false);
        }

        ImageView sidebar_menu_icon = (ImageView) convertView.findViewById(R.id.sidebar_lv_menu_icon);
        TextView sidebar_menu_title = (TextView) convertView.findViewById(R.id.sidebar_lv_menu_title);
        sidebar_menu_icon.setImageResource(menuIcons[position]);
        sidebar_menu_title.setText(menuItems[position]);
        return convertView;
    }
}
