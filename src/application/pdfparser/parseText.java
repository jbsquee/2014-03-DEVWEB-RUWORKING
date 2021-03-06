package application.pdfparser;
import java.util.ArrayList;

import application.pdfparser.context.Header;
import application.pdfparser.context.Promise;
import application.pdfparser.dao.PdfModel;
import application.postrequest.Politician;

public class parseText {
	boolean copyFlag = true;
	Header header = new Header();
	String location = new String();
	String name = new String();
	String party = new String();
	Promise [] promise = new Promise[5];
	
	public parseText() {
		for(int i =0; i<promise.length; i++) {
			promise[i] = new Promise();
		}
	}
	
	public void parse(String [] array, int index, ArrayList<Politician> poliURL) throws Exception {
		String headRegex = ".*공약번호.*";
		int promiseCount = -1;
		int count = 0;
		//header 부분 처리
		while(true) {
			if(array[count].matches(headRegex)) break;
			header.pushStringBuffer(array[count]);
			count++;
		}
		int poliId = header.parse(poliURL, index);

		//공약부분 처리 
		for(int i = count; i<array.length; i++) {
			if(array[i].matches(headRegex)) promiseCount++;
			promise[promiseCount].pushStringBuffer(array[i] + "\n");
		}
		for(int i = 0; i<5; i++) {
			promise[i].parse(poliId);
		}
		
	}
}
