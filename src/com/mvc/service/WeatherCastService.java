package com.mvc.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;
import com.mvc.dto.WeatherCastDTO;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

enum Area{
    서울("60","127"), 부산("98","76"), 대구("89","90"), 인천("55","124"), 광주("58","74"), 대전("67","100"),
    울산("102","84"), 세종특별자치시("66","103"), 경기도("60","120"), 강원도("73","134"), 충청북도("69","107"), 충청남도("68","110"),
    전라북도("63","89"), 전라남도("51","67"), 경상북도("89","91"), 경상남도("91","77"), 제주도("52","38");
    
     
    final private String nx;
    final private String ny;
     
    private Area(String nx,String ny) {
        this.nx = nx;
        this.ny = ny;
        
    }
    public String getNx() {
        return nx;
    }
    public String getNy() {
    	return ny;
    }
}

public class WeatherCastService {
	HttpServletRequest req;
	HttpServletResponse resp;
	int nx = 0;
	int ny = 0;

	public WeatherCastService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = null;
		if (eElement.getElementsByTagName(sTag).item(0) != null) {
			nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		}
		Node nValue = (Node) nlList.item(0);
		return nValue.getNodeValue();
	}

	private static String getDay(String pattern, int day) {
		DateFormat dtf = new SimpleDateFormat(pattern);
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, day);
		return dtf.format(cal.getTime());
	}

	public void mainWeatherCast() {
		int page = 1;
		String area = req.getParameter("area");
		if (area == null) {
			area = "서울";
		}
		Area areaCoords = Area.valueOf(area);
		ArrayList<WeatherCastDTO> list = new ArrayList<WeatherCastDTO>();
		ArrayList<WeatherCastDTO> weatherCastList = new ArrayList<WeatherCastDTO>();
		WeatherCastDTO dto = null;

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date time = new Date();
		String today = format.format(time);
		try {
			while (page <= 2) {
				String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?serviceKey=OYnBOIZFr7Qq92NvKqRJCgsU0E%2BdAOAjTZ9N8g0lLXvqa4GOfMmtvwgj7%2FhE7QNa64K%2FbbqXxOV3fZO9ffucVQ%3D%3D&numOfRows=100&dataType=XML"
						+ "&base_date=" + today + "&base_time=0500&nx=" + areaCoords.getNx() + "&ny=" + areaCoords.getNy() + "&pageNo=" + page;
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(url);
				doc.getDocumentElement().normalize();

				NodeList nList = (NodeList) doc.getElementsByTagName("item");
				for (int temp = 0; temp < nList.getLength(); temp++) {
					dto = new WeatherCastDTO();
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						if (getTagValue("fcstTime", eElement).equals("1500")
								&& (getTagValue("category", eElement).equals("POP")
										|| getTagValue("category", eElement).equals("PTY")
										|| getTagValue("category", eElement).equals("REH")
										|| getTagValue("category", eElement).equals("SKY")
										|| getTagValue("category", eElement).equals("TMX"))) {
							dto.setCategory(getTagValue("category", eElement));
							dto.setFcstDate(getTagValue("fcstDate", eElement));
							dto.setFcstValue(getTagValue("fcstValue", eElement));
							list.add(dto);
						}
					}
				}
				page++;
			}
			for (WeatherCastDTO weatherCastDTO : list) {
				weatherCastList.add(classify(weatherCastDTO));
			}

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("list", weatherCastList);
			map.put("today", getDay("yyyyMMdd", 0));
			map.put("tomorrow", getDay("yyyyMMdd", 1));
			map.put("afterTomorrow", getDay("yyyyMMdd", 2));
			Gson gson = new Gson();
			resp.setContentType("text/html; charset=UTF-8");
			String json = gson.toJson(map);
			resp.getWriter().print(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private WeatherCastDTO classify(WeatherCastDTO dto) {
		if (getDay("yyyyMMdd", 0).equals(dto.getFcstDate())) {
			category(dto);
		} else if (getDay("yyyyMMdd", 1).equals(dto.getFcstDate())) {
			category(dto);
		} else {
			category(dto);
		}
		return dto;
	}

	private void category(WeatherCastDTO dto) {
		switch (dto.getCategory()) {
			case "POP": // 강수확률 %
				dto.setPOP(dto.getFcstValue() + "%");
				break;
			case "PTY": // 강수 형태 코드값
				dto.setPTY(getPTY(dto.getFcstValue()));
				break;
			case "R06": // 6시간 강수량 범주 (1mm)
				dto.setR06(dto.getFcstValue() + "mm");
				break;
			case "REH": // 습도 %
				dto.setREH(dto.getFcstValue() + "%");
				break;
			case "SKY": // 하늘상태 코드값
				dto.setSKY(getSKY(dto.getFcstValue()));
				break;
			case "TMN": // 아침 최저기온 ℃
				dto.setTMN(dto.getFcstValue() + "℃");
				break;
			case "TMX": // 낮 최고기온 ℃
				dto.setTMX(dto.getFcstValue() + "℃");
				break;
			}
	}

	private String getSKY(String fcstValue) {
		String SKY = null;
		
		switch (fcstValue) {
			case "1":
				SKY = "맑음";
				break;
			case "3":
				SKY = "구름많음";
				break;
			case "4":
				SKY = "흐림";
				break;
			}
		return SKY;
	}

	private String getPTY(String fcstValue) {
		String PTY = null;
		
		switch (fcstValue) {
			case "0":
				PTY = "없음";
				break;
			case "1":
				PTY = "비";
				break;
			case "2":
				PTY = "비/눈";
				break;
			case "3":
				PTY = "눈";
				break;
			case "4":
				PTY = "소나기";
				break;
			case "5":
				PTY = "빗방울";
				break;
			case "6":
				PTY = "빗방울/눈날림";
				break;
			case "7":
				PTY = "눈날림";
				break;
			}
		return PTY;
	}

}
