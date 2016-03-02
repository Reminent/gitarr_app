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

    public List<Advert> parse(String is) {
        List<Advert> adverts = new ArrayList();
        Advert advert = new Advert();
        String text = "";
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
                        if (tagname.equalsIgnoreCase("product")) {
                            // create a new instance of adverts
                            advert = new Advert();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("product")) {
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
                eventType = parser.getEventType();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return adverts;
    }
}
