/* Generated By:JJTree: Do not edit this line. ASTNotExistsFunc.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.openrdf.query.parser.sparql.ast;

public
class ASTNotExistsFunc extends SimpleNode {
  public ASTNotExistsFunc(int id) {
    super(id);
  }

  public ASTNotExistsFunc(SyntaxTreeBuilder p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(SyntaxTreeBuilderVisitor visitor, Object data) throws VisitorException {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=9ee9e8e4e232065421151ba867abecb0 (do not edit this line) */
