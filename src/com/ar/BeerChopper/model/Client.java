package com.ar.BeerChopper.model;


import com.google.gson.annotations.SerializedName;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Clase que modela un cliente, subclase de usuario.
 * Posee un saldo en dinero.
 * Posee un email, que tiene que ser �nico por cada cliente.
 */
public class Client extends User{

    @SerializedName("cash")
    private float cash;

    @SerializedName("lastConsume")
    private float lastConsume;

    public Client(String userName, Card card, float cash, String email){
    	super(userName, card);
    	this.setCash(cash);
    	this.setEmail(email);
    }
    
   public Client(String userName, float cash, String email) {
	   super(userName);
	   this.setCash(cash);
   	   this.setEmail(email);
	}

	public boolean hasCash() {
		return this.getCash() > 0;
	}
    
	private String generateProblemWithFormatMessage() {
		return "Problem with format";
	}
    
    public float getCash() {
		return cash;
	}
	public void setCash(float cash) {
		this.cash = cash;
	}

    public synchronized void cashUpdate(float anAmount){
        if (this.getCash() - anAmount >= 0){
            Float newCash = this.getCash() - anAmount;
            NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
            try {
                float fixedCash = numberFormat.parse(newCash.toString()).floatValue();
                this.setCash(fixedCash);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else { this.setCash(0);}
    }

    public void addCash(float anAmount){
        Float newCash = this.getCash() + anAmount;
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        try {
            float fixedCash = numberFormat.parse(newCash.toString()).floatValue();
            this.setCash(fixedCash);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public float getLastConsume() {
        return lastConsume;
    }

    public void setLastConsume(float lastConsume) {
        this.lastConsume = lastConsume;
    }
}
