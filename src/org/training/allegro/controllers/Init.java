package org.training.allegro.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.openrdf.repository.RepositoryException;
import org.training.allegro.utilities.ModelProvider;

import com.franz.agraph.jena.AGModel;

@WebServlet(name = "Init", urlPatterns = { "/init" })
public class Init extends Abstract {
	private static final long serialVersionUID = 1L;

	protected void performTask(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {
			AGModel model = ModelProvider.getModel();
			session.setAttribute("size", model.size());
			session.setAttribute("model", model);
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		jump("/jsp/welcome.jsp", request, response);
	}
}