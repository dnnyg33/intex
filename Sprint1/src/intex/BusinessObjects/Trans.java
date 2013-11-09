package intex.BusinessObjects;

import java.util.Date;
import java.util.List;

import intex.DataException;
import intex.SearchCriteria;

/**
 * Trans BO
 *
 * @author Group 2-3
 * @version 1.1
 */
public class Trans extends BusinessObject{
	
	public void construct(Date dateOf, double subtotal, double tax,
			String custId, String storeId, double total, String empId) {
		this.dateOf = dateOf;
		this.subtotal = subtotal;
		this.tax = tax;
		this.custId = custId;
		this.storeId = storeId;
		this.total = total;
		this.empId = empId;
	}



	@BusinessObjectField
	private java.util.Date dateOf;
	@BusinessObjectField
	private double subtotal;
	@BusinessObjectField
	private double tax;//numeric(4,3)
	@BusinessObjectField
	private String custId;
	private Customer customer;
	
	@BusinessObjectField
	private String empId;
	private Employee employee;
	
	@BusinessObjectField
	private String storeId;
	private Store store;
	
	@BusinessObjectField
	private double total;
	
	
	
	/**creates a new instance of this object*/
	public Trans(String id) {
		super(id);
	}



	/**
	 * @return the subtotal
	 */
	public double getSubtotal() {
		return subtotal;
	}



	/**
	 * @param subtotal the subtotal to set
	 */
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	setDirty();}



	/**
	 * @return the tax
	 */
	public double getTax() {
		return tax;
	}



	/**
	 * @param tax the tax to set
	 */
	public void setTax(double tax) {
		this.tax = tax;
	setDirty();}



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
	//	setCustomer(custId);
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
	//	setStore(storeId);
	setDirty();}



	/**
	 * @return the total
	 */
	public double getTotal() {
		return total;
	}



	/**
	 * @param total the total to set
	 */
	public void setTotal(double total) {
		this.total = total;
	setDirty();}



	/**
	 * @param dateOf the dateOf to set
	 */
	public void setDateOf(java.util.Date dateOf) {
		this.dateOf = dateOf;
	setDirty();}



	/**
	 * @return the dateOf
	 */
	public java.util.Date getDateOf() {
		return dateOf;
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
	//	setEmployee(empId);
		setDirty();
	}



	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}



	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		
		this.customer = customer;
		setDirty();
	}



	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}



	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(String employeeId) {
		try {
			this.employee = BusinessObjectDAO.getInstance().searchForBO("Employee", new SearchCriteria("id",
					employeeId));
		} catch (DataException e) {
			System.out.println("couldn't find the employeeId");
			e.printStackTrace();
		}
		setDirty();
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
			this.store = BusinessObjectDAO.getInstance().searchForBO("Store", new SearchCriteria("id", storeId));
		} catch (DataException e) {
			System.out.println("couldn't find the storeId");
			e.printStackTrace();
		}
		setDirty();
	}


	/**sets the customer involved in this transaction*/
	public void setCustomer(String custId2) {
		try {
			this.customer = BusinessObjectDAO.getInstance().searchForBO("Customer", new SearchCriteria("id", custId2));//TODO same data or new data
		} catch (DataException e) {
			System.out.println("couldn't find the employee with that id");
			e.printStackTrace();
		}
		
	}


	/**returns a list of revenue sources included in this transaction*/
	public List<intex.BusinessObjects.RevenueSource> getRevenueSources(
			String transId) {
		List<RevenueSource> rs=null;
		try {
			rs = BusinessObjectDAO.getInstance().searchForList("RevenueSource", new SearchCriteria("transid", transId));
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return rs;
	}



}
