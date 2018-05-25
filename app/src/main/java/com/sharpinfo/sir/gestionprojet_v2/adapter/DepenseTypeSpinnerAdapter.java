package com.sharpinfo.sir.gestionprojet_v2.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import bean.DepenseType;

public class DepenseTypeSpinnerAdapter extends ArrayAdapter<DepenseType> {

    private Context context;
    private final List<DepenseType> depenseTypes;

    public DepenseTypeSpinnerAdapter(Context context, int textViewResourceId, List<DepenseType> depenseTypes) {
        super(context, textViewResourceId, depenseTypes);
        this.context = context;
        this.depenseTypes = depenseTypes;
    }

    @Override
    public int getCount() {
        return depenseTypes.size();
    }

    @Override
    public DepenseType getItem(int position) {
        return depenseTypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView depenseTypeView = (TextView) super.getView(position, convertView, parent);
        depenseTypeView.setTextColor(Color.BLACK);
        depenseTypeView.setText(depenseTypes.get(position).getNom());

        return depenseTypeView;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView projet = (TextView) super.getDropDownView(position, convertView, parent);
        projet.setTextSize(20f);
        projet.setPadding(15, 15, 15, 15);
        projet.setTextColor(Color.BLACK);
        projet.setText(depenseTypes.get(position).getNom());
        return projet;
    }
}
