package com.mycompany.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ExecuteCommand {
   public static void executeCommand(String command) {
      Runtime runtime = Runtime.getRuntime();
      Process process = null;
      try {
         process = runtime.exec(command);
         BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
         String line = null;
         while((line = br.readLine())!=null) {
            System.out.println(line);
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            process.getErrorStream().close();    // 프로세스의 stream을 비워줌
            process.getInputStream().close();    // 프로세스의 stream을 비워줌
            process.getOutputStream().close();   // 프로세스의 stream을 비워줌
            process.waitFor();               	 // 프로세스 종료를 기다림
         }catch(Exception e){
            e.printStackTrace();
         }
      }
   }
}
