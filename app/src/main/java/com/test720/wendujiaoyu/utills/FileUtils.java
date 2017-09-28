package com.test720.wendujiaoyu.utills;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Environment;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by LuoPan on 2017/8/17 21:07.
 */

public class FileUtils {

    private static File mSaveFile;

    public static void writeFile(String fileName, String writestr) throws IOException {
        try {
            String status = Environment.getExternalStorageState();
            if (status.equals(Environment.MEDIA_MOUNTED)) {

                File DatalDir = Environment.getExternalStorageDirectory();
                File myDir = new File(DatalDir, "/com.test720.www");
                myDir.mkdirs();
                String mDirectoryname = DatalDir.toString() + "/com.test720.www";
                mSaveFile = new File(mDirectoryname, fileName + ".txt");

                FileOutputStream fout = new FileOutputStream(mSaveFile.getAbsolutePath());
                byte[] bytes = writestr.getBytes();
                fout.write(bytes);
                fout.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //读数据
    public static String readFile(String fileName) {
        String res = "";
        try {
            String status = Environment.getExternalStorageState();
            if (status.equals(Environment.MEDIA_MOUNTED)) {

                FileInputStream fin = new FileInputStream(mSaveFile);
                int length = 0;
                try {
                    length = fin.available();
                    byte[] buffer = new byte[length];
                    fin.read(buffer);
                    res = EncodingUtils.getString(buffer, "UTF-8");
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (FileNotFoundException e) {
            return res;
        }
        return res;

    }


    public static void FlipAnimatorXViewShow(final View oldView, final View newView, final long time) {

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(oldView, "rotationX", 0, 90);
        final ObjectAnimator animator2 = ObjectAnimator.ofFloat(newView, "rotationX", -90, 0);
        animator2.setInterpolator(new OvershootInterpolator(2.0f));

        animator1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                oldView.setVisibility(View.GONE);
                animator2.setDuration(time).start();
                newView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator1.setDuration(time).start();
    }


}
