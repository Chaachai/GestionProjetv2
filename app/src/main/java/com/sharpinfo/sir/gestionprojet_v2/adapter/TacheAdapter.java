package com.sharpinfo.sir.gestionprojet_v2.adapter;


import android.app.AlertDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.sharpinfo.sir.gestionprojet_v2.R;
import com.sharpinfo.sir.gestionprojet_v2.action.tache.TacheListActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bean.Projet;
import bean.Societe;
import bean.Tache;
import service.ProjetService;
import service.SocieteService;
import service.TacheService;

public class TacheAdapter extends RecyclerView.Adapter<TacheAdapter.ViewHolder> {

    private List<Tache> mtaches;
    TacheListActivity tacheListActivity = new TacheListActivity();
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private int currentHeure;
    private  int currentMinute;
    private int heureDebut, minuteDebut, heureFin, minuteFin;

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

            tacheitem = itemView.findViewById(R.id.tache_item);
            seeMore = itemView.findViewById(R.id.tache_seeMore);
            nbrHeur = itemView.findViewById(R.id.tache_nbrheur);
            date = itemView.findViewById(R.id.tache_date);
            heur = itemView.findViewById(R.id.tache_heur);

        }
    }

    private Integer calculateNbrOfMinutes() {
        heureFin = heureFin * 60;
        Integer totalHeureFin = heureFin + minuteFin;
        Log.d("tacheCreate", totalHeureFin.toString());
        heureDebut = heureDebut * 60;
        Integer totalHeureDebut = heureDebut + minuteDebut;
        Log.d("tacheCreate", totalHeureDebut.toString());

        Integer nbrMinute = totalHeureFin - totalHeureDebut;
        if (nbrMinute == 0) {
            return -2;
        }
        if (nbrMinute > 0) {
            return nbrMinute;
        }

        return -1;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext();

        final LayoutInflater inflater = LayoutInflater.from(context);

        //inflate custom layout
//        final View tacheView = inflater.inflate(R.layout.item_tache_list, parent, false);
        final View tacheView = inflater.inflate(R.layout.item_tache_list, null);

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
                                final EditText heureDebutEditText = mView.findViewById(R.id.heur_debut_edit_tache);
                                final EditText heureFinEditText = mView.findViewById(R.id.heur_fin_edit_tache);
                                final EditText commentaire = mView.findViewById(R.id.commentaire_edit_tache);
                                Button button = mView.findViewById(R.id.button_edit_tache);

                                //popup timePickerDialog for heurDebutEditText
                                heureDebutEditText.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        calendar = Calendar.getInstance();
                                        currentHeure = calendar.get(Calendar.HOUR_OF_DAY);
                                        currentMinute = calendar.get(Calendar.MINUTE);

                                        timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                                heureDebutEditText.setText(String.format("%02d:%02d", hourOfDay, minute));
                                                heureDebut = hourOfDay;
                                                minuteDebut = minute;
                                                Log.d("TacheAdapterdebut", heureDebut + " :" + minuteDebut);
                                            }
                                        }, currentHeure, currentMinute, true);

                                        timePickerDialog.show();
                                    }
                                });
                                //////////////
                                //popup timePickerDialog for heurFinEditText
                                heureFinEditText.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        calendar = Calendar.getInstance();
                                        currentHeure = calendar.get(Calendar.HOUR_OF_DAY);
                                        currentMinute = calendar.get(Calendar.MINUTE);

                                        timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                                heureFinEditText.setText(String.format("%02d:%02d", hourOfDay, minute));
                                                heureFin = hourOfDay;
                                                minuteFin = minute;
                                                Log.d("TacheAdapterfin", heureFin + " :" + minuteFin);
                                            }
                                        }, currentHeure, currentMinute, true);

                                        timePickerDialog.show();
                                    }
                                });
                                ///////////

                                //date
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                String dateString = dateFormat.format(mtaches.get(viewHolder.getAdapterPosition()).getDate());

                                date.setText(dateString);

///

                                //formatting nbr of minutes to display in hour:minutes
//                                int nbrOfMinutes = mtaches.get(viewHolder.getAdapterPosition()).getNbrHeures();
//                                int heure = nbrOfMinutes / 60;
//                                int minute = nbrOfMinutes % 60;
                                //


                                heureFinEditText.setText(mtaches.get(viewHolder.getAdapterPosition()).getHeureDebut());
                                heureDebutEditText.setText(mtaches.get(viewHolder.getAdapterPosition()).getHeureDebut());
                                commentaire.setText(mtaches.get(viewHolder.getAdapterPosition()).getCommentaire());

                                builder.setView(mView);
                                final AlertDialog alertDialog = builder.create();
                                final TacheService tacheService = new TacheService(context);

                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        int totalminutes = calculateNbrOfMinutes();
                                        if (totalminutes == -2) {
                                            Toast.makeText(context, "Veuillez choisir une heure debut et une heure fin",
                                                    Toast.LENGTH_LONG).show();
                                        } else if (totalminutes == -1) {
                                            Toast.makeText(context, "L'heure debut est superieur a l'heure fin",
                                                    Toast.LENGTH_LONG).show();
                                        } else {
                                            Tache tache = mtaches.get(viewHolder.getAdapterPosition());

                                            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                            try {
                                                Date dateEdited = format.parse(date.getText() + "");
                                                tache.setDate(dateEdited);
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            tache.setNbrHeures(totalminutes);
                                            tache.setHeureDebut(String.valueOf(heureDebutEditText.getText()));
                                            tache.setHeureFin(String.valueOf(heureFinEditText.getText()));
                                            tache.setCommentaire(commentaire.getText() + "");
                                            tacheService.edit(tache);

                                            Log.d("tag", "************** " + date.getText());
                                            Log.d("tag", "************** " + heureDebutEditText.getText());
                                            Log.d("tag", "************** " + heureFinEditText.getText());
                                            Log.d("tag", "************** " + commentaire.getText());

                                            notifyDataSetChanged();
                                            alertDialog.dismiss();

                                            Snackbar snackbar = Snackbar.make(tacheView, "The task was updated successfully", Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                        }
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
                                removeFromList(viewHolder.getAdapterPosition(), viewHolder, context);
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
                heur.setText(mtaches.get(viewHolder.getAdapterPosition()).getHeureDebut());

                int nbrOfMinutes = mtaches.get(viewHolder.getAdapterPosition()).getNbrHeures();
                int heure = nbrOfMinutes / 60;
                int minute = nbrOfMinutes % 60;
                nbrHeur.setText(String.format("%d:%d", heure, minute));

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

        textView3.setText(tache.getHeureDebut());
        textView2.setText(dateString);
        int nbrOfMinutes = tache.getNbrHeures();
        int heure = nbrOfMinutes / 60;
        int minute = nbrOfMinutes % 60;
        textView.setText(String.format("%d:%d", heure, minute));

    }

    private void removeFromList(int position, ViewHolder viewHolder, Context context) {
        Tache tache = mtaches.get(viewHolder.getAdapterPosition());
        TacheService tacheService = new TacheService(context);
        tacheService.remove(tache);
        Log.d("tag", "societe has been removed");
        mtaches.remove(position);
        notifyItemRemoved(viewHolder.getAdapterPosition());
        notifyItemRangeChanged(viewHolder.getAdapterPosition(), mtaches.size());

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
