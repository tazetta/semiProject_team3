package com.mvc.dto;

public class WeatherCastDTO {
	private String category;
	private String fcstDate;
	private String fcstValue;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFcstDate() {
		return fcstDate;
	}
	public void setFcstDate(String fcstDate) {
		this.fcstDate = fcstDate;
	}
	public String getFcstValue() {
		return fcstValue;
	}
	public void setFcstValue(String fcstValue) {
		this.fcstValue = fcstValue;
	}
	
	private String POP; // 강수 확률 %
	private String PTY; // 강수 형태 코드값
	private String R06; // 6시간 강수량 범주 (1mm)
	private String REH; // 습도 %
	private String SKY; // 하늘상태 코드값
	private String TMN; // 아침 최저기온 섭씨 
	private String TMX; // 낮 최고기온 섭씨
//	private String UUU; // 풍속(동서성분) m/s
//	private String VVV; // 풍속(남북성분) m/s

	public String getPOP() {
		return POP;
	}
	public void setPOP(String pOP) {
		POP = pOP;
	}
	public String getPTY() {
		return PTY;
	}
	public void setPTY(String pTY) {
		PTY = pTY;
	}
	public String getR06() {
		return R06;
	}
	public void setR06(String r06) {
		R06 = r06;
	}
	public String getREH() {
		return REH;
	}
	public void setREH(String rEH) {
		REH = rEH;
	}
	public String getSKY() {
		return SKY;
	}
	public void setSKY(String sKY) {
		SKY = sKY;
	}
	public String getTMN() {
		return TMN;
	}
	public void setTMN(String tMN) {
		TMN = tMN;
	}
	public String getTMX() {
		return TMX;
	}
	public void setTMX(String tMX) {
		TMX = tMX;
	}
//	public String getUUU() {
//		return UUU;
//	}
//	public void setUUU(String uUU) {
//		UUU = uUU;
//	}
//	public String getVVV() {
//		return VVV;
//	}
//	public void setVVV(String vVV) {
//		VVV = vVV;
//	}

	
}
