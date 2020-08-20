package com.mycompany.domain;

public class FindHospitalVO {
	private String findhospitalName;
	private String findhospitalTel;
	private String cityName;
	private String province;
	private String findhospitalAddress;
	private String findhospitalX;
	private String findhospitalY;
	private String findhospitalOpenhour;
	private String keyWord;//검색어

	
	public String getFindhospitalOpenhour() {
		return findhospitalOpenhour;
	}
	public void setFindhospitalOpenhour(String findhospitalOpenhour) {
		this.findhospitalOpenhour = findhospitalOpenhour;
	}
	
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	
	public String getFindhospitalName() {
		return findhospitalName;
	}
	public void setFindhospitalName(String findhospitalName) {
		this.findhospitalName = findhospitalName;
	}
	public String getFindhospitalTel() {
		return findhospitalTel;
	}
	public void setFindhospitalTel(String findhospitalTel) {
		this.findhospitalTel = findhospitalTel;
	}
	public String getFindhospitalAddress() {
		return findhospitalAddress;
	}
	public void setFindhospitalAddress(String findhospitalAddress) {
		this.findhospitalAddress = findhospitalAddress;
	}
	public String getFindhospitalX() {
		return findhospitalX;
	}
	public void setFindhospitalX(String findhospitalX) {
		this.findhospitalX = findhospitalX;
	}
	public String getFindhospitalY() {
		return findhospitalY;
	}
	public void setFindhospitalY(String findhospitalY) {
		this.findhospitalY = findhospitalY;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	
}
