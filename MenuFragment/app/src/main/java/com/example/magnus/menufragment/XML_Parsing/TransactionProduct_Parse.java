package com.example.magnus.menufragment.XML_Parsing;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class TransactionProduct_Parse {
    public List<TransactionProduct> parse(String url) {
        List<TransactionProduct> transactionProducts = null;
        XmlPullParser parser = Xml.newPullParser();
        try {
            // auto-detect the encoding from the stream
            parser.setInput(new StringReader(url));
            int eventType = parser.getEventType();
            TransactionProduct currentTransactionProduct = null;
            boolean done = false;
            while (eventType != XmlPullParser.END_DOCUMENT && !done){
                String name = null;
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        transactionProducts = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("transactionProduct")){
                            currentTransactionProduct = new TransactionProduct();
                        } else if (currentTransactionProduct != null){
                            if (name.equalsIgnoreCase("genre")){
                                currentTransactionProduct.setGenre(parser.nextText());
                            } else if (name.equalsIgnoreCase("imageTitle")){
                                currentTransactionProduct.setImageTitle(parser.nextText());
                            } else if (name.equalsIgnoreCase("imageUrl")){
                                currentTransactionProduct.setImageUrl(parser.nextText());
                            } else if (name.equalsIgnoreCase("imageid")){
                                currentTransactionProduct.setImageid(parser.nextText());
                            } else if (name.equalsIgnoreCase("manufacturer")){
                                currentTransactionProduct.setManufacturer(parser.nextText());
                            } else if (name.equalsIgnoreCase("productName")){
                                currentTransactionProduct.setProductName(parser.nextText());
                            } else if (name.equalsIgnoreCase("productid")){
                                currentTransactionProduct.setProductid(parser.nextText());
                            } else if (name.equalsIgnoreCase("purchasePrice")){
                                currentTransactionProduct.setPurchasePrice(parser.nextText());
                            } else if (name.equalsIgnoreCase("sellingPrice")){
                                currentTransactionProduct.setSellingPrice(parser.nextText());
                            } else if (name.equalsIgnoreCase("transactionProductAmount")){
                                currentTransactionProduct.setTransactionProductAmount(parser.nextText());
                            } else if (name.equalsIgnoreCase("transactionProductQuantity")){
                                currentTransactionProduct.setTransactionProductQuantity(parser.nextText());
                            } else if (name.equalsIgnoreCase("transactionProductid")){
                                currentTransactionProduct.setTransactionProductid(parser.nextText());
                            } else if (name.equalsIgnoreCase("transactionAmount")){
                                currentTransactionProduct.setTransactionAmount(parser.nextText());
                            } else if (name.equalsIgnoreCase("transactionDate")){
                                currentTransactionProduct.setTransactionDate(parser.nextText());
                            } else if (name.equalsIgnoreCase("transactionid")){
                                currentTransactionProduct.setTransactionid(parser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("transactionProduct") &&
                                currentTransactionProduct != null){
                            transactionProducts.add(currentTransactionProduct);
                        } else if (name.equalsIgnoreCase("transactionProducts")){
                            done = true;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return transactionProducts;
    }
}