package com.example.common.tools;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class StringsTools {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String ImageToStrings(String path) {
        if (Objects.isNull(path)) {
            return "";
        } else {
            try {
                FileInputStream inputStream = new FileInputStream(path);
                byte[] data = new byte[inputStream.available()];
                inputStream.read(data);
                inputStream.close();
                return new String(data).trim();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
