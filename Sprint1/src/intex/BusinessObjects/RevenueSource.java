package intex.BusinessObjects;


import intex.DataException;
import intex.SearchCriteria;

/**
 * RevenueSource BO
 *
 * @author Group 2-3
 * @version 1.1
 */
public abstract class RevenueSource extends BusinessObject {
	
	public void construct(double chargeAmt, String transId,
			 String typeOf) {
		this.chargeAmt = chargeAmt;
		this.transId = transId;
		this.typeOf = typeOf;
	}


	@BusinessObjectField
	private double chargeAmt;
	@BusinessObjectField
	private String transId;
	private Trans trans;
	@BusinessObjectField
	private String typeOf;
	private Sale sale;//TODO what's this for?
	
	/**creates a new instance of this object*/
	public RevenueSource(String id) {
		super(id);
	}


	/**
	 * @return the chargeAmt
	 */
	public double getChargeAmt() {
		return chargeAmt;
	}


	/**
	 * @param chargeAmt the chargeAmt to set
	 */
	public void setChargeAmt(double chargeAmt) {
		this.chargeAmt = chargeAmt;
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
		//setTrans(transId);
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
	 * @return the trans
	 */
	public Trans getTrans() {
		return trans;
	}


	/**
	 * @param trans the trans to set
	 */
	public void setTrans(String transId) {
		try {
					this.trans = BusinessObjectDAO.getInstance().searchForBO("Trans", new SearchCriteria("id", transId));
				} catch (DataException e) {
					System.out.println("couldn't find the transId");
					e.printStackTrace();
				}
		setDirty();
	}


	/**
	 * @return the sale
	 */
	public Sale getSale() {
		return sale;
	}


	/**
	 * @param sale the sale to set
	 */
	public void setSale(Sale sale) {
		this.sale = sale;
	}


	/**abstract method*/
	public abstract int getQuantity();
	/**abstract method*/
	public abstract String getSkuOrRevenueType();

	/**abstract method*/
	public abstract void setQuantity(int quantity);

//
//	public Object getTransList(String revenueSourceId) {
//		List<Trans> trans=null;
//		try {
//			trans = BusinessObjectDAO.getInstance().searchForList("Trans", new SearchCriteria("empid", revenueSourceId));
//		} catch (DataException e) {
//			e.printStackTrace();
//		}
//	return trans;
//	}



}
