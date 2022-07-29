package com.ssafy.rent.model.dto;

public class TourismInfo {
	private String dong;
	private String Tourname;
	private String classification;
	
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}
	public String getTourname() {
		return Tourname;
	}
	public void setTourname(String tourname) {
		this.Tourname = tourname;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	@Override
	public String toString() {
		return "TourismInfo [dong=" + dong + ", Tourname=" + Tourname + ", classification=" + classification + "]";
	}
	
}
