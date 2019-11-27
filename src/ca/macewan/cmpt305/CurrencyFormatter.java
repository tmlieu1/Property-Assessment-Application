package ca.macewan.cmpt305;

import java.text.NumberFormat;

public class CurrencyFormatter {
	//Converts to currencyFormatter, stripping cent values.
	public static String format(long num) {
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
		currencyFormatter.setMaximumFractionDigits(0);
		String cf = currencyFormatter.format(num);
		return cf;
	}
}
