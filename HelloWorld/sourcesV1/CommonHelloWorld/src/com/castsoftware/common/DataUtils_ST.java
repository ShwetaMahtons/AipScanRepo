package com.castsoftware.common;

/**
 * Helper class used to access data.
 */
public class DataUtils_ST {
	// Logger for this class
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(DataUtils_ST.class);

	/** Constant used to indicate that a query is executed without cache */
	public static int QUERY_NO_CACHE_ST = 0;

	/** Constant used to indicate that a query is executed with a short cache */
	public static int QUERY_SHORT_CACHE_ST = 1;
	/** Duration in seconds of the short cache */
	public static int QUERY_SHORT_CACHE_PERIOD_ST = 30;

	/** Constant used to indicate that a query is executed with a medium cache */
	public static int QUERY_MEDIUM_CACHE_ST = 2;
	/** Duration in seconds of the medium cache */
	public static int QUERY_MEDIUM_CACHE_PERIOD_ST = 600;

	/** Constant used to indicate that a query is executed with a long cache */
	public static int QUERY_LONG_CACHE_ST = 3;
	/** Duration in seconds of the long cache */
	public static int QUERY_LONG_CACHE_PERIOD_ST = 3600;

	/** Constant AND */
	public static final String AND_ST = " and "; //$NON-NLS-1$
	/** Constant LikeNoCase (start)*/
	public static final String LIKE_START_ST = " likeNoCase '%"; //$NON-NLS-1$
	/** Constant Like (end)*/
	public static final String LIKE_END_ST = "%' "; //$NON-NLS-1$
	/** Constant Equal (start)*/
	public static final String EQUAL_START_ST = " = '"; //$NON-NLS-1$
	/** Constant Equal (end)*/
	public static final String EQUAL_END_ST = "' "; //$NON-NLS-1$
	/** Constant GreaterThan (start)*/
	public static final String GT_START_ST = " > '"; //$NON-NLS-1$
	/** Constant GreaterThan (end)*/
	public static final String GT_END_ST = "' "; //$NON-NLS-1$
	/** Constant LowerThan (start)*/
	public static final String LT_START_ST = " < '"; //$NON-NLS-1$
	/** Constant LowerThan (end)*/
	public static final String LT_END_ST = "' "; //$NON-NLS-1$

	/**
	 * Build a condition using the operator (start and end) and the attribute (name and value)
	 * @param condition	built condition
	 * @param attributeName
	 * @param operatorStart
	 * @param attributeValue
	 * @param operatorEnd
	 */
	public static void buildQueryCondition_ST(StringBuffer condition, String attributeName, String operatorStart, String attributeValue, String operatorEnd) {
		if (attributeValue.length() > 0) {
			if (condition.length() > 0) {
				condition.append(AND_ST);
			}
			condition.append(attributeName + operatorStart + attributeValue + operatorEnd);
		} else
			log.error("attributeValue::is empty"); //$NON-NLS-1$
	}













	/**
	 * Log the query.
	 * @param query Query to be logged.
	 * @param caller The function that had requested a log.
	 */
	public static void logDebugQuery(String query, String caller) {
		if (log.isDebugEnabled()) {
			if (query.length() > 0) {
				log.debug("ExecuteQueryCaller::" + caller); //$NON-NLS-1$
				log.debug(query.toString());
			} else {
				log.debug("QueryError::No column defined in the query"); //$NON-NLS-1$
			}
		}
	}
}