package com.sharpinfo.sir.gestionprojet_v2.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import bean.Projet;
import bean.Societe;

public class ProjetSpinnerAdapter extends ArrayAdapter<Projet> {

    private Context context;
    private final List<Projet> projets;

    public ProjetSpinnerAdapter(Context context, int textViewResourceId, List<Projet> projets) {
        super(context, textViewResourceId, projets);
        this.context = context;
        this.projets = projets;
    }

    @Override
    public int getCount() {
        return projets.size();
    }

    @Override
    public Projet getItem(int position) {
        return projets.get(position);
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
        TextView projet = (TextView) super.getView(position, convertView, parent);
        projet.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        projet.setText(projets.get(position).getNom());

        // And finally return your dynamic (or custom) view for each spinner item
        return projet;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }


    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView projet = (TextView) super.getDropDownView(position, convertView, parent);
        projet.setTextSize(20f);
        projet.setPadding(15,15,15,15);
        projet.setTextColor(Color.BLACK);
        projet.setText(projets.get(position).getNom());
        return projet;
    }
}
