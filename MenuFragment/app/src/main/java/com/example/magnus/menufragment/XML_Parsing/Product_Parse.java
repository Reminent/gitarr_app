package com.example.magnus.menufragment.XML_Parsing;

import android.util.Xml;

import com.example.magnus.menufragment.XML_Parsing.Product;

import org.xmlpull.v1.XmlPullParser;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Product_Parse {
    public List<Product> parse(String url) {
        List<Product> products = null;
        XmlPullParser parser = Xml.newPullParser();
        try {
            // auto-detect the encoding from the stream
            parser.setInput(new StringReader(url));
            int eventType = parser.getEventType();
            Product currentProduct = null;
            boolean done = false;
            while (eventType != XmlPullParser.END_DOCUMENT && !done){
                String name = null;
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        products = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("product")){
                            currentProduct = new Product();
                        } else if (currentProduct != null){
                            if (name.equalsIgnoreCase("genre")){
                                currentProduct.setGenre(parser.nextText());
                            } else if (name.equalsIgnoreCase("imageURl")){
                                currentProduct.setImageURl(parser.nextText());
                            } else if (name.equalsIgnoreCase("imageId")){
                                currentProduct.setImageId(parser.nextText());
                            } else if (name.equalsIgnoreCase("menufacturer")){
                                currentProduct.setManufacturer(parser.nextText());
                            } else if (name.equalsIgnoreCase("productName")){
                                currentProduct.setProductName(parser.nextText());
                            } else if (name.equalsIgnoreCase("productId")){
                                currentProduct.setProductId(parser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("product") &&
                                currentProduct != null){
                            products.add(currentProduct);
                        } else if (name.equalsIgnoreCase("products")){
                            done = true;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return products;
    }
}