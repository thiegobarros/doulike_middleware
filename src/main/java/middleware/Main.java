package middleware;

public class Main {
	public static void main(String[] args)  {
		
		String topic = "creathus/doulike";
		int qos = 2;
		String broker = "tcp://192.168.0.93:1883";
		String clientId = "creathus/doulike";
		
		Subscriber subs = new Subscriber(
			topic,
			qos,
			broker,
			clientId
		);
		
		subs.create();
	}
}
