package com.sharpinfo.sir.gestionprojet_v2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.sharpinfo.sir.gestionprojet_v2.R;
import com.sharpinfo.sir.gestionprojet_v2.action.projet.ProjetListActivity;

import java.util.ArrayList;
import java.util.List;

import bean.Projet;

public class ProjetAdapter extends RecyclerView.Adapter<ProjetAdapter.ViewHolder> {

    private List<Projet> mProjets;
    ProjetListActivity projetListActivity = new ProjetListActivity();


    public ProjetAdapter(List<Projet> projets) {
        mProjets = projets;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView description;
        public TextView nom;
        public TextView seeMore;
        public ConstraintLayout projetitem;


        public ViewHolder(View itemView) {
            super(itemView);

            projetitem = (ConstraintLayout) itemView.findViewById(R.id.projet_item);
            seeMore = (TextView) itemView.findViewById(R.id.projet_seeMore);
            description = (TextView) itemView.findViewById(R.id.projet_item_description);
            nom = (TextView) itemView.findViewById(R.id.projet_item_nom);

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);

        //inflate custom layout
        View projetView = inflater.inflate(R.layout.item_projet_list, parent, false);

        // Return a new holder instance
        //final was added here because of toast
        final ProjetAdapter.ViewHolder viewHolder = new ProjetAdapter.ViewHolder(projetView);

        viewHolder.seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ta5", "insideOnCLickListener");
                //onclick the see more 3buttons
                //popupmenu
                PopupMenu popupMenu = new PopupMenu(context, viewHolder.seeMore);
                //inflating menu from xml
                popupMenu.inflate(R.menu.options_menu);
                //onclick
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_item_options_menu:
                                Log.d("ta5", "menu1");
                                break;
                            case R.id.delete_item_options_menu:
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

        viewHolder.projetitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Projet projet = mProjets.get(position);

        TextView nomTextView = viewHolder.nom;
        TextView descriptiontextView = viewHolder.description;

        nomTextView.setText(projet.getNom());
        descriptiontextView.setText(projet.getDescription());


    }

    public void setfilter(List<Projet> filteredprojets) {
        mProjets = new ArrayList<>();
        mProjets.addAll(filteredprojets);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mProjets.size();
    }
}
