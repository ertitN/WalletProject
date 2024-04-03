package com.example.walletservice.model.enumeration;

public enum Currency {
    TRY("Türk Lirası","₺"),
    USD("Amerikan Doları","$"),
    EUR("Euro","€"),
    GBP("İngiliz Poundu","");

    private String currencyName;
    private String currencySign;

    Currency(String currencyName, String currencySign) {
        this.currencyName = currencyName;
        this.currencySign = currencySign;
    }


    Currency(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySign() {
        return currencySign;
    }

    public void setCurrencySign(String currencySign) {
        this.currencySign = currencySign;
    }
}
