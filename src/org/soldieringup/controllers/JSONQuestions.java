package org.soldieringup.controllers;

import java.util.List;

import org.soldieringup.Question;
import org.soldieringup.service.MongoEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class JSONQuestions extends BasePubJSONController
{
	@Autowired
	private MongoEngine engine;

	@RequestMapping(value="posts", method = RequestMethod.GET)
	/**
	 * Queries for the latest 10 questions that have been posted.
	 * @return Question
	 */
	public @ResponseBody String getQuestions ()
	{
		List<Question> arrQuestions = engine.getTopQuestions ();
		final GsonBuilder builder = new GsonBuilder();
		builder.setVersion(1.0);
		builder.excludeFieldsWithoutExposeAnnotation ();
		final Gson gson = builder.create();

		System.out.println ("Question count:" + arrQuestions.size ());
		String json = gson.toJson(arrQuestions);
		return json;
	}
}
