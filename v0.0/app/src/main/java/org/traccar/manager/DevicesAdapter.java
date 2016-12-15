package org.traccar.manager;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import org.traccar.manager.model.Device;
import java.util.List;


/**
 * Created by admin on 15/12/16.
 */
public class DevicesAdapter extends ArrayAdapter<Device> {
    public DevicesAdapter(Context context, List<Device> devices) {
        super(context, 0, devices);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        final Device device = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Handle clicks for buttons inside the list item
        Button callOperator = (Button) convertView.findViewById(R.id.buttonCall);

        callOperator.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                    // @saif:7/12/2016 Modify so a call is placed to the device using the id as the number
                    String uri = "tel:" + device.getPhone().trim() ;
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                    dialIntent.setData(Uri.parse(uri));
                    getContext().startActivity(dialIntent);
                }
            });


        // Lookup view for data population
        // and populate the data into the template view using the data object

        // Operator Name
        TextView operatorName = (TextView) convertView.findViewById(R.id.operatorName);
        operatorName.setText(device.getName());

        // Transit Status
        TextView transitStatus = (TextView) convertView.findViewById(R.id.transitStatus);
        String statusText = device.getStatus();
        if(statusText.equals("offline")) {
            statusText = "Offline";
            transitStatus.setTextColor(Color.parseColor("#556569"));
        }
        else if(statusText.equals("online"))
        {
            statusText = "Online";
            transitStatus.setTextColor(Color.parseColor("#198423"));
        }
        else if(statusText.equals("") || statusText.equals("unknown"))
        {
            statusText = "Unknown";
            transitStatus.setTextColor(Color.parseColor("#F40D0D"));
        }
        transitStatus.setText(statusText);

        // Return the completed view to render on screen
        return convertView;

    }
}
