/*
 * Copyright Aduna (http://www.aduna-software.com/) (c) 1997-2007.
 *
 * Licensed under the Aduna BSD-style license.
 */
package org.openrdf.query.parser.sparql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openrdf.query.algebra.And;
import org.openrdf.query.algebra.Filter;
import org.openrdf.query.algebra.Join;
import org.openrdf.query.algebra.LeftJoin;
import org.openrdf.query.algebra.Projection;
import org.openrdf.query.algebra.SingletonSet;
import org.openrdf.query.algebra.StatementPattern;
import org.openrdf.query.algebra.TupleExpr;
import org.openrdf.query.algebra.ValueExpr;
import org.openrdf.query.algebra.Var;
import org.openrdf.query.algebra.helpers.QueryModelVisitorBase;

/**
 * A graph pattern consisting of (required and optional) tuple expressions,
 * binding assignments and boolean constraints.
 * 
 * @author Arjohn Kampman
 */
public class GraphPattern {

	/**
	 * The context of this graph pattern.
	 */
	private Var contextVar;

	/**
	 * The StatementPattern-scope of this graph pattern.
	 */
	private StatementPattern.Scope spScope = StatementPattern.Scope.DEFAULT_CONTEXTS;

	/**
	 * The required tuple expressions in this graph pattern.
	 */
	private List<TupleExpr> requiredTEs = new ArrayList<TupleExpr>();

	/**
	 * The optional tuple expressions in this graph pattern, mapped to a set of
	 * constraints applicable to each optional.
	 */
	private Map<TupleExpr, List<ValueExpr>> optionalTEs = new HashMap<TupleExpr, List<ValueExpr>>();

	/**
	 * The boolean constraints in this graph pattern.
	 */
	private List<ValueExpr> constraints = new ArrayList<ValueExpr>();

	/**
	 * Creates a new graph pattern.
	 */
	public GraphPattern() {
	}

	/**
	 * Creates a new graph pattern that inherits the context and scope from a
	 * parent graph pattern.
	 */
	public GraphPattern(GraphPattern parent) {
		contextVar = parent.contextVar;
		spScope = parent.spScope;
	}

	public void setContextVar(Var contextVar) {
		this.contextVar = contextVar;
	}

	public Var getContextVar() {
		return contextVar;
	}

	public void setStatementPatternScope(StatementPattern.Scope spScope) {
		this.spScope = spScope;
	}

	public StatementPattern.Scope getStatementPatternScope() {
		return spScope;
	}

	public void addRequiredTE(TupleExpr te) {
		requiredTEs.add(te);
	}

	public void addRequiredSP(Var subjVar, Var predVar, Var objVar) {
		addRequiredTE(new StatementPattern(spScope, subjVar, predVar, objVar, contextVar));
	}

	public List<TupleExpr> getRequiredTEs() {
		return Collections.unmodifiableList(requiredTEs);
	}

	/**
	 * add the supplied tuple expression as an optional expression, with a list
	 * of constraints that hold as conditions.
	 * 
	 * @param te
	 *        a tuple expression
	 * @param constraints
	 *        a list of constraints that form a condition for the LeftJoin to be
	 *        formed from the optional TE.
	 */
	public void addOptionalTE(TupleExpr te, List<ValueExpr> constraints) {
		optionalTEs.put(te, constraints);
	}

	public Map<TupleExpr, List<ValueExpr>> getOptionalTEs() {
		return Collections.unmodifiableMap(optionalTEs);
	}

	public void addConstraint(ValueExpr constraint) {
		constraints.add(constraint);
	}

	public void addConstraints(Collection<ValueExpr> constraints) {
		this.constraints.addAll(constraints);
	}

	public List<ValueExpr> getConstraints() {
		return Collections.unmodifiableList(constraints);
	}

	public List<ValueExpr> removeAllConstraints() {
		List<ValueExpr> constraints = this.constraints;
		this.constraints = new ArrayList<ValueExpr>();
		return constraints;
	}

	/**
	 * Removes all tuple expressions and constraints.
	 */
	public void clear() {
		requiredTEs.clear();
		optionalTEs.clear();
		constraints.clear();
	}

	/**
	 * Builds a combined tuple expression from the tuple expressions and
	 * constraints in this graph pattern.
	 * 
	 * @return A tuple expression for this graph pattern.
	 */
	public TupleExpr buildTupleExpr() {
		TupleExpr result;

		if (requiredTEs.isEmpty()) {
			result = new SingletonSet();
		}
		else {
			result = requiredTEs.get(0);

			for (int i = 1; i < requiredTEs.size(); i++) {
				TupleExpr te = requiredTEs.get(i);
				// if (containsProjection(te) || containsProjection(result))
				// {
				// result = new BottomUpJoin(result, te);
				// }
				// else {
				result = new Join(result, te);
				// }
			}
		}

		for (TupleExpr optTE : optionalTEs.keySet()) {
			List<ValueExpr> constraints = optionalTEs.get(optTE);
			if (constraints != null && !constraints.isEmpty()) {
				ValueExpr condition = constraints.get(0);
				for (int i = 1; i < constraints.size(); i++) {
					condition = new And(condition, constraints.get(i));
				}

				result = new LeftJoin(result, optTE, condition);
			}
			else {
				result = new LeftJoin(result, optTE);
			}
		}

		for (ValueExpr constraint : constraints) {
			result = new Filter(result, constraint);
		}

		return result;
	}

}
