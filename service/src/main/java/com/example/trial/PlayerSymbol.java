package com.example.trial;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PlayerSymbol {
	COMPUTER("C"),//position is occupied by computer
	PLAYER("P"),//position is occupied by player
	EMPTY("E")//position is empty
	;
	
	private final String representation;
	
	private PlayerSymbol(String representation) {
		this.representation = representation;
	}
	
	@JsonValue
	public String getRepresentation() {
		return representation;
	}
}
