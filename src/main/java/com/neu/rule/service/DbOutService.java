package com.neu.rule.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public interface DbOutService {
	void shell(String cmd);

	void insertFile(String url);
	
	public String updateTxtPath(String[] nvids,String date) throws IOException, ParseException;
	
	public String ids_updatePath(String[] nvids);

	public void mkver(String path, String content);
}
