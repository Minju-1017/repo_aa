package com.aa.common;

import java.text.DecimalFormat;

import org.springframework.stereotype.Component;

@Component
public class AAUtil {
	
	public final static DecimalFormat DF_WON = new DecimalFormat("#,###");
	
	public static String formatWon(String number) {
		return DF_WON.format(Integer.parseInt(number));
	}
	
	public static String formatWon(int number) {
		return DF_WON.format(number);
	}
	
	public static String formatPhone(String number) {
		if (number == null) return "";
		
		if (number.length() == 8) {
		  return number.replaceFirst("^([0-9]{4})([0-9]{4})$", "$1-$2");
		} else if (number.length() == 12) {
		  return number.replaceFirst("(^[0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
		} else {
			return number.replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");
		}
	}
	
}
