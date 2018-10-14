package com.example.trial;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class GameController {

	@Autowired
	private ComputerPlayer computerPlayer;
	
	@CrossOrigin
	@RequestMapping(value="/nextMove", method=RequestMethod.GET)
	public String index(@RequestParam(value="board")String boardAsJsonString) {
		System.out.println("String received by the service:" + boardAsJsonString);
		Deserializer deserializer = Deserializer.getDeserializer();
		ObjectMapper mapper = new ObjectMapper();
		Board board = null, nextMove = null;
		String nextBoardMove = null;
		try {
			board = deserializer.getBoardFromJSONString(boardAsJsonString);
			System.out.println("Deserialized board:" + board);
			nextMove = computerPlayer.findNextBestMove(board);
			System.out.println(nextMove);
			nextBoardMove = mapper.writeValueAsString(nextMove);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nextBoardMove;
	}
}
