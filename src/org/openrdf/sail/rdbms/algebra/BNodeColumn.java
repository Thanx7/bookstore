/*
 * Copyright Aduna (http://www.aduna-software.com/) (c) 2008.
 *
 * Licensed under the Aduna BSD-style license.
 */
package org.openrdf.sail.rdbms.algebra;

import org.openrdf.query.algebra.Var;
import org.openrdf.sail.rdbms.algebra.base.RdbmsQueryModelVisitorBase;
import org.openrdf.sail.rdbms.algebra.base.ValueColumnBase;

/**
 * Represents the BNode value of a variable.
 * 
 * @author James Leigh
 * 
 */
public class BNodeColumn extends ValueColumnBase {

	public BNodeColumn(ColumnVar var) {
		super(var);
	}

	public BNodeColumn(Var var) {
		super(var);
	}

	@Override
	public <X extends Exception> void visit(RdbmsQueryModelVisitorBase<X> visitor)
		throws X
	{
		visitor.meet(this);
	}
}
