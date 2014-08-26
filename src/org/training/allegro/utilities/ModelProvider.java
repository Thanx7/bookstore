package org.training.allegro.utilities;

import org.openrdf.repository.RepositoryException;

import com.franz.agraph.jena.AGGraph;
import com.franz.agraph.jena.AGGraphMaker;
import com.franz.agraph.jena.AGModel;
import com.franz.agraph.repository.AGCatalog;
import com.franz.agraph.repository.AGRepository;
import com.franz.agraph.repository.AGRepositoryConnection;
import com.franz.agraph.repository.AGServer;

public class ModelProvider {
	private final static String SERVER_URL = "http://epbyminw2052t1:10035";
	private final static String CATALOG_ID = "/";
	private final static String USERNAME = "test";
	private final static String PASSWORD = "1";

	private static AGRepositoryConnection conn;

	private static AGRepositoryConnection getConnection()
			throws RepositoryException {
		if (conn == null || conn.isEmpty()) {
			AGServer server = new AGServer(SERVER_URL, USERNAME, PASSWORD);
			AGCatalog catalog = server.getCatalog(CATALOG_ID);
			try {
				System.out.println("Available repositories in catalog "
						+ (catalog.getCatalogName()) + ": "
						+ catalog.listRepositories());
				AGRepository myRepository = catalog.openRepository("Bookstore");
				conn = myRepository.getConnection();
				System.out.println("Repository "
						+ (myRepository.getRepositoryID())
						+ " is up! It contains " + (conn.size())
						+ " statements.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return conn;
	}

	public static AGModel getModel() throws RepositoryException {
		AGRepositoryConnection conn = getConnection();
		AGGraphMaker maker = new AGGraphMaker(conn);
		AGGraph graph = maker.getGraph();
		AGModel model = new AGModel(graph);
		return model;
	}

	public void closeConn(AGModel model, AGGraph graph, AGGraphMaker maker,
			AGRepositoryConnection conn, AGRepository myRepository)
			throws RepositoryException {
		model.close();
		graph.close();
		maker.close();
		conn.close();
		myRepository.shutDown();
	}
}