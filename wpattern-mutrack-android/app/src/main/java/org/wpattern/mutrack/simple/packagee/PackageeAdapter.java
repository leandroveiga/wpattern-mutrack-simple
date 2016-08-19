package org.wpattern.mutrack.simple.packagee;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.springframework.web.client.RestTemplate;
import org.wpattern.mutrack.simple.R;

import java.util.ArrayList;

public class PackageeAdapter extends ArrayAdapter<PackageeBean> {

    public PackageeAdapter(Context context, ArrayList<PackageeBean> packagees) {
        super(context, 0, packagees);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PackageeBean packagee = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.packagee_row_fragment, parent, false);
        }

        TextView nameTextView = (TextView) convertView.findViewById(R.id.namePackageRow);
        TextView codeTextView = (TextView) convertView.findViewById(R.id.codePackageRow);
        TextView descriptionTextView = (TextView) convertView.findViewById(R.id.descriptionPackageRow);

        nameTextView.setText(packagee.getName());
        codeTextView.setText(packagee.getCode());
        descriptionTextView.setText(packagee.getDescription());

        FloatingActionButton fab = (FloatingActionButton)convertView.findViewById(R.id.buttonDelete);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://localhost:8080/api/private/packagee";

                // Create a new RestTemplate instance
                RestTemplate restTemplate = new RestTemplate();

                // Add the String message converter
                //restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

                // Make the HTTP GET request, marshaling the response to a String
                try {
                    //Object[] result = restTemplate.getForObject(url, Object[].class, "Android");
                    PackageeRest packageeRest = new PackageeRest();

                    packageeRest.execute(url);

                } catch (Exception ex) {
                    Log.e(ex.getMessage(), "");
                }


                Snackbar.make(view, "Deleted the package \"" + packagee.getName() + "\"", Snackbar.LENGTH_LONG)
                        .setAction("Code", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar.make(view, "Code: " + packagee.getCode(), Snackbar.LENGTH_LONG).show();
                            }
                        }).show();
            }
        });

        return convertView;
    }
}
