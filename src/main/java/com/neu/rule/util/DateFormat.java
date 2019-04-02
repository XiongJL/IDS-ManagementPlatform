package com.neu.rule.util;

import java.sql.Date;
import java.text.SimpleDateFormat;


public class DateFormat {

	public Date format(String strDate) {
		String str = strDate;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date d = null;
		try {
			d = format.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		java.sql.Date date = new java.sql.Date(d.getTime());
		return date;
	}

	public void test() {
		System.out.println(format("1996-12-31T16:00:00.000Z"));
	}
}
