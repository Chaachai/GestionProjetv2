package com.sharpinfo.sir.gestionprojet_v2.helper;

import android.content.Context;
import android.content.Intent;

/**
 * Created by moulaYounes on 21/08/2015.
 */
public class Dispacher {

    public static void forward(Context context, Class distination) {
        Intent intent = new Intent(context,distination);
        context.startActivity(intent);
    }




}
