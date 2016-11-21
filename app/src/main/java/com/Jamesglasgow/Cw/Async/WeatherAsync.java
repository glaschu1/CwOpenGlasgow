package com.Jamesglasgow.Cw.Async;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.Jamesglasgow.Cw.Parsers.WeatherRSSParser;
import com.Jamesglasgow.Cw.models.WeatherRSSitem;

import java.net.MalformedURLException;

/**
 * Created by jamesglasgow on 07/11/2016.
 */

public class WeatherAsync extends AsyncTask<String, Integer, WeatherRSSitem> {

        private Context appContext;
        private String urlRSSToParse;

        public WeatherAsync(Context currentAppContext, String urlRSS)
        {
            appContext = currentAppContext;
            urlRSSToParse = urlRSS;
        }

        // A callback method executed on UI thread on starting the task
        @Override
        protected void onPreExecute() {
            // Message to indicate start of parsing
            Toast.makeText(appContext,"Parsing started!", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected WeatherRSSitem doInBackground(String... params)
        {
            WeatherRSSitem parsedData;
            WeatherRSSParser rssParser = new WeatherRSSParser();
            try {
                rssParser.parseRSSData(urlRSSToParse);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            parsedData = rssParser.getRSSDataItem();

            return parsedData;
        }

        // A callback method executed on UI thread, invoked after the completion of the task
        // When doInbackground has completed, the return value from that method is passed into this event
        // handler.
        @Override
        protected void onPostExecute(WeatherRSSitem result) {
            // Message to indicate end of parsing
            Toast.makeText(appContext,"Parsing finished!", Toast.LENGTH_SHORT).show();
        }

    }