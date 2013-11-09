package intex.BusinessObjects;


/**
 * CRental BO
 * 
 * @author Group 2-3
 * @version 1.1
 */
public class CRental extends CProduct {

	public void construct(double pricePerDay, double replacementPrice) {
		this.pricePerDay = pricePerDay;
		this.replacementPrice = replacementPrice;
	}


	@BusinessObjectField
	private double pricePerDay;
	@BusinessObjectField
	private double replacementPrice;
	
	 /** Creates a new instance of this object */
	public CRental(String id) {
		super(id);
	}


	/**
	 * @return the pricePerDay
	 */
	public double getPricePerDay() {
		return pricePerDay;
	}


	/**
	 * @param pricePerDay the pricePerDay to set
	 */
	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}


	/**
	 * @return the replacementPrice
	 */
	public double getReplacementPrice() {
		return replacementPrice;
	}


	/**
	 * @param replacementPrice the replacementPrice to set
	 */
	public void setReplacementPrice(double replacementPrice) {
		this.replacementPrice = replacementPrice;
	}

	
	
	
}
