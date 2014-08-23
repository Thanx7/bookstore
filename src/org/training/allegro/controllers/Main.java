package org.training.allegro.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.training.allegro.utilities.Jena;

@WebServlet(name = "Main", urlPatterns = {"/jsp/main"})
public class Main extends Abstract {
	private static final long serialVersionUID = 1L;

	protected void performTask(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println("1");
		try {
			//Jena.example3(false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		jump("/jsp/main.jsp", request, response);
	}

}