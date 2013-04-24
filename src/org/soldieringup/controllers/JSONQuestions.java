package org.soldieringup.controllers;

import org.soldieringup.service.MongoEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JSONQuestions extends BasePubJSONController
{
	@Autowired
	private MongoEngine engine;

	@RequestMapping(value="questions", method = RequestMethod.GET)
	/**
	 * Queries for the latest 10 questions that have been posted.
	 * @return Question
	 */
	public @ResponseBody String getQuestions ()
	{
		return toJson(engine.getTopQuestions ());
	}
}
