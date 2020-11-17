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
	
	public boolean send(String data) throws ClientProtocolException, IOException {
		PropertyConfigurator.configure(log4jConfPath);
		CloseableHttpClient client = HttpClients.createDefault();
	    HttpPost httpPost = new HttpPost("http://localhost:8080/doulike/REST/Service/Request");
	    
	    JSONObject json = new JSONObject();
	    json.put("serviceName", "CrudService");
	    json.put("methodName", "saveLogSensor");
	    
	    JSONObject values = new JSONObject(data);
	    
	    JSONObject parameters = new JSONObject();
	    parameters.put("name", "LogSensor");
	    parameters.put("value", values);
	    
	    Object[] arrayParams = new Object[1];
	    arrayParams[0] = parameters;
	    
	    json.put("parameters", arrayParams);

	    StringEntity entity = new StringEntity(json.toString());
	    httpPost.setEntity(entity);
	    httpPost.setHeader("Accept", "application/json");
	    httpPost.setHeader("Content-type", "application/json");
	 
	    CloseableHttpResponse response = client.execute(httpPost);
	    client.close();
	    
	    if (response.getStatusLine().getStatusCode() != 200) {
	    	logger.error("Save - Error");
	    	logger.error("Error "+response.getStatusLine().getStatusCode());
	    	logger.error("Error "+response.getEntity().getContent());
	    	return false;
	    }	    
	    logger.info("Save - Completed");
		return true;
	}
}
