package com.example.mhealthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class camActivity extends AppCompatActivity {


    private FirebaseAuth auth;
    private TextureView textureView;
    AnimationDrawable animationDrawable;
    DatabaseReference current_user_db , hrtrateDB;

    String cameraId;

    protected CameraDevice cameraDevice;
    protected CameraCaptureSession cameraCaptureSessions;
    protected CaptureRequest.Builder captureRequestBuilder;
    private Size imageDimension;
    private static final int REQUEST_CAMERA_PERMISSION = 1;

    private Handler mbackgroundhandler;
    private HandlerThread mbackgroundthread;

    public static int hrt_rate_cal;
    private int current_Rolling_avg;
    private int last_rolling_avg;
    private int last_last_rolling_avg;
    private long[] time_array;
    private int num_click = 0;
    private int num_beats = 0;
    TextView hrt_rate_txt, hrt_rate_txt2;
    Button okBTn;

    int totalday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);

        textureView = findViewById(R.id.cam_texture);
        assert textureView != null;
        textureView.setSurfaceTextureListener(textureListener);
        time_array = new long[15];
        hrt_rate_txt = findViewById(R.id.cam_txt);
        hrt_rate_txt2 = findViewById(R.id.cam_txt2);
        ImageView image = (ImageView) findViewById(R.id.hrt_img);
        animationDrawable = (AnimationDrawable) image.getDrawable();
        animationDrawable.start();
        okBTn = findViewById(R.id.Okbtn);
        okBTn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    }

    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

            Bitmap bmp = textureView.getBitmap();
            int width = bmp.getWidth();
            int height = bmp.getHeight();
            int[] pixels = new int[height * width];
            // Get pixels from the bitmap, starting at (x,y) = (width/2,height/2)
            // and totaling width/20 rows and height/20 columns
            bmp.getPixels(pixels, 0, width, width / 2, height / 2, width / 20, height / 20);
            int sum = 0;
            for (int i = 0; i < height * width; i++) {
                int red = (pixels[i] >> 16) & 0xFF;
                sum = sum + red;
            }
            // Waits 20 captures, to remove startup artifacts.  First average is the sum.
            if (num_click == 20) {
                current_Rolling_avg = sum;
            }
            // Next 18 averages needs to incorporate the sum with the correct N multiplier
            // in rolling average.
            else if (num_click > 20 && num_click < 49) {
                current_Rolling_avg = (current_Rolling_avg * (num_click - 20) + sum) / (num_click - 19);
            }
            // From 49 on, the rolling average incorporates the last 30 rolling averages.
            else if (num_click >= 49) {
                current_Rolling_avg = (current_Rolling_avg * 29 + sum) / 30;
                if (last_rolling_avg > current_Rolling_avg && last_rolling_avg > last_last_rolling_avg && num_beats < 15) {
                    time_array[num_beats] = System.currentTimeMillis();
                    num_beats++;
                    if (num_beats == 15) {
                        calculateBPM();
                    }
                }
            }
            // Another capture
            num_click++;
            // Save previous two values
            last_last_rolling_avg = last_rolling_avg;
            last_rolling_avg = current_Rolling_avg;

        }
    };

    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            cameraDevice = camera;
            createCameraPreview();
        }

        @Override
        public void onDisconnected(CameraDevice cameraDevice) {
            cameraDevice.close();

        }

        @Override
        public void onError(CameraDevice cameraDevice, int i) {
            if (cameraDevice != null)
                cameraDevice.close();
            cameraDevice = null;

        }
    };

    // onResume
    protected void startBackgroundThread() {
        mbackgroundthread = new HandlerThread("Camera Background");
        mbackgroundthread.start();
        mbackgroundhandler = new Handler(mbackgroundthread.getLooper());
    }

    // onPause
    protected void stopBackgroundThread() {
        mbackgroundthread.quitSafely();
        try {
            mbackgroundthread.join();
            mbackgroundthread = null;
            mbackgroundhandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void calculateBPM() {

        int cal = 0;
        long[] timedist = new long[14];
        for (int i = 0; i < 14; i++) {
            timedist[i] = time_array[i + 1] - time_array[i];
        }
        Arrays.sort(timedist);
        cal = (int) timedist[timedist.length / 2];
        hrt_rate_cal = 60000 / cal;

        hrt_rate_txt2.setText("");
        hrt_rate_txt.setText("Heart Rate = " + hrt_rate_cal + " BPM");
        animationDrawable.stop();
        insertintoDB(hrt_rate_cal);

    }

    public void insertintoDB(int hrt_rate_cal) {
        auth = FirebaseAuth.getInstance();

        String userId = auth.getCurrentUser().getUid();


        current_user_db = FirebaseDatabase.getInstance().getReference().child("HeartRate").child(userId);

        Map newPost = new HashMap();

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        newPost.put("timestamp", currentDateTimeString);

        newPost.put("Current HeartRate", hrt_rate_cal);

        current_user_db.setValue(newPost);



    }

    protected void createCameraPreview() {
        try {
            SurfaceTexture texture = textureView.getSurfaceTexture();
            assert texture != null;
            texture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
            Surface surface = new Surface(texture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);
            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    if (null == cameraDevice) {
                        return;
                    }
                    cameraCaptureSessions = cameraCaptureSession;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Toast.makeText(camActivity.this, "Configuration change", Toast.LENGTH_SHORT).show();
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    // Opening camera to measure heart rate
    private void openCamera() {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(camActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                return;
            }
            manager.openCamera(cameraId, stateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    protected void updatePreview() {
        if (null == cameraDevice) {
            Log.w("CAMERA", "Error in Updating");
        }
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        captureRequestBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH);
        try {
            cameraCaptureSessions.setRepeatingRequest(captureRequestBuilder.build(), null, mbackgroundhandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void closeCamera() {
        if (null != cameraDevice) {
            cameraDevice.close();
            cameraDevice = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // app is closed
                Toast.makeText(camActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startBackgroundThread();
        if (textureView.isAvailable()) {
            openCamera();
        } else {
            textureView.setSurfaceTextureListener(textureListener);
        }
    }

    @Override
    protected void onPause() {
        closeCamera();
        stopBackgroundThread();
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


}

