import java.util.Random;
public class Particle2 {

	

	private double[] velocity;
	private double[] cPos;
	private double[] tempPos;
	private double[] pbPos;
	private double SSLValue;

	private Random rnd;

	public Particle2(double[] velocity, double[] cPos, double[] pbPos, double SSLValue) {

		this.velocity = velocity;
		this.cPos = cPos;
		this.pbPos = pbPos;
		this.SSLValue = SSLValue;

	}

	

	public double[] getPB() {

		System.out.println("pb is : " + pbPos);

		// System.out.println("pb is : " +pbPos);

		return pbPos;
	}

	public void savePB(double[] cPos) {
		pbPos = cPos;
	}

	public void savecPos(double[] cPos) {
		this.cPos = cPos;

	}

	public double[] getcPos() {
		// System.out.println("pb is : " +pbPos);
		return cPos;
	}

	public void saveSSLValue(double SSLValue) {
		this.SSLValue = SSLValue;
	}

	public double getSSLValue() {
		// System.out.println("SSL is : " + SSLValue);
		return SSLValue;
	}

	public double[] getVelocity() {
		return velocity;
	}

	public void setVelocity(double[] newVelocity) {

		velocity = newVelocity;
	}
}
