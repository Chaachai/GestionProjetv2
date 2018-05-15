package com.sharpinfo.sir.gestionprojet_v2.action.depense;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sharpinfo.sir.gestionprojet_v2.R;
import com.sharpinfo.sir.gestionprojet_v2.adapter.DepenseAdapter;

import java.util.List;

import bean.Depense;
import helper.Dispacher;
import service.DepenseService;

import android.view.ViewGroup.LayoutParams;

public class DepenseListActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;

//    private RelativeLayout mRelativeLayout;
//    private ConstraintLayout mButton;

    private PopupWindow mPopupWindow;

    DepenseService depenseService = new DepenseService(this);
    private RecyclerView depenseRecyclerView;

    private void injecterGUI() {
        depenseRecyclerView = (RecyclerView) findViewById(R.id.depenseRecyclerView);
    }

    private void initAdapter() {
        List<Depense> depenses = depenseService.findAll();

        DepenseAdapter depenseAdapter = new DepenseAdapter(depenses);

        depenseRecyclerView.setAdapter(depenseAdapter);
        depenseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }



//    public void test() {
//        // Get the application context
//        mContext = getApplicationContext();
//
//        // Get the activity
//        mActivity = DepenseListActivity.this;
//
//        // Get the widgets reference from XML layout
//        mRelativeLayout = findViewById(R.id.rl_custom_layout);
//        mButton = findViewById(R.id.depense_item);
//
//        // Set a click listener for the text view
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Initialize a new instance of LayoutInflater service
//                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
//
//                // Inflate the custom layout/view
//                View customView = inflater.inflate(R.layout.custom_layout, null);
//
//                /*
//                    public PopupWindow (View contentView, int width, int height)
//                        Create a new non focusable popup window which can display the contentView.
//                        The dimension of the window must be passed to this constructor.
//
//                        The popup does not provide any background. This should be handled by
//                        the content view.
//
//                    Parameters
//                        contentView : the popup's content
//                        width : the popup's width
//                        height : the popup's height
//                */
//                // Initialize a new instance of popup window
//                mPopupWindow = new PopupWindow(
//                        customView,
//                        LayoutParams.WRAP_CONTENT,
//                        LayoutParams.WRAP_CONTENT
//                );
//
//                // Set an elevation value for popup window
//                // Call requires API level 21
//                if (Build.VERSION.SDK_INT >= 21) {
//                    mPopupWindow.setElevation(5.0f);
//                }
//
//                // Get a reference for the custom view close button
//                ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);
//
//                // Set a click listener for the popup window close button
//                closeButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        // Dismiss the popup window
//                        mPopupWindow.dismiss();
//                    }
//                });
//
//                /*
//                    public void showAtLocation (View parent, int gravity, int x, int y)
//                        Display the content view in a popup window at the specified location. If the
//                        popup window cannot fit on screen, it will be clipped.
//                        Learn WindowManager.LayoutParams for more information on how gravity and the x
//                        and y parameters are related. Specifying a gravity of NO_GRAVITY is similar
//                        to specifying Gravity.LEFT | Gravity.TOP.
//
//                    Parameters
//                        parent : a parent view to get the getWindowToken() token from
//                        gravity : the gravity which controls the placement of the popup window
//                        x : the popup's x location offset
//                        y : the popup's y location offset
//                */
//                // Finally, show the popup window at the center location of root relative layout
//                mPopupWindow.showAtLocation(mButton, Gravity.CENTER, 0, 0);
////                mPopupWindow.showAsDropDown();
//            }
//        });
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depense_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        //populate data in the recyclerView
        injecterGUI();
        initAdapter();
//        showPopup();
//        test();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dispacher.forward(DepenseListActivity.this, DepenseCreateActivity.class);
            }
        });
    }

    public void showPopup() {

        mContext = getApplicationContext();

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View mView = getLayoutInflater().inflate(R.layout.depense_info_popup, null);

        builder.setView(mView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}
