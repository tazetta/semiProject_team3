package com.mvc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.service.WeatherCastService;

@WebServlet({"/mainWeatherCast"})
public class WeatherCastController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String subAddr = req.getRequestURI().substring(req.getContextPath().length());

		WeatherCastService service = new WeatherCastService(req, resp);

		switch (subAddr) {
		case "/mainWeatherCast":
			System.out.println("mainWeatherCast 요청");
			service.mainWeatherCast();
			break;
		}
	}

}
