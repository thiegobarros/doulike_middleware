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

public class Subscriber implements MqttCallback {

	private String topic;
	private int qos;
	private String broker;
	private String clientId;
	
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
	        System.out.println("Connecting to broker: "+getBroker());
	        connOpts.setUserName(username);
	        connOpts.setPassword(password.toCharArray());
	        sampleClient.connect(connOpts);
	        System.out.println("Connected");
	        
	        //Loop Connection
	        while (sampleClient.isConnected()) {
	        	sampleClient.subscribe(getTopic(), getQos());
	        }
	        
	        //Disconnect
	        sampleClient.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
            
	    } catch(MqttException me) {
	    	
	        System.out.println("reason "+me.getReasonCode());
	        System.out.println("msg "+me.getMessage());
	        System.out.println("loc "+me.getLocalizedMessage());
	        System.out.println("cause "+me.getCause());
	        System.out.println("excep "+me);
	        me.printStackTrace();
	        
	    }
	}

	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
	}
	
	public void messageArrived(String topic, MqttMessage message) throws MqttException, ClientProtocolException, IOException {
		String data = new String(message.getPayload());
        System.out.println(String.format("[%s] %s", topic, data));
        Save save = new Save();
        save.send(data);
    }
	
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
	}
}
