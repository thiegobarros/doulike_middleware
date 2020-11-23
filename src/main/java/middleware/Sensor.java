package middleware;

import org.json.JSONObject;

public class Sensor {
	
	private String mac;
	private String type;
	private String value;
	
	public Sensor(String values) {
		makeSensor(values);
	}

	public String getMac() {
		return mac;
	}

	private void setMac(String mac) {
		this.mac = mac;
	}

	public String getType() {
		return type;
	}

	private void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	private void setValue(String value) {
		this.value = value;
	}
	
	public void makeSensor(String values) {
		JSONObject sensor = new JSONObject(values);
		
		setMac(sensor.getString("sensor_mac"));
		setType(sensor.getString("type"));
		setValue(sensor.getString("value"));
	}
	
	public String print() {
		return String.format("mac: %s | type: %s | value: %s", getMac(), getType(), getValue());
	}
}
