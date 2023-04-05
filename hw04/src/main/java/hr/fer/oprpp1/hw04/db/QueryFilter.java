package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * Filter that filters StudentRecords by the given list of ConditionalExpressions.
 */
public class QueryFilter implements IFilter {
	
	/**
	 * List of ConditionalExpressions of a query.
	 */
	private List<ConditionalExpression> queryExpressions;
	
	/**
	 * Constructor for the QueryFilter. Sets the internal list of ConditionalExpressions to the given list.
	 * 
	 * @param queryExpressions - list of ConditionalExpressions of a query
	 */
	public QueryFilter(List<ConditionalExpression> queryExpressions) {
		this.queryExpressions = queryExpressions;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		boolean accepted = true;
		
		for(ConditionalExpression ce : queryExpressions) {
			accepted = accepted && ce.getComparisonOperator().satisfied(
					ce.getFieldGetter().get(record), 
					ce.getStringLiteral());
		}
		
		return accepted;
	}
}
