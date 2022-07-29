package com.ssafy.rent.util;

import java.util.LinkedList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import com.ssafy.rent.model.dto.HomeDeal;

/**
 *  HomeRentHistory.xml 파일에서 주택 전월세 정보를 읽어 파싱하는 핸들러 클래스 
 */
public class HomeRentSAXHandler extends DefaultHandler {
	/**아파트 거래 정보를 담는다 */
	private List<HomeDeal> homes;
	/**파상힌 아파트 거래 정보*/
	private HomeDeal home;
	/**태그 바디 정보를 임시로 저장*/
	private String temp;
	public HomeRentSAXHandler(){
		homes = new LinkedList<HomeDeal>();
	}
	public void startElement(String uri, String localName
			                           , String qName, Attributes att ){
		if(qName.equals("item")){
			home = new HomeDeal(HomeSaxParser.no++);
			home.setType(HomeDeal.HOME_RENT);
			homes.add(home);
		}
	}
	public void endElement(String uri, String localName, String qName){
		if(qName.equals("지역코드")) { 
			home.setCode(Integer.parseInt(temp));
		}else if(qName.equals("연립다세대")) { 
			home.setAptName(temp.trim());
		}else if(qName.equals("법정동")) { 
			home.setDong(temp.trim());
		}else if(qName.equals("보증금액")) { 
			home.setDealAmount(temp);
		}else if(qName.equals("월세금액")) { 
			home.setRentMoney(temp);
		}else if(qName.equals("건축년도")) { 
			home.setBuildYear(Integer.parseInt(temp));
		}else if(qName.equals("년")) { 
			home.setDealYear(Integer.parseInt(temp));
		}else if(qName.equals("월")) { 
			home.setDealMonth(Integer.parseInt(temp));
		}else if(qName.equals("일")) { 
			home.setDealDay(Integer.parseInt(temp));
		}else if(qName.equals("전용면적")) { 
			home.setArea(Double.parseDouble(temp));
		}else if(qName.equals("지번")) { 
			home.setJibun(temp);
		}else if(qName.equals("층")) { 
			home.setFloor(Integer.parseInt(temp));
		}
	}
	public void characters(char[]ch, int start, int length){
		temp = new String(ch, start, length);
	}
	public List<HomeDeal> getHomes() {
		return homes;
	}
	public void setHomes(List<HomeDeal> homes) {
		this.homes = homes;
	}
}





