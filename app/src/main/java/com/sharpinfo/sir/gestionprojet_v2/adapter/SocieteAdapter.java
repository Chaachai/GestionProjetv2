package com.sharpinfo.sir.gestionprojet_v2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sharpinfo.sir.gestionprojet_v2.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import bean.Societe;

public class SocieteAdapter extends RecyclerView.Adapter<SocieteAdapter.ViewHolder> {

    private List<Societe> msocietes;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.societe_raisonSociale);

        }
    }

    public SocieteAdapter(List<Societe> societes) {
        msocietes = societes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //inflate custom layout
        View societeView = inflater.inflate(R.layout.item_societe_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(societeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SocieteAdapter.ViewHolder viewHolder, int position) {
        Societe societe = msocietes.get(position);
        Log.d("Societe", "ha smya dyal societe " + societe.getRaisonSociale() + " ha la date :" + societe.getDateFondation() + " !");
        TextView textView = viewHolder.nameTextView;

        //date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String dateString = dateFormat.format(societe.getDateFondation());


        textView.setText(societe.getRaisonSociale() + ";" + dateString);


    }

    @Override
    public int getItemCount() {
        return msocietes.size();
    }


}
