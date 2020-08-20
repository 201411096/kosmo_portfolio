package com.example.okidoghere2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyMessagingService extends FirebaseMessagingService {
    NotificationManager manager;
    NotificationCompat.Builder builder;


    public MyMessagingService() {
    }

    private static final String TAG = "FMS";

    @Override
    // 새로운 토큰을 확인했을 때 호출 됨
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        Log.d(TAG, "onNewToken 호출 됨: " + token);
    }
    // onMessageReceived 재정의
    // FirebaseMessagingService.onMessageReceived 메서드를 재정의하면 수신된 RemoteMessage 객체를 기준으로
    // 작업을 수행하고 메시지 데이터를 가져올 수 있습니다.
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived 호출 됨.");
        //------------------------------------------------
        // (2) 메세지 수신 기능 추가
        // - push메시지 받았을 때 그 내용 확인한 후 액티비티 쪽으로 보내는 메소드 호출
        Log.d(TAG, "리모트"+remoteMessage.toString());
        Log.d(TAG, "리모트"+remoteMessage);
        String from = remoteMessage.getFrom();
        if(remoteMessage.getData()!=null){
            Map<String, String> data = remoteMessage.getData();
            String message = data.get("message");
            String messageTitle = data.get("messageTitle");
            String link = data.get("link");
            Log.d(TAG, "from : " + from + ", messageTitle : " + messageTitle + ", message: " + message + ", link: " + link);
    //        showNoti(messageTitle, message, link);
            sendToActivity(getApplicationContext(), from, messageTitle, message, link);
        }else{
            Log.d(TAG,"데이터 안뜸");
        }
//        if(remoteMessage.getNotification()!=null) {
//            //notification일 경우
//            String title = remoteMessage.getNotification().getTitle();
//            String body = remoteMessage.getNotification().getBody();
//            Uri uri = remoteMessage.getNotification().getLink();
//            sendToActivity(getApplicationContext(), from, title, body, uri.toString());
//            Log.d(TAG, "from : " + from + ", messageTitle : " + title + ", message: " + body + ", link: " + uri);
//        }else{
//            Log.d(TAG,"노티 데이터 안뜸");
//        }
    }
    //------------------------------------------------
    // (2) 메세지 수신 기능 추가
    // - 엑티비티 쪽으로 데이타를 보내기 위해 인텐트 객체를 만들고 startActivity() 호출
    //    private void sendToActivity(Context context, String from, String contents){
    private void sendToActivity(Context context, String from, String title, String body, String uri){
        //※ 인텐트란
        //
        // 앱 컴포넌트가 무엇을 할 것인지를 담는 메시지 객체입니다.
        // 메시지는 의사소통을 하기 위해 보내고 받는 것이지요.
        // 메시지를 사용하는 가장 큰 목적은 다른 액티비티, 서비스, 브로드캐스트 리시버, 컨텐트 프로바이더 등을 실행하는 것입니다.
        // 인텐트는 그들 사이에 데이터를 주고 받기 위한 용도로도 쓰입니다.x
//        Intent intent = new Intent(context, MainActivity.class);
        //알림(Notification)을 관리하는 관리자 객체를 운영체제(Context)로부터 소환하기
        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Notification 객체를 생성해주는 건축가객체 생성(AlertDialog 와 비슷)
        NotificationCompat.Builder builder= null;


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            String channelID="channel_01"; //알림채널 식별자
            String channelName="MyChannel01"; //알림채널의 이름(별명)

            //알림채널 객체 만들기
            NotificationChannel channel= new NotificationChannel(channelID,channelName,NotificationManager.IMPORTANCE_DEFAULT);

            //알림매니저에게 채널 객체의 생성을 요청
            notificationManager.createNotificationChannel(channel);

            //알림건축가 객체 생성
            builder=new NotificationCompat.Builder(this, channelID);


        }else{
            //알림 건축가 객체 생성
            builder= new NotificationCompat.Builder(this, null);
        }
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("uri", uri);
        intent.putExtra("title", title);
        intent.putExtra("body", body);
        Log.d(TAG, uri+title+body);
//
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        //건축가에게 원하는 알림의 설정작업
        builder.setSmallIcon(R.drawable.push_icon);

        //상태바를 드래그하여 아래로 내리면 보이는
        //알림창(확장 상태바)의 설정
        builder.setContentTitle(title);//알림창 제목
        builder.setContentText(body);//알림창 내용
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        //알림창의 큰 이미지
//        Bitmap bm= BitmapFactory.decodeResource(getResources(),R.drawable.gametitle_09);
//        builder.setLargeIcon(bm);//매개변수가 Bitmap을 줘야한다.

        //건축가에게 알림 객체 생성하도록
        Notification notification=builder.build();

        //알림매니저에게 알림(Notify) 요청
        notificationManager.notify(1, notification);

        //알림 요청시에 사용한 번호를 알림제거 할 수 있음.
        //notificationManager.cancel(1);
//////===================================================================================================
//        Intent intent = new Intent(context, MainActivity.class);
//        intent.putExtra("from", from);
//        intent.putExtra("uri", uri);
//        intent.putExtra("title", title);
//        intent.putExtra("body", body);
////
////
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
////
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, notificationChannel)
//                .setSmallIcon(R.drawable.ic_launcher_background)
//                .setContentTitle(title)
//                .setContentText(body)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true);
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        notificationManager.notify(1, builder.build());// builder.build());
//
////        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        context.startActivity(intent);
//
//        Log.d(TAG, "sendToActivity from : " + from + ", messageTitle : " + title + ", message: " + body + ", link: " + uri);
////=====================================================================================================

//        Intent urlView = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//        urlView.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        context.startActivity(urlView);
    }

//    public void showNoti(String title, String body, String uri){
//        builder = null;
//        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        Log.d(TAG, "showNoti: "+title+ body+ uri);
//        //하위 버전일 경우
//        builder = new NotificationCompat.Builder(this,null);
//        //알림창 제목
//        builder.setContentTitle(title);
//        //알림창 메시지
//        builder.setContentText(body);
//        //알림창 아이콘
//        builder.setSmallIcon(R.drawable.push_icon);
//        Notification notification = builder.build();
//        //알림창 실행
//        manager.notify(1,notification);
//    }

//    public void showNoti(String title, String body, String uri){
//
//        //알림(Notification)을 관리하는 관리자 객체를 운영체제(Context)로부터 소환하기
//        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        //Notification 객체를 생성해주는 건축가객체 생성(AlertDialog 와 비슷)
//        NotificationCompat.Builder builder= null;
//
//        builder= new NotificationCompat.Builder(this, null);
//
//        //알림창 제목
//        builder.setContentTitle(title);
//        //알림창 메시지
//        builder.setContentText(body);
//        //알림창 아이콘
//        builder.setSmallIcon(R.drawable.ic_launcher_background);
//
//        //알림창 클릭시에 실행할 작업(SecondActivity 실행)설정
//        Intent intent = new Intent(this, SecondActivity.class);
//        // 지금 실행하는 것이 아니라 잠시 보류시키는 Intent객체 필요
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
//        builder.setContentIntent(pendingIntent);
//
//        //알림창 클릭시에 자동으로 알림제거
//        builder.setAutoCancel(true);
//
//
//        //건축가에게 알림 객체 생성하도록
//        Notification notification=builder.build();
//
//        //알림매니저에게 알림(Notify) 요청
//        notificationManager.notify(0, notification);
//        Log.d(TAG, "messageTitle : " + title + ", message: " + body + ", link: " + uri);
//    }




}
