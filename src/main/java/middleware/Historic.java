package middleware;

import org.json.JSONObject;
import java.time.LocalDateTime;

public class Historic {
	
	private JSONObject historic = new JSONObject();
	
	public JSONObject get() {
		return this.historic;
	}
	
	public void pushHistoric(Boolean signal, Sensor sensor, String description, String index) {
		LocalDateTime agora = LocalDateTime.now();	
		JSONObject step_sensor = new JSONObject();
		
		step_sensor.put("signal", signal);
		step_sensor.put("type", sensor.getType());
		step_sensor.put("mac", sensor.getMac());
		step_sensor.put("value", sensor.getValue());
		step_sensor.put("description", description);
		step_sensor.put("dateTime", agora);
		
		this.historic.put(index, step_sensor);
	}
}
