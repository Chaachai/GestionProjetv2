package com.sharpinfo.sir.gestionprojet_v2.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.sharpinfo.sir.gestionprojet_v2.R;
import com.sharpinfo.sir.gestionprojet_v2.action.tache.TacheListActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bean.Depense;
import bean.Projet;
import bean.Societe;
import bean.Tache;
import service.ProjetService;
import service.SocieteService;
import service.TacheService;

public class TacheAdapter extends RecyclerView.Adapter<TacheAdapter.ViewHolder> {

    private List<Tache> mtaches;
    TacheListActivity tacheListActivity = new TacheListActivity();


    public TacheAdapter(List<Tache> taches) {
        mtaches = taches;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nbrHeur;
        public TextView date;
        public TextView heur;
        public TextView seeMore;
        public ConstraintLayout tacheitem;


        public ViewHolder(View itemView) {
            super(itemView);

            tacheitem = (ConstraintLayout) itemView.findViewById(R.id.tache_item);
            seeMore = (TextView) itemView.findViewById(R.id.tache_seeMore);
            nbrHeur = (TextView) itemView.findViewById(R.id.tache_nbrheur);
            date = (TextView) itemView.findViewById(R.id.tache_date);
            heur = (TextView) itemView.findViewById(R.id.tache_heur);

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);

        //inflate custom layout
        final View tacheView = inflater.inflate(R.layout.item_tache_list, parent, false);

        // Return a new holder instance
        //final was added here because of toast
        final ViewHolder viewHolder = new ViewHolder(tacheView);


        viewHolder.seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ta5", "insideOnCLickListener");
                PopupMenu popupMenu = new PopupMenu(context, viewHolder.seeMore);
                popupMenu.inflate(R.menu.options_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_item_options_menu:

                                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                final View mView = inflater.inflate(R.layout.tache_edit_popup, null);

                                ImageButton dismissButton = mView.findViewById(R.id.dismiss_edit_tache);

                                final EditText date = mView.findViewById(R.id.date_edit_tache);
                                final EditText heur = mView.findViewById(R.id.heur_edit_tache);
                                final EditText nbrHeur = mView.findViewById(R.id.nbrheur_edit_tache);
                                final EditText commentaire = mView.findViewById(R.id.commentaire_edit_tache);
                                Button button = mView.findViewById(R.id.button_edit_tache);

                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                String dateString = dateFormat.format(mtaches.get(viewHolder.getAdapterPosition()).getDate());

                                date.setText(dateString);
                                heur.setText(mtaches.get(viewHolder.getAdapterPosition()).getHeur());
                                nbrHeur.setText(mtaches.get(viewHolder.getAdapterPosition()).getNbrHeures() + "");
                                commentaire.setText(mtaches.get(viewHolder.getAdapterPosition()).getCommentaire());

                                builder.setView(mView);
                                final AlertDialog alertDialog = builder.create();
                                final TacheService tacheService = new TacheService(context);

                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Tache tache = mtaches.get(viewHolder.getAdapterPosition());

                                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                        try {
                                            Date dateEdited = format.parse(date.getText() + "");
                                            tache.setDate(dateEdited);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                        tache.setNbrHeures(Double.valueOf(nbrHeur.getText() + ""));
                                        tache.setHeur("" + heur.getText());
                                        tache.setCommentaire(commentaire.getText() + "");
                                        tacheService.edit(tache);

                                        Log.d("tag", "************** " + date.getText());
                                        Log.d("tag", "************** " + heur.getText());
                                        Log.d("tag", "************** " + nbrHeur.getText());
                                        Log.d("tag", "************** " + commentaire.getText());

                                        notifyDataSetChanged();
                                        alertDialog.dismiss();

                                        Snackbar snackbar = Snackbar.make(tacheView, "The task was updated successfully", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    }
                                });

                                dismissButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        alertDialog.dismiss();
                                    }
                                });

                                alertDialog.show();
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

        viewHolder.tacheitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View mView = inflater.inflate(R.layout.tache_info_popup, null);

                ImageButton dismissButton = mView.findViewById(R.id.dismiss_tache);

                TextView date = mView.findViewById(R.id.date_popup_tache);
                TextView heur = mView.findViewById(R.id.heur_popup_tache);
                TextView nbrHeur = mView.findViewById(R.id.nbrheur_popup_tache);
                TextView commentaire = mView.findViewById(R.id.commentaire_popup_tache);
                TextView projet = mView.findViewById(R.id.projet_popup_tache);
                TextView societe = mView.findViewById(R.id.societe_popup_tache);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String dateString = dateFormat.format(mtaches.get(viewHolder.getAdapterPosition()).getDate());

                date.setText(dateString);
                heur.setText(mtaches.get(viewHolder.getAdapterPosition()).getHeur());
                nbrHeur.setText(mtaches.get(viewHolder.getAdapterPosition()).getNbrHeures() + "");
                commentaire.setText(mtaches.get(viewHolder.getAdapterPosition()).getCommentaire() + "");


                Projet p = mtaches.get(viewHolder.getAdapterPosition()).getProjet();
                Societe s = mtaches.get(viewHolder.getAdapterPosition()).getSociete();


                ProjetService projetService = new ProjetService(context);
                SocieteService societeService = new SocieteService(context);

                Projet pro = projetService.find(p.getId());
                Societe soc = societeService.find(s.getId());
                if (pro == null) {
                    projet.setText("------");
                } else {
                    projet.setText(pro + "");
                }
                if (soc == null) {
                    societe.setText("------");
                } else {
                    societe.setText(soc + "");
                }

                builder.setView(mView);
                final AlertDialog alertDialog = builder.create();

                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();

            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TacheAdapter.ViewHolder viewHolder, int position) {
        Tache tache = mtaches.get(position);
        TextView textView = viewHolder.nbrHeur;
        TextView textView2 = viewHolder.date;
        TextView textView3 = viewHolder.heur;

        //date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String dateString = dateFormat.format(tache.getDate());

        textView3.setText(tache.getHeur());
        textView2.setText(dateString);
        textView.setText(tache.getNbrHeures() + "");

    }

    public void setfilter(List<Tache> filteredTaches) {
        mtaches = new ArrayList<>();
        mtaches.addAll(filteredTaches);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mtaches.size();
    }


}