/*
 * Copyright Aduna (http://www.aduna-software.com/) (c) 1997-2006.
 *
 * Licensed under the Aduna BSD-style license.
 */
package org.openrdf.query.parser.serql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.openrdf.query.algebra.And;
import org.openrdf.query.algebra.Filter;
import org.openrdf.query.algebra.Join;
import org.openrdf.query.algebra.LeftJoin;
import org.openrdf.query.algebra.SingletonSet;
import org.openrdf.query.algebra.StatementPattern;
import org.openrdf.query.algebra.TupleExpr;
import org.openrdf.query.algebra.ValueExpr;
import org.openrdf.query.algebra.Var;

/**
 * A graph pattern consisting of (required and optional) tuple expressions and
 * boolean constraints.
 */
class GraphPattern {

	/**
	 * The graph pattern's parent, if any.
	 */
	private GraphPattern parent = null;

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
	 * The optional tuple expressions and their constraints in this graph
	 * pattern.
	 */
	private List<OptionalTupleExpr> optionalTEs = new ArrayList<OptionalTupleExpr>();

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
		if (parent != null) {
			this.parent = parent;
			contextVar = parent.contextVar;
			spScope = parent.spScope;
		}
	}

	public GraphPattern getParent() {
		return parent;
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

	public List<TupleExpr> getRequiredTEs() {
		return Collections.unmodifiableList(requiredTEs);
	}

	public void addOptionalTE(GraphPattern gp) {
		List<ValueExpr> constraints = gp.removeAllConstraints();
		TupleExpr tupleExpr = gp.buildTupleExpr();

		OptionalTupleExpr optTE;

		if (constraints.isEmpty()) {
			optTE = new OptionalTupleExpr(tupleExpr);
		}
		else {
			ValueExpr constraint = constraints.get(0);
			for (int i = 1; i < constraints.size(); i++) {
				constraint = new And(constraint, constraints.get(i));
			}

			optTE = new OptionalTupleExpr(tupleExpr, constraint);
		}

		optionalTEs.add(optTE);
	}

	public List<OptionalTupleExpr> getOptionalTEs() {
		return Collections.unmodifiableList(optionalTEs);
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
				result = new Join(result, requiredTEs.get(i));
			}
		}

		for (OptionalTupleExpr optTE : optionalTEs) {
			if (optTE.hasConstraint()) {
				result = new LeftJoin(result, optTE.getTupleExpr(), optTE.getConstraint());
			}
			else {
				result = new LeftJoin(result, optTE.getTupleExpr());
			}
		}

		for (ValueExpr constraint : constraints) {
			result = new Filter(result, constraint);
		}

		return result;
	}
}
