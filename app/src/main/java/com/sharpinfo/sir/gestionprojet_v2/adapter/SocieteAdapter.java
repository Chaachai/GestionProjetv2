package com.sharpinfo.sir.gestionprojet_v2.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
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
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.sharpinfo.sir.gestionprojet_v2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bean.Societe;
import service.ProjetService;
import service.SocieteService;

public class SocieteAdapter extends RecyclerView.Adapter<SocieteAdapter.ViewHolder> {

    private List<Societe> msocietes;


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
        final View societeView = inflater.inflate(R.layout.item_societe_list, parent, false);

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

                                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                final View mView = inflater.inflate(R.layout.societe_edit_popup, null);

                                ImageButton dismissButton = mView.findViewById(R.id.dismiss_edit_societe);

                                final EditText dateFondation = mView.findViewById(R.id.date_fondation_edit);
                                final EditText raisonSociale = mView.findViewById(R.id.raison_sociale_edit);
                                Button button = mView.findViewById(R.id.button_edit_societe);

                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                String dateString = dateFormat.format(msocietes.get(viewHolder.getAdapterPosition()).getDateFondation());

                                dateFondation.setText(dateString);
                                raisonSociale.setText(msocietes.get(viewHolder.getAdapterPosition()).getRaisonSociale());

                                builder.setView(mView);
                                final AlertDialog alertDialog = builder.create();
                                final SocieteService societeService = new SocieteService(context);

                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Societe societe = msocietes.get(viewHolder.getAdapterPosition());

                                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                        try {
                                            Date dateEdited = format.parse(dateFondation.getText() + "");
                                            societe.setDateFondation(dateEdited);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                        societe.setRaisonSociale(raisonSociale.getText() + "");
                                        societeService.edit(societe);

                                        notifyDataSetChanged();
                                        alertDialog.dismiss();

                                        Snackbar snackbar = Snackbar.make(societeView, "The company was updated successfully", Snackbar.LENGTH_LONG);
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
//
                                break;
                            case R.id.delete_item_options_menu:
                                Log.d("ta5", "me " + msocietes.get(viewHolder.getAdapterPosition()).getId());
                                Log.d("ta5", "me " + msocietes.get(viewHolder.getAdapterPosition()).getRaisonSociale());
//                                societeService.removeSociete(msocietes.get(viewHolder.getAdapterPosition()));
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
