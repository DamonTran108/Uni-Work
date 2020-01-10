import java.util.Arrays;
import java.util.Random;

public class Main {
	private int numOfAntenna = 5;

	private AntennaArray array = new AntennaArray(numOfAntenna, 55.0);
	private Random rnd;
	private Particle particle;

	public Main() {
		// particle = new Particle(0, 0, 0);
		rnd = new Random();
		double[] design = new double[10];
		design[0] = 0.5;
		design[1] = 1.0;
		design[2] = 1.5;

		run();
	}

	public void run() {
		
		PsoSolution();

	}

	private double[] generateSolution() {

		double value1 = 0;
		double value2 = 0;

		double value3 = 0;

		double[] solution = new double[3];
		boolean works = false;

		double val1 = 0;
		double val2 = 0;

		double val3 = 0;

		for (int i = 0; i < 1000; i++) {
			do {
				value1 = rnd.nextDouble() * 0.5;
				val1 = value1;
				solution[0] = val1;
				array.bounds();
				value2 = rnd.nextDouble() * (1.5 - val1) + val1;
				val2 = value2;
				solution[1] = val2;

				value3 = rnd.nextDouble() * (1.5 - val2) + val2;
				val3 = value3;
				solution[2] = 1.5;
				System.out.println(solution[0] + " " + solution[1] + " " + solution[2]);

				if (array.is_valid(solution) == true) {
					works = true;
					System.out.print("		valid solution	: ");
				}

			} while (!works);
			System.out.println(array.evaluate(solution));
		}
		return solution;

	}

	// -Initialisation of the particle values must be random-
	// Velocity is the difference between the pbPos and Cpos halved
	private Particle ConstructParticle(double velocity, double cPos, double pbPos, double SSLValue) {
		Particle particle = new Particle(velocity, cPos, pbPos, SSLValue);
		return particle;
	}

	private Particle2 ConstructParticle2(double[] velocity, double[] cPos, double[] pbPos, double SSLValue) {
		Particle2 particle = new Particle2(velocity, cPos, pbPos, SSLValue);
		return particle;
	}

	

	public boolean checkParticlePosition(double Cposition) {
		if (Cposition < 0.25 | Cposition > 1.25) {

			return false;
		}
		return true;
	}


	public int getGlobalBestIndex(double[] personalBests) {
		int index = 0;
		double globalBest = personalBests[0];

		for (int i = 0; i < personalBests.length; i++) {
			
			if (personalBests[i] < globalBest) {
				globalBest = personalBests[i];
				index = i;
			}
		}

		return index;
	}

	public double getLowestSSL(double[] SSLValues) {

		double lowestVal = SSLValues[0];

		// Arrays.sort(SSLValues);

		Arrays.sort(SSLValues);

		for (int i = 0; i < SSLValues.length; i++) {
			// System.out.println("SSL Values : " + i + " " + personalBests[i]);
			if (SSLValues[i] < lowestVal && SSLValues[i] != 0) {
				lowestVal = SSLValues[i];

			}
		}

		return lowestVal;
	}

	
	

	
	private void SSLLoop(double[] SSLValues) {
		for (int i = 0; i < SSLValues.length; i++) {
			System.out.println("------------------" + SSLValues[i] + "------------");
		}
	}

	


	private void something() {
		int population = 10;
		int iter_max = 100000;
		double[] antArray = new double[numOfAntenna];
		for (int j = 0; j < iter_max; j++) {
			for (int i = 0; i < population; i++) {
				double cPos = rnd.nextDouble() * (((antArray.length / 2) - 0.25) - array.MIN_SPACING)
						+ array.MIN_SPACING;
				double tempPoint = rnd.nextDouble() * (((antArray.length / 2) - 0.25) - array.MIN_SPACING)
						+ array.MIN_SPACING;
				double velocity = cPos - tempPoint / 2;
				double pbPos = 0;
				double SSLValue = 0;

				ConstructParticle(velocity, cPos, pbPos, SSLValue);
			}
		}
	}

	private void PsoSolution() {
		
		int population = 20;
		int iter_max = 40;
		double[] antArray = new double[numOfAntenna];

		Particle2 swarm[] = new Particle2[population];

		double[] velocity = new double[antArray.length];
		double[] cPos = new double[antArray.length];
		double[] pbPos = new double[antArray.length];
		double[] tempPos = new double[antArray.length];

		double SSLValue = 0;
		double globalBest = 0.0;
		

			for (int i = 0; i < swarm.length; i++) {
				// Generate array of current positions for antennas

				cPos = PsoSolution();
				tempPos = PsoSolution();
				pbPos = cPos;

				System.out.println("cPOS " + array.evaluate(cPos));
				System.out.println("tempPos " + array.evaluate(tempPos));
				for (int h = 0; h < antArray.length; h++) {

					velocity[h] = (cPos[h] - tempPos[h]) / 2;
				}

				SSLValue = array.evaluate(cPos);
				

				System.out.println("SSL value " + SSLValue);

				swarm[i] = new Particle2(velocity, cPos, pbPos, SSLValue);
				System.out.println("new SLL VALUE " + SSLValue);



			}
			
			globalBest = swarm[0].getSSLValue();
			
			for (int counter = 0; counter < iter_max; counter++) {
				for (Particle2 pies : swarm) {
					if (globalBest > pies.getSSLValue()) {
						
						globalBest = pies.getSSLValue();
						System.out.println("new globalBest Found`" + globalBest);
					}
				}
				for (Particle2 particles : swarm) {
					for (int p = 0; p < velocity.length; p++) {
						tempPos = new double[cPos.length];
						tempPos = cPos;
						

						
						
												velocity[p] = 0.721 * particles.getVelocity()[p]
								+ 1.1193 * rnd.nextDouble()
										* (particles.getPB()[p] - particles.getcPos()[p] /* cPos[p] */)
								+ 1.1193 * rnd.nextDouble() * (globalBest - particles.getcPos()[p]);
												
						
						
						
						
												cPos[p] = particles.getcPos()[p] + particles.getVelocity()[p];

						
					}
					cPos[antArray.length-1] = antArray.length/2.0;
					
					particles.savecPos(cPos);
					System.out.println("nope"+ array.evaluate(cPos));
					particles.setVelocity(velocity);
					antArray = particles.getcPos();
					double tempSSL = 0;

					if (array.is_valid(particles.getcPos())) {

					
						tempSSL = array.evaluate(particles.getcPos());
						System.out.println("|||||||||||||||||||||" + tempSSL);
						System.out.println("current SSL " + particles.getSSLValue());

						if (tempSSL < particles.getSSLValue()) {
							System.out.println("new personal best found ");
							particles.savePB(cPos);
							particles.saveSSLValue(tempSSL);
							

						}
						
						
					}
					}
			
					
				
			
			}
			for (Particle2 pies : swarm) {
				double pieSSL = pies.getSSLValue();
				System.out.println("ALL SSL " + pieSSL);
				}
			System.out.println("india " + globalBest);
		}
	}

