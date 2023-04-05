package com.castsoftware.common;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;



/**
 * Helper class used in BusinessBridge template applications.
 */
public class Utils_ST {
	// The logger instance of this class
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(Utils_ST.class);
	
	private static final String RESOURCE_BUNDLE_NAME = "pagecode.pages.templates.i18n.Resources"; //$NON-NLS-1$	

	public static String returnSubString(String s, int l){
		if(s.length()>l){
			return s.substring(l);
		}else{
			return s;
		}
				
	}

	/**
	 * Serializes an array of Strings.
	 * 
	 * @param items the array of strings to be serialized.
	 * @param separator the string used as separator. Ex: , AND or OR.
	 * @param attribute the used attribute. 
	 * @param operator the string used as operator. Ex: =, IsReferencedBy, HasForReference.. 
	 * @returns a string where each token is separated by the separator. 
	 */
	public static String serializeStrings_ST(String[] items, String separator, String attribute, String operator) {
		StringBuffer sb = new StringBuffer();

		if (items != null) {
			boolean bFirst = true;
			for (int i = 0; i < items.length; i++) {
				if (!items[i].equals("test")) {
					if (!bFirst) 
						sb.append(" " + separator + " " ); //$NON-NLS-1$ //$NON-NLS-2$
					sb.append(attribute + " " + operator + " '" + items[i]+ "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					log.debug("["+i+"]:"+items[i]); //$NON-NLS-1$ //$NON-NLS-2$
					bFirst = false;
				}
			}
		}
		return sb.toString();
	}











	/**
	 * Gets the value of the supplied JavaServer Faces value-binding expression.
	 * 
	 * @param context The current Faces context.
	 * @param vb The value-binding expression to evaluate.
	 * @return An <code>Object</code> containing the result of the evaluation.
	 */
	public static Object getValueBinding(String context, String vb) {
		return null;
	}
}