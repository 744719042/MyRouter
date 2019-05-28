package com.example.myrouter;

import android.content.pm.ApplicationInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.routerapi.RouterManager;
import com.example.routerapi.RouterRequest;
import com.example.routerbase.annotation.Router;

import java.io.IOException;
import java.util.Enumeration;

import dalvik.system.DexFile;


@Router(path = "/app/index")
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gotoMain(View view) {
        RouterRequest request = new RouterRequest.Builder("/home/index").withActivity(this).build();
        RouterManager.getInstance().navigate(request);
    }

    public void printDexFile(View view) {
        final ApplicationInfo applicationInfo = getApplicationInfo();
        Log.e(TAG, "dataDir = " + applicationInfo.dataDir);
        Log.e(TAG, "deviceProtectedDataDir = " + applicationInfo.deviceProtectedDataDir);
        Log.e(TAG, "nativeLibraryDir = " + applicationInfo.nativeLibraryDir);
        Log.e(TAG, "publicSourceDir = " + applicationInfo.publicSourceDir);
        Log.e(TAG, "sourceDir = " + applicationInfo.sourceDir);

        new Thread(new Runnable() {
            @Override
            public void run() {
                DexFile dexfile = null;
                try {
                    dexfile = new DexFile(applicationInfo.sourceDir);
                    Enumeration<String> dexEntries = dexfile.entries();
                    while (dexEntries.hasMoreElements()) {
                        String className = dexEntries.nextElement();
                        if (className.startsWith("com.example.router.config")) {
                            Log.e(TAG, className);
                        }

                        if (className.startsWith("com.example.filter.config")) {
                            Log.e(TAG, className);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
