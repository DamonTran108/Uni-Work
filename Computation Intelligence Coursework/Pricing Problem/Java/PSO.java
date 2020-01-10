import java.util.Random;

public class PSO {
	// Initialisation of class variables....

	// Double Array to store swarm's best set of prices for goods.
	private double[] globalbestPrices;

	// Double array to store initial random prices
	private double[] prices;

	private int numberofGoods;
	private Random rng;
	//variable f of object PricingProblem for fitness function
	PricingProblem f;

	/**
	 * 
	 * Runs the PSO algorithm
	 */
	public void run() {

		PSOsolution();
	}

	// Constructing class variables and assigning values.
	public PSO() {
		numberofGoods = 20;
		rng = new Random();
		f = PricingProblem.courseworkInstance();
		prices = new double[numberofGoods];
	}

	/**
	 * Creates a set of prices and stores it in an array
	 * @return a single solution instance
	 */
	public double[] generateRandomPrices() {
		for (int i = 0; i < prices.length; i++) {
			prices[i] = rng.nextDouble() * 10;

		}
		return prices;
	}

	/**
	 * PSO algorithm , particles create individual solutions and try find the optimal or best solution in the swarm to create the greatest revenue.
	 */
	private void PSOsolution() {
		// Number of particles
		int population = 50;
		Random rnd = new Random();
		// Number of iterations to run the algorithm
		int iter_max = 200;

		// Global best (swarm's best revenue)
		double bestRevenue = 0;

		// Revenue for individual particle assignments
		double revenue = 0;

		// Defining of local arrays to assign to each particle upon construction
		double[] velocity = new double[prices.length];
		double[] cPrices = new double[prices.length];
		double[] pbPrices = new double[prices.length];
		double[] tempPrices = new double[prices.length];

		// Array of particle objects
		Particle swarm[] = new Particle[population];

		// Loop to construct individual particles and assign them appropriate values.
		for (int i = 0; i < swarm.length; i++) {

			cPrices = generateRandomPrices();
			tempPrices = generateRandomPrices();
			pbPrices = cPrices;

			// Loop to construct initial velocity for particle
			for (int h = 0; h < prices.length; h++) {

				velocity[h] = (cPrices[h] - tempPrices[h]) / 2;
			}

			revenue = f.evaluate(cPrices);

			System.out.println("original revenue: " + revenue);

			// Constructing particle and inserting to swarm array
			swarm[i] = new Particle(velocity, cPrices, pbPrices, revenue);

			// Finding global best revenue of swarm
			if (bestRevenue < swarm[i].getRevenue()) {

				bestRevenue = swarm[i].getRevenue();
				globalbestPrices = swarm[i].getPB().clone();
				System.out.println("new best revenue Found: " + bestRevenue);
			}
		}

		// Iteration starts...
		for (int counter = 0; counter < iter_max; counter++) {
			// Global best at time of iteration
			System.out.println("global best revenue is " + bestRevenue);

			// Loop to update each particle's velocity and position (position being prices)
			// in the swarm
			for (Particle particles : swarm) {
				// Reconstruct local array to ensure memory location assignment is correct
				velocity = new double[prices.length];
				for (int p = 0; p < velocity.length; p++) {

					// Updating velocity
					velocity[p] = 0.721 * particles.getVelocity()[p]
							+ 1.1193 * rnd.nextDouble()
									* (particles.getPB()[p] - particles.getcPrices()[p])
							+ 1.1193 * rnd.nextDouble() * (globalbestPrices[p] - particles.getcPrices()[p]);

					System.out.println(velocity[p]);

					// Updating particle's position (prices)
					cPrices[p] = particles.getcPrices()[p] + velocity[p];

				}
				System.out.println("");

				// Local variable to store newly created prices' revenue
				double tempRev = 0;

				prices = cPrices;
				// Check if new prices are valid, if true then store them.
				if (f.is_valid(prices)) {
					particles.savecPrices(cPrices);
					particles.setVelocity(velocity);

					// Assign local variable to newly updated prices' revenue
					tempRev = f.evaluate(particles.getcPrices());

					// Current particle's revenue
					System.out.println("current revenue: " + particles.getRevenue());

					// If new revenue produced is better than current update the personal best
					if (tempRev > particles.getRevenue()) {
						System.out.println("new personal best found ");
						System.out.println("|||||||||||||||||||||" + tempRev);
						particles.savePB(cPrices);
						particles.saveRevenue(tempRev);

						// If new revenue produced is better than global best update global best revenue
						// and prices;
						if (tempRev > bestRevenue) {
							bestRevenue = tempRev;
							globalbestPrices = particles.getcPrices().clone();
							System.out.println("new global best found " + tempRev);
						}
					}

				}

				// If new updated velocity and prices are not valid... save them anyway, they
				// will be attracted back into a feasible region due to global and personal
				// best.
				particles.savecPrices(cPrices);
				particles.setVelocity(velocity);

			}

		}

		// counter variable to print out all revenues of the swarm
		int counter = 0;
		for (Particle pies : swarm) {
			double pieSSL = pies.getRevenue();
			System.out.println("ALL revenue: " + " " + counter + " " + pieSSL);
			counter++;
		}
		// Loop to print out all prices of the global best
		for (int l = 0; l < prices.length; l++) {
			System.out.println(globalbestPrices[l]);
		}
		System.out.println("global best revenue: " + bestRevenue);

	}

}
