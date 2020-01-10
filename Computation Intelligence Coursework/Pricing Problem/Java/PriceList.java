
public class PriceList {
	// Defining Class variables
	double[] prices;
	double revenue;

	public PriceList(double[] prices, double revenue) {
		// Construct the array and assign it to the parameter in a loop to avoid them
		// sharing same memory location
		this.prices = new double[prices.length];
		for (int i = 0; i < prices.length; i++) {
			this.prices[i] = prices[i];
		}

		this.revenue = revenue;
	}

	public double[] getPrices() {
		return prices;
	}

	public void setPrices(double[] newPrices) {
		this.prices = newPrices;
	}

	public double getRevenue() {
		return revenue;
	}

	public void setRevenue(double newRevenue) {
		this.revenue = newRevenue;
	}

}
