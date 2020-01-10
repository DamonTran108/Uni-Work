import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class TSP_AIS {
	// private TSP_AIS tsp;
	private float[] solutions;
	// private String[] initSolutions;
	private double fitness;
	private int population;

	private String[][] weights;
	private String[][] csvWeights;
	// private ArrayList<String> route = new ArrayList<>();
	static ArrayList<float[]> cities = new ArrayList<float[]>();
	int pop = cities.size();
	private int clones;
	// private ArrayList<float[]> newRoute = new ArrayList<float[]>();
	private float[] tempCities = new float[cities.size()];
	private float[][] initSolutions;
	private float[] clonalPool;

	public TSP_AIS() {
		clones = 6;
		population = 6;
		initSolutions = new float[population][cities.size() + 1];
		// collumn 0 will store the best solution and the rest will store the
		// solutions t
		solutions = new float[population];
		clonalPool = new float[population];
		// initSolutions = new float[population][cities.size()];

		// initSolutions = new String[population];
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		TSP_AIS tsp = new TSP_AIS();
		tsp.readFile();

		tsp.generateRandomRoute(cities);
		tsp.getCostOfRoute2(cities);
		tsp.localSearch(cities);
		tsp.cloneParentSolutions();
		// tsp.localSearch(cities);
		tsp.generateInitalSolutions();

	}

	public void readFile() {
		// String path = "";
		// int counter = 0;

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("ulysses16.csv"));
			String line = "";
			while ((line = reader.readLine()) != null) {
				try {

					String[] words = line.split(",");
					float[] index0 = { Float.parseFloat(words[0]), Float.parseFloat(words[1]),
							Float.parseFloat(words[2]) };

					cities.add(index0);

					System.out.println("index 0 : " + index0[0] + "   " + "index 1 : " + index0[1] + "  " + "index 2 : "
							+ index0[2]);
				} catch (NumberFormatException e) {
					continue;
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public float getCostOfRoute2(ArrayList<float[]> cities) {

		// Collections.shuffle(cities);

		// Xb-Xa^2 + yb-ya^2
		float cost = 0;
		// Loop thhrough cities arraylist
		// get index and the x and y values
		// get index2 and x values
		float Xb = 0;
		float Xa = 0;

		float Yb = 0;
		float Ya = 0;

		float xDifference = 0;
		float yDifference = 0;

		int index = 0;

		for (float[] element : cities) {
			if (index == cities.size() - 1) {
				System.out.println("poo");

				return cost;
			}
			float[] tElement = cities.get(index + 1);
			System.out.println("city size is : " + cities.size());

			System.out.println("city : " + element[0]);
			System.out.print(" " + " X :" + element[1]);
			System.out.println(" " + " Y :" + element[2]);

			Xa = element[1];
			Ya = element[2];

			Xb = tElement[1];
			Yb = tElement[2];

			System.out.println("Temp elements city : " + tElement[0]);

			System.out.println("Temp elements x : " + tElement[1]);

			System.out.println("Temp elements y : " + tElement[2]);

			xDifference = Xb - Xa;
			yDifference = Yb - Ya;

			cost += Math.sqrt((xDifference * xDifference) + (yDifference * yDifference));
			System.out.println("index : " + index);

			System.out.println(cost);
			index++;

		}

		System.out.println("Xa:  " + Xa + "	Xb :" + Xb);

		return cost;
	}

	private double localSearch(ArrayList<float[]> cities) {
		float normalisedFitness = 0;
		float solution = 0;
		ArrayList<ArrayList<float[]>> solutionsha = new ArrayList<ArrayList<float[]>>();
		;
		initSolutions = new float[population][cities.size() + 1];
		for (int i = 0; i < 6; i++) {
			// generateRandomRoute(cities);

			float[] tempSolution = new float[1];
			tempSolution[0] = (float) solution;
			ArrayList<float[]> tempRoute = new ArrayList<float[]>();
			ArrayList<float[]> newRoute = new ArrayList<float[]>();
			tempCities = new float[cities.size()];

			// float[] Troute = new float[tempRoute.size()];

			newRoute = generateRandomRoute(cities);
			tempRoute = newRoute;
			solution = getCostOfRoute2(tempRoute);
			solutionsha.add(tempRoute);
			for (float pee : solutionsha.get(0).get(2)) {
				System.out.println("pee " + pee);
			}
			// solutionsha.get(1);
			// Stores best route and outputs best solution
			// if (solution > getCostOfRoute2(cities)) {
			// tempRoute = newRoute;
			// solution = getCostOfRoute2(cities);

			// System.out.println(cities.get(i));
			// }
			int f = 0;
			for (float[] element : tempRoute) {

				tempCities[f] = element[0];
				for (int j = 0; j < cities.size(); j++) {

					// while (j < cities.size()) {
					// tempCities[j] = element[0];

					System.out.print("Cities : " + tempCities[f] + " ");
					initSolutions[i][0] = solution;
					initSolutions[i][f + 1] = tempCities[f];
					// System.out.println("Citieslol : " + initSolutions[i][f+
					// 1] + " ");
					System.out.println("solution " + i + "  " + initSolutions[i][0]);
					System.out.println("F " + f);
					// j++;

				}

				f++;
				System.out.println("hahah " + solutionsha.get(i));
			}

		}

		System.out.println("Best solution is " + solution);
		return solution;
	}

	private ArrayList<float[]> generateRandomRoute(ArrayList<float[]> cities) {
		Collections.shuffle(cities);
		return cities;
	}

	private float getBestFitness() {
		float bestFitness = initSolutions[0][0];
		for (int i = 0; i < initSolutions.length; i++) {
			// for (int j = 0; j < initS[i].length; j++) {
			if (bestFitness > initSolutions[i][0]) {
				bestFitness = initSolutions[i][0];
			}
			// }
		}
		return bestFitness;
	}

	private float getNormalisedFitness() {
		float normalisedFit = 0;
		getBestFitness();
		for (int i = 0; i < initSolutions.length; i++) {
			normalisedFit = getBestFitness() / initSolutions[i][0];
		}
		System.out.println("normalised fitness");
		return normalisedFit;
	}

	private void cloneParentSolutions() {
		// for (int c = 0; c < clones; c++) {
		for (int i = 0; i < initSolutions.length; i++) {
			System.out.println("Clone version : " + i);
			for (int j = 1; j < initSolutions[i].length; j++) {
				clonalPool[i] = initSolutions[i][j];
				System.out.println("clonal Pool " + clonalPool[i]);
			}
			// }
		}
	}

	private void generateInitalSolutions() {
		float solution = 0 ;
		ArrayList<ArrayList<float[]>> clonesha = new ArrayList<ArrayList<float[]>>();
		 float[] initFitness = new float[population]; 
		ArrayList<ArrayList<float[]>> solutionsha = new ArrayList<ArrayList<float[]>>();
		ArrayList<float[]> newRoute = new ArrayList<float[]>();
		
		//Storing the initial solutions in ArrayList
		for (int i = 0; i < population; i++) {
			newRoute = generateRandomRoute(cities);
			solution = getCostOfRoute2(newRoute);
			initFitness[i] = solution;
			solutionsha.add(newRoute);
			
			//clone the solutions
			for(int c = 0 ; c < clones;c++){
				clonesha.add(newRoute);
			}
		}
	}
	
	private float getBestSolution(float[] initFitness){
		float bestSolution = initFitness[0];
		for(int i = 0 ; i < initFitness.length; i++){
			if(bestSolution > initFitness[i]){
				bestSolution = initFitness[i]; 
			}
		}
		return bestSolution;

	}
	
	private float[] calculateNormalisedSolution(float[] initFitness){
		
		float bestSolution = getBestFitness();
		float[] normalisedFitness = new float[population];
		for(int i = 0 ; i < initFitness.length;i++){
			normalisedFitness[i] = initFitness[i] / bestSolution;
		double mutation = 	population * normalisedFitness[i];
		}
		return normalisedFitness;
	}
}
