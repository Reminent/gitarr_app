package com.example.magnus.menufragment.XML_Parsing;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Consultation_Parse {
    public List<Consultation> parse(String url) {
        List<Consultation> consultations = null;
        XmlPullParser parser = Xml.newPullParser();
        try {
            // auto-detect the encoding from the stream
            parser.setInput(new StringReader(url));
            int eventType = parser.getEventType();
            Consultation currentConsultation = null;
            boolean done = false;
            while (eventType != XmlPullParser.END_DOCUMENT && !done){
                String name = null;
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        consultations = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("consultation")){
                            currentConsultation = new Consultation();
                        } else if (currentConsultation != null){
                            if (name.equalsIgnoreCase("consultationDescription")){
                                currentConsultation.setConsultationDescription(parser.nextText());
                            } else if (name.equalsIgnoreCase("consultationid")){
                                currentConsultation.setConsultationid(parser.nextText());
                            } else if (name.equalsIgnoreCase("customerName")){
                                currentConsultation.setCustomerName(parser.nextText());
                            } else if (name.equalsIgnoreCase("customerPhone")){
                                currentConsultation.setCustomerPhone(parser.nextText());
                            } else if (name.equalsIgnoreCase("endDateAndTime")){
                                currentConsultation.setEndDateAndTime(parser.nextText());
                            } else if (name.equalsIgnoreCase("startDateAndTime")){
                                currentConsultation.setStartDateAndTime(parser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("consultation") &&
                                currentConsultation != null){
                            consultations.add(currentConsultation);
                        } else if (name.equalsIgnoreCase("consultations")){
                            done = true;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return consultations;
    }
}
