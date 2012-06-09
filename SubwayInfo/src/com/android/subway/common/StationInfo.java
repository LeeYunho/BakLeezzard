package com.android.subway.common;

public class StationInfo {
	private String Line;
	private String StationName;

	public StationInfo(String line, String stationName) {
		// TODO Auto-generated constructor stub
		Line = line;
		StationName = stationName;
	}

	public String getLine() {
		return Line;
	}

	public String getStationName() {
		return StationName;
	}
}