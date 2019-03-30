package com.example.hospital.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hospital.R;
import com.example.hospital.table.Pacient;

import java.util.List;

/**
 * Created by Wachsbeere on 2018/12/31.
 */

public class PacientListAdapter extends BaseAdapter {

    private Context context;
    private List<Pacient> pacientList;

    public PacientListAdapter(Context context, List<Pacient> pacientList) {
        this.context = context;
        this.pacientList = pacientList;
    }

    @Override
    public int getCount() {
        return pacientList.size();
    }

    @Override
    public Object getItem(int position) {
        return pacientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Pacient pacient = pacientList.get(position);
        if (pacient == null){
            return null;
        }

        PacientListAdapter.ViewHolder holder = null;
        if (view != null){
            holder = (PacientListAdapter.ViewHolder) view.getTag();
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.item_pacient, null);

            holder = new PacientListAdapter.ViewHolder();
            holder.hospitalizationNoTextView = (TextView) view.findViewById(R.id.hospitalizationNoTextView);
            holder.nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            holder.sexTextView = (TextView) view.findViewById(R.id.sexTextView);
            holder.admissionDateTextView = (TextView) view.findViewById(R.id.admissionDateTextView);
            holder.dischargedDateTextView = (TextView) view.findViewById(R.id.dischargedDateTextView);
            holder.roomNoTextView = (TextView) view.findViewById(R.id.roomNoTextView);
            holder.doctorNoTextView = (TextView) view.findViewById(R.id.doctorNoTextView);


            view.setTag(holder);
        }

        holder.hospitalizationNoTextView.setText(pacient.getHospitalizationNo() + "");
        holder.nameTextView.setText(pacient.getName());
        holder.sexTextView.setText(pacient.getSex());
        holder.admissionDateTextView.setText(pacient.getAdmissionDate());
        holder.dischargedDateTextView.setText(pacient.getDischargedDate());
        holder.roomNoTextView.setText(pacient.getRoomNo() + "");
        holder.doctorNoTextView.setText(pacient.getDoctorNo() + "");

        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    public static class ViewHolder {
        public TextView hospitalizationNoTextView;
        public TextView nameTextView;
        public TextView sexTextView;
        public TextView admissionDateTextView;
        public TextView dischargedDateTextView;
        public TextView roomNoTextView;
        public TextView doctorNoTextView;
    }
}

