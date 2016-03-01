package com.example.magnus.menufragment;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Advert_Parse {
    /*
    public ArrayList<Advert> parseXML (String results)
            throws XmlPullParserException, IOException
    {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        ArrayList<Advert> adds = null;
        Advert advert = null;

        xpp.setInput(new StringReader(results));
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name = null;

            switch(eventType) {
                case XmlPullParser.START_DOCUMENT:
                    adds = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    name = xpp.getName();
                    if (name == "product") {
                        advert = new Advert();
                    } else if (advert != null) {
                        if (name == "genre") {
                            advert.setGenre(xpp.nextText());
                        } else if (name == "imageURl") {
                            advert.setImageURl(xpp.nextText());
                        } else if (name == "imageId") {
                            advert.setImageId(xpp.nextText());
                        } else if (name == "title") {
                            advert.setTitle(xpp.nextText());
                        } else if (name == "manufacturer") {
                            advert.setManufacturer(xpp.nextText());
                        } else if (name == "productName") {
                            advert.setProductName(xpp.nextText());
                        } else if (name == "productId") {
                            advert.setProductId(xpp.nextText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = xpp.getName();

                    if (name.equalsIgnoreCase("product") && advert != null) {
                        adds.add(advert);
                    }
                    break;
            }
            eventType = xpp.next();
        }
        return adds;
    }*/

    //Nytt

    List<Advert> adverts;
    private Advert advert;
    private String text;

    public Advert_Parse() {
        adverts = new ArrayList<Advert>();
    }

    public List<Advert> getAdverts() {
        return adverts;
    }

    public List<Advert> parse(String is) {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();
            //parser.setInput(is, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("product")) {
                            // create a new instance of employee
                            advert = new Advert();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("product")) {
                            // add employee object to list
                            adverts.add(advert);
                        } else if (tagname.equalsIgnoreCase("genre")) {
                            advert.setGenre(text);
                        } else if (tagname.equalsIgnoreCase("imageURl")) {
                            advert.setImageURl(text);
                        } else if (tagname.equalsIgnoreCase("imageId")) {
                            advert.setImageId(text);
                        } else if (tagname.equalsIgnoreCase("title")) {
                            advert.setTitle(text);
                        } else if (tagname.equalsIgnoreCase("manufacturer")) {
                            advert.setManufacturer(text);
                        } else if (tagname.equalsIgnoreCase("productName")) {
                            advert.setProductName(text);
                        } else if (tagname.equalsIgnoreCase("productId")) {
                            advert.setProductId(text);
                        }
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return adverts;
    }
}
