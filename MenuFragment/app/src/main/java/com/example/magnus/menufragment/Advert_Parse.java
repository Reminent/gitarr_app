package com.example.magnus.menufragment;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Advert_Parse {
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
    }
}
