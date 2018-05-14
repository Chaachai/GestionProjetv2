package com.sharpinfo.sir.gestionprojet_v2.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import bean.Manager;
import dao.helper.DbStructure;

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

    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView manager = (TextView) super.getView(position, convertView, parent);
        manager.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        manager.setText(managers.get(position).getNom() + " " + managers.get(position).getPrenom());

        // And finally return your dynamic (or custom) view for each spinner item
        return manager;
    }

    @Override
    public boolean isEnabled(int position) {
        if (position == 0) {
            return false;
        } else return true;
    }


    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView manager = (TextView) super.getDropDownView(position, convertView, parent);
        if (position == 0) {
            manager.setTextColor(Color.GRAY);
            manager.setText("Select a manager");
        } else {
            manager.setTextColor(Color.BLACK);
            manager.setText(managers.get(position).getNom());
        }
        return manager;
    }
}
