package org.training.allegro.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Welcome", urlPatterns = {"/jsp/welcome"})
public class Welcome extends Abstract {
	private static final long serialVersionUID = 1L;

	protected void performTask(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		jump("/jsp/welcome.jsp", request, response);
	}
}