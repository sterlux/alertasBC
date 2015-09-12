/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SaveCallback;


public class StarterApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    Parse.initialize(this, "KOZTnHkdmrMemCqF8jZjmcOaUKwgIHAIclXUxEEh", "h1zFJHl0JEcAYEnokQhJH18yE3yps9VnyXbQQsbq");
    ParseInstallation.getCurrentInstallation().saveInBackground();

//    ParseUser.enableAutomaticUser();
//    ParseACL defaultACL = new ParseACL();
//    // Optionally enable public read access.
//    // defaultACL.setPublicReadAccess(true);
//    ParseACL.setDefaultACL(defaultACL, true);
//
    ParsePush.subscribeInBackground("", new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if (e == null) {
          Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
        } else {
          Log.e("com.parse.push", "failed to subscribe for push", e);
        }
      }
    });
  }
}
