package tik.englishcenter.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class Formatter {
	public static String currencyFormat(Object currenyString) {
		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
		String raw = "";
		if( currenyString instanceof Integer || currenyString instanceof Long || currenyString instanceof String) {
		 raw = formatter.format(Long.valueOf(currenyString.toString()));
		}
		return raw;
	}
}
