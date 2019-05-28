package com.example.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.routerapi.RouterManager;
import com.example.routerapi.RouterRequest;
import com.example.routerbase.annotation.Router;


@Router(path="/home/index")
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void gotoLogin(View view) {
        RouterRequest request = new RouterRequest.Builder("/login/index").withActivity(this).build();
        RouterManager.getInstance().navigate(request);
    }
}
