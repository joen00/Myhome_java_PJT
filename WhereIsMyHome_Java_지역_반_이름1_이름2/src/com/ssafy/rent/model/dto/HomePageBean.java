package com.ssafy.rent.model.dto;

import java.util.Arrays;

public class HomePageBean {
	
	/** 
	 * 0 : 아파트 매매
	 * 1 : 아파트 전월세
	 * 2 : 다세대 매매
	 * 3 : 다세대 전월세  
	 * */
	private boolean[] searchType;
	
	private String  dong;
	private String  aptName;
	
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}

	public String getAptname() {
		return aptName;
	}
	public void setAptname(String aptName) {
		this.aptName = aptName;
	}

	public boolean[] getSearchType() {
		return searchType;
	}
	public void setSearchType(boolean[] searchType) {
		this.searchType = searchType;
	}

	@Override
	public String toString() {
		return "HomePageBean [dong=" + dong + ", aptname=" + aptName + ", searchType="	+ Arrays.toString(searchType) + "]";
	}

}
