package com.sinolife.mailbusiness.utils;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.apache.commons.io.FileUtils.openOutputStream;

/**
 * 文件处理类
 */
public class FileUtils {

    /**
     * 上传文件
     * @param file
     * @param filePath
     * @param fileName
     * @throws Exception
     */
    public static void uploadFile(byte[] file, String filePath, String fileName) throws IOException {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    public static long copyFile(File input, OutputStream output) throws IOException {
        FileInputStream fis = new FileInputStream(input);

        long var3;
        try {
            var3 = IOUtils.copyLarge(fis, output);
        } finally {
            fis.close();
        }

        return var3;
    }

    public static void copyInputStreamToFile(InputStream source, File destination) throws IOException {
        try {
            FileOutputStream output = openOutputStream(destination);

            try {
                IOUtils.copy(source, output);
                output.close();
            } finally {
                IOUtils.closeQuietly(output);
            }
        } finally {
            IOUtils.closeQuietly(source);
        }

    }

}
