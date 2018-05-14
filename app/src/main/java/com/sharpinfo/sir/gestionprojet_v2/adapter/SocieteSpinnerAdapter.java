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

    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView societe = (TextView) super.getView(position, convertView, parent);
        societe.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        societe.setText(societes.get(position).getRaisonSociale());

        // And finally return your dynamic (or custom) view for each spinner item
        return societe;
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
        TextView societe = (TextView) super.getDropDownView(position, convertView, parent);
        if (position == 0) {
            societe.setTextColor(Color.GRAY);
            societe.setText("Select a societe");
        } else {
            societe.setTextColor(Color.BLACK);
            societe.setText(societes.get(position).getRaisonSociale());
        }
        return societe;
    }
}
