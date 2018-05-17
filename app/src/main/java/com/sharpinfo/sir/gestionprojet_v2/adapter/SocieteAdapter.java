package com.sharpinfo.sir.gestionprojet_v2.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.sharpinfo.sir.gestionprojet_v2.R;
import com.sharpinfo.sir.gestionprojet_v2.action.depense.DepenseListActivity;
import com.sharpinfo.sir.gestionprojet_v2.action.projet.ProjetListActivity;
import com.sharpinfo.sir.gestionprojet_v2.action.tache.TacheListActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import bean.Depense;
import bean.Manager;
import bean.Societe;
import helper.Dispacher;
import helper.Session;
import service.ManagerService;
import service.ProjetService;
import service.SocieteService;

public class SocieteAdapter extends RecyclerView.Adapter<SocieteAdapter.ViewHolder> {

    private List<Societe> msocietes;
    private AlertDialog alertDialog;

    public SocieteAdapter(List<Societe> societes) {
        msocietes = societes;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView seeMore;
        public LinearLayout societeitem;



        public ViewHolder(View itemView) {
            super(itemView);

            societeitem = (LinearLayout) itemView.findViewById(R.id.societe_item);
            seeMore = (TextView) itemView.findViewById(R.id.societe_seeMore);
            nameTextView = (TextView) itemView.findViewById(R.id.societe_raisonSociale);

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);

        final SocieteService societeService = new SocieteService(context);
        final ProjetService projetService = new ProjetService(context);

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
                                Log.d("ta5", "me " + msocietes.get(viewHolder.getAdapterPosition()).getId());
                                Log.d("ta5", "me " + msocietes.get(viewHolder.getAdapterPosition()).getRaisonSociale());
                                societeService.removeSociete(msocietes.get(viewHolder.getAdapterPosition()));
//                                societeService.deleteSociete(msocietes.get(viewHolder.getAdapterPosition()));
//                                societeService.removeSociete(msocietes.get(viewHolder.getAdapterPosition()));
//                                societeService.remove(msocietes.get(viewHolder.getAdapterPosition()).getId());
//                                societeService.remove(msocietes.get(viewHolder.getAdapterPosition()).getId());
//                                societeService.removeSociete(msocietes.get(viewHolder.getAdapterPosition()));
//                                projetService.deleteBySociete(msocietes.get(viewHolder.getAdapterPosition()));
//                                //remove depense w tache
//                                removeFromList(viewHolder.getAdapterPosition(), viewHolder);
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

        viewHolder.societeitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on click the whole line
                Toast.makeText(context, "test  societe " + msocietes.get(viewHolder.getAdapterPosition()).getRaisonSociale(), Toast.LENGTH_SHORT).show();
                Log.d("tad", "" + msocietes.get(viewHolder.getAdapterPosition()).getRaisonSociale());

                ///Create AlertDIalog with custom buttonss
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View mView = inflater.inflate(R.layout.societe_info_popup, null);
                ImageButton dismissButton = mView.findViewById(R.id.dismiss_societe_popup);

                TextView nomSociete = mView.findViewById(R.id.nom_societe_update);
                TextView dateFondationSociete = mView.findViewById(R.id.date_societe_popup);
                TextView nomManagerSociete = mView.findViewById(R.id.nom_manager_societe_popup);
                Button depenseSocieteBtn = mView.findViewById(R.id.depenses_societe_popup);
                Button tacheSocieteBtn = mView.findViewById(R.id.taches_societe_popup);
                Button projetSocieteBtn = mView.findViewById(R.id.projets_societe_popup);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String dateString = dateFormat.format(msocietes.get(viewHolder.getAdapterPosition()).getDateFondation());

                dateFondationSociete.setText(dateString);
                nomSociete.setText(msocietes.get(viewHolder.getAdapterPosition()).getRaisonSociale());

                Manager m = msocietes.get(viewHolder.getAdapterPosition()).getManager();
                ManagerService managerService = new ManagerService(context);
                Manager manager = managerService.find(m.getId());
                if (manager == null) {
                    nomManagerSociete.setText("Manager not affected");
                } else {
                    nomManagerSociete.setText(manager.getNom()+" "+manager.getPrenom());
//                    nomManagerSociete.setText(manager.toString());
                }

//                if (msocietes.get(viewHolder.getAdapterPosition()).getManager() == null) {
//                    nomManagerSociete.setText("Manager was not affected");
//                } else {
//                    nomManagerSociete.setText(msocietes.get(viewHolder.getAdapterPosition()).getManager().getNom() + " " + msocietes.get(viewHolder.getAdapterPosition()).getManager().getPrenom());
//                }

                depenseSocieteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Session.setAttribute(msocietes.get(viewHolder.getAdapterPosition()), "societeRecherce");
                        Dispacher.forward(context, DepenseListActivity.class);
                        alertDialog.dismiss();
                    }
                });

                tacheSocieteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Session.setAttribute(msocietes.get(viewHolder.getAdapterPosition()), "societeRecherce");
                        Dispacher.forward(context, TacheListActivity.class);
                        alertDialog.dismiss();
                    }
                });

                projetSocieteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("tag", msocietes.get(viewHolder.getAdapterPosition()).getId() + "");
//                        Intent intent = new Intent(context, ProjetListActivity.class);
//                        intent.putExtra("societeId",msocietes.get(viewHolder.getAdapterPosition()).getId());
//                        context.startActivity(intent);

                        Session.setAttribute(msocietes.get(viewHolder.getAdapterPosition()), "societeRecherce");
                        Dispacher.forward(context, ProjetListActivity.class);
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
    public void onBindViewHolder(SocieteAdapter.ViewHolder viewHolder, int position) {
        Societe societe = msocietes.get(position);
        Log.d("Societe", "ha smya dyal societe " + societe.getRaisonSociale() + " ha la date :" + societe.getDateFondation() + " !");
        TextView textView = viewHolder.nameTextView;

        //date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String dateString = dateFormat.format(societe.getDateFondation());


        textView.setText(societe.getRaisonSociale() + ";" + dateString);


    }

    public void removeFromList(int position, ViewHolder viewHolder) {
        Log.d("tag", "societe has been removed");
        msocietes.remove(position);
        notifyItemRemoved(viewHolder.getAdapterPosition());
        notifyItemRangeChanged(viewHolder.getAdapterPosition(), msocietes.size());

    }

    public void setfilter(List<Societe> filteredSocietes) {
        msocietes = new ArrayList<>();
        msocietes.addAll(filteredSocietes);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return msocietes.size();
    }


}
