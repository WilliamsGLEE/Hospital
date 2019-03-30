package com.example.hospital.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hospital.R;
import com.example.hospital.table.Bed;
import com.example.hospital.table.BedAndNurse;

import java.util.List;

/**
 * Created by Wachsbeere on 2018/12/31.
 */

public class BedListAdapter extends BaseAdapter {

    private Context context;
    private List<?> list;
    private boolean isLinkQuery;

    public BedListAdapter(Context context, List<?> list, boolean isLinkQuery) {
        this.context = context;
        this.list = list;
        this.isLinkQuery = isLinkQuery;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (!isLinkQuery) {
            Bed bed = (Bed) list.get(position);
            if (bed == null){
                return null;
            }

            BedListAdapter.ViewHolder holder = null;
            if (view != null){
                holder = (BedListAdapter.ViewHolder) view.getTag();
            }else {
                view = LayoutInflater.from(context).inflate(R.layout.item_bed, null);

                holder = new BedListAdapter.ViewHolder();
                holder.roomNoTextView = (TextView) view.findViewById(R.id.roomNoTextView);
                holder.bedNoTextView = (TextView) view.findViewById(R.id.bedNoTextView);
                holder.nurseNoTextView = (TextView) view.findViewById(R.id.nurseNoTextView);

                view.setTag(holder);
            }

            holder.roomNoTextView.setText(bed.getRoomNo() + "");
            holder.bedNoTextView.setText(bed.getBedNo() + "");
            holder.nurseNoTextView.setText(bed.getNurseNo() + "");

            return view;


        } else {

            BedAndNurse bedAndNurse = (BedAndNurse) list.get(position);
            if (bedAndNurse == null){
                return null;
            }

            BedListAdapter.ViewHolder2 holder2 = null;
            if (view != null){
                holder2 = (BedListAdapter.ViewHolder2) view.getTag();
            }else {
                view = LayoutInflater.from(context).inflate(R.layout.item_bedandnurse, null);

                holder2 = new BedListAdapter.ViewHolder2();
                holder2.roomNoTextView = (TextView) view.findViewById(R.id.roomNoTextView);
                holder2.bedNoTextView = (TextView) view.findViewById(R.id.bedNoTextView);
                holder2.nurseNoTextView = (TextView) view.findViewById(R.id.nurseNoTextView);
                holder2.nameTextView = (TextView) view.findViewById(R.id.nameTextView);
                holder2.jobTextView = (TextView) view.findViewById(R.id.jobTextView);

                view.setTag(holder2);
            }

            holder2.roomNoTextView.setText(bedAndNurse.getRoomNo() + "");
            holder2.bedNoTextView.setText(bedAndNurse.getBedNo() + "");
            holder2.nurseNoTextView.setText(bedAndNurse.getNurseNo() + "");
            holder2.nameTextView.setText(bedAndNurse.getName());
            holder2.jobTextView.setText(bedAndNurse.getJobName());

            return view;
        }
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    public static class ViewHolder {
        public TextView roomNoTextView;
        public TextView bedNoTextView;
        public TextView nurseNoTextView;
    }

    public static class ViewHolder2 {
        public TextView roomNoTextView;
        public TextView bedNoTextView;
        public TextView nurseNoTextView;
        public TextView nameTextView;
        public TextView jobTextView;
    }
}

