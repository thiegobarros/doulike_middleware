package middleware;

import org.json.JSONObject;

public class Historic {
	
	private JSONObject historic = new JSONObject();
	
	public JSONObject get() {
		return this.historic;
	}
	
	public void pushHistoric(Boolean signal, Sensor sensor, String description) {
		JSONObject step_sensor = new JSONObject();
		step_sensor.put("signal", signal);
		step_sensor.put("tipo", sensor.getType());
		step_sensor.put("mac", sensor.getMac());
		step_sensor.put("descrição", description);
		step_sensor.put("data/hora", "04/01/1991");
		this.historic.accumulate("data", step_sensor);
	}
}
