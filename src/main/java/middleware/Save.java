package middleware;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;

public class Save {
	public boolean send(String data) throws ClientProtocolException, IOException {
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
//	    System.out.println(response.getStatusLine().getStatusCode());
//	    System.out.println(response.getEntity().getContent());
//	    assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
	    
	    client.close();
	    if (response.getStatusLine().getStatusCode() != 200) {
	    	return false;
	    }	    
		return true;
	}
}
