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

public class file_filter_script {
    
    static String BASE_DIR = "E:\\Temp\\";
    static int totCnt = 0;
    static List<Map<String, String>> replaceList = new ArrayList<Map<String, String>>();
    static String printType = "B";//A:전체파일 목록과 라인표시, B:라인이 존재하는 파일만 표시.
    static String upperType = "O";//O:대문자로 변경후 비교, X:대소문자 구분

    public static void main(String[] args) throws IOException {
        System.out.println("프로그램을 시작합니다.");
        System.out.println("프로그램을 시작합니다.");
        System.out.println("프로그램을 시작합니다.");
        System.out.println("@@@@@@@@@@@@@@@@@@@@");
        System.out.println("@@@@@@@@@@@@@@@@@@@@ START");
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

        String[] rsltStr = new String[replaceList.size()];
        for(int k = 0 ; k < replaceList.size(); k++){
            Map<String, String> row = replaceList.get(k);
            args[0] = row.get("filter");
            String rtn_val = toFilter(args);

            rsltStr[k] = rtn_val;
        }

        //결과도출
        toRslt(rsltStr, fileNm);

    }

    private static void toRslt(String[] rsltStr, String fileNm){
        //최대길이의 항목으로 배열크기 잡기
        int filterLen0 = 0;
        int filterLen1 = 0;
        int filterLen2 = 0;
        for(int i = 0 ; i < 3; i++){
            String[] rowArr = rsltStr[i].split(",");
            if(i==0) filterLen0 = rowArr.length;
            if(i==1) filterLen1 = rowArr.length;
            if(i==2) filterLen2 = rowArr.length;
        }

        //초기화
        String[] row0 = new String[filterLen0];
        String[] row1 = new String[filterLen1];
        String[] row2 = new String[filterLen2];
        
        for(int i = 0 ; i < rsltStr.length; i++){
            String rows = rsltStr[i];
            
            String[] rowArr = rsltStr[i].split(",");
            if(rowArr.length > 0){
                for(int ii = 0 ; ii < rowArr.length; ii++){
                    if(i==0){
                        row0[ii] = rowArr[ii];
                    }
                    else if(i==1){
                        row1[ii] = rowArr[ii];
                    }
                    else if(i==2){
                        row2[ii] = rowArr[ii];
                    }
                }
            }
            else{
                if(i==0){
                    row0[i] = rows;
                }
                else if(i==1){
                    row1[i] = rows;
                }
                //else if(1==2){
                else{
                    row2[i] = rows;
                }
            }// end of if

        }// end of for

        //결과보기
        for(int i = 0 ; i < row2.length ; i++){
            if(!row2[i].equals("0")){
                for(int k = 0 ; k < row0.length ; k++){
                    String comA = String.valueOf(row0[k]);
                    String comB = String.valueOf(row1[k]);
                    if(!comA.equals(comB)){
                        int intZ = Integer.parseInt(row2[i]);
                        int intA = Integer.parseInt(row0[k]);
                        int intB = Integer.parseInt(row1[k]);
                        if(intZ >= intA && intZ <= intB ){
                            if(printType.equals("B")){
                                System.out.println("필터 존재하는 파일 : " + fileNm);
                            }
                            System.out.println("=====>>> 라인 : " + row2[i]);
                        }
                    }
                }//end of for
            }//end of if
        }//end of for
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
                totCnt++;

                exeReplace(replaceList, fList[i].getPath());
            }
            else if(fList[i].isDirectory()){
                getListFiles(fList[i].getPath());
            }
        }
    }


    private static String toFilter(String[] args) throws IOException{
        String rtn_lineCnt = "";
        BufferedReader br = null;
        PrintWriter pw = null;

        String line, content;
        String strFilter = args[0];

        StringBuilder sb = new StringBuilder();
        
        try{
            File file = new File(args[2]);
            br = new BufferedReader(new FileReader(file));

            int lineCnt = 1;
            while((line = br.readLine()) != null){
                sb.append(line + "\r\n");

                content = new String(sb);

                //대소문자 구분없이 비교
                if(upperType.equals("o")){
                    if(content.toUpperCase().indexOf(strFilter.toUpperCase()) != -1){
                        if(rtn_lineCnt.length() > 0){
                            rtn_lineCnt = rtn_lineCnt.concat(",");
                        }
                        rtn_lineCnt = rtn_lineCnt.concat(String.valueOf(lineCnt));
                    }
                }
                //대소문자 구분
                else{
                    if(content.indexOf(strFilter) != -1){
                        if(rtn_lineCnt.length() > 0){
                            rtn_lineCnt = rtn_lineCnt.concat(",");
                        }
                        rtn_lineCnt = rtn_lineCnt.concat(String.valueOf(lineCnt));
                    }
                }
                sb.delete(0, sb.length());
                lineCnt++;
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(br != null) br.close();
            if(pw != null) pw.close();
        }

        if(rtn_lineCnt.equals("")) rtn_lineCnt = "0";
        return rtn_lineCnt;
    }

    private static List<Map<String, String>> getFilterInfo(){
        List<Map<String, String>> replaceList = new ArrayList<Map<String, String>>();

        System.out.println("===== 필터 대상 존재여부 =====");

        Map<String, String> replaceMap = new HashMap<String, String>();
        replaceMap.put("filter", "<script>");
        System.out.println("@replaceList : " + replaceMap.toString());
        replaceList.add(replaceMap);
        
        System.out.println("===== //필터 대상 존재여부 =====");

        return replaceList;
    }

    
}

