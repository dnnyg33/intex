package intex.BusinessObjects;

import intex.DataException;
import intex.SearchCriteria;

/**
 * PProduct BO
 *
 * @author Group 2-3
 * @version 1.1
 */
public abstract class PProduct extends Product {

	@BusinessObjectField
	private String serialNum;
	@BusinessObjectField
	private String shelfLocation;
	@BusinessObjectField
	private String datePurchased;
	@BusinessObjectField
	private double cost;
	@BusinessObjectField
	private String cProductId;
	private CProduct cProduct;
	@BusinessObjectField
	private String storeId;
	private Store store;
	@BusinessObjectField
	private double physicalProductCommissionRate;
	@BusinessObjectField
	private String status;
	//@BusinessObjectField
	//private String typeof="physical";
	
	/**creates a new instance of this object*/
	public PProduct(String id) {
		super(id);
	}

	/**
	 * @return the serialNum
	 */
	/*public String getSerialNum() {
		return serialNum;
	}

	/**
	 * @param serialNum the serialNum to set
	 */
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	setDirty();}

	/**
	 * @return the shelfLocation
	 */
	public String getShelfLocation() {
		return shelfLocation;
	}

	/**
	 * @param shelfLocation the shelfLocation to set
	 */
	public void setShelfLocation(String shelfLocation) {
		this.shelfLocation = shelfLocation;
	setDirty();}

	/**
	 * @return the datePurchased
	 */
	public String getDatePurchased() {
		return datePurchased;
	}

	/**
	 * @param datePurchased the datePurchased to set
	 */
	public void setDatePurchased(String datePurchased) {
		this.datePurchased = datePurchased;
	setDirty();}

	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}

	

	/**
	 * @return the cPorductId
	 */
	public String getCProductId() {
		return cProductId;
	}

	/**
	 * @param cPorductId the cPorductId to set
	 */
	public void setCProductId(String cProductId) {
		this.cProductId = cProductId;
	//	setCProduct(cProductId);
	setDirty();}

	/**
	 * @return the storeId
	 */
	public String getStoreId() {
		return storeId;
	}

	/**
	 * @param storeId the storeId to set
	 */
	public void setStoreId(String storeId) {
		this.storeId = storeId;
		setStore(storeId);
	setDirty();}

	/**
	 * @return the physicalProductCommissionRate
	 */
	public double getPhysicalProductCommissionRate() {
		return physicalProductCommissionRate;
	}

	/**
	 * @param physicalProductCommissionRate the physicalProductCommissionRate to set
	 */
	public void setPhysicalProductCommissionRate(
			double physicalProductCommissionRate) {
		this.physicalProductCommissionRate = physicalProductCommissionRate;
	setDirty();}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	setDirty();}

	/**
	 * @return the cProduct
	 */
	public CProduct getCProduct() {
		return cProduct;
	}

	/**
	 * @param cProduct the cProduct to set
	 */
	@Override
	public void setCProduct(String cProductId) {
		try {
			cProduct = BusinessObjectDAO.getInstance().searchForBO("CProduct", new SearchCriteria("id", cProductId));
		} catch (DataException e) {
			System.out.println("couldn't find the cProductId");
			e.printStackTrace();
		}
		
	}

	/**
	 * @return the store
	 */
	public Store getStore() {
		return store;
	}

	/**
	 * @param store the store to set
	 */
	public void setStore(String storeId) {
		try {
			this.store = BusinessObjectDAO.getInstance().create("Store", storeId);
		} catch (DataException e) {
			System.out.println("couldn't find the storeId");
			e.printStackTrace();
		}
	
	}

	/**
	 * @return the typeOf
	 */
	public abstract String getTypeof();

	/**
	 * @param typeOf the typeOf to set
	 */
	public abstract void setTypeof(String typeOf);

	/** returns the cProduct sku that this pproduct is related to--if the Cproduct is set*/
	@Override
	public String getSku() {
		return cProduct.getSku();
		
		
	}

	@Override
	public String getSerialNum() {
		return serialNum;
	}

	/** returns the cProduct sku that this pproduct is related to--if the Cproduct is set*/
	@Override
	public String getDescription() {
		
		return cProduct.getDescription();
	}
	
	/**Convenience method that adds the p and c product commission rates*/
	@Override
	public double getFullCommissionRate() {
		return cProduct.getCommissionRate()+physicalProductCommissionRate;
		
	}
	/**sets the cost*/
	@Override
	public void setCost(double valueOf) {
		this.cost=valueOf;
		setDirty();
		
	}
	/**sets the commission rate*/
	@Override
	public void setCommissionRate(double valueOf) {
		this.physicalProductCommissionRate=valueOf-cProduct.getCommissionRate();
		setDirty();
		
	}
	/**sets description*/
	@Override
	public void setDescription(String description) {
		cProduct.setDescription(description);
		setDirty();
	}
	/**gets avg cost*/
	@Override
	public double getAverageCost() {
		return cProduct.getAverageCost();
	}
	/**sets avg cost*/
	@Override
	public void setAverageCost(double valueOf) {
		cProduct.setAverageCost(valueOf);
		
	}
	/**gets manufacturer*/
	@Override
	public String getManufacturer(){
		return cProduct.getManufacturer();
	}

	

}
