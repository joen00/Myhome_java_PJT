package com.ssafy.rent.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.ssafy.rent.model.dto.HomeDeal;
import com.ssafy.rent.model.dto.HomeInfo;
import com.ssafy.rent.model.dto.HomePageBean;
import com.ssafy.rent.model.dto.TourismInfo;
import com.ssafy.rent.util.HomeSaxParser;
import com.ssafy.rent.util.TravelSaxParser;

public class HomeDaoImpl implements HomeDao{
	
	private Map<String, HomeInfo> homeInfo;
	private Map<String, List<HomeDeal>> deals;
	private int size;
	private List<HomeDeal> search;
	private String[] searchType= {HomeDeal.APT_DEAL, HomeDeal.APT_RENT, HomeDeal.HOME_DEAL, HomeDeal.HOME_RENT};
	public HomeDaoImpl() {
		loadData();
	}
	
	// tour
	public List<TourismInfo> tourlist;
	
	
	/**
	 * 아파트 정보와 아파트 거래 정보를  xml 파일에서 읽어온다.
	 */
	public void loadData() {
		HomeSaxParser parser = new HomeSaxParser();
		homeInfo = parser.getHomeInfo();
		deals = parser.getDeals();
		size = parser.getSize();
		search = new ArrayList<HomeDeal>(size);
	}
	
	/**
	 * 검색 조건(key) 검색 단어(word)에 해당하는 아파트 거래 정보(HomeInfo)를  검색해서 반환.  
	 * @param bean  검색 조건과 검색 단어가 있는 객체
	 * @return 조회한 주택 목록
	 */
	public List<HomeDeal> searchAll(HomePageBean  bean){
		search.clear();
		List<HomeDeal> finds = new LinkedList<HomeDeal>();
		
		boolean[] type = bean.getSearchType();
		for (int i = 0, size=type.length; i < size; i++) {
			if(type[i]) {
				// System.out.println(searchType[i]);
				search.addAll(deals.get(searchType[i]));
			}
		}
		// System.out.println(search);
		String dong = bean.getDong();
		String aptName = bean.getAptname();
		if(dong!=null) {	
			for (HomeDeal deal : search) {
				if(deal.getDong().contains(dong)) {
					finds.add(deal);
				}
			}
		}else if(aptName != null) {
			for (HomeDeal deal : search) {
				if(deal.getAptName().contains(aptName)) {
					finds.add(deal);
				}
			}
		}else {
			finds = search;
		}
		return finds;
	}
	
	/**
	 * 아파트 식별 번호에 해당하는 아파트 거래 정보를 검색해서 반환한다.<br/>
	 * 법정동+아파트명을 이용하여 HomeInfo에서 건축연도, 법정코드, 지번, 이미지 정보를 찾아서 HomeDeal에 setting한 정보를 반환한다. 
	 * @param no	검색할 아파트 식별 번호
	 * @return		아파트 식별 번호에 해당하는 아파트 거래 정보를 찾아서 리턴한다, 없으면 null이 리턴됨
	 */
	public HomeDeal search(int no) {
		
		String csvFile = "./res/travelData.csv";

        ArrayList<TourismInfo> tour = new ArrayList<TourismInfo>();
        ArrayList<String> RandomTour = new ArrayList<String>();
        tour = (ArrayList<TourismInfo>) new TravelSaxParser().read(csvFile);
        tourlist = tour;     
		for(int i=0;i<search.size();i++) {
			
            if (((search.get(i)).getNo()) == no) {         	
            	for(int j=0;j<tourlist.size();j++) {
            		
            		
            		if (tourlist.get(j).getDong().contains((search.get(i)).getDong())) {
            			
            			if(j==0)
            				//System.out.println(tourlist.get(i).getDong().replaceAll("\"","")+" :"+"주요 관광지 목록:");
            			RandomTour.add(tourlist.get(j).getTourname().replaceAll("\"",""));
            			
            		}
            	}
        
            	if(RandomTour.size()>=3)
            	{
            	System.out.println("**********관광지 목록 Top3***********");
            	for(int k=0;k<3;k++) {
            		
            		System.out.println(RandomTour.get(k));
            	}
            	}
                return search.get(i);
            }
        }
        return null;
	}
	
//	public static void main(String[] args) {
//		HomeDaoImpl dao = new HomeDaoImpl();
//		//dao.loadData();
//		System.out.println(dao.search(1));
//		System.out.println("===========================법정동으로 검색=================================");
//		HomePageBean dongSearch = new HomePageBean();
//		dongSearch.setDong("사직동");
//		print(dao.searchAll(dongSearch));
//		
//		System.out.println("===========================아파트 이름으로 검색=================================");
//		HomePageBean nameSearch = new HomePageBean();
//		nameSearch.setAptname("신동아블루아광화문의 꿈");
//		print(dao.searchAll(nameSearch));
//		
//		System.out.println("===========================전체 검색=================================");
//		print(dao.searchAll(null));
//		System.out.println("============================================================");
//	}
	
	public static void print(List<HomeDeal> homes) {
		for (HomeDeal home : homes) {
			System.out.println(home);
		}
	}
}
