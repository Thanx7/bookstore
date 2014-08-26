package org.training.allegro.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.franz.agraph.jena.AGModel;
import com.franz.agraph.jena.AGQuery;
import com.franz.agraph.jena.AGQueryExecutionFactory;
import com.franz.agraph.jena.AGQueryFactory;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

@WebServlet(name = "Ex", urlPatterns = { "/ex" })
public class Ex extends Abstract {
	private static final long serialVersionUID = 1L;

	protected void performTask(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<List<RDFNode>> triples = new ArrayList<>();
		HttpSession session = request.getSession();
		AGModel model = (AGModel) session.getAttribute("model");

		int zIndex = Integer.parseInt(request.getParameter("zIndex"));
		String queryString = null;
		switch (zIndex) {
		case 1:
			queryString = "SELECT ?s WHERE { ?s <http://www.w3.org/2000/01/rdf-schema#subClassOf> <http://dbpedia.org/ontology/#PrintedMatter> }";
			break;
		case 2:
			queryString = "SELECT ?s ?o WHERE {?s <http://www.w3.org/2000/01/rdf-schema#subClassOf> ?o}";
			break;
		case 3:
			queryString = "SELECT ?s ?o WHERE {  {?s <http://example.org/quantity> ?o}  UNION  {?s <http://example.org/price> ?o}}";
			break;
		case 4:
			queryString = "select ?s where { ?s rdf:type <owl:Class> }";
			break;
		case 5:
			queryString = "SELECT ?s ?o WHERE { ?s <http://example.org/quantity> ?o . FILTER (?o >= 100)}";
			break;
		case 6:
			queryString = "SELECT ?s ?p ?o WHERE { ?s ?p ?o . FILTER regex(?o, \"Map\") }";
			break;
		case 7:
			queryString = "SELECT ?s ?p ?o WHERE { ?s <http://example.org/quantity> ?p . OPTIONAL {?s <http://example.org/price> ?o }}";
			break;
		default:
			break;
		}

		AGQuery sparql = AGQueryFactory.create(queryString);
		QueryExecution qe = AGQueryExecutionFactory.create(sparql, model);
		try {
			ResultSet results = qe.execSelect();

			while (results.hasNext()) {
				QuerySolution result = results.next();
				List<RDFNode> triple = new ArrayList<>();
				triple.add(result.get("s"));
				triple.add(result.get("p"));
				triple.add(result.get("o"));
				triples.add(triple);
			}
		} finally {
			qe.close();
		}

		request.setAttribute("triples", triples);
		jump("/jsp/ex.jsp", request, response);
	}
}