package eu.farstar.cervi;

public class Config {

	private static Config config;

	private int noOfPlayers;
	private float wormSpeed;

	private Config() { }

	public static Config getInstance() {
		if (config == null) {
			config = new Config();
			config.setNoOfPlayes(2);
			config.setWormSpeed(20);
		}
		return config;
	}

	public void setNoOfPlayes(int i) {
		this.noOfPlayers=2;
	}

	public int getNoOfPlayes() {
		return this.noOfPlayers;
	}
	
	public float getWormSpeed() {
		return wormSpeed;
	}

	public void setWormSpeed(float wormSpeed) {
		this.wormSpeed = wormSpeed;
	}

}
