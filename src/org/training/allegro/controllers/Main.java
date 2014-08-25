package org.training.allegro.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.allegro.utilities.Jena;

import com.hp.hpl.jena.rdf.model.RDFNode;

@WebServlet(name = "Main", urlPatterns = { "/jsp/main" })
public class Main extends Abstract {
	private static final long serialVersionUID = 1L;
	private List<List<RDFNode>> triples = new ArrayList<>();

	protected void performTask(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			triples = Jena.example3(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("triples", triples);
		jump("/jsp/main.jsp", request, response);
	}

}