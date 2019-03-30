package com.example.hospital.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hospital.R;
import com.example.hospital.table.Dector;

import java.util.List;

/**
 * Created by Wachsbeere on 2018/12/31.
 */

public class DoctorListAdapter extends BaseAdapter {

    private Context context;
    private List<Dector> doctorList;

    public DoctorListAdapter(Context context, List<Dector> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }

    @Override
    public int getCount() {
        return doctorList.size();
    }

    @Override
    public Object getItem(int position) {
        return doctorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Dector dector = doctorList.get(position);
        if (dector == null){
            return null;
        }

        DoctorListAdapter.ViewHolder holder = null;
        if (view != null){
            holder = (DoctorListAdapter.ViewHolder) view.getTag();
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.item_doctor, null);

            holder = new DoctorListAdapter.ViewHolder();
            holder.doctorIdTextView = (TextView) view.findViewById(R.id.doctorIdTextView);
            holder.doctorNameTextView = (TextView) view.findViewById(R.id.doctorNameTextView);
            holder.doctorJobTextView = (TextView) view.findViewById(R.id.doctorJobTextView);

            view.setTag(holder);
        }

        holder.doctorIdTextView.setText(dector.getDectorNo() + "");
        holder.doctorNameTextView.setText(dector.getName());
        holder.doctorJobTextView.setText(dector.getJobName());

        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    public static class ViewHolder {
        public TextView doctorIdTextView;
        public TextView doctorNameTextView;
        public TextView doctorJobTextView;
    }
}
