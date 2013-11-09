package intex.BusinessObjects;

import intex.DataException;

/**
 * Payment BO
 *
 * @author Group 2-3
 * @version 1.1
 */
public class Payment extends BusinessObject {
	
	@BusinessObjectField
	private double amount;
	@BusinessObjectField
	private double changeBack;
	@BusinessObjectField
	private String typeOf;
	@BusinessObjectField
	private String transId;
	private Trans trans;
	
	/**creates a new instance of this object*/
	public Payment(String id) {
		super(id);
		// TODO Auto-generated constructor stub
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
	setDirty();}

	/**
	 * @return the changeBack
	 */
	public double getChangeBack() {
		return changeBack;
	}

	/**
	 * @param changeBack the changeBack to set
	 */
	public void setChangeBack(double changeBack) {
		this.changeBack = changeBack;
	setDirty();}

	/**
	 * @return the typeOf
	 */
	public String getTypeOf() {
		return typeOf;
	}

	/**
	 * @param typeOf the typeOf to set
	 */
	public void setTypeOf(String typeOf) {
		this.typeOf = typeOf;
	setDirty();}

	/**
	 * @return the transId
	 */
	public String getTransId() {
		return transId;
	}

	/**
	 * @param transId the transId to set
	 */
	public void setTransId(String transId) {
		this.transId = transId;
	//	setTrans(transId);
	setDirty();}

	/**
	 * @return the trans
	 */
	public Trans getTrans() {
		return trans;
	}

	/**
	 * @param trans the trans to set
	 */
	public void setTrans(String trans) {
		try {
			this.trans = BusinessObjectDAO.getInstance().create("Trans", transId);
		} catch (DataException e) {
			System.out.println("couldn't find the transId");
			e.printStackTrace();
		}
		setDirty();
	}

}
