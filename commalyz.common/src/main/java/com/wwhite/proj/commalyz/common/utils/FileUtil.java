package com.wwhite.proj.commalyz.common.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by 092455790 on 2016-1-18.
 */
public class FileUtil {

    public static void listToFile(List<String> data, String filename){

        try {
            File f = new File(filename);
            FileOutputStream out = new FileOutputStream(f, false);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
            for( int i=0; i<data.size(); ++i )
            {
                bw.write(data.get(i));
                bw.newLine();;
            }
            bw.flush();
            bw.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void setToFile(Set<String> data, String filename){

        try {
            File f = new File(filename);
            FileOutputStream out = new FileOutputStream(f, false);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));

            for( String k : data ){
                bw.write(k);
                bw.newLine();
            }

            bw.flush();
            bw.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void stringToFile(String data, String filename){

        try {
            File f = new File(filename);
            FileOutputStream out = new FileOutputStream(f, false);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
            bw.write(data);
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void bytesToFile(byte [] data, String filename){
        try {
            File file = new File(filename);
            FileOutputStream fops = new FileOutputStream(file);
            fops.write(data);
            fops.flush();
            fops.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readStringFromFile(String filename, String encode) {
        try {
            File f = new File(filename);
            FileInputStream fis = new FileInputStream(f);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, encode));
            StringBuffer sb = new StringBuffer();
            String tempStr = null;
            while ((tempStr = br.readLine()) != null)
                sb.append(tempStr);
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<String> readStringListFromFile(String filename){
        List<String> resList = new ArrayList<String>();
        try {
            File f = new File(filename);
            FileInputStream fis = new FileInputStream(f);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            StringBuffer sb = new StringBuffer();
            String tempStr = null;
            while ((tempStr = br.readLine()) != null) {
                resList.add(tempStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resList;
    }

    public static Set<String> readStringSetFromFile(String filename){

        List<String> strList = FileUtil.readStringListFromFile(filename);
        Set<String> resSet = new HashSet<String>();

        for( int i=0; i<strList.size(); ++i )
            resSet.add(strList.get(i));

        return resSet;
    }

    public static Set<Integer> readIntegerSetFromFile(String filename){

        List<String> strList = FileUtil.readStringListFromFile(filename);
        Set<Integer> resSet = new HashSet<Integer>();

        for( int i=0; i<strList.size(); ++i ) {
            try {
                resSet.add(Integer.valueOf(strList.get(i)));
            }catch (Exception e){

            }
        }

        return resSet;
    }

    public static void toFile(Set<Integer> inputSet, String filename){

        try {
            File f = new File(filename);
            FileOutputStream out = new FileOutputStream(f, false);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));

            for( Integer d : inputSet )
            {
                try {
                    bw.write(d.toString());
                    bw.newLine();
                }catch (Exception e){
                    ;
                }
            }

            bw.flush();
            bw.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String [] getFilenamesFromDir(String dir){
        File rootDirFile = new File(dir);
        String [] filenames = rootDirFile.list();
        return filenames;
    }

    public static boolean createDir(String dirName){

        File file =new File(dirName);
        if  (!file .exists()  && !file .isDirectory())
        {
            //System.out.println(dirName + " [ not exists !]");
            file .mkdir();
            return true;
        } else
        {
            //System.out.println(dirName + "[ already exists !]");
        }

        return false;
    }


    public static void appendStringToFile(String dataline, String filename){

        try {
            File f = new File(filename);
            FileOutputStream out = new FileOutputStream(f, true);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));

            bw.write(dataline);
            bw.newLine();

            bw.flush();
            bw.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    
    public static void getAllFileName(String path, List<String> fileName)
    {
        File file = new File(path);
        File [] files = file.listFiles();
//        String [] names = file.list();
//        if(names != null){
//        	for( int i=0; i<names.length; ++i ){
//        		fileName.add(path+"\\"+names[i]);
//        	}
//        }
        for(File a:files)
        {
            if(a.isDirectory())
            {
                getAllFileName(a.getAbsolutePath(),fileName);
            }else{
            	fileName.add(a.getAbsolutePath());
            }
        }
    }
}


