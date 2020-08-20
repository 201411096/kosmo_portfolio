package com.example.okidoghere2;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.io.File;

public class ResultActivity extends AppCompatActivity implements AutoPermissionsListener {

    private WebView webView;
    //    private String url = "https://192.168.0.19:8443/petcommunity/test_kys.jsp";
    private String pushUrl="";
    private String memberX = "";
    private String memberY = "";
    // 디바이스 권한 설정에 필요한 변수
    public ValueCallback<Uri> filePathCallbackNormal;
    public ValueCallback<Uri[]> filePathCallbackLollipop;
    public final static int FILECHOOSER_NORMAL_REQ_CODE = 2001;
    public final static int FILECHOOSER_LOLLIPOP_REQ_CODE = 2002;
    private Uri cameraImageUri = null;
    private static final int MY_PERMISSION_STORAGE = 1111;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView)findViewById(R.id.webView);
        // 위치정보 획득 함수
        startLocationService();
        // 각종 권한 획득
        checkVerify();
        // 자바스크립트 허용
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);
//        webSettings.setSafeBrowsingEnabled(false);
        webSettings.setGeolocationEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);


        // url을 load해라
//        webView.loadUrl(url);
        // 브라우져 세팅(일반 브라우져)
        webView.setWebViewClient(new WebViewClientClass());
        // 브라우져 세팅(크롬 input 세팅)
        webView.setWebChromeClient(new WebChromeClient()
//===================================================================================
        {
            // 위치정보 허가
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, true);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }


            // 자바스크립트의 alert창
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Alert")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.confirm();
                                    }
                                })
                        .setCancelable(false)
                        .create()
                        .show();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message,
                                       final JsResult result) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Confirm")
                        .setMessage(message)
                        .setPositiveButton("Yes",
                                new AlertDialog.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.confirm();
                                    }
                                })
                        .setNegativeButton("No",
                                new AlertDialog.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.cancel();
                                    }
                                })
                        .setCancelable(false)
                        .create()
                        .show();
                return true;
            }

            // For Android 5.0+ 카메라 - input type="file" 태그를 선택했을 때 반응
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public boolean onShowFileChooser(
                    WebView webView, ValueCallback<Uri[]> filePathCallback,
                    FileChooserParams fileChooserParams) {
                Log.d("MainActivity", "5.0+");


                // Callback 초기화 (중요!)
                if (filePathCallbackLollipop != null) {
                    filePathCallbackLollipop.onReceiveValue(null);
                    filePathCallbackLollipop = null;
                }
                filePathCallbackLollipop = filePathCallback;

                boolean isCapture = fileChooserParams.isCaptureEnabled();
                runCamera(isCapture);
                return true;
            }

        });

//=================================================================================================================
        // SSL 인증서 무시하는 setWebViewClient 호출
//        webView.setWebViewClient(new SslWebViewConnect());
        webView.setWebViewClient(new SslWebViewConnect());
        // 현재 토큰 검색
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult result) {
                String newToken = result.getToken();
                println("등록 id : " + newToken);
                String instanceId = FirebaseInstanceId.getInstance().getId();
                println("확인된 인스턴스 id : " + instanceId);
                // Spring js의 함수로 토큰 보내는 부분
//                String url="https://192.168.0.19:8443/petcommunity/test_token.do?tokenId="+newToken+"&memberX="+memberX+"&memberY="+memberY;
//                String url ="file://android_assets/www/index.html";
                webView.loadUrl(pushUrl);
//                webView.loadUrl("javascript:setMessage("+newToken+")");
//                sendRegistrationToServer(newToken);
            }
        });

//================================================================================================
        // 4. 위험 권한을 위한 코드 추가하기
        AutoPermissions.Companion.loadAllPermissions(this, 101);
//================================================================================================


    }//end onCreate
    //==================================================================================
    // 1.위치 관리자 객체 참조하기-시스템 서비스로 getSystemService() 메서드 활용
    public void startLocationService(){
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE); // LocationManager 객체 참조하기
        try{
            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER); // 이전에 확인했던 위치 정보 가져오기
            if (location != null){
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                memberX = String.valueOf(latitude);
                memberY = String.valueOf(longitude);
                String message = "최근 위치 -> Latitude: " + latitude + "\nLongitude: "+ longitude;
                println(message);
            }
            // 3.위치 정보 업데이트 요청하기-시간 or 이동한 만큼 requestLocationUpdate()메서드로 재요청
            GPSListener gpsListener = new GPSListener(); // 리스너 객체 생성
            long minTime = 10000;
            float minDistance = 0;

            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener); // 위치 요청
            Toast.makeText(getApplicationContext(), "내 위치확인 요청", Toast.LENGTH_SHORT).show();

        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    //    @Override
//    public void onRequestPermissionsResult(int requestCoode, String permissions[], int[] grantResults){
//        super.onRequestPermissionsResult(requestCoode, permissions, grantResults);
//        AutoPermissions.Companion.parsePermissions(this, requestCoode, permissions, this);
//    }
    @Override
    public void onDenied(int requestCode, String[] permission) {
        Toast.makeText(this, "permissions denied: " + permission.length, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGranted(int requestCode, String[] permission) {
        Toast.makeText(this, "permissions granted: " + permission.length, Toast.LENGTH_SHORT).show();

    }

    // 2.위치 리스너 구현하기
    class GPSListener implements LocationListener {
        public void onLocationChanged(Location location){ // 위치가 확인되었을 때 자동으로 호출되는 on LocationChanged()메서드
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();
            memberX = String.valueOf(latitude);
            memberY = String.valueOf(longitude);
            String message = "내 위치-> Latitude: "+ latitude + "Longitude: "+ longitude;
            println(message);
        }
        public void onProviderDisabled(String provider){}

        public void onProviderEnabled(String porvider){}

        public void onStatusChanged(String provider, int status, Bundle extras){}
    }

    // 4. 위치 권한




//===================================================================================
//private class SslWebViewConnect extends WebViewClient {
//    @Override
//    public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error){
//        StringBuilder sb= new StringBuilder();
//        if (error != null) {
//            switch (error.getPrimaryError()) {
//                case SslError.SSL_EXPIRED:
//                    sb.append("이 사이트의  보안 인증서가 만료되었습니다.\n");
//                    break;
//                case SslError.SSL_IDMISMATCH:
//                    sb.append("이 사이트의 보안 인증서 ID가 일치하지 않습니다.\n");
//                    break;
//                case SslError.SSL_NOTYETVALID:
//                    sb.append("이 사이트의 보안 인증서가 아직 유효하지 않습니다.\n");
//                    break;
//                case SslError.SSL_UNTRUSTED:
//                    sb.append("이 사이트의 이 사이트의 보안 인증서는 신뢰할 수 없습니다.\n");
//                    break;
//                default:
//                    sb.append("보안 인증서에 오류가 있습니다.\n");
//                    break;
//            }
//        }
//        sb.append("계속 진행하시겠습니까?");
//        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//        builder.setMessage(sb.toString());
//        builder.setPositiveButton("진행", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                handler.proceed();
//            }
//        });
//        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                handler.cancel();
//            }
//        });
//        final AlertDialog dialog = builder.create();
//        dialog.show();
//    }
//}
//====================================================================================================



    //====================================================================================================
// 로그에 토큰 출력
    public void println(String data){
        Log.d("FMS", data);
    }
    //-----------------------------------------------------------------
    // 서비스로부터 인텐트를 받았을 때의 처리
    @Override
    protected void onNewIntent(Intent intent) {
        println("onNewIntent 호출 됨");
        if (intent != null) {
            processIntent(intent);
        }

        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent){
        String from = intent.getStringExtra("from");
        if (from == null){
            println("from is null");
            return;
        }

//        String contents = intent.getStringExtra("contents");
        String title = intent.getStringExtra("title");
        String body = intent.getStringExtra("body");
        String uri = intent.getStringExtra("uri");
        pushUrl = uri;
//        println("DATA : " + from + ", " + contents);
        println("DATA : " + from + ", title: " + title + ", body:" + body );
    }

    @Override
    // 뒤로가기 버튼 눌렀을 때 원래 화면으로 돌아가기
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WebViewClientClass extends WebViewClient {

        @Override
        // 현제 페이지의 url을 읽어오는 메서드
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void checkVerify(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                new AlertDialog.Builder(this)
                        .setTitle("알림")
                        .setMessage("권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다.")
                        .setNeutralButton("설정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_STORAGE:
                for (int i = 0; i < grantResults.length; i++){
                    // grantResult[]: 권한 허용 0, 권한 거부 -1
                    if (grantResults[i] < 0){
                        Toast.makeText(ResultActivity.this, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                break;
        }
    }
    //--------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------
//액티비티가 종료될 때 결과를 받고 파일을 전송할 때 사용
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode)
        {
            case FILECHOOSER_NORMAL_REQ_CODE:
                if (resultCode == RESULT_OK)
                {
                    if (filePathCallbackNormal == null) return;
                    Uri result = (data == null || resultCode != RESULT_OK) ? null : data.getData();
                    //  onReceiveValue 로 파일을 전송한다.
                    filePathCallbackNormal.onReceiveValue(result);
                    filePathCallbackNormal = null;
                }
                break;
            case FILECHOOSER_LOLLIPOP_REQ_CODE:
                if (resultCode == RESULT_OK)
                {
                    if (filePathCallbackLollipop == null) return;
                    if (data == null)
                        data = new Intent();
                    if (data.getData() == null)
                        data.setData(cameraImageUri);

                    filePathCallbackLollipop.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
                    filePathCallbackLollipop = null;
                }
                else
                {
                    if (filePathCallbackLollipop != null)
                    {   //  resultCode에 RESULT_OK가 들어오지 않으면 null 처리하지 한다.(이렇게 하지 않으면 다음부터 input 태그를 클릭해도 반응하지 않음)
                        filePathCallbackLollipop.onReceiveValue(null);
                        filePathCallbackLollipop = null;
                    }

                    if (filePathCallbackNormal != null)
                    {
                        filePathCallbackNormal.onReceiveValue(null);
                        filePathCallbackNormal = null;
                    }
                }
                break;
            default:

                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    // 카메라 기능 구현
    private void runCamera(boolean _isCapture) {
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        File path = getFilesDir();
        File file = new File(path, "sample.png"); // sample.png 는 카메라로 찍었을 때 저장될 파일명이므로 사용자 마음대로
        // File 객체의 URI 를 얻는다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String strpa = getApplicationContext().getPackageName();
            cameraImageUri = FileProvider.getUriForFile(this, strpa + ".fileprovider", file);
        } else {
            cameraImageUri = Uri.fromFile(file);
        }
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri);

        if (!_isCapture) { // 선택팝업 카메라, 갤러리 둘다 띄우고 싶을 때
            Intent pickIntent = new Intent(Intent.ACTION_PICK);
            pickIntent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            pickIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            String pickTitle = "사진 가져올 방법을 선택하세요.";
            Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);

            // 카메라 intent 포함시키기..
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[]{intentCamera});
            startActivityForResult(chooserIntent, FILECHOOSER_LOLLIPOP_REQ_CODE);
        } else {// 바로 카메라 실행..
            startActivityForResult(intentCamera, FILECHOOSER_LOLLIPOP_REQ_CODE);
        }
    }





}//end