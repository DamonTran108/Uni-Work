import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

public class AIS {
	// Defining of class variables
	private PriceList[] parentPrices;
	private int numberofGoods;
	private PricingProblem f;
	private Random rng;
	private int solutionPopSize;
	private Double temp;
	private double revenue;
	private HashMap<double[], Double> hmap;

	/**
	 * Set up class variables for algorithm
	 */
	public AIS() {
		temp = new Double(revenue);
		hmap = new HashMap<double[], Double>();

		solutionPopSize = 5;
		rng = new Random();
		numberofGoods = 20;
		parentPrices = new PriceList[solutionPopSize];

		f = PricingProblem.courseworkInstance();
	}

	public void run() {
		int iter_max =200;
		generateRandomparentPrices();

		for (int i = 0; i < iter_max; i++) {
			ais();

		}
	}

	/**
	 * Method for generating random prices and storing it in an array
	 * 
	 * @return double array consisting of prices
	 */
	public double[] generateRandomPrice() {
		double[] prices = new double[numberofGoods];
		for (int i = 0; i < numberofGoods; i++) {

			prices[i] = rng.nextDouble() * 10;

		}
		return prices;
	}

	/**
	 * Generate an array of PriceList objects and storing them inside the array
	 * 
	 * @return PriceList Array with newly constructed pricelist objects
	 */
	public PriceList[] generateRandomparentPrices() {

		for (int p = 0; p < parentPrices.length; p++) {

			double[] allparentPrices = new double[numberofGoods];
			for (int i = 0; i < numberofGoods; i++) {

				allparentPrices[i] = rng.nextDouble() * 10;

			}
			revenue = f.evaluate(allparentPrices);
			System.out.println("revenue is " + revenue);
			PriceList solution = new PriceList(allparentPrices, revenue);
			parentPrices[p] = solution;

			for (int j = 0; j < numberofGoods; j++) {
				System.out.println("number " + j + " " + solution.getPrices()[j]);
			}
		}

		return parentPrices;
	}

	/**
	 * Clone method for cloning initial randomly generated revenues
	 */
	public PriceList[] clone() {
		PriceList[] clone1 = new PriceList[solutionPopSize];
		for (int c = 0; c < parentPrices.length; c++) {
			clone1[c] = parentPrices[c];
		}

		return clone1;
	}

	/**
	 * Gets the normalised fitness for each solution in the initial population and
	 * stores it in a local array
	 * 
	 * @return an array that keeps the normalised fitness level for all initial
	 *         price solutions
	 */
	private double[] NormaliseFitness() {
		double[] nF = new double[solutionPopSize];
		PriceList[] tempParentPrices = new PriceList[parentPrices.length];
		for (int c = 0; c < parentPrices.length; c++) {
			tempParentPrices[c] = parentPrices[c];
		}

		for (int i = 0; i < tempParentPrices.length; i++) {
			nF[i] = tempParentPrices[i].getRevenue() / getBestFitness();
		}

		for (int j = 0; j < nF.length; j++) {
			// System.out.println(nF[j]);
		}
		return nF;
	}

	/**
	 * Checks the entire PriceList array of objects to find the best revenue out of
	 * all the pricelist objects
	 * 
	 * @return the best revenue in the array
	 */
	private double getBestFitness() {
		PriceList[] tempParentPrices = new PriceList[parentPrices.length];
		for (int c = 0; c < parentPrices.length; c++) {
			tempParentPrices[c] = parentPrices[c];
		}
		// Assign local variable to first index revenue in the array
		double bestRevenue = tempParentPrices[0].getRevenue();

		// Loop to check if any index revenue is greater than it
		for (int i = 0; i < tempParentPrices.length; i++) {
			//If so, update best revenue
			if (bestRevenue < tempParentPrices[i].getRevenue()) {
				bestRevenue = tempParentPrices[i].getRevenue();
			}
		}

		return bestRevenue;
	}

	
	/**
	 * Creates a set of clones that is set by variable numOfClones. And adds the
	 * array to an arraylist
	 * 
	 * @return ArrayList of clones of parent solutions
	 */
	public ArrayList<PriceList[]> createClonalPool() {
		int numOfClones = 5;
	
		ArrayList<PriceList[]> clones = new ArrayList<PriceList[]>();
		for (int i = 0; i < numOfClones; i++) {
	
			clones.add(clone());
		}
	
		return clones;
	}

	/**
	 * Finds the best solution to the problem it can using the artificial immune
	 * system algorithm
	 */
	public void ais() {
		// Variable to assign choose how many worst solutions you replace
		int displacement = 2;

		ArrayList<PriceList[]> clonalPool = new ArrayList<PriceList[]>();
		clonalPool = createClonalPool();
		Random rnd = new Random();
		// Variable to apply to mutation and mutate the prices
		double guassian;

		// P is 'rho' variable used to assign a hotspot to perform contiguous mutation
		// and find mutation rate.
		int p = 0;

		double mutationRate = 0;

		// Counter variable to apply the correct normalised fitness value
		int counter = 0;

		PriceList[] mutatedClones = new PriceList[clonalPool.size() * parentPrices.length];
		PriceList[] selectionArray = new PriceList[mutatedClones.length + parentPrices.length];

		int mutationCount = 0;
		double revenue = 0;

		// Loop through every cloned PriceList array to find each priceList object
		// (solution) in the clonalpool
		for (PriceList[] parentCloneArray : clonalPool) {

			counter = 0;

			// Loop through each PriceList object (solution) in the parentCloneArray
			// To perform mutations to the prices in each solution.
			for (PriceList priceList : parentCloneArray) {

				

				// Set Rho variable
				p = 1;

				//Set mutation rate
				mutationRate = Math.exp(-p * NormaliseFitness()[counter]);

				guassian = (rnd.nextDouble() * 2) - 1;

				System.out.println("mutation Rate is " + mutationRate);

				// Set length to find contiguous region
				int length = (int) Math.round((numberofGoods * mutationRate));

				System.out.println("length is " + length);

				// Loop to apply mutation to a specific contiguous region of the prices.
				for (int pIndex = p; pIndex < p + length; pIndex++) {

					

					System.out.println("");
					// If statement to counter array out of bounds (when mutation can't be applied
					// to values outside the assigned region)
					if (pIndex >= numberofGoods) {

						revenue = f.evaluate(priceList.getPrices());

						// Construct new PriceList object with mutation applied and revenue and store it
						// in new local array
						PriceList tempPriceList = new PriceList(priceList.getPrices(), revenue);
						mutatedClones[mutationCount] = tempPriceList;
						break;

					}
					// Mutate prices at given region indexes
					priceList.getPrices()[pIndex] = priceList.getPrices()[pIndex] + (mutationRate * guassian);

				}

				
				revenue = f.evaluate(priceList.getPrices());
				System.out.println("mutated revenue: " + revenue);

				// Construct new PriceList object with mutation applied and revenue and store it
				// in new local array
				PriceList tempPriceList = new PriceList(priceList.getPrices(), revenue);
				mutatedClones[mutationCount] = tempPriceList;
				mutationCount++;
				counter++;
			}

		}

		// Loop to check mutated prices
		for (PriceList pricelists : mutatedClones) {
			for (int index = 0; index < pricelists.getPrices().length; index++) {
				System.out.println("mutated Prices- " + "Price number" + index + ": " + pricelists.getPrices()[index]);

			}

			System.out.println("mutated revenues: " + pricelists.getRevenue());
			System.out.println(" ");
		}

		// Putting the parent solution and mutated clones into a new array for next
		// phase of clonal Selection.
		System.out.println("------------------ADDING MUTATED AND PARENT SOLUTIONS TO NEW ARRAY------------------");
		int i;
		for (i = 0; i < parentPrices.length; i++) {

			selectionArray[i] = parentPrices[i];
			System.out.println("Parent revenue:  " + selectionArray[i].getRevenue());

		}

		int j;
		int c = i;
		for (j = 0; j < mutatedClones.length; j++) {

			selectionArray[c] = mutatedClones[j];
			c++;
		}
		System.out.println("");
		for (PriceList ps : selectionArray) {
			System.out.println("UnSorted selection array revenue: " + ps.getRevenue());
		}

		// bubbleSort
		// Boolean to trigger when to stop loop
		boolean flag = true;
		// Loop for first index comparison
		for (int index = 0; index < selectionArray.length - 1; index++) {
			// Loop for second index comparison
			for (int index2 = 0; index2 < selectionArray.length - index - 1; index2++) {
				// If first index revenue is greater than the next
				if (selectionArray[index2].getRevenue() > selectionArray[index2 + 1].getRevenue()) {
					// Store the greater object in temp variable
					PriceList temp = selectionArray[index2];

					// Set lower value object in index2 to lower index
					selectionArray[index2] = selectionArray[index2 + 1];

					// Set greater value object in index2 to the greater index.
					selectionArray[index2 + 1] = temp;

					flag = false;

				}

			}

			if (flag) {

				break;

			}

		}

		System.out.println(" ");
		System.out.println("----------------Sorted Array-----------------");
		for (int h = 0; h < selectionArray.length; h++) {
			System.out.println(selectionArray[h].getRevenue());
		}

		// Re-elect parentPrices as best 5 from selectionArray
		int bestF = selectionArray.length - 1;
		for (int so = 0; so < solutionPopSize; so++) {
			parentPrices[so] = selectionArray[bestF];
			bestF--;
		}

		// Print statements to see the new parentPrices array
		System.out.println("");
		System.out.println("-----------New Parents------------");
		for (int a = 0; a < parentPrices.length; a++) {
			System.out.println("revenue: " + parentPrices[a].getRevenue());
		}
		System.out.println("------------------------------------");

		// Keep best 3 replace worst 2 with random solutions
		for (int so = parentPrices.length - displacement; so < parentPrices.length; so++) {
			double[] price = new double[numberofGoods];
			price = generateRandomPrice();
			revenue = f.evaluate(price);
			PriceList randomPrice = new PriceList(price, revenue);
			parentPrices[so] = randomPrice;
		}

		for (int index = 0; index < parentPrices[0].getPrices().length; index++) {
			System.out.println("best prices to use for revenue: " + parentPrices[0].getPrices()[index]);

		}
		System.out.println("best revenue: " + parentPrices[0].getRevenue());

	}

}
