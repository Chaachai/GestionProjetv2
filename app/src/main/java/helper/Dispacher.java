package helper;

import android.content.Context;
import android.content.Intent;

/**
 * Created by moulaYounes on 21/08/2015.
 */
public class Dispacher {

    public static void forward(Context context, Class destination) {
        Intent intent = new Intent(context,destination);
        context.startActivity(intent);
    }




}
