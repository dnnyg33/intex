package intex.BusinessObjects;

import java.util.Date;

import intex.DataException;
import intex.SearchCriteria;

/**
 * Commission BO
 * 
 * @author Group 2-3
 * @version 1.1
 */
public class Commission extends BusinessObject {
	
	public void construct(String empId, double amount, Date dateOf, String transId) {
		this.empId = empId;
		this.amount = amount;
		this.dateOf = dateOf;
		this.transId=transId;
	}

	@BusinessObjectField
	private String empId;
	private Employee employee;
	@BusinessObjectField
	private double amount;
	@BusinessObjectField
	private java.util.Date dateOf;//yyyy-mm-dd
	@BusinessObjectField
	private String transId;

 /** Creates a new instance of this object */
	public Commission(String id) {
		super(id);
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
	setDirty();}

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
	 * @return the dateOf
	 */
	public java.util.Date getDateOf() {
		return dateOf;
	}

	/**
	 * @param dateOf the dateOf to set
	 */
	public void setDateOf(java.util.Date dateOf) {
		this.dateOf = dateOf;
	setDirty();}

	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * @param employee the employee to set
	 */
	public String setEmployee(String empId) {
	try {
				this.employee = BusinessObjectDAO.getInstance().searchForBO("Employee", new SearchCriteria("id", empId));//TODO same data or new data
			} catch (DataException e) {
				System.out.println("couldn't find the employee with that id");
				e.printStackTrace();
			}
	setDirty();
	return employee.getFirstName();}

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
	}
	

}
