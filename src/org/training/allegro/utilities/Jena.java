package org.training.allegro.utilities;

import java.util.ArrayList;
import java.util.List;

import com.franz.agraph.jena.AGGraph;
import com.franz.agraph.jena.AGGraphMaker;
import com.franz.agraph.jena.AGModel;
import com.franz.agraph.jena.AGQuery;
import com.franz.agraph.jena.AGQueryExecutionFactory;
import com.franz.agraph.jena.AGQueryFactory;
import com.franz.agraph.repository.AGCatalog;
import com.franz.agraph.repository.AGRepository;
import com.franz.agraph.repository.AGRepositoryConnection;
import com.franz.agraph.repository.AGServer;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

public class Jena {
	static private final String SERVER_URL = "http://epbyminw2052t1:10035";
	static private final String CATALOG_ID = "/";
	static private final String USERNAME = "test";
	static private final String PASSWORD = "1";

	private static List<List<RDFNode>> triples = new ArrayList<>();

	public static AGGraphMaker example1(boolean close) throws Exception {
		AGServer server = new AGServer(SERVER_URL, USERNAME, PASSWORD);
		AGCatalog catalog = server.getCatalog(CATALOG_ID);
		AGRepository myRepository = null;
		AGRepositoryConnection conn = null;
		try {
			System.out.println("Available repositories in catalog "
					+ (catalog.getCatalogName()) + ": "
					+ catalog.listRepositories());
			myRepository = catalog.openRepository("Bookstore");
			conn = myRepository.getConnection();
			System.out.println("Repository " + (myRepository.getRepositoryID())
					+ " is up! It contains " + (conn.size()) + " statements.");
		} catch (Exception e) {
			e.printStackTrace();
		}

		AGGraphMaker maker = new AGGraphMaker(conn);

		if (close) {
			maker.close();
			conn.close();
			myRepository.shutDown();
			return null;
		}
		return maker;
	}

	public static AGModel example2(boolean close) throws Exception {
		AGGraphMaker maker = example1(false);
		AGGraph graph = maker.getGraph();
		AGModel model = new AGModel(graph);

		System.out.println("Triple count: " + model.size());

		if (close) {
			model.close();
			graph.close();
			maker.close();
			return null;
		}
		return model;
	}

	public static List<List<RDFNode>> example3(boolean close) throws Exception {
		AGModel model = example2(false);

		try {
			String queryString = "SELECT ?s ?p ?o  WHERE {?s ?p ?o .}";

			AGQuery sparql = AGQueryFactory.create(queryString);
			QueryExecution qe = AGQueryExecutionFactory.create(sparql, model);
			try {
				ResultSet results = qe.execSelect();

				int count = 1;
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
		} finally {
			model.close();
		}
		return triples;
	}
}
