package br.com.adrianomenezes.converters;

public class NumberConverter {
	
	public static Double convertToDouble(String strNum) {

		if (strNum.isEmpty() || strNum.isBlank()) return 0D;
		strNum = strNum.replace(",", ".");
		if (isNumeric(strNum)) {
			return Double.parseDouble(strNum);
		}
		
		return 0D;
	}

	public static boolean isNumeric(String strNum) {
		if (strNum.isEmpty() || strNum.isBlank()) return false;
		strNum = strNum.replace(",", ".");
		
		return strNum.matches("[-+]?[0-9]*\\.?[0-9]+");
	}

}
