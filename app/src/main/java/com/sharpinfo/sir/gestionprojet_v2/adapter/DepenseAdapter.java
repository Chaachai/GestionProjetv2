package com.sharpinfo.sir.gestionprojet_v2.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.sharpinfo.sir.gestionprojet_v2.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import bean.Depense;

public class DepenseAdapter extends RecyclerView.Adapter<DepenseAdapter.ViewHolder> {

    private List<Depense> mdepenses;


    public DepenseAdapter(List<Depense> depenses) {
        mdepenses = depenses;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView montant;
        public TextView date;
        public TextView seeMore;
        public LinearLayout depenseitem;


        public ViewHolder(View itemView) {
            super(itemView);

            depenseitem = (LinearLayout) itemView.findViewById(R.id.depense_item);
            seeMore = (TextView) itemView.findViewById(R.id.depense_seeMore);
            montant = (TextView) itemView.findViewById(R.id.depense_montant);
            date = (TextView) itemView.findViewById(R.id.depense_date);

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //inflate custom layout
        View societeView = inflater.inflate(R.layout.item_societe_list, parent, false);

        // Return a new holder instance
        //final was added here because of toast
        final ViewHolder viewHolder = new ViewHolder(societeView);


        viewHolder.seeMore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.d("ta5", "insideOnCLickListener");
                //onclick the see more 3buttons
                //popupmenu
                PopupMenu popupMenu = new PopupMenu(context, viewHolder.seeMore);
                //inflating menu from xml
                popupMenu.inflate(R.menu.depense_options_menu);
                //onclick
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_item_options_menu:
                                Log.d("ta5", "menu1");
                                break;
                            case R.id.edit_item_option_menu:
                                Log.d("ta5", "menu2");
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        viewHolder.depenseitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on click the whole line
                Toast.makeText(context, "test  depense " + String.valueOf(viewHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                Log.d("tad", "" + mdepenses.get(viewHolder.getAdapterPosition()).getMontant());
            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DepenseAdapter.ViewHolder viewHolder, int position) {
        Depense depense = mdepenses.get(position);
        Log.d("Depense", "ha lfloos dyal depense " + depense.getMontant() + " ha la date :" + depense.getDate() + " !");
        TextView textView = viewHolder.montant;
        TextView textView2 = viewHolder.date;

        //date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm", Locale.getDefault());
        String dateString = dateFormat.format(depense.getDate());


        textView.setText(dateString);
        textView2.setText(depense.getMontant()+"  DHs");


    }

    @Override
    public int getItemCount() {
        return mdepenses.size();
    }


}
