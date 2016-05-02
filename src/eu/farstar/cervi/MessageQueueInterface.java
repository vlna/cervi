package eu.farstar.cervi;

public interface MessageQueueInterface {
	
	public void add(String messageId, int messageValue);
	public boolean processQueue();
}
