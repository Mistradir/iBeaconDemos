package com.example.thomas.ibeacondemos.Fragments.demos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thomas.ibeacondemos.R;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;

import java.util.Arrays;


public class BeaconTransmittorFragment extends Fragment {

    TextView available;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transmitter, container, false);
        available = (TextView) getActivity().findViewById(R.id.available);
        BeaconParser beaconParser = new BeaconParser()
                .setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25");
        BeaconTransmitter beaconTransmitter = new BeaconTransmitter(getActivity().getApplicationContext(), beaconParser);
        if(beaconTransmitter.checkTransmissionSupported(getActivity().getApplicationContext()) == beaconTransmitter.SUPPORTED){
            Beacon beacon = new Beacon.Builder()
                    .setId1("a423ah4-rae4-5p21-4315-gh3a72b0428a")
                    .setId2("411")
                    .setId3("223")
                    .setManufacturer(0x0118)
                    .setTxPower(-59)
                    .setDataFields(Arrays.asList(new Long[]{0l}))
                    .build();
            beaconTransmitter.startAdvertising(beacon);
        } else {
            Log.i("availibility", "geen toegankelijkheid");
            available.setText("Device not supported");
        }
        return rootView;
    }
}
