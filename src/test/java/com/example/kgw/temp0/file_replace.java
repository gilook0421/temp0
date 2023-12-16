package com.example.kgw.temp0;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class file_replace {
    
    static String BASE_DIR = "E:\\Temp\\";

    public static void main(String[] args) {
        System.out.println("스크립트 변환을 시작합니다.");
        System.out.println("1차 : 변환규칙 반영.");
        System.out.println("2차 : 변환규칙에 의해 중복생성된 경우 제거]n");

        args = new String[3];
        args[0] = "";
        args[1] = "";
        args[2] = "";

        // 1차 변환규칙가져오기
        List<Map<String, String>> replaceList = new ArrayList<Map<String, String>>();
        replaceList = getReplaceInfo();
        exeReplace(args, replaceList);

        // 2차 중복제거규칙가져오기
        replaceList = getReplaceDupInfo();
        exeReplace(args, replaceList);
    }

    private static void exeReplace(String[] args, List<Map<String, String>> replaceList){

        // 파일목록가져오기
        File files[] = null;
        try{
            files = getFileList();
        }
        catch (Exception e1){
            e1.printStackTrace();
        }

        if(files != null && files.length > 0){
            System.out.println("==========");
            System.out.println("대상파일 건수 : " + files.length);
            System.out.println("==========");

            // 치환하기
            for(File file : files){
                args[2] = file.toString();

                for(int k=0; k<replaceList.size() ; k++){
                    Map<String, String> row = replaceList.get(k);
                    args[0] = row.get("target");
                    args[1] = row.get("replace");
                    String str_exeyn = row.get("exeyn");

                    if(str_exeyn == "true"){
                        toReplace(args);
                    }// end of if
                }// end of for
            }// end of for
        }
        else{
            System.out.println("대상파일이 존재하지 않습니다.");
        }
    }

    private static File[] getFileList(){
        File dir = new File(BASE_DIR);
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File pathname){
                return pathname.getName().endsWith("asp");
            }
        };

        File files[] = dir.listFiles(filter);
        System.out.println("==========");
        int cnt = 0;
        for(File file : files){
            System.out.println("[" + cnt + "]files : " + file);
            cnt++;
        }

        return files;
    }

    private static void toReplace(String[] args){
        BufferedReader br = null;
        PrintWriter pw = null;

        String line, content;
        String regex = args[0];
        String replace = args[1];

        StringBuffer sb = new StringBuffer();
        File f = null;
        for(int i=2; i<args.length;i++){
            try{
                File file = new File(args[i]);
                br = new BufferedReader(new FileReader(file));

                while((line = br.readLine()) != null){
                    sb.append(line + "\r\n");
                }

                content = new String(sb);
                content = content.replaceAll(regex, replace);
                sb.delete(0, sb.length());

                if(i==2){
                    f = new File(file.getParent());
                    f.mkdir();
                }

                pw = new PrintWriter(f.getPath() + File.separator + file.getName());
                pw.write(content);
            }
            catch(Exception e){
                e.printStackTrace();
            }
            finally{
                try {
                    br.close();
                    pw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }// end of for
    }


    private static List<Map<String, String>> getReplaceInfo(){

        List<Map<String, String>> replaceList = new ArrayList<Map<String, String>>();

        System.out.println("\n@@@@@@@@@@ 1차 대상 규칙");
        Map<String, String> replaceMap0 = new HashMap<String, String>();
        replaceMap0.put("targe", "<HTML>");
        replaceMap0.put("replace", "<!DOCTYPE html>\r\n<HTML>");
        replaceMap0.put("exeyn", "true");
        System.out.println("@replaceList : " + replaceMap0.toString());
        replaceList.add(replaceMap0);

        return replaceList;
    }

    private static List<Map<String, String>> getReplaceDupInfo(){

        List<Map<String, String>> replaceDupList = new ArrayList<Map<String, String>>();

        System.out.println("\n@@@@@@@@@@ 2차 대상 규칙");
        Map<String, String> replaceMap0 = new HashMap<String, String>();
        replaceMap0.put("targe", "<!DOCTYPE html>\r\n<!DOCTYPE html>");
        replaceMap0.put("replace", "<!DOCTYPE html>");
        replaceMap0.put("exeyn", "true");
        System.out.println("@replaceDupList : " + replaceMap0.toString());
        replaceDupList.add(replaceMap0);

        return replaceDupList;

    }





}
