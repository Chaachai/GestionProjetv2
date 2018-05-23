package com.sharpinfo.sir.gestionprojet_v2.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import bean.Manager;

public class ManagerSpinnerAdapter extends ArrayAdapter<Manager> {

    private Context context;
    private final List<Manager> managers;

    public ManagerSpinnerAdapter(Context context, int textViewResourceId, List<Manager> managers) {
        super(context, textViewResourceId, managers);
        this.context = context;
        this.managers = managers;
    }

    @Override
    public int getCount() {
        return managers.size();
    }

    @Override
    public Manager getItem(int position) {
        return managers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView managerView = (TextView) super.getView(position, convertView, parent);
        managerView.setTextColor(Color.BLACK);
        managerView.setText(managers.get(position).getNom() + " " + managers.get(position).getPrenom());

        return managerView;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView manager = (TextView) super.getDropDownView(position, convertView, parent);
        manager.setTextSize(20f);
        manager.setPadding(15,15,15,15);
        manager.setTextColor(Color.BLACK);
        if (managers.get(position).getNom().equals("")) {
            manager.setText(managers.get(position).getPrenom());
        } else if (managers.get(position).getPrenom().equals("")) {
            manager.setText(managers.get(position).getNom());
        } else
            manager.setText(managers.get(position).getNom() + " " + managers.get(position).getPrenom());
        return manager;
    }
}
