package org.training.allegro.utilities;

import java.util.List;

import org.openrdf.OpenRDFException;
import org.openrdf.repository.RepositoryException;

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

	public static AGGraphMaker example1(boolean close) throws Exception {
		AGServer server = new AGServer(SERVER_URL, USERNAME, PASSWORD);
		try {
			System.out.println("Available catalogs: " + server.listCatalogs());
		} catch (OpenRDFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		AGCatalog catalog = server.getCatalog(CATALOG_ID);
		try {
			System.out.println("Available repositories in catalog "
					+ (catalog.getCatalogName()) + ": "
					+ catalog.listRepositories());
		} catch (OpenRDFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		AGRepository myRepository = null;
		try {
			myRepository = catalog.openRepository("Bookstore");
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AGRepositoryConnection conn = null;
		try {
			conn = myRepository.getConnection();

			System.out.println("Got a connection.");
			System.out.println("Repository " + (myRepository.getRepositoryID())
					+ " is up! It contains " + (conn.size()) + " statements.");
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		AGGraphMaker maker = new AGGraphMaker(conn);
		System.out.println("Got a graph maker for the connection.");

		List<String> indices = conn.listValidIndices();
		System.out.println("All valid triple indices: " + indices);

		indices = conn.listIndices();
		System.out.println("Current triple indices: " + indices);

		if (close) {
			// tidy up
			maker.close();
			conn.close();
			myRepository.shutDown();
			return null;
		}
		return maker;
	}

	public static AGModel example2(boolean close) throws Exception {
		System.out.println("\nStarting example2().");
		AGGraphMaker maker = example1(false);
		AGGraph graph = maker.getGraph();
		AGModel model = new AGModel(graph);

		System.out.println("Triple count before inserts: " + model.size()); //
		// model.add(bob, name, bobsName);
		if (close) {
			model.close();
			graph.close();
			maker.close();
			return null;
		}
		return model;
	}

	public static void example3(boolean close) throws Exception {
		AGModel model = example2(false);
		System.out.println("\nStarting example3().");
		try {
			String queryString = "SELECT ?s ?p ?o  WHERE {?s ?p ?o .}";

			AGQuery sparql = AGQueryFactory.create(queryString);
			QueryExecution qe = AGQueryExecutionFactory.create(sparql, model);
			try {
				ResultSet results = qe.execSelect();

				while (results.hasNext()) {
					QuerySolution result = results.next();
					RDFNode s = result.get("s");
					RDFNode p = result.get("p");
					RDFNode o = result.get("o");
					System.out.println(" { " + s + " " + p + " " + o + " . }");
				}
			} finally { // qe.close();
			}
		} finally { // model.close();
		}

	}
}
