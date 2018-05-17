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
import com.sharpinfo.sir.gestionprojet_v2.action.projet.ProjetListActivity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bean.Projet;
import service.ProjetService;

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
