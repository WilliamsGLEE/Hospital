package com.example.hospital.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hospital.R;
import com.example.hospital.table.Nurse;

import java.util.List;

/**
 * Created by Wachsbeere on 2018/12/31.
 */

public class NurseListAdapter extends BaseAdapter {

    private Context context;
    private List<Nurse> nurseList;

    public NurseListAdapter(Context context, List<Nurse> nurseList) {
        this.context = context;
        this.nurseList = nurseList;
    }

    @Override
    public int getCount() {
        return nurseList.size();
    }

    @Override
    public Object getItem(int position) {
        return nurseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Nurse nurse = nurseList.get(position);
        if (nurse == null){
            return null;
        }

        NurseListAdapter.ViewHolder holder = null;
        if (view != null){
            holder = (NurseListAdapter.ViewHolder) view.getTag();
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.item_nurse, null);

            holder = new NurseListAdapter.ViewHolder();
            holder.nurseIdTextView = (TextView) view.findViewById(R.id.nurseIdTextView);
            holder.nurseNameTextView = (TextView) view.findViewById(R.id.nurseNameTextView);
            holder.nurseJobTextView = (TextView) view.findViewById(R.id.nurseJobTextView);

            view.setTag(holder);
        }

        holder.nurseIdTextView.setText(nurse.getNurseNo() + "");
        holder.nurseNameTextView.setText(nurse.getName());
        holder.nurseJobTextView.setText(nurse.getJobName());

        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    public static class ViewHolder {
        public TextView nurseIdTextView;
        public TextView nurseNameTextView;
        public TextView nurseJobTextView;
    }
}

