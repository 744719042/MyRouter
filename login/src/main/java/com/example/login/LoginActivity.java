package com.example.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.routerapi.RouterManager;
import com.example.routerapi.RouterRequest;
import com.example.routerbase.annotation.Router;

@Router(path = "/login/index")
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void gotoMain(View view) {
        RouterRequest request = new RouterRequest.Builder("/app/index").withActivity(this).build();
        RouterManager.getInstance().navigate(request);
    }
}
