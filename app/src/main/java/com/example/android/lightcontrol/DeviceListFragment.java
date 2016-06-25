package com.example.android.lightcontrol;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class DeviceListFragment extends Fragment {
   private BluetoothDevice mmDevice;
    public DeviceListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_device_list, container, false);


        final ArrayList<PairedDevices> extras;
        final ArrayList<BluetoothDevice> devices;

        extras = (ArrayList<PairedDevices>) getActivity().getIntent().getSerializableExtra("PAIRED_DEVICES");
        Bundle bundle=getActivity().getIntent().getExtras();

        devices=  bundle.getParcelableArrayList("DEVICES");


        ArrayList<PairedDevices> data = new ArrayList<>();

        for (int i = 0; i < extras.size(); i++) {
            Log.v("asdas ", extras.get(i).getName() +  " " + extras.get(i).getAddress() );
            //PairedDevices device = new PairedDevices(extras.get(i).getName(), extras.get(i).getAddress());
            //data.add(device);
        }

        DeviceAdapter adaptador = new DeviceAdapter(getActivity(), extras);
        ListView list_devices = (ListView) rootView.findViewById(R.id.listView);

        list_devices.setAdapter(adaptador);

        list_devices.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selection = position;
                mmDevice = devices.get(selection);
                Intent activity1 = new Intent(getActivity(), MainActivity.class);
                activity1.putExtra("SELECTION", mmDevice);
                startActivity(activity1);
            }
        });
        return (rootView);

    }

}
