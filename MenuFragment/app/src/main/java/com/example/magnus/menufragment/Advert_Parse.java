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
    public List<Advert> parse (String results)
            throws XmlPullParserException, IOException
    {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parser = factory.newPullParser();

        List<Advert> adverts = null;
        Advert advert = null;

        parser.setInput(new StringReader(results));
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name = null;

            switch(eventType) {
                case XmlPullParser.START_DOCUMENT:
                    advert = new Advert();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name == "product") {
                        advert = new Advert();
                    } else if (advert != null) {
                        if (name == "genre") {
                            advert.setGenre(parser.nextText());
                        } else if (name == "imageURl") {
                            advert.setImageURl(parser.nextText());
                        } else if (name == "imageId") {
                            advert.setImageId(parser.nextText());
                        } else if (name == "title") {
                            advert.setTitle(parser.nextText());
                        } else if (name == "manufacturer") {
                            advert.setManufacturer(parser.nextText());
                        } else if (name == "productName") {
                            advert.setProductName(parser.nextText());
                        } else if (name == "productId") {
                            advert.setProductId(parser.nextText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();

                    if (name.equalsIgnoreCase("product") && advert != null) {
                        adverts.add(advert);
                    }
                    break;
            }
            eventType = parser.next();
        }
        return adverts;
    }
    */

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
            parser.setInput(new StringReader(is));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("products")) {
                            // create a new instance of adverts
                            advert = new Advert();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("products")) {
                            // add advert object to list
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
