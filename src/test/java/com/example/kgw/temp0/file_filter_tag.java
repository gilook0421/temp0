package com.example.kgw.temp0;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class file_filter_tag {
    
    static String BASE_DIR = "E:\\Temp\\";
    static int totCnt = 0;
    static List<Map<String, String>> replaceList = new ArrayList<Map<String, String>>();
    static String printType = "B";

    public static void main(String[] args) throws IOException {
        System.out.println("프로그램을 시작합니다.");
        System.out.println("프로그램을 시작합니다.");
        System.out.println("프로그램을 시작합니다.");
        System.out.println("@@@@@@@@@@@@@@@@@@@@");
        System.out.println("@@@@@@@@@@@@@@@@@@@@ START");
        System.out.println("@@@@@@@@@@@@@@@@@@@@ 필터의 존재여부를 알려주는 프로그램입니다.");
        System.out.println("@@@@@@@@@@@@@@@@@@@@");
        System.out.println("");
        System.out.println("");

        // 변환규칙가져오기
        replaceList = getFilterInfo();
        // --변환규칙가져오기

        // 파일목록 가져오기
        System.out.println("");
        System.out.println("@@@@@@@@@@@@@@@@@@@@ 파일목록 보기");
        getListFiles(BASE_DIR);
        System.out.println("");
        System.out.println("@@@@@@@@@@@@@@@@@@@@ //파일목록 보기");
        // --파일목록 가져오기

        System.out.println("");
        System.out.println("");
        System.out.println("@@@@@@@@@@@@@@@@@@@@");
        System.out.println("@@@@@@@@@@@@@@@@@@@@ END");
        System.out.println("@@@@@@@@@@@@@@@@@@@@");
        System.out.println("프로그램을 종료되었습니다.");
        System.out.println("프로그램을 종료되었습니다.");
        System.out.println("프로그램을 종료되었습니다.");
    }

    private static void exeReplace(List<Map<String, String>> replaceList, String fileNm) throws IOException{

        File file = new File(fileNm);
        if(!file.getName().endsWith("asp")){
            return;
        }

        String[] args = new String[3];
        args[0] = "";
        args[1] = "";
        args[2] = file.toString();

        for(int k = 0 ; k < replaceList.size(); k++){
            Map<String, String> row = replaceList.get(k);
            args[0] = row.get("filter");
            toFilter(args);
        }

    }

    // 파일목록 가져오기
    private static void getListFiles(String strDirPath) throws IOException{
        File dir = new File(strDirPath);

        File[] fList = dir.listFiles();
        for(int i = 0 ; i < fList.length ; i++){
            if(fList[i].isFile() && fList[i].getPath().endsWith("asp")){
                if(printType.equals("A")){
                    System.out.println("");
                    System.out.println("["+totCnt+"]" + fList[i].getPath());
                    totCnt++;
                }

                exeReplace(replaceList, fList[i].getPath());
            }
            else if(fList[i].isDirectory()){
                getListFiles(fList[i].getPath());
            }
        }
    }


    private static void toFilter(String[] args) throws IOException{
        BufferedReader br = null;
        PrintWriter pw = null;

        String line, content;
        String strFilter = args[0];

        StringBuilder sb = new StringBuilder();
        File f = null;
        for(int i=2 ; i<args.length; i++){
            try{
                File file = new File(args[i]);
                br = new BufferedReader(new FileReader(file));

                while((line = br.readLine()) != null){
                    sb.append(line + "\r\n");
                }

                content = new String(sb);

                if(content.toUpperCase().indexOf(strFilter.toUpperCase()) != -1){
                    if(printType.equals("A")){
                        System.out.println("");
                    }
                }
                else{
                    if(printType.equals("A")){
                        System.out.print("[[대상필터 : " + strFilter + "]]");
                        System.out.print("=====>>> X.");
                    }
                    if(printType.equals("B")){
                        System.out.print("\n@ 파일정보 : " + args[2]);
                        System.out.print("[[대상필터 : " + strFilter + "]]");
                        System.out.print("=====>>> X.");
                    }
                }
                sb.delete(0, sb.length());

                if(i==2){
                    f = new File(file.getParent());
                    f.mkdir();
                }


            }
            catch(Exception e){
                e.printStackTrace();
            }
            finally{
                if(br != null) br.close();
                if(pw != null) pw.close();
            }
        }
    }

    private static List<Map<String, String>> getFilterInfo(){
        List<Map<String, String>> replaceList = new ArrayList<Map<String, String>>();

        System.out.println("===== 필터 대상 존재여부 =====");

        Map<String, String> replaceMap = new HashMap<String, String>();
        replaceMap.put("filter", "/Edge_Chrome/com/inc/test.inc");
        System.out.println("@replaceList : " + replaceMap.toString());
        replaceList.add(replaceMap);
        
        System.out.println("===== //필터 대상 존재여부 =====");

        return replaceList;
    }

    
}

