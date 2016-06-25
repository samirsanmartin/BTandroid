package com.example.android.lightcontrol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by samirsanmartin on 6/12/16.
 */
public class DeviceAdapter extends ArrayAdapter<PairedDevices> {

    private ArrayList<PairedDevices> datos;
    public DeviceAdapter(Context context, ArrayList<PairedDevices> datos){
        super(context, R.layout.pdevicelayout, datos );
        this.datos=datos;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.pdevicelayout, null);

        TextView nombre = (TextView) item.findViewById(R.id.name);
        nombre.setText(datos.get(position).getName());

        TextView direccion = (TextView) item.findViewById(R.id.address);
        direccion.setText(datos.get(position).getAddress());

        return(item);
    }
}
