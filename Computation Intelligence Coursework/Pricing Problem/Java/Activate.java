
public class Activate {
	private static PSO pso;

	private static AIS ais;

	public static void main(String[] args) {

		// Uncomment to Run ais.run for Artificial Immune System algorithm
		//ais = new AIS();
		//ais.run();
		
		// Uncomment to Run pso.run for Particle Swarm algorithm
		pso = new PSO();
		pso.run();
	}

}
