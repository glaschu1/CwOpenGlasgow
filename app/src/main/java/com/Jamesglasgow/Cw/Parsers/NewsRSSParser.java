package com.Jamesglasgow.Cw.Parsers;

import android.util.Log;

import com.Jamesglasgow.Cw.models.NewsRSSitem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

/**
 * Created by jamesglasgow on 14/11/2016.
 */

public class NewsRSSParser {

    public LinkedList<NewsRSSitem> ParseStart(String dataToParse){

        URL url1 = null;
        try {
            url1 = new URL(dataToParse);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.e("Checks", "Website " + url1);
        LinkedList<NewsRSSitem> alist = null;
        alist=parseData(url1);
        return alist;
    }
    /**
     *This is the parser it parser the data when after open iternet conection and
     * searching inputstream it activates and add the data to the item
     */
    private LinkedList<NewsRSSitem> parseData(URL dataToParse)
    {
        NewsRSSitem Info=null;
        LinkedList<NewsRSSitem> alist = null;

        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            //String xmlRSS = getStringFromInputStream(getInputStream(dataToParse), "UTF-8");
            //xpp.setInput(new StringReader(xmlRSS));
            xpp.setInput(getInputStream(dataToParse), "UTF-8");

            boolean stared=false;
            int eventType = xpp.getEventType();
            //boolean insideItem = false;
            alist  = new LinkedList<NewsRSSitem>();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {

                // Found a start tag
                if(eventType == XmlPullParser.START_TAG)
                {

                    // Check which Tag we have
                    if (xpp.getName().equalsIgnoreCase("item"))//channel
                    {
                        if(!stared){
                            stared=true;
                        }

                    }
                    else
                        // Check which Tag we have
                        if (xpp.getName().equalsIgnoreCase("title")&&stared)
                        {

                            Info = new NewsRSSitem();
                            //
                            Info.setItemName(xpp.nextText());

                        }
                        else if (xpp.getName().equalsIgnoreCase("link")&&stared)
                        {

                            String temp =xpp.nextText();

                            Info.setitemWeb(temp);



                        }else if (xpp.getName().equalsIgnoreCase("description")&&stared)
                        {
                            String temp =xpp.nextText();
                            Info.setItemDesc(temp);


                        }


                }else
                if(eventType == XmlPullParser.END_TAG)
                {
                    if (xpp.getName().equalsIgnoreCase("item"))
                    {
                        //Log.e("MyTag","widget is " + widget.toString());
                        alist.add(Info);
                    }
                    else
                    if (xpp.getName().equalsIgnoreCase("channel"))
                    {
                        int size;
                        size = alist.size();
                        //Log.e("MyTag", "channel size is " + size);
                    }
                }

                // Get the next event
                eventType = xpp.next();

            } // End of while
        }
        catch (XmlPullParserException ae1)
        {
            Log.e("MyTag", "Parsing error " + ae1.toString());
        }
        catch (IOException ae1)
        {
            Log.e("MyTag", "IO error during parsing");
        }
        return alist;

    }
    //open conection
    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }

    }




}
