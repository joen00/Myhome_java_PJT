package com.ssafy.rent.util;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import com.ssafy.rent.model.dto.HomeInfo;

/**
 *  AptDealHistory.xml 파일에서 아파트 거래 정보를 읽어 파싱하는 핸들러 클래스 
 */
public class HomeSAXHandler extends DefaultHandler {
	/**
	 * 아파트 거래 정보를 식별하기 위한 번호로 차후 DB에서는 primary key로 대체하지만 
	 * 현재 버전에서는 0번부터 순차 부여한다. 
	 */
	private int no;
	/**아파트 거래 정보를 담는다 */
	private Map<String,HomeInfo> homes;
	/**파상힌 아파트 거래 정보*/
	private HomeInfo home;
	/**태그 바디 정보를 임시로 저장*/
	private String temp;
	public HomeSAXHandler(){
		homes = new HashMap<String,HomeInfo>();
	}
	public void startElement(String uri, String localName
			                           , String qName, Attributes att ){
		if(qName.equals("item")){
			home = new HomeInfo(no++);
		}
	}
	
	public void endElement(String uri, String localName, String qName){
		if(qName.equals("지역코드")) { 
			home.setCode(Integer.parseInt(temp));
		}else if(qName.equals("아파트")) { 
			home.setAptName(temp.trim());
		}else if(qName.equals("법정동")) { 
			home.setDong(temp.trim());
		}else if(qName.equals("img")) { 		
			home.setImg(temp);
			// System.out.println(home.getImg());
		}else if(qName.equals("건축년도")) { 
			home.setBuildYear(Integer.parseInt(temp));
		}else if(qName.equals("지번")) { 
			home.setJibun(temp);
		}else if(qName.equals("item")) {
			homes.put(home.getDong()+home.getAptName(), home);
		}
	}
	public void characters(char[]ch, int start, int length){
		temp = new String(ch, start, length);
	}
	public Map<String,HomeInfo> getHomeInfo() {
		return homes;
	}
}





