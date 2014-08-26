package org.training.allegro.utilities;

import java.util.ArrayList;
import java.util.List;

import com.franz.agraph.jena.AGModel;
import com.franz.agraph.jena.AGQuery;
import com.franz.agraph.jena.AGQueryExecutionFactory;
import com.franz.agraph.jena.AGQueryFactory;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

public class Jena {

	public static List<List<RDFNode>> example3(AGModel model) throws Exception {
		List<List<RDFNode>> triples = new ArrayList<>();
		try {
			String queryString = "SELECT ?s ?p ?o  WHERE {?s ?p ?o .}";

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
		} finally {
			model.close();
		}
		return triples;
	}
}
