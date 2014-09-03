package com.ar.BeerChopper.model;

/**
 * Clase que modela usuario.
 * Posee un nombre de usuario (es �nico por cada usuario).
 * Posee una tarjeta actual, que es la tarjeta que actualmente tiene registrada ese usuario.
 */
public class User {
    
    private Long userId;
    private String userName;
    private Card currentCard;
    private String email;
    private boolean erased = false;
    
    public User(){}
    
    public User(String userName){
    	this.setUserName(userName);
    }
    
    public User(String userName, Card card){
    	this.setCurrentCard(card);
    	this.setUserName(userName);
    }
    
    public void erase(){
    	this.erased = true;
    }
    
    public Long getUserId() {
		return userId;
	}
    public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Card getCurrentCard() {
		return currentCard;
	}
	public void setCurrentCard(Card currentCard) {
		this.currentCard = currentCard;
	}
	public boolean isErased() {
		return erased;
	}
	public void setErased(boolean erased) {
		this.erased = erased;
	}
	@Override
	public String toString(){
    	return this.getUserName();
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
           
}
