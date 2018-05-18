package com.sharpinfo.sir.gestionprojet_v2.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.sharpinfo.sir.gestionprojet_v2.action.depense.DepenseListActivity;
import com.sharpinfo.sir.gestionprojet_v2.action.projet.ProjetListActivity;
import com.sharpinfo.sir.gestionprojet_v2.action.tache.TacheListActivity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bean.Projet;
import service.DepenseService;
import bean.Societe;
import helper.Dispacher;
import helper.Session;
import service.ProjetService;
import service.TacheService;
import service.SocieteService;

public class ProjetAdapter extends RecyclerView.Adapter<ProjetAdapter.ViewHolder> {

    private List<Projet> mProjets;
    private AlertDialog alertDialog;
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
        final View projetView = inflater.inflate(R.layout.item_projet_list, parent, false);

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


                                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                final View mView = inflater.inflate(R.layout.projet_edit_popup, null);

                                ImageButton dismissButton = mView.findViewById(R.id.dismiss_edit_projet);

                                final EditText date = mView.findViewById(R.id.date_debut_projet);
                                final EditText titre = mView.findViewById(R.id.titre_projet);
                                final EditText budget = mView.findViewById(R.id.budget_projet);
                                final EditText description = mView.findViewById(R.id.description_projet);
                                Button button = mView.findViewById(R.id.button_edit_projet);

                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                String dateString = dateFormat.format(mProjets.get(viewHolder.getAdapterPosition()).getDateDebut());

                                date.setText(dateString);
                                titre.setText(mProjets.get(viewHolder.getAdapterPosition()).getNom());
                                budget.setText(mProjets.get(viewHolder.getAdapterPosition()).getBudget() + "");
                                description.setText(mProjets.get(viewHolder.getAdapterPosition()).getDescription());

                                builder.setView(mView);
                                final AlertDialog alertDialog = builder.create();
                                final ProjetService projetService = new ProjetService(context);

                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Projet projet = mProjets.get(viewHolder.getAdapterPosition());

                                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                        try {
                                            Date dateEdited = format.parse(date.getText() + "");
                                            projet.setDateDebut(dateEdited);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                        projet.setBudget(BigDecimal.valueOf(Double.valueOf(budget.getText() + "")));
                                        projet.setNom(titre.getText() + "");
                                        projet.setDescription(description.getText() + "");
                                        projetService.edit(projet);

                                        notifyDataSetChanged();
                                        alertDialog.dismiss();

                                        Snackbar snackbar = Snackbar.make(projetView, "The project was updated successfully", Snackbar.LENGTH_LONG);
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
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                                builder1.setTitle("Confirm?");
                                builder1.setMessage("Deleting this project will also delete its expenses and tasks ");
                                builder1.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        removeFromList(viewHolder.getAdapterPosition(), viewHolder, context);
                                        dialog.dismiss();
                                    }
                                });

                                builder1.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        // Do nothing
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alert = builder1.create();
                                alert.show();
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

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View mView = inflater.inflate(R.layout.projet_info_popup, null);
                ImageButton dismissButton = mView.findViewById(R.id.dismiss_popup_projet);

                TextView titreProjet = mView.findViewById(R.id.titre_popup_projet);
                TextView dateDebut = mView.findViewById(R.id.date_popup_projet);
                TextView budget = mView.findViewById(R.id.budget_popup_pojet);
                TextView societeProjet = mView.findViewById(R.id.societe_popup_projet);
                TextView description = mView.findViewById(R.id.description_popup_projet);
                Button consulterDepense = mView.findViewById(R.id.consulter_depense_popup);
                Button consulterTache = mView.findViewById(R.id.consulter_taches_popup);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String dateString = dateFormat.format(mProjets.get(viewHolder.getAdapterPosition()).getDateDebut());

                dateDebut.setText(dateString);
                titreProjet.setText(mProjets.get(viewHolder.getAdapterPosition()).getNom());

                Societe s = mProjets.get(viewHolder.getAdapterPosition()).getSociete();
                SocieteService societeService = new SocieteService(context);
                Societe societe = societeService.find(s.getId());
                if (societe == null) {
                    societeProjet.setText("Company not affected");
                } else {
                    societeProjet.setText(societe+"");
                }

                consulterDepense.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Session.setAttribute(mProjets.get(viewHolder.getAdapterPosition()), "projetRecherche");
                        Dispacher.forward(context, DepenseListActivity.class);
                        alertDialog.dismiss();
                    }
                });

                consulterTache.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Session.setAttribute(mProjets.get(viewHolder.getAdapterPosition()), "projetRecherche");
                        Dispacher.forward(context, TacheListActivity.class);
                        alertDialog.dismiss();
                    }
                });

                builder.setView(mView);
                alertDialog = builder.create();

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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Projet projet = mProjets.get(position);

        TextView nomTextView = viewHolder.nom;
        TextView descriptiontextView = viewHolder.description;

        nomTextView.setText(projet.getNom());
        descriptiontextView.setText(projet.getDescription());


    }

    public void removeFromList(int position, ViewHolder viewHolder, Context context) {
        Projet projet = mProjets.get(viewHolder.getAdapterPosition());
        ProjetService projetService = new ProjetService(context);
        TacheService tacheService = new TacheService(context);
        DepenseService depenseService = new DepenseService(context);

        tacheService.deleteByProjet(projet);
        depenseService.deleteByProjet(projet);

        projetService.removeProjet(projet);
        mProjets.remove(position);
        notifyItemRemoved(viewHolder.getAdapterPosition());
        notifyItemRangeChanged(viewHolder.getAdapterPosition(), mProjets.size());

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
