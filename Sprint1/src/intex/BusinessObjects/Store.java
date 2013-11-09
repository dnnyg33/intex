package intex.BusinessObjects;

import java.util.LinkedList;
import java.util.List;

import intex.DataException;
import intex.SearchCriteria;

/**
 * Store BO
 *
 * @author Group 2-3
 * @version 1.1
 */
public class Store extends BusinessObject{

	

	public void construct(String location, String managerId,
			 String address, String phone, String subnetId,
			double salesTaxRate) {
		
		this.location = location;
		this.managerId = managerId;
		
		this.address = address;
		this.phone = phone;
		this.subnetId = subnetId;
		this.salesTaxRate = salesTaxRate;
	}

	@BusinessObjectField
	private String location;
	@BusinessObjectField
	public String managerId;
	private Employee manager;
	
	@BusinessObjectField
	private String address;
	@BusinessObjectField
	private String phone;
	@BusinessObjectField
	private String subnetId;
	@BusinessObjectField
	private double salesTaxRate;
	
	/**creates a new instance of this object*/
	public Store(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	setDirty();}
	/**
	 * @return the managerId
	 */
	public String getManagerId() {
		return managerId;
	}
	/**
	 * @param managerId the managerId to set
	 */
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	//	setManager(managerId);
	setDirty();}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	setDirty();}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	setDirty();}
	
	
	/**
	 * @param subnetId the subnetId to set
	 */
	public void setSubnetId(String subnetId) {
		this.subnetId = subnetId;
	setDirty();}
	/**
	 * @return the salesTaxRate
	 */
	public double getSalesTaxRate() {
		return salesTaxRate;
	}
	/**
	 * @param salesTaxRate the salesTaxRate to set
	 */
	public void setSalesTaxRate(double salesTaxRate) {
		this.salesTaxRate = salesTaxRate;
	setDirty();}
	/**
	 * @return the manager
	 */
	public Employee getManager() {
		return manager;
	}
	/**
	 * @param manager the manager to set
	 */
	public void setManager(String managerId) {
		try {
					this.manager = BusinessObjectDAO.getInstance().searchForBO("Employee", new SearchCriteria("id", managerId));
				} catch (DataException e) {
					System.out.println("couldn't find the managerId");
					e.printStackTrace();
				}
		setDirty();
		
		
	}
	
	
	  /**
	   * Retrieves the StoreProduct relationship object for this owner.
	   */
	  private List<StoreProduct> getStoreProducts() throws DataException {
	    return BusinessObjectDAO.getInstance().searchForList("StoreProduct", new SearchCriteria("storeid", id));
	  }
	  
	  /**
	   * Retrieves the actual Product objects for this Store.
	   * This is a convenience method to traverse the intermediary table.
	   */
	  public List<Product> getProducts() throws DataException {
	    List<Product> products = new LinkedList<Product>();
	    for (StoreProduct sp: this.getStoreProducts()) {
	      products.add(sp.getProduct());
	    }
	    return products;
	  }
	/**returns a list of transactions that are associate with this store*/  
	public List<intex.BusinessObjects.Trans> getTrans(String storeId) {
		List<Trans> trans=null;
		try {
			trans = BusinessObjectDAO.getInstance().searchForList("Trans", new SearchCriteria("storeid", storeId));
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return trans;
	}
	
	/**
	 * @return the subnetId
	 */
	public String getSubnetId() {
		return subnetId;
	}	
	
}
