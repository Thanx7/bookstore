/*
 * Copyright Aduna (http://www.aduna-software.com/) (c) 2008.
 *
 * Licensed under the Aduna BSD-style license.
 */
package org.openrdf.sail.rdbms.algebra;

import org.openrdf.sail.rdbms.algebra.base.BinarySqlOperator;
import org.openrdf.sail.rdbms.algebra.base.RdbmsQueryModelVisitorBase;
import org.openrdf.sail.rdbms.algebra.base.SqlExpr;

/**
 * The SQL concatenate expression (||).
 * 
 * @author James Leigh
 * 
 */
public class SqlConcat extends BinarySqlOperator {

	public SqlConcat(SqlExpr leftArg, SqlExpr rightArg) {
		super(leftArg, rightArg);
	}

	@Override
	public <X extends Exception> void visit(RdbmsQueryModelVisitorBase<X> visitor)
		throws X
	{
		visitor.meet(this);
	}
}
