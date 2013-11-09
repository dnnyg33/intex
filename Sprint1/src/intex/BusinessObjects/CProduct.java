package intex.BusinessObjects;

import intex.DataException;
import intex.SearchCriteria;

/**
 * CProduct BO
 * 
 * @author Group 2-3
 * @version 1.1
 */
public class CProduct extends Product {

	
	@BusinessObjectField
	private String description;
	@BusinessObjectField
	private String manufacturer;
	@BusinessObjectField
	private double averageCost;
	@BusinessObjectField
	private double commissionRate;
	@BusinessObjectField
	private String sku;
	@BusinessObjectField
	private String typeof="conceptual";
	@BusinessObjectField
	private String model;
	private CRental cRental;
	
	 /** Creates a new instance of this object */
	public CProduct(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	setDirty();}

	
	/**
	 * @return the description
	 */
	@Override
	public String getDescription() {
		return description;}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	setDirty();}

	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;}

	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	setDirty();}

	/**
	 * @return the averageCost
	 */
	@Override
	public double getAverageCost() {
		return averageCost;}


	/**
	 * @return the commissionRate
	 */
	public double getCommissionRate() {
		return commissionRate;}

//	/**
//	 * @param commissionRate the commissionRate to set
//	 */
//	public void setCommissionRate(double commissionRate) {
//		this.commissionRate = commissionRate;
//	setDirty();}

	/**
	 * @return the sku
	 */
	@Override
	public String getSku() {
		return sku;}

	/**
	 * @param sku the sku to set
	 */
	public void setSku(String sku) {
		this.sku = sku;
	setDirty();}

	/**
	 * @return the typeof
	 */
	public String getTypeof() {
		return typeof;
	}

	/**
	 * @param typeof the typeof to set
	 */
	public void setTypeof(String typeof) {
		this.typeof = typeof;
		setDirty();
		super.setTypeOf(typeof);
	}


	@Override
	public String getSerialNum() {
		// TODO Auto-generated method stub
		return "#CP";
	}


	@Override
	public double getFullCommissionRate() {
		return commissionRate;
	}


	@Override
	public void setCProduct(String CProductId) {
		System.out.println("this should never happen");
		setDirty();
		
	}


	@Override
	public String getCProductId() {
		System.out.println("this should never happen");
		return null;
	}


	@Override
	public void setAverageCost(double valueOf) {
		this.averageCost=valueOf;
		setDirty();
		
	}


	@Override
	public void setCommissionRate(double valueOf) {
		this.commissionRate=valueOf;
		setDirty();
		
	}


	@Override
	public void setSerialNum(String text) {
		String message="No serial number field";
		System.out.println(message+"-->"+text);
		
	}


	@Override
	public void setCost(double valueOf) {
		this.averageCost=valueOf;
		System.out.println("only for physcial products");
		
	}


	@Override
	public double getCost() {
		return averageCost;
	}


	/**
	 * @return the cRental
	 */
	public CRental getcRental() {
		return cRental;
	}


	/**
	 * @param cRental the cRental to set
	 */
	public void setcRental(String cRentalId) {
		try {
			this.cRental = BusinessObjectDAO.getInstance().searchForBO("CRental", new SearchCriteria("id", cRentalId));
		} catch (DataException e) {
			e.printStackTrace();
		}
	}


	public String getCRentalId() {
		return cRental.getId();
	}


	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}


	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
		setDirty();
	}


}
