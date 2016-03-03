package com.example.magnus.menufragment.XML_Parsing;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Inventory_Parse {
    public List<Inventory> parse(String url) {
        List<Inventory> inventories = null;
        XmlPullParser parser = Xml.newPullParser();
        try {
            // auto-detect the encoding from the stream
            parser.setInput(new StringReader(url));
            int eventType = parser.getEventType();
            Inventory currentInventory = null;
            boolean done = false;
            while (eventType != XmlPullParser.END_DOCUMENT && !done){
                String name = null;
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        inventories = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("inventory")){
                            currentInventory = new Inventory();
                        } else if (currentInventory != null){
                            if (name.equalsIgnoreCase("inventoryQuantity")){
                                currentInventory.setInventoryQuantity(parser.nextText());
                            } else if (name.equalsIgnoreCase("inventoryid")){
                                currentInventory.setInventoryid(parser.nextText());
                            } else if (name.equalsIgnoreCase("genre")){
                                currentInventory.setGenre(parser.nextText());
                            } else if (name.equalsIgnoreCase("imageTitle")){
                                currentInventory.setImageTitle(parser.nextText());
                            } else if (name.equalsIgnoreCase("imageUrl")){
                                currentInventory.setImageUrl(parser.nextText());
                            } else if (name.equalsIgnoreCase("imageid")){
                                currentInventory.setImageid(parser.nextText());
                            } else if (name.equalsIgnoreCase("manufacturer")){
                                currentInventory.setManufacturer(parser.nextText());
                            } else if (name.equalsIgnoreCase("productName")){
                                currentInventory.setProductName(parser.nextText());
                            } else if (name.equalsIgnoreCase("productid")){
                                currentInventory.setProductid(parser.nextText());
                            } else if (name.equalsIgnoreCase("purchasePrice")){
                                currentInventory.setPurchasePrice(parser.nextText());
                            } else if (name.equalsIgnoreCase("sellingPrice")){
                                currentInventory.setSellingPrice(parser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("inventory") &&
                                currentInventory != null){
                            inventories.add(currentInventory);
                        } else if (name.equalsIgnoreCase("inventories")){
                            done = true;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return inventories;
    }
}
