package intex.BusinessObjects;

import java.util.Date;

/**
 * Membership BO
 *
 * @author Group 2-3
 * @version 1.1
 */
public class Membership extends BusinessObject {
	
	 
	public void construct(String creditCard, Date startDate,
			Date expirationDate, String securityCode, boolean trial,
			String custId) {
		this.creditCard = creditCard;
		this.startDate = startDate;
		this.expirationDate = expirationDate;
		this.securityCode = securityCode;
		this.trial = trial;
		this.custId = custId;
	}

	@BusinessObjectField
	private String creditCard;
	@BusinessObjectField
	private Date startDate;
	@BusinessObjectField
	private Date expirationDate;
	@BusinessObjectField
	private String securityCode;
	@BusinessObjectField
	private	boolean trial;
	@BusinessObjectField
	private String custId;
	
	/**creates a new instance of this object*/
	public Membership(String id) {
		super(id);
	}

	/**
	 * @return the creditCard
	 */
	public String getCreditCard() {
		return creditCard;
	}

	/**
	 * @param creditCard the creditCard to set
	 */
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
		setDirty();
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
		setDirty();
	}

	/**
	 * @return the expirationDate
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
		setDirty();
	}

	/**
	 * @return the securityCode
	 */
	public String getSecurityCode() {
		return securityCode;
	}

	/**
	 * @param securityCode the securityCode to set
	 */
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
		setDirty();
	}
	

	/**
	 * @return the trial
	 */
	public boolean isTrial() {
		return trial;
	}

	/**
	 * @param trial the trial to set
	 */
	public void setTrial(boolean trial) {
		this.trial = trial;
		setDirty();
	}

	/**
	 * @return the custId
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * @param custId the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
		setDirty();
	}

}
