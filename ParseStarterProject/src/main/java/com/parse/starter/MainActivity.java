/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.PushService;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends ActionBarActivity {
  ListView listview;
  List<ParseObject> ob;
  ProgressDialog mProgressDialog;
  ListViewAdapter adapter;
  private List<Notification> notificationList = null;
  List<ParseObject> cities;
  Spinner sItems ;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
      listview = (ListView) findViewById(R.id.lstNotifications);
    new RemoteDataTask().execute();
      String currentCity = this.getCurrentCity();
      this.getCities();
      if(currentCity != null) {
          sItems = (Spinner) findViewById(R.id.cbCities);
          int position = ((ArrayAdapter)sItems.getAdapter()).getPosition(getCityById(currentCity));
          sItems.setSelection(position);
      }

    //Spinner initialize
    sItems = (Spinner) findViewById(R.id.cbCities);

    sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            City selected = (City) sItems.getSelectedItem();
            ParseInstallation installation = ParseInstallation.getCurrentInstallation();
            installation.put("City", selected.getID());
            installation.saveEventually();

            new RemoteDataTask().execute();

//        Toast toast = Toast.makeText(getApplicationContext(), selectedItemView.getId() + "," + id + " , "+selected.getName(), Toast.LENGTH_SHORT);
//        toast.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            // your code here
        }

    });


  }

    private String getCurrentCity() {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        String cityID = installation.getString("City");
        return cityID;
    }

    private City getCityById(String ID){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("City");
        City result = new City();
        ParseObject queryResult = null;
        try {
            queryResult = query.get(ID);
        }
        catch (ParseException ex){
            Log.e("Parse exception", ex.getMessage());
        }
        result.setID(queryResult.getObjectId());
        result.setName(queryResult.getString("Name"));
        return result;
    }

    private void getCities() {

    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("City");
    try {
      cities = query.find();
      List<City> spinnerArray =  new ArrayList<City>();
      for(ParseObject current : cities) {
        City newCity = new City();
        newCity.setID(current.getObjectId());
        newCity.setName(current.getString("Name"));
        spinnerArray.add(newCity);
      }

      ArrayAdapter<City> adapter = new ArrayAdapter<City>(
              this, android.R.layout.simple_spinner_item, spinnerArray);

      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

      sItems = (Spinner) findViewById(R.id.cbCities);
      sItems.setAdapter(adapter);

//      City selected = (City) sItems.getSelectedItem();
//      Toast toast = Toast.makeText(getApplicationContext(), selected.getID() + "," + selected.getName(), Toast.LENGTH_SHORT);
//      toast.show();



    }
    catch(ParseException e) {
      Log.e("Error", e.getMessage());
      e.printStackTrace();
    }
  }

  // RemoteDataTask AsyncTask
  private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      // Create a progressdialog
      mProgressDialog = new ProgressDialog(MainActivity.this);
      // Set progressdialog title
      mProgressDialog.setTitle("Parse.com Simple ListView Tutorial");
      // Set progressdialog message
      mProgressDialog.setMessage("Loading...");
      mProgressDialog.setIndeterminate(false);
      // Show progressdialog
      mProgressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
      // Locate the class table named "Country" in Parse.com
      notificationList = new ArrayList<Notification>();
      try {
      ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
              "Notification");
      query.orderByDescending("createdAt");
        ob = query.find();
        for (ParseObject notification : ob) {

                Notification noti = new Notification();
                noti.setMessage((String) notification.get("NotificationText"));

                notificationList.add(noti);

        }
      } catch (ParseException e) {
        Log.e("Error", e.getMessage());
        e.printStackTrace();
      }
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      // Locate the listview in listview_main.xml

      // Pass the results into ListViewAdapter.java
      adapter = new ListViewAdapter(MainActivity.this,
              notificationList);
      // Binds the Adapter to the ListView
      listview.setAdapter(adapter);
      // Close the progressdialog
      mProgressDialog.dismiss();
    }

  }
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
