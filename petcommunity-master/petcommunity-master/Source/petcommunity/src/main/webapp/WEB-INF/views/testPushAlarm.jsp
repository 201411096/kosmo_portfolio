<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%@ page import='java.net.*, java.io.*' %>  
<%@ page import='org.json.simple.*' %>
 
 <%
// FCM 서버에서 보낸 토큰값 :   FirebaseInstanceId.getInstance().getToken()
// 콘솔에 출력하고는 그 값을 복사해서 사용해야 한다
// 실제적으로는 이 값을 DB에 넣고 jsp에서는 DB에서 가지고 와서 사용
//userDeviceIdKey is the device id you will query from your database
String userDeviceIdKey = "ewiv0mePHoA:APA91bGtU-Lz2YiCfqiqD8VPgkMETBpHwHRjO3nIOkbyYQn7K1-EkrcivA6_csoIuN4OFv5vG_VDXTuAxJB-dg5ombLgKf2YH2tALvv3K-zdTH3J_34BEqqXIn5FU6Vld2gq1EkW3-Vs";
 
 //Method to send Notifications from server to client end.
 // firebase.google.com 사이트에서 해당 프로젝트 > 설정 > 클라우드 메세지 > 서버 키 토큰 복사
String AUTH_KEY_FCM = "AAAA5I6xC80:APA91bFtb40SjHrzCQJ3fV_2IrvovLIPTN-X6LpEB-LgxFlOWSR4pejrgR6UmTNukASbymKTZspKRuNlitwRBkB5pxWsp9RDMQEEJxMD6jTH-17eR6pnMHOVnJvR6QCSEwpXhv63lc_R";
String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";


String authKey = AUTH_KEY_FCM;   // You FCM AUTH key
String FMCurl = API_URL_FCM;     

URL url = new URL(FMCurl);
HttpURLConnection conn = (HttpURLConnection) url.openConnection();

conn.setUseCaches(false); 
conn.setDoInput(true);
conn.setDoOutput(true);

conn.setRequestMethod("POST");
conn.setRequestProperty("Authorization","key="+authKey);
conn.setRequestProperty("Content-Type","application/json");

JSONObject json = new JSONObject();
json.put("to",userDeviceIdKey.trim());
JSONObject info = new JSONObject();
info.put("title", "나의캐톡");   // Notification title
info.put("body", "당신은 사랑받기위해"); // Notification body
json.put("contents", info);

OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
System.out.println(">" + json.toString());
wr.write(json.toString());
wr.flush();
conn.getInputStream();  
  %>  
