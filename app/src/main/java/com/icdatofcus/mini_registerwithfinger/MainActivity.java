package com.icdatofcus.mini_registerwithfinger;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.testing.aramis.sourceafis.FingerprintMatcher;
import com.testing.aramis.sourceafis.FingerprintTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import asia.kanopi.fingerscan.Fingerprint;
import asia.kanopi.fingerscan.Status;

public class MainActivity extends AppCompatActivity {


    EditText Name, Email, Username, Password, Accountbalance, Sex, Dept, Level, DOB;

    ImageView LeftThumbImage, LeftIndexImage, RightThumbImage, RightIndexImage;

    Button LeftThumbCapture, LeftIndexCapture, RightThumbCapture, RightIndexCapture;
    Button SavetoDB;

    TextView ConfirmFingerMatch, ScannerStatus;

    TextView Strin;

    String first = "fff";
    String second = "fff";

    String LeftThumbFirst, LeftThumbConfirmed;
   // public static String LeftThumbConfirmed = null;
    String LeftIndexFirst, LeftIndexConfirmed;
    String RightThumbFirst, RightThumbConfirmed;
    String RightIndexFirst, RightIndexConfirmed;
    String register_with_finger = "http://128.0.1.2/register_with_fingerprint_and_rest.php";

    Fingerprint fingerprint;

    byte[] imgg;
    Bitmap bitmap, bitmapAnother;
    private static final int SCAN_FINGER = 0;
    private byte[] bytesObject, byteAnotherObject;

    StringBuilder sb;

    //public static Handler printHandler, updateHandler;


    private FingerprintTemplate probeTemplate, candidateTemplate;
    private FingerprintMatcher pleaseMatch = new FingerprintMatcher();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = findViewById(R.id.name);
        Email = findViewById(R.id.email);
        Username = findViewById(R.id.user_name);
        Password = findViewById(R.id.password);
        Accountbalance = findViewById(R.id.accountbalance);
        Sex = findViewById(R.id.sex);
        Dept = findViewById(R.id.dept);
        Level = findViewById(R.id.level);
        DOB = findViewById(R.id.dob);

        LeftThumbImage = findViewById(R.id.leftThumb_fp);
        LeftIndexImage = findViewById(R.id.leftIndex_fp);
        RightThumbImage = findViewById(R.id.rightThumb_fp);
        RightIndexImage = findViewById(R.id.rightIndex_fp);

        LeftThumbCapture = findViewById(R.id.left_Thumb_Capture);
        LeftIndexCapture = findViewById(R.id.left_Index_Capture);
        RightThumbCapture = findViewById(R.id.right_Thumb_Capture);
        RightIndexCapture = findViewById(R.id.right_Index_Capture);

        SavetoDB = findViewById(R.id.saveDB);

        ConfirmFingerMatch = findViewById(R.id.fingerMatch);
        ScannerStatus = findViewById(R.id.scannerStatus);
        Strin = findViewById(R.id.strin);
        Strin.setTextIsSelectable(true);
        Strin.setSelectAllOnFocus(true);
        Strin.requestFocus();

        fingerprint = new Fingerprint();

    }


    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onStop() {
        fingerprint.turnOffReader();
        super.onStop();
    }

    Handler printHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            byte [] image;

            String errorMessage = "empty";
            int status = msg.getData().getInt("status");
            Intent intent = new Intent();
            intent.putExtra("status", status);



            if (status == Status.SUCCESS) {



                image = msg.getData().getByteArray("img");
                bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                LeftThumbImage.setImageBitmap(bitmap);
                intent.putExtra("img", image);
                ConfirmFingerMatch.setText("Fingerprint captured");

//                LeftThumbFirst = ConfirmFingerMatch.getText().toString();

//
//                byte[] decodedByte = Base64.decode(LeftThumbFirst, 0);
//                bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);


         //   try {

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                bytesObject = baos.toByteArray();
//                bytesObject = baos.toByteArray();
//                bytesObject = image;
//                bytesObject = first.getBytes();
                LeftThumbFirst = Base64.encodeToString(bytesObject, Base64.DEFAULT);

                probeTemplate = new FingerprintTemplate()
                        .dpi(500).create(bytesObject);



//                BigInteger biggi = new BigInteger(bytesObject);


//                Makes it slower with 4 secs we could use a spinnerview here
//                LeftThumbFirst = biggi.toString();

//                Strin.setText(Arrays.toString(bytesObject));
//
//                Strin.setText("okay");
//
//
//
//                sb = new StringBuilder(bytesObject.length * Byte.SIZE);
//                for( int i = 0; i < Byte.SIZE * bytesObject.length; i++ ) {
//                    sb.append((bytesObject[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
//                    sb.toString();
//                }
//
//



//                    return LeftThumbFirst;
//                } catch (NullPointerException e) {
//                    return null;
//                } catch (OutOfMemoryError e) {
//                    return null;
//                }

              //  To Decode
//                byte [] cake = Base64.decode(LeftThumbConfirmed, Base64.DEFAULT);
//                Bitmap wide = BitmapFactory.decodeByteArray(cake, 0, cake.length);
//                RightThumbImage.setImageBitmap(wide);



            } else {
                errorMessage = msg.getData().getString("errorMessage");
                intent.putExtra("errorMessage", errorMessage);
            }
            // setResult(RESULT_OK, intent);
            //  finish();
        }
    };

    Handler updateHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            int status = msg.getData().getInt("status");

            switch (status) {
                case Status.INITIALISED:
                    ScannerStatus.setText("Setting up reader");
                    break;
                case Status.SCANNER_POWERED_ON:
                    ScannerStatus.setText("Reader powered on");
                    break;
                case Status.READY_TO_SCAN:
                    ScannerStatus.setText("Ready to scan finger");
                    break;
                case Status.FINGER_DETECTED:
                    ScannerStatus.setText("Finger Detected");
                    break;
                case Status.RECEIVING_IMAGE:
                    ScannerStatus.setText("Receiving Image");
                    break;
                case Status.FINGER_LIFTED:
                    ScannerStatus.setText("Finger has been lifted off reader");
                    break;
                case Status.SCANNER_POWERED_OFF:
                    ScannerStatus.setText("Reader is off");
                    break;
                case Status.SUCCESS:
                    ScannerStatus.setText("Fingerprint Successfully Captured");
                    break;
                case Status.ERROR:
                    ScannerStatus.setText("Erroring");
                    break;
                default:
                    ScannerStatus.setText(String.valueOf(status));
                    break;
            }

        }
    };

    Handler printAnotherHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            byte [] image;
            String errorMessage = "empty";
            int status = msg.getData().getInt("status");
            Intent intent = new Intent();
            intent.putExtra("status", status);



            if (status == Status.SUCCESS) {



                image = msg.getData().getByteArray("img");
                bitmapAnother = BitmapFactory.decodeByteArray(image, 0, image.length);
                LeftIndexImage.setImageBitmap(bitmapAnother);
                intent.putExtra("img", image);
                ConfirmFingerMatch.setText("Fingerprint captured");

//                LeftThumbFirst = ConfirmFingerMatch.getText().toString();

//
//                byte[] decodedByte = Base64.decode(LeftThumbFirst, 0);
//                bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);


                //   try {

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmapAnother.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byteAnotherObject = baos.toByteArray();
//                byteAnotherObject = baos.toByteArray();
//                byteAnotherObject = image;
//                byteAnotherObject = second.getBytes();
                LeftIndexFirst = Base64.encodeToString(byteAnotherObject, Base64.DEFAULT);

                candidateTemplate = new FingerprintTemplate()
                        .dpi(500).create(byteAnotherObject);



//                BigInteger biggi = new BigInteger(byteAnotherObject);


//                Makes it slower with 4 secs we could use a spinnerview here
//                LeftThumbFirst = biggi.toString();

//                Strin.setText(Arrays.toString(bytesObject));

//                Strin.setText("okay");
//
//
//
//                sb = new StringBuilder(byteAnotherObject.length * Byte.SIZE);
//                for( int i = 0; i < Byte.SIZE * byteAnotherObject.length; i++ ) {
//                    sb.append((byteAnotherObject[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
//                    sb.toString();
//                }
//




//                    return LeftThumbFirst;
//                } catch (NullPointerException e) {
//                    return null;
//                } catch (OutOfMemoryError e) {
//                    return null;
//                }

                //  To Decode
//                byte [] cake = Base64.decode(LeftThumbConfirmed, Base64.DEFAULT);
//                Bitmap wide = BitmapFactory.decodeByteArray(cake, 0, cake.length);
//                RightThumbImage.setImageBitmap(wide);



            } else {
                errorMessage = msg.getData().getString("errorMessage");
                intent.putExtra("errorMessage", errorMessage);
            }
            // setResult(RESULT_OK, intent);
            //  finish();
        }
    };

    Handler updateAnotherHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            int status = msg.getData().getInt("status");

            switch (status) {
                case Status.INITIALISED:
                    ScannerStatus.setText("Setting up reader");
                    break;
                case Status.SCANNER_POWERED_ON:
                    ScannerStatus.setText("Reader powered on");
                    break;
                case Status.READY_TO_SCAN:
                    ScannerStatus.setText("Ready to scan finger");
                    break;
                case Status.FINGER_DETECTED:
                    ScannerStatus.setText("Finger Detected");
                    break;
                case Status.RECEIVING_IMAGE:
                    ScannerStatus.setText("Receiving Image");
                    break;
                case Status.FINGER_LIFTED:
                    ScannerStatus.setText("Finger has been lifted off reader");
                    break;
                case Status.SCANNER_POWERED_OFF:
                    ScannerStatus.setText("Reader is off");
                    break;
                case Status.SUCCESS:
                    ScannerStatus.setText("Fingerprint Successfully Captured");
                    break;
                case Status.ERROR:
                    ScannerStatus.setText("Erroring");
                    break;
                default:
                    ScannerStatus.setText(String.valueOf(status));
                    break;
            }

        }
    };



    public void  LeftThumbCapture (View view) {

        fingerprint.scan(this, printHandler, updateHandler);

    }

    public void LeftIndexCapture (View view) {

        fingerprint.scan(this, printAnotherHandler, updateAnotherHandler);

    }

    public void RightThumbCapture (View view) {

    }

    public void RightIndexCapture (View view) {

        double score = pleaseMatch.index(this.probeTemplate).match(this.candidateTemplate);

        boolean matches = score >= 40;

        if (matches) {
            Toast.makeText(MainActivity.this, "Successfully Matched Dave, Congrats", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(MainActivity.this, "Few more Changes Dave", Toast.LENGTH_SHORT).show();
        }
    }




    public void  saveToDB (View view) {


        StringRequest Minutae = new StringRequest(Request.Method.POST, register_with_finger,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray may = new JSONArray(response);
                            JSONObject god = may.getJSONObject(0);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
            {
//            @Override
//                public byte[] getBody(){
//                return bytesObject;
//            }
//            @Override
//                public String getBodyContentType() {
////                return "image/jpeg";
//                return "left_thumb_fingerprint";
//            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> params = new HashMap<>();

//                params.put("name", Name.getText().toString());
                params.put("email", Email.getText().toString());
//                params.put("user_name", Username.getText().toString());
//                params.put("password", Password.getText().toString());
                params.put("left_thumb_fingerprint", LeftThumbFirst);
//                params.put("left_thumb_fingerprint", sb.toString());
//                params.put("left_thumb_fingerprint", "Okay");
//                params.put("accountbalance", Accountbalance.getText().toString());
//                params.put("sex", Sex.getText().toString());
//                params.put("department", Dept.getText().toString());
//                params.put("level", Level.getText().toString());
//                params.put("d_o_b", DOB.getText().toString());


                return params;
            }

        };
        MyCountlesston.getmInstance(MainActivity.this).addToRequestQueue(Minutae);
        Minutae.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));


        Toast toast = Toast.makeText(this, "Okay", Toast.LENGTH_SHORT);
        toast.show();

    }


}
