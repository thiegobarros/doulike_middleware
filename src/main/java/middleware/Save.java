package middleware;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Save {
	
	private static final Logger logger = Logger.getLogger(Subscriber.class);
	private String log4jConfPath = "src/main/java/middleware/log4j.properties";
	
	public boolean send(String data, Sensor sensor, Historic hist) throws ClientProtocolException, IOException {
		PropertyConfigurator.configure(log4jConfPath);
		CloseableHttpClient client = HttpClients.createDefault();
	    HttpPost httpPost = new HttpPost("http://localhost:8080/doulike/logSensor");
	    
	    try {
	    	JSONObject json = new JSONObject(data);

		    StringEntity entity = new StringEntity(json.toString());
		    httpPost.setEntity(entity);
		    httpPost.setHeader("Accept", "application/json");
		    httpPost.setHeader("Content-type", "application/json");
		 
		    CloseableHttpResponse response = client.execute(httpPost);
		    client.close();
		    
		    if (response.getStatusLine().getStatusCode() != 200) {
		    	logger.error("Sensor request - "+sensor.print());
		    	logger.error("Error - "+response.getStatusLine().getStatusCode());
		    	logger.error("Error - "+response.getEntity().getContent());
		    	hist.pushHistoric(false, sensor, response.getEntity().getContent().toString(), "requestError");
		    	return false;
		    }
		    
		    logger.info("Saved - "+sensor.print());
		    hist.pushHistoric(true, sensor, "Sensor salvo com sucesso", "successSave");
			return true;
			
	    } catch (IOException ex) {
	    	logger.error("Sensor - "+sensor.print());
	    	logger.error("Exception - "+ex);
	    	hist.pushHistoric(false, sensor, ex.toString(), "internalError");
	    	return false;
	    }
	}
	
	public boolean sendHistoric(Historic hist) throws ClientProtocolException, IOException {
		PropertyConfigurator.configure(log4jConfPath);
		CloseableHttpClient client = HttpClients.createDefault();
	    HttpPost httpPost = new HttpPost("http://localhost:8080/doulike/logMiddleware/All");
		
		try {			
			StringEntity entity = new StringEntity(hist.get().toString());
		    httpPost.setEntity(entity);
		    httpPost.setHeader("Accept", "application/json");
		    httpPost.setHeader("Content-type", "application/json");
		 
		    CloseableHttpResponse response = client.execute(httpPost);
		    client.close();
		    
		    if (response.getStatusLine().getStatusCode() != 200) {
		    	logger.error("Historic request");
		    	logger.error("Error - "+response.getStatusLine().getStatusCode());
		    	logger.error("Error - "+response.getEntity().getContent());
		    	return false;
		    }
		    
		    logger.info("Historic Saved");
			return true;
		} catch (IOException ex) {
			logger.info("Historic Internal Error");
			return false;
		}
	}
}
