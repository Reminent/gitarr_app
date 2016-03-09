package com.example.magnus.menufragment.XML_Parsing;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Advert_Parse {
    public List<Advert> parse(String url) {
        List<Advert> adverts = null;
        XmlPullParser parser = Xml.newPullParser();
        try {
            // auto-detect the encoding from the stream
            parser.setInput(new StringReader(url));
            int eventType = parser.getEventType();
            Advert currentAdvert = null;
            boolean done = false;
            while (eventType != XmlPullParser.END_DOCUMENT && !done){
                String name = null;
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        adverts = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("advert")){
                            currentAdvert = new Advert();
                        } else if (currentAdvert != null){
                            if (name.equalsIgnoreCase("advertDate")){
                                currentAdvert.setAdvertDate(parser.nextText());
                            } else if (name.equalsIgnoreCase("advertTitle")){
                                currentAdvert.setAdvertTitle(parser.nextText());
                            } else if (name.equalsIgnoreCase("advertid")){
                                currentAdvert.setAdvertid(parser.nextText());
                            } else if (name.equalsIgnoreCase("advertDescription")){
                                currentAdvert.setAdvertDescription(parser.nextText());
                            } else if (name.equalsIgnoreCase("genre")){
                                currentAdvert.setGenre(parser.nextText());
                            } else if (name.equalsIgnoreCase("imageTitle")){
                                currentAdvert.setImageTitle(parser.nextText());
                            } else if (name.equalsIgnoreCase("imageUrl")){
                                currentAdvert.setImageUrl(parser.nextText());
                            } else if (name.equalsIgnoreCase("imageid")){
                                currentAdvert.setImageid(parser.nextText());
                            } else if (name.equalsIgnoreCase("manufacturer")){
                                currentAdvert.setManufacturer(parser.nextText());
                            } else if (name.equalsIgnoreCase("productName")){
                                currentAdvert.setProductName(parser.nextText());
                            } else if (name.equalsIgnoreCase("productid")){
                                currentAdvert.setProductid(parser.nextText());
                            } else if (name.equalsIgnoreCase("purchasePrice")){
                                currentAdvert.setPurchasePrice(parser.nextText());
                            } else if (name.equalsIgnoreCase("sellingPrice")){
                                currentAdvert.setSellingPrice(parser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("advert") &&
                                currentAdvert != null){
                            adverts.add(currentAdvert);
                        } else if (name.equalsIgnoreCase("adverts")){
                            done = true;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return adverts;
    }
}
