package com.example.magnus.menufragment.XML_Parsing;

import android.util.Xml;

import com.example.magnus.menufragment.XML_Parsing.Product;

import org.xmlpull.v1.XmlPullParser;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Transaction_Parse {

    public List<Transaction> parse(String url) {
        List<Transaction> transactions = null;
        XmlPullParser parser = Xml.newPullParser();

        try {
            // auto-detect the encoding from the stream
            parser.setInput(new StringReader(url));
            int eventType = parser.getEventType();
            Transaction currentTransaction = null;
            boolean done = false;
            while (eventType != XmlPullParser.END_DOCUMENT && !done){
                String name = null;
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        transactions = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("transaction")){
                            currentTransaction = new Transaction();
                        } else if (currentTransaction != null){
                            if (name.equalsIgnoreCase("transactionAmount")){
                                currentTransaction.setTransactionAmount(parser.nextText());
                            } else if (name.equalsIgnoreCase("transactionDate")){
                                currentTransaction.setTransactionDate(parser.nextText());
                            } else if (name.equalsIgnoreCase("transactionid")){
                                currentTransaction.setTransactionid(parser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("transaction") &&
                                currentTransaction != null){
                            transactions.add(currentTransaction);
                        } else if (name.equalsIgnoreCase("transactions")){
                            done = true;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }
}