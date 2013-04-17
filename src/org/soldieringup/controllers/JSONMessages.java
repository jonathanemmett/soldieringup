package org.soldieringup.controllers;

import java.util.GregorianCalendar;

import org.bson.types.ObjectId;
import org.soldieringup.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
@RequestMapping("/rest")
public class JSONMessages {
	// Example:
	//http://localhost:8080/soldieringup/rest/messages

	@RequestMapping(value="accounts", method = RequestMethod.GET)
	public @ResponseBody String handleGetMessages(@RequestParam String email) {
		System.out.println ("Received request for email:" + email);
		Gson gson = new Gson();
		return gson.toJson(getMessage (email));
	}

	@RequestMapping(value="accounts", params = {"msgID"}, method = RequestMethod.GET)
	public @ResponseBody String handleGetMessage(@RequestParam String msgID) {
		System.out.println ("Received request for msgID:" + msgID);
		Gson gson = new Gson();
		return gson.toJson(getMessage (msgID));
	}

	//http://localhost:8080/soldieringup/rest/messages/msg.do?msgID=<id>
	@RequestMapping(value="accounts", method = RequestMethod.POST)
	public @ResponseBody String handlePostMessages(@RequestParam String msgID) {
		Gson gson = new Gson();
		return gson.toJson(getMessage (msgID));
	}

	@RequestMapping(value="accounts", params = {"msgID"}, method = RequestMethod.POST)
	public @ResponseBody String handlePostMessage(@RequestParam String msgID) {
		System.out.println ("Received request for msgID:" + msgID);
		Gson gson = new Gson();
		return gson.toJson(getMessage (msgID));
	}

	private Message getMessage (String msgID)
	{
		Message msg = new Message ();
		msg.setId (new ObjectId ());
		msg.setAuthorId (new ObjectId ());
		msg.setTitle ("Question");
		msg.setBody ("Have you ever done this before");
		msg.setDate (new GregorianCalendar ());
		return msg;
	}
}