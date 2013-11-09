package intex.BusinessObjects;

import intex.DataException;

/**
 * Fee BO
 *
 * @author Group 2-3
 * @version 1.1
 */
public class Fee extends RevenueSource {
	
	@BusinessObjectField
	private double amount;
	@BusinessObjectField
	private boolean waived;
	@BusinessObjectField
	private String rentalId;
	@BusinessObjectField
	private String empId;
	private Rental rental;
	
	/**creates new instance of this object*/
	public Fee(String id) {
		super(id);
	}

	@Override
	public int getQuantity() {
		return 1;
	}

	@Override
	public String getSkuOrRevenueType() {
		return "Fee";
	}

	@Override
	public void setQuantity(int quantity) {
		
		throw new RuntimeException("You should never set the quantity for a fee");
	

	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
		setDirty();
	}

	/**
	 * @return the waived
	 */
	public boolean isWaived() {
		return waived;
	}

	/**
	 * @param waived the waived to set
	 */
	public void setWaived(boolean waived) {
		this.waived = waived;
		setDirty();
	}

	/**
	 * @return the rental
	 */
	public Rental getRental() {
		return rental;
	}

	/**
	 * @param rental the rental to set
	 */
	public void setRental(Rental rental) {
		this.rental = rental;
		setDirty();
	}
	
	public void setRental(String rentalId){
		try {
			this.rental=BusinessObjectDAO.getInstance().read(rentalId);
		} catch (DataException e) {
			e.printStackTrace();
		}
		setDirty();
	}

	/**
	 * @return the rentalId
	 */
	public String getRentalId() {
		return rentalId;
	}

	/**
	 * @param rentalId the rentalId to set
	 */
	public void setRentalId(String rentalId) {
		this.rentalId = rentalId;
		setDirty();
	}

	/**
	 * @return the empId
	 */
	public String getEmpId() {
		return empId;
	}

	/**
	 * @param empId the empId to set
	 */
	public void setEmpId(String empId) {
		this.empId = empId;
		setDirty();
	}

}
