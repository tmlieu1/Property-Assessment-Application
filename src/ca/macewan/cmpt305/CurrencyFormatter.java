package ca.macewan.cmpt305;

import java.text.NumberFormat;

/**
 * class for converting integer/long formats into currency strings
 */
public class CurrencyFormatter {
	
	/**
	 * Converts to currencyFormatter, stripping cent values.
	 * @param num
	 * @return
	 */
	public static String format(long num) {
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
		currencyFormatter.setMaximumFractionDigits(0);
		String cf = currencyFormatter.format(num);
		return cf;
	}
}