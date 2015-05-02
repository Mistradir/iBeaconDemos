package com.example.thomas.ibeacondemos.Fragments.demos;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thomas.ibeacondemos.IBeaconApplication;
import com.example.thomas.ibeacondemos.R;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Thomas on 20/04/2015.
 */
public class DistanceFragment extends Fragment {

    private ImageView distanceImage;
    private TextView major;
    private TextView minor;
    private TextView accuracy;
    private Beacon closestBeacon;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_distance_demo, container, false);
        distanceImage = (ImageView) rootView.findViewById(R.id.image_verVan);
        major = (TextView) rootView.findViewById(R.id.major);
        minor = (TextView) rootView.findViewById(R.id.minor);
        accuracy =  (TextView) rootView.findViewById(R.id.accuracy);
        return rootView;
    }

    public void updateUI(ArrayList<Beacon> beacons) {
        Log.i("Blup", "Ik kom hier");
        new UpdateUI().execute(beacons);
    }

    private class UpdateUI extends AsyncTask<ArrayList<Beacon>, Integer, Beacon> {
        @Override
        protected Beacon doInBackground(ArrayList<Beacon>... beacons) {
            ArrayList<Beacon> beaconArrayList = beacons[0];
            closestBeacon = null;
            for(Beacon beacon : beaconArrayList) {
                if(closestBeacon == null) {
                    closestBeacon = beacon;
                } else if(beacon.getDistance() < closestBeacon.getDistance()) {
                    closestBeacon = beacon;
                }
            }
            return closestBeacon;
        }

        @Override
        protected void onPostExecute(Beacon closestBeacon) {
            super.onPostExecute(closestBeacon);
            major.setText("Major: " + closestBeacon.getId2().toString());
            minor.setText("Minor: " + closestBeacon.getId3().toString());
            accuracy.setText(String.format("Accuracy: %.2f", closestBeacon.getDistance()));//String.format( "Value of a: %.2f", a )
            if (closestBeacon.getDistance() < 1) {
                distanceImage.setImageResource(R.drawable.dichtbij);
            } else if (closestBeacon.getDistance() < 3) {
                distanceImage.setImageResource(R.drawable.indebuurt);
            } else {
                distanceImage.setImageResource(R.drawable.vervan);
            }
        }
    }
}
