package tik.englishcenter.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Validators {
	public static boolean checkName(String name) {
		if (name.matches("[^0-9]"))
			return true;
		return false;
	}

	public static boolean checkPhone_Number(String phone_number) {
		if (phone_number.matches("0\\d{9}") && phone_number.length() == 10)
			return true;
		return false;
	}

	public static boolean checkEmail(String email) {
		if (email.matches("\\w+@\\w+[.]\\w+"))
			return true;
		return false;
	}

	public static boolean checkDate(String dateString) {
		if (dateString.matches("(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d"))
			return true;
		return false;
	}

	public static boolean checkDigit(String digitString) {
		if(digitString.matches("(\\d+)"))
			return true;
		return false;
	}
	public static boolean checkDigitWithFloatPoint(String digitString) {
		if(digitString.matches("[+-]?([0-9]*[.])?[0-9]+"))
			return true;
		return false;
	}
	public static boolean isBefore(String dateBefore, String dateAfter) {
		DateFormat spf = new DateFormat();
		if(spf.parse(dateBefore).before(spf.parse(dateAfter))){
			return true;
		}
		return false;
	}
	
	public static boolean isSameDate(String d1, String d2) {
		DateFormat df = new DateFormat();
		return df.parse(d1).compareTo(df.parse(d2)) == 0;
	}

	public static boolean checkText(String text) {
		if(text.matches("^[a-zA-Z]+$"))
			return false;
		return true;
	}
	
	public static void main(String[] args) {
		System.out .println(isSameDate("28/4/2021", "28/04/2021"));
	}
}
