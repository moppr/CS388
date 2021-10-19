package com.example.parstagram;

import com.parse.Parse;
import com.parse.ParseObject;

import android.app.Application;

public class ParseApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("fiFkiBMWAAOFifYf9aUXTQUV84ATq9ZiKtSvpHzp")
                .clientKey("o1tvNRegQLLC1utCE5VBOw2ntu3NYTeSNrAAHiW4")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }

}
