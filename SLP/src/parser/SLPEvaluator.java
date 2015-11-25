package parser;

import java.io.IOException;

import ToDelete.ASTNode;
import ToDelete.AssignStmt;
import ToDelete.BinaryOpExpr;
import ToDelete.Expr;
import ToDelete.NumberExpr;
import ToDelete.Operator;
import ToDelete.PrintStmt;
import ToDelete.ReadIExpr;
import ToDelete.Stmt;
import ToDelete.StmtList;
import ToDelete.UnaryOpExpr;
import ToDelete.VarExpr;
import ast.PropagatingVisitor;

/** Evaluates straight line programs.
 */
public class SLPEvaluator implements PropagatingVisitor<Environment, Integer> {
	protected ASTNode root;

	/** Constructs an SLP interpreter for the given AST.
	 * 
	 * @param root An SLP AST node.
	 */
	public SLPEvaluator(ASTNode root) {
		this.root = root;
	}
	
	/** Interprets the AST passed to the constructor.
	 */
	public void evaluate() {
		Environment env = new Environment();
		root.accept(this, env);
	}
	
	public Integer visit(StmtList stmts, Environment env) {
		for (Stmt st : stmts.statements) {
			st.accept(this, env);
		}
		return null;
	}

	public Integer visit(Stmt stmt, Environment env) {
		throw new UnsupportedOperationException("Unexpected visit of Stmt!");
	}

	public Integer visit(PrintStmt stmt, Environment env) {
		Integer printValue = stmt.expr.accept(this, env);
		System.out.println(printValue);
		return null;
	}

	public Integer visit(AssignStmt stmt, Environment env) {
		Expr rhs = stmt.rhs;
		Integer expressionValue = rhs.accept(this, env);
		VarExpr var = stmt.varExpr;
		env.update(var, expressionValue);
		return null;
	}

	public Integer visit(Expr expr, Environment env) {
		throw new UnsupportedOperationException("Unexpected visit of Expr!");
	}

	public Integer visit(ReadIExpr expr, Environment env) {
		int readValue;
		try {
			System.out.println("Enter number: ");
			readValue = System.in.read() - '0'; // read integer
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		return new Integer(readValue);
		// return readValue; also works in Java 1.5 because of auto-boxing
	}

	public Integer visit(VarExpr expr, Environment env) {
		return env.get(expr);
	}

	public Integer visit(NumberExpr expr, Environment env) {
		return new Integer(expr.value);		
		// return expr.value; also works in Java 1.5 because of auto-boxing
	}

	public Integer visit(UnaryOpExpr expr, Environment env) {
		Operator op = expr.op;
		Integer value = expr.operand.accept(this, env);
		
		int result;
		if (op == Operator.MINUS)
			result = - value.intValue();
		else if (op == Operator.LNEG)
			result = (value.intValue() == 0)? 1: 0;
		else
			throw new RuntimeException("Encountered unexpected operator " + op);				

		return new Integer(result);
	}

	public Integer visit(BinaryOpExpr expr, Environment env) {
		Integer lhsValue = expr.lhs.accept(this, env);
		int lhsInt = lhsValue.intValue();
		Integer rhsValue = expr.rhs.accept(this, env);
		int rhsInt = rhsValue.intValue();
		int result;
		switch (expr.op) {
		case DIVIDE:
			if (rhsInt == 0)
				throw new RuntimeException("Attempt to divide by zero: " + expr);
			result = lhsInt / rhsInt;
			break;
		case MINUS:
			result = lhsInt - rhsInt;
			break;
		case MULTIPLY:
			result = lhsInt * rhsInt;
			break;
		case PLUS:
			result = lhsInt + rhsInt;
			break;
		case MOD:
			result = lhsInt % rhsInt;
			break;
		case LT:
			result = lhsInt < rhsInt ? 1 : 0;
			break;
		case GT:
			result = lhsInt > rhsInt ? 1 : 0;
			break;
		case LTE:
			result = lhsInt <= rhsInt ? 1 : 0;
			break;
		case GTE:
			result = lhsInt >= rhsInt ? 1 : 0;
			break;
		case LAND:
			result = (lhsInt!=0 && rhsInt!=0) ? 1 : 0;
			break;
		case LOR:
			result = (lhsInt!=0 || rhsInt!=0) ? 1 : 0;
			break;
		case EQUAL: 
			result = (lhsInt == rhsInt) ? 1 : 0;
		case NEQUAL: 
			result = (lhsInt != rhsInt) ? 1 : 0;		
		default:
			throw new RuntimeException("Encountered unexpected operator type: " + expr.op);
		}
		return new Integer(result);
	}
}