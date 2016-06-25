package com.example.android.lightcontrol;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private BluetoothAdapter BA;
    private int REQUEST_ENABLE_BT = 1;
    private Button encender, apagar, buscar;
    private ToggleButton change;
    private ArrayList<PairedDevices> data;
    private ArrayList<BluetoothDevice> BTdevices;
    private BluetoothDevice mmDevice;
    private BluetoothSocket mmSocket;
    private OutputStream mmOutputStream;
    private InputStream mmInputStream;
    private int control;
    private String msg;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        encender = (Button) rootView.findViewById(R.id.Encender_BT);
        apagar = (Button) rootView.findViewById(R.id.Apagar_BT);
        buscar = (Button) rootView.findViewById(R.id.Search_BD);
        change = (ToggleButton) rootView.findViewById(R.id.toggleButton);

        BA = BluetoothAdapter.getDefaultAdapter();
        if (BA == null) {
            Context context = getActivity();
            CharSequence text = "Device not supported ";
            int duration = Toast.LENGTH_SHORT;

            Toast.makeText(context, text, duration).show();
        }

        //Encender Bluetooth
        enableBT();

        encender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableBT();
            }
        });

        apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BA.isEnabled()) {
                    BA.disable();
                    Toast.makeText(getActivity(), "Bluetooth apagado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Bluetooth ya está apagado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<BluetoothDevice> pDevices = BA.getBondedDevices();
                // If there are paired devices
                if (pDevices.size() > 0) {
                    data = new ArrayList<>();
                    BTdevices = new ArrayList<>();

                    for (BluetoothDevice device : pDevices) {
                        // Add the name and address to an array adapter to show in a ListView

                        PairedDevices dispositivo = new PairedDevices(device.getName(), device.getAddress());
                        data.add(dispositivo);
                        BTdevices.add(device);

                    }

                    Intent intent = new Intent(getActivity(), DeviceList.class);
                    intent.putExtra("PAIRED_DEVICES", data);
                    intent.putExtra("DEVICES",BTdevices);
                    control=1;
                    startActivity(intent);


                } else {
                    Toast.makeText(getActivity(), "No hay dispositivos", Toast.LENGTH_SHORT).show();
                }

            }
        });



        Bundle bundle = getActivity().getIntent().getExtras();
        if(bundle != null) {
            mmDevice = bundle.getParcelable("SELECTION");
            if(mmDevice != null) {
                //mmDevice = BTdevices.get(sel);
                UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID
                try {
                    mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);

                    mmSocket.connect();
                    mmOutputStream = mmSocket.getOutputStream();
                    mmInputStream = mmSocket.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

      change.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(change.isChecked()) {
                  msg = "p";
                  try {
                      if(mmOutputStream != null) {
                          mmOutputStream.write(msg.getBytes());
                      }
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }
              else{
                  msg = "a";
                  try {
                      if(mmOutputStream!= null) {
                          mmOutputStream.write(msg.getBytes());
                      }
                  } catch (IOException e) {
                      e.printStackTrace();
                  }

              }

          }
      });

        return rootView;
    }

    public void enableBT() {
        if (!BA.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            CharSequence text = "Bluetooth ya esta encendido ";
            int duration = Toast.LENGTH_SHORT;

            Toast.makeText(getActivity(), text, duration).show();

        }
    }
}


