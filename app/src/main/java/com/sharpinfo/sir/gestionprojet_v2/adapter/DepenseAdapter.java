package com.sharpinfo.sir.gestionprojet_v2.adapter;


import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.sharpinfo.sir.gestionprojet_v2.R;
import com.sharpinfo.sir.gestionprojet_v2.action.depense.DepenseListActivity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import bean.Depense;
import bean.Projet;
import bean.Societe;
import service.ProjetService;

public class DepenseAdapter extends RecyclerView.Adapter<DepenseAdapter.ViewHolder> {

    private List<Depense> mdepenses;
    DepenseListActivity depenseListActivity = new DepenseListActivity();



    public DepenseAdapter(List<Depense> depenses) {
        mdepenses = depenses;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView montant;
        public TextView date;
        public TextView seeMore;
        public ConstraintLayout depenseitem;


        public ViewHolder(View itemView) {
            super(itemView);

            depenseitem = (ConstraintLayout) itemView.findViewById(R.id.depense_item);
            seeMore = (TextView) itemView.findViewById(R.id.depense_seeMore);
            montant = (TextView) itemView.findViewById(R.id.depense_montant);
            date = (TextView) itemView.findViewById(R.id.depense_date);

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);

        //inflate custom layout
        View depenseView = inflater.inflate(R.layout.item_depense_list, parent, false);

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
//                depenseListActivity.showPopup();
                PopupWindow popupWindow = new PopupWindow();

                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = inflater.inflate(R.layout.depense_info_popup, null);

                popupWindow = new PopupWindow(
                        customView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

                if (Build.VERSION.SDK_INT >= 21) {
                    popupWindow.setElevation(5.0f);
                }

                ImageButton dismissButton = customView.findViewById(R.id.dismiss_depense);

                // Set a click listener for the popup window close button
                final PopupWindow finalPopupWindow = popupWindow;
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        finalPopupWindow.dismiss();
                    }
                });

                TextView date = customView.findViewById(R.id.date_popup);
                TextView heur = customView.findViewById(R.id.heur_popup);
                TextView montant = customView.findViewById(R.id.montant_popup);
                TextView projet = customView.findViewById(R.id.projet_popup);
                TextView societe = customView.findViewById(R.id.societe_popup);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String dateString = dateFormat.format(mdepenses.get(viewHolder.getAdapterPosition()).getDate());

                date.setText(dateString);
                heur.setText("--:--");
                montant.setText(mdepenses.get(viewHolder.getAdapterPosition()).getMontant()+"");
//                Projet p = mdepenses.get(viewHolder.getAdapterPosition()).getProjet();


                ProjetService projetService = new ProjetService(context);
                Projet p = projetService.findByDepense(mdepenses.get(viewHolder.getAdapterPosition()));
                Log.d("tag","Projet ====================== "+p);

                Societe s = mdepenses.get(viewHolder.getAdapterPosition()).getSociete();

                Log.d("tag","Societe ====================== "+s);
                String projectName = mdepenses.get(viewHolder.getAdapterPosition()).getProjet().getNom();
                String companyName = mdepenses.get(viewHolder.getAdapterPosition()).getSociete().getRaisonSociale();
                if(projectName == null){
                    projet.setText("--------");
                }else{
                    projet.setText(projectName);
                }
                if(companyName == null){
                    societe.setText("--------");
                }else{
                    societe.setText(companyName);
                }

                popupWindow.showAtLocation(viewHolder.depenseitem, Gravity.CENTER, 0, 0);

                Toast.makeText(context, "test  depense " + String.valueOf(viewHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                Log.d("tad", "" + mdepenses.get(viewHolder.getAdapterPosition()).getMontant());
            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DepenseAdapter.ViewHolder viewHolder, int position) {
        Depense depense = mdepenses.get(position);
        TextView textView = viewHolder.montant;
        TextView textView2 = viewHolder.date;

        //date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm", Locale.getDefault());
        String dateString = dateFormat.format(depense.getDate());


        textView2.setText(dateString);
        textView.setText(depense.getMontant() + "  DHs");

    }

    @Override
    public int getItemCount() {
        return mdepenses.size();
    }


}
