package org.training.allegro.controllers;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.allegro.utilities.Jena;

@WebServlet(name = "Main", urlPatterns = { "/jsp/main" })
public class Main extends Abstract {
	private static final long serialVersionUID = 1L;

	protected void performTask(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Date d = new Date();
		request.setAttribute("date", d);

		try {
			Jena.example3(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		jump("/jsp/main.jsp", request, response);
	}

}