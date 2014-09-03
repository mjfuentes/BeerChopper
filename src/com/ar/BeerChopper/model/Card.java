package com.ar.BeerChopper.model;

/**
 * Clase que modela tarjeta.
 * Posee un n�mero de tarjeta, correspondiente con el c�digo RFID de la tarjeta f�sica.
 * Posee un usuario, propietario de la tarjeta.
 * Posee una fecha de alta, correspondiente a cuando se registr� la adquisici�n de la tarjeta por parte del usuario.
 * Posee una fecha de baja, correspondiente a cuando se dio de baja la tarjeta en el sistema (Por defecto valor null).
 */

import java.util.Date;

public class Card {
	
	private Long cardId;
	private long number;
	private User user;

	
	public Card(){}
	
	public Card(long number, User user, Date createDate) {
		this.number = number;
		this.user = user;

	}

	
	@Override
	public String toString(){
		String print = String.format("Card number: %s for user: %s ", 
					   this.getNumber(), this.getUser().toString());
		return print;
	}
	
	public Long getCardId() {
		return cardId;
	}
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
		
}
