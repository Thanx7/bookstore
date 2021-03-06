package org.training.allegro.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.training.allegro.utilities.Jena;

import com.franz.agraph.jena.AGModel;
import com.hp.hpl.jena.rdf.model.RDFNode;

@WebServlet(name = "Main", urlPatterns = { "/main" })
public class Main extends Abstract {
	private static final long serialVersionUID = 1L;
	private List<List<RDFNode>> triples = new ArrayList<>();

	protected void performTask(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		AGModel model = (AGModel) session.getAttribute("model");
		try {
			triples = Jena.example3(model);
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("triples", triples);
		jump("/jsp/main.jsp", request, response);
	}

}