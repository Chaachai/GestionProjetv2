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
import com.sharpinfo.sir.gestionprojet_v2.action.depense.DepenseListActivity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bean.Depense;
import bean.DepenseType;
import bean.Projet;
import bean.Societe;
import helper.Session;
import service.DepenseService;
import service.DepenseTypeService;
import service.ProjetService;
import service.SocieteService;

public class DepenseAdapter extends RecyclerView.Adapter<DepenseAdapter.ViewHolder> {

    private List<Depense> mdepenses;
    DepenseListActivity depenseListActivity = new DepenseListActivity();


    public DepenseAdapter(List<Depense> depenses) {
        mdepenses = depenses;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView montant;
        public TextView heur;
        public TextView date;
        public TextView seeMore;
        public TextView typeDepense;
        public ConstraintLayout depenseitem;


        public ViewHolder(View itemView) {
            super(itemView);

            depenseitem = itemView.findViewById(R.id.depense_item);
            seeMore = itemView.findViewById(R.id.depense_seeMore);
            montant = itemView.findViewById(R.id.depense_montant);
            heur = itemView.findViewById(R.id.depense_heur);
            date = itemView.findViewById(R.id.depense_date);
            typeDepense = itemView.findViewById(R.id.type_depense);

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        Session.setAttribute(context, "contextForDepenseType");

        final LayoutInflater inflater = LayoutInflater.from(context);

        //inflate custom layout
        final View depenseView = inflater.inflate(R.layout.item_depense_list, parent, false);

        // Return a new holder instance
        //final was added here because of toast
        final ViewHolder viewHolder = new ViewHolder(depenseView);


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
                                final View mView = inflater.inflate(R.layout.depense_edit_popup, null);

                                ImageButton dismissButton = mView.findViewById(R.id.dismiss_edit);

                                final EditText date = mView.findViewById(R.id.date_edit);
                                final EditText heur = mView.findViewById(R.id.heur_edit);
                                final EditText montant = mView.findViewById(R.id.montant_edit);
                                Button button = mView.findViewById(R.id.button_edit);

                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                String dateString = dateFormat.format(mdepenses.get(viewHolder.getAdapterPosition()).getDate());

                                date.setText(dateString);
                                heur.setText(mdepenses.get(viewHolder.getAdapterPosition()).getHeur());
                                montant.setText(mdepenses.get(viewHolder.getAdapterPosition()).getMontant() + "");

                                builder.setView(mView);
                                final AlertDialog alertDialog = builder.create();
                                final DepenseService depenseService = new DepenseService(context);

                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Depense depense = mdepenses.get(viewHolder.getAdapterPosition());

                                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                        try {
                                            Date dateEdited = format.parse(date.getText() + "");
                                            depense.setDate(dateEdited);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                        depense.setMontant(BigDecimal.valueOf(Double.valueOf(montant.getText() + "")));
                                        depense.setHeur(heur.getText() + "");
                                        depenseService.edit(depense);


                                        Log.d("tag", "************** " + date.getText());
                                        Log.d("tag", "************** " + heur.getText());
                                        Log.d("tag", "************** " + montant.getText());

                                        notifyDataSetChanged();
                                        alertDialog.dismiss();

                                        Snackbar snackbar = Snackbar.make(depenseView, "The expense was updated successfully", Snackbar.LENGTH_LONG);
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

                                Log.d("ta5", "menu1");
                                break;
                            case R.id.delete_item_options_menu:
                                removeFromList(viewHolder.getAdapterPosition(), viewHolder, context);
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

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View mView = inflater.inflate(R.layout.depense_info_popup, null);

                ImageButton dismissButton = mView.findViewById(R.id.dismiss_depense);

                TextView date = mView.findViewById(R.id.date_popup);
                TextView heur = mView.findViewById(R.id.heur_popup);
                TextView montant = mView.findViewById(R.id.montant_popup);
                TextView projet = mView.findViewById(R.id.projet_popup);
                TextView societe = mView.findViewById(R.id.societe_popup);
                TextView typeDepense = mView.findViewById(R.id.type_depense_popup);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String dateString = dateFormat.format(mdepenses.get(viewHolder.getAdapterPosition()).getDate());

                date.setText(dateString);
                heur.setText(mdepenses.get(viewHolder.getAdapterPosition()).getHeur());
                montant.setText(String.format("%s", mdepenses.get(viewHolder.getAdapterPosition()).getMontant()));


                Projet p = mdepenses.get(viewHolder.getAdapterPosition()).getProjet();
                Societe s = mdepenses.get(viewHolder.getAdapterPosition()).getSociete();
                DepenseType dt = mdepenses.get(viewHolder.getAdapterPosition()).getDepenseType();

                ProjetService projetService = new ProjetService(context);
                SocieteService societeService = new SocieteService(context);
                DepenseTypeService depenseTypeService = new DepenseTypeService(context);


                Projet pro = projetService.find(p.getId());
                Societe soc = societeService.find(s.getId());
                DepenseType depType = depenseTypeService.find(dt.getId());

                if (depType == null) {
                    typeDepense.setText("------");
                } else {
                    typeDepense.setText(depType.getNom());
                }
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
    public void onBindViewHolder(DepenseAdapter.ViewHolder viewHolder, int position) {
        Depense depense = mdepenses.get(position);
        TextView textView = viewHolder.montant;
        TextView textView2 = viewHolder.date;
        TextView textView3 = viewHolder.heur;
        TextView textView4 = viewHolder.typeDepense;
        //date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String dateString = dateFormat.format(depense.getDate());

        textView3.setText(depense.getHeur());
        textView2.setText(dateString);
        textView.setText(depense.getMontant() + "  DHs");

        Context context = (Context) Session.getAttribut("contextForDepenseType");
        DepenseTypeService depenseTypeService = new DepenseTypeService(context);
        DepenseType dt = depense.getDepenseType();
        DepenseType depType = depenseTypeService.find(dt.getId());

        if (depType == null) {
            textView4.setText("");
        } else {
            textView4.setText(depType.getNom());
        }
        Session.delete("contextForDepenseType");
    }


    private void removeFromList(int position, ViewHolder viewHolder, Context context) {
        Depense depense = mdepenses.get(viewHolder.getAdapterPosition());
        DepenseService dService = new DepenseService(context);
        dService.remove(depense);
        Log.d("tag", "societe has been removed");
        mdepenses.remove(position);
        notifyItemRemoved(viewHolder.getAdapterPosition());
        notifyItemRangeChanged(viewHolder.getAdapterPosition(), mdepenses.size());

    }

    public void setfilter(List<Depense> filteredDepenses) {
        mdepenses = new ArrayList<>();
        mdepenses.addAll(filteredDepenses);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mdepenses.size();
    }


}
