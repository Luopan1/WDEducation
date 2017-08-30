package com.test.com.utills;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by LuoPan on 2017/7/27.
 */

public class HintUtils {
    private static Toast toast;

    public static void showToast(Context context,
                                 String content) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
