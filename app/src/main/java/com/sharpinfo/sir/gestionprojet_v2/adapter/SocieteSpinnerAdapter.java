package com.sharpinfo.sir.gestionprojet_v2.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import bean.Societe;

public class SocieteSpinnerAdapter extends ArrayAdapter<Societe> {

    private Context context;
    private final List<Societe> societes;

    public SocieteSpinnerAdapter(Context context, int textViewResourceId, List<Societe> societes) {
        super(context, textViewResourceId, societes);
        this.context = context;
        this.societes = societes;
    }

    @Override
    public int getCount() {
        return societes.size();
    }

    @Override
    public Societe getItem(int position) {
        return societes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView societe = (TextView) super.getView(position, convertView, parent);
        societe.setTextColor(Color.BLACK);
        societe.setText(societes.get(position).getRaisonSociale());

        return societe;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView societe = (TextView) super.getDropDownView(position, convertView, parent);
        societe.setTextSize(20f);
        societe.setPadding(15, 15, 15, 15);
        societe.setTextColor(Color.BLACK);
        societe.setText(societes.get(position).getRaisonSociale());
        return societe;
    }
}
