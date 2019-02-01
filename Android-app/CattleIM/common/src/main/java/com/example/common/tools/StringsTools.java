package com.example.common.tools;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class StringsTools {

    public static String ImageToStrings(String path) {
        if (path == null) {
            return "";
        } else {
            try {
                FileInputStream inputStream = new FileInputStream(path);
                byte[] data = new byte[inputStream.available()];
                inputStream.read(data);
                inputStream.close();
                // 对字节数组Base64编码
                return Base64.encodeToString(data, Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String ListToString(List<String> list){
        StringBuilder str = null;
        for (int i = 0; i < list.size(); i++){
            if (i == 0){
                str = new StringBuilder(list.get(i));
            }else {
                str.append(",").append(list.get(i));
            }
        }

        return str.toString();
    }

}
