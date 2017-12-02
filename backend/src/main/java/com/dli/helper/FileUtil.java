package com.dli.helper;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtil {


    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

   public static boolean renameFile(String file, String toFile) {

        File toBeRenamed = new File(file);
        //检查要重命名的文件是否存在，是否是文件
        if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {
            return false;
        }

        File newFile = new File(toFile);

        //修改文件名
     return   toBeRenamed.renameTo(newFile);

    }

    public  static  boolean moveFile( String scrfile, String targetpath ){


        File file = new File(scrfile);


        File dir = new File( targetpath);

        if(!dir.exists()){
            dir.mkdirs();
        }
        // Move file to new directory
        boolean sucess=   file.renameTo(new File(dir, file.getName()));

        return     sucess;
    }
}



