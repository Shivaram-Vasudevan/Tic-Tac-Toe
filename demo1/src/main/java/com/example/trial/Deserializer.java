package com.example.trial;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Deserializer {
	private static Deserializer deserializer;
	static {
		deserializer = new Deserializer();
	}
	
	private Deserializer() { }
	
	public static Deserializer getDeserializer() {
		return deserializer;
	}
	
	public Board getBoardFromJSONString(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Board board = mapper.reader().forType(Board.class).readValue(jsonString);
		return board;
	}
}
