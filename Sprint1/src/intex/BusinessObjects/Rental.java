package intex.BusinessObjects;

import intex.DataException;

import java.util.Date;

/**
 * Rental BO
 *
 * @author Group 2-3
 * @version 1.1
 */
public class Rental extends RevenueSource {
	
	
	
	
	public void construct(Date dateIn, Date dateOut, Date dateDue,
			boolean reminderSent, String forRentId) {

		this.dateIn = dateIn;
		this.dateOut = dateOut;
		this.dateDue = dateDue;
		this.reminderSent = reminderSent;
		this.forRentId = forRentId;
	}

	@BusinessObjectField
	private Date dateIn;
	@BusinessObjectField
	private Date dateOut;
	@BusinessObjectField
	private Date dateDue;
	@BusinessObjectField
	private boolean reminderSent;
	@BusinessObjectField
	private String forRentId;
	private ForRent forRent;
	
	
	
	
	/**creates a new instance of this object*/
	public Rental(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getSkuOrRevenueType() {
		return "Rental";
	}

	/**
	 * @return the dateIn
	 */
	public Date getDateIn() {
		return dateIn;
	}

	/**
	 * @param dateIn the dateIn to set
	 */
	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
		setDirty();
	}

	/**
	 * @return the dateOut
	 */
	public Date getDateOut() {
		return dateOut;
	}

	/**
	 * @param dateOut the dateOut to set
	 */
	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
		setDirty();
	}

	/**
	 * @return the dateDue
	 */
	public Date getDateDue() {
		return dateDue;
	}

	/**
	 * @param dateDue the dateDue to set
	 */
	public void setDateDue(Date dateDue) {
		this.dateDue = dateDue;
		setDirty();
	}

	/**
	 * @return the reminderSent
	 */
	public boolean isReminderSent() {
		return reminderSent;
	}

	/**
	 * @param reminderSent the reminderSent to set
	 */
	public void setReminderSent(boolean reminderSent) {
		this.reminderSent = reminderSent;
		setDirty();
	}

	/**
	 * @return the forRentId
	 */
	public String getForRentId() {
		return forRentId;
	}

	/**
	 * @param forRentId the forRentId to set
	 */
	public void setForRentId(String forRentId) {
		this.forRentId = forRentId;
	}

	/**
	 * @return the forRent
	 */
	public ForRent getForRent() {
		return forRent;
	}

	/**
	 * @param forRent the forRent to set
	 */
	public void setForRent(String forRentId) {
		try {
			this.forRent = BusinessObjectDAO.getInstance().read(forRentId);
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int getQuantity() {
		return 1;
	}

	@Override
	public void setQuantity(int quantity) {
		throw new RuntimeException("shouldn't happen");
		
	}

	/**
	 * @param forRent the forRent to set
	 */
	public void setForRent(ForRent forRent) {
		this.forRent = forRent;
		setDirty();
	}

}
