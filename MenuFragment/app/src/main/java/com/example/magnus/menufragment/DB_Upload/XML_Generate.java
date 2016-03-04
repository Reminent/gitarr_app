package com.example.magnus.menufragment.DB_Upload;

public class XML_Generate {
    public String imageTable (String imageTitle, String imageUrl) {
        String xmlImage = "<image>\n" +
                "<imageTitle>" + imageTitle + "</imageTitle>\n" +
                "<imageUrl>" + imageUrl + "</imageUrl>\n" +
                "</image>";
        return xmlImage;
    }

    public String advertTable (String advertDate, String advertDescription, String advertTitle, String productidFk) {
        String xmlAdvert = "<advert>\n" +
                "<advertDate>" + advertDate + "</advertDate>\n" +
                "<advertDescription>" + advertDescription + "</advertDescription>\n" +
                "<advertTitle>" + advertTitle + "</advertTitle>\n" +
                "<productidFk>" + productidFk + "</productidFk>\n" +
                "</advert>";
        return xmlAdvert;
    }

    public String consultationTable (String customerName, String customerPhone, String endDateAndTime, String startDateAndTime, String consultationDescripton) {
        String xmlConsultation = "<consultation>\n" +
                "<customerName>" + customerName + "</customerName>\n" +
                "<customerPhone>" + customerPhone + "</customerPhone>\n" +
                "<endDateAndTime>" + endDateAndTime + "</endDateAndTime>\n" +
                "<startDateAndTime>" + startDateAndTime + "</startDateAndTime>\n" +
                "<consultationDescripton>" + consultationDescripton + "</consultationDescripton>\n" +
                "</consultation>";
        return xmlConsultation;
    }

    public String inventoryTable (String inventoryQuantity, String productidFk) {
        String xmlInventory = "<inventory>\n" +
                "<inventoryQuantity>" + inventoryQuantity + "</inventoryQuantity>\n" +
                "<productidFk>" + productidFk + "</productidFk>\n" +
                "</inventory>";
        return xmlInventory;
    }

    public String productTable (String genre, String imageidFk, String manufacturer, String productName, String productid, String purchasePrice, String sellingPrice) {
        String xmlProduct = "<product>\n" +
                "<genre>" + genre + "</genre>\n" +
                "<imageidFk>" + imageidFk + "</imageidFk>\n" +
                "<manufacturer>" + manufacturer + "</manufacturer>\n" +
                "<productName>" + productName + "</productName>\n" +
                "<productid>" + productid + "</productid>\n" +
                "<purchasePrice>" + purchasePrice + "</purchasePrice>\n" +
                "<sellingPrice>" + sellingPrice + "</sellingPrice>\n" +
                "</product>";
        return xmlProduct;
    }

    public String transactionTable (String transactionAmount, String transactionDate) {
        String xmlTransaction = "<transaction>\n" +
                "<transactionAmount>" + transactionAmount + "</transactionAmount>\n" +
                "<transactionDate>" + transactionDate + "</transactionDate>\n" +
                "</transaction>";
        return xmlTransaction;
    }

    public String transactionConsultationTable (String consultationidFk, String transactionConsultationAmount, String transactionConsultationQuantity, String transactionidFk) {
        String xmlTransactionConsultation = "<transactionConsultation>\n" +
                "<consultationidFk>" + consultationidFk + "</consultationidFk>\n" +
                "<transactionConsultationAmount>" + transactionConsultationAmount + "</transactionConsultationAmount>\n" +
                "<transactionConsultationQuantity>" + transactionConsultationQuantity + "</transactionConsultationQuantity>\n" +
                "<transactionidFk>" + transactionidFk + "</transactionidFk>\n" +
                "</transactionConsultation>";
        return xmlTransactionConsultation;
    }

    public String transactionProductTable (String productidFk, String transactionProductAmount, String transactionProductQuantity, String transactionidFk) {
        String xmlTransactionProduct = "<transactionProduct>\n" +
                "<productidFk>" + productidFk + "</productidFk>\n" +
                "<transactionProductAmount>" + transactionProductAmount + "</transactionProductAmount>\n" +
                "<transactionProductQuantity>" + transactionProductQuantity + "</transactionProductQuantity>\n" +
                "<transactionidFk>" + transactionidFk + "</transactionidFk>\n" +
                "</transactionProduct>";
        return xmlTransactionProduct;
    }
}
