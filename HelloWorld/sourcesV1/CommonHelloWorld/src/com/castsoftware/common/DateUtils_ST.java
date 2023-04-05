package com.castsoftware.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;



/**
 * Helper class used to convert, retrieve dates.
 */
public class DateUtils_ST {

	/**
	 * Gets the type of display calendar.
	 * @return One of the following values: "MonthDay", "MonthWeek" or "MonthDayWeek".
	 */
	public static String getCalendarType_ST(Integer intervalType) {
		// Default.
		return "";
	}
	 /**
     * @return -1 si pDate1<pDate2
     *          0 si pDate1=pDate2
     *          1 si pDate1>pDate2
     * @param pDate1 Date
     * @param pDate2 Date
     */
    public static int compareNoTime (final java.util.Date pDate1,
        final java.util.Date pDate2) {
        if ((pDate1 == null ) || (pDate2 == null )) {
           return 0;
        }

        int            v1;
        int            v2;
        final Calendar vCal = Calendar.getInstance();
        vCal.setTime(pDate1);
        v1 = vCal.get(Calendar.YEAR);
        vCal.setTime(pDate2);
        v2 = vCal.get(Calendar.YEAR);

        if (v1 < v2) {
            return -1;
        }
        else if (v1 > v2) {
            return 1;
        }

        vCal.setTime(pDate1);
        v1 = vCal.get(Calendar.MONTH);
        vCal.setTime(pDate2);
        v2 = vCal.get(Calendar.MONTH);

        if (v1 < v2) {
            return -1;
        }
        else if (v1 > v2) {
            return 1;
        }

        vCal.setTime(pDate1);
        v1 = vCal.get(Calendar.DATE);
        vCal.setTime(pDate2);
        v2 = vCal.get(Calendar.DATE);

        if (v1 < v2) {
            return -1;
        }
        else if (v1 > v2) {
            return 1;
        }
        else {
            return 0;
        }
    }
        
    /**
     * replace characters  (é -> E, ç -> C, c -> C).
     * @param origine original string
     * @return replaced string
     */
    public static  String replaceCharsUpperCase2 ( String origine) {
        StringBuffer  result = new StringBuffer ();
        char         car = ' ';
        int          pos = -1;

        if (origine != null ) {
            for (int i = 0; i < origine.length(); i++) {
                car     = origine.charAt(i);
               
            }

            return result.toString();
        }
        else {
            return null ;
        }
    }
   
    /**
     * replace oldStr by newStr     
     * in source.
     *
     * @param source source string
     * @param oldStr the old string
     * @param newStr the new string
     *
     * @return a  String derived from source by replacing every
     * occurrence of oldStr with newStr.
     */
    public static  String replace ( String source,  String oldStr,  String newStr) {
        if (source == null ) {
            return null ;
        }

        StringBuffer     result    = new StringBuffer ();
        StringTokenizer tokenizer = new StringTokenizer(source, oldStr, true);
         String          token     = null ;

        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();

            if (token.equals(oldStr)) {
                result.append(newStr);
            }
            else {
                result.append(token);
            }
        }
        return result.toString();
    }
    
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Retourne un java.util.Date à partir d'une date en String
     *
     */
    public static java.util.Date parseDate ( String date)
	throws ParseException {
        if (date == null ) {
            return null ;
        }

        try {
            return dateFormatter.parse(date);
        }
        catch (ParseException pe) {
            throw pe;
        }
    }
    
    private static final  String PATTERN_REPLACE =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzcaaaaaaaAAAAAA"
        + "eeeeeEEEEiiiiIIIIoooooOOOOOuuuuUUUUyyY1234567890.-_ ";    
        
    /**
     * But eliminer les caractères accentués
     * @param origine
     * @return String
     */
    public static  String eraseAccent ( String origine) {
        StringBuffer  result = new StringBuffer ();
        char         car = ' ';
        int          pos = -1;

        if (origine != null ) {
            for (int i = 0; i < origine.length(); i++) {
                car     = origine.charAt(i);
                
                result.append((pos == -1) ? car : PATTERN_REPLACE.charAt(pos));
            }

            return result.toString();
        }
        else {
            return null ;
        }
    }
 }