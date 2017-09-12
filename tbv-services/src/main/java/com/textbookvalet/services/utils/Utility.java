package com.textbookvalet.services.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class Utility {

	public static String formatAmt(BigDecimal amount) {
		if (amount == null) {
			amount = BigDecimal.ZERO;
		}

		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		return formatter.format(amount);
	}
}
