package middleware;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Subscriber implements MqttCallback {

	private String topic;
	private int qos;
	private String broker;
	private String clientId;
	private static final Logger logger = Logger.getLogger(Subscriber.class);
	private String log4jConfPath = "src/main/java/middleware/log4j.properties";
	
	public Subscriber(
		String topic,
		int qos,
		String broker,
		String clientId
	) {
		setTopic(topic);
		setQos(qos);
		setBroker(broker);
		setClientId(clientId);
	}
	
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public int getQos() {
		return qos;
	}

	public void setQos(int qos) {
		this.qos = qos;
	}

	public String getBroker() {
		return broker;
	}

	public void setBroker(String broker) {
		this.broker = broker;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void create() {
	
		PropertyConfigurator.configure(log4jConfPath);
		MemoryPersistence persistence  = new MemoryPersistence();
		String username = "creathus";
		String password = "doulike";
		
	    try {
	    	
	    	//Settings
	        MqttClient sampleClient = new MqttClient(getBroker(), getClientId(), persistence);
	        MqttConnectOptions connOpts = new MqttConnectOptions();
	        connOpts.setCleanSession(true);
	        sampleClient.setCallback(this);

	        //Connection
	        logger.info("Connecting to broker: "+getBroker());
	        connOpts.setUserName(username);
	        connOpts.setPassword(password.toCharArray());
	        sampleClient.connect(connOpts);
	        logger.info("Connected");
	        
	        //Loop Connection
	        while (sampleClient.isConnected()) {
	        	sampleClient.subscribe(getTopic(), getQos());
	        }
	        
	        //Disconnect
	        sampleClient.disconnect();
	        logger.info("Disconnected");
            System.exit(0);
            
	    } catch(MqttException me) {
	    	
	        logger.error("reason "+me.getReasonCode());
	        logger.error("msg "+me.getMessage());
	        logger.error("loc "+me.getLocalizedMessage());
	        logger.error("cause "+me.getCause());
	        logger.error("excep "+me);
	        me.printStackTrace();
	        
	    }
	}

	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
	}
	
	public void messageArrived(String topic, MqttMessage message) throws MqttException, ClientProtocolException, IOException {
		String data = new String(message.getPayload());
		logger.info(String.format("[%s] %s", topic, data));
        Save save = new Save();
        save.send(data);
    }
	
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
	}
}
