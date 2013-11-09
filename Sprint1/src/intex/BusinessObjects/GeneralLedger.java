package intex.BusinessObjects;

import intex.DataException;
import intex.SearchCriteria;

import java.util.List;

/**
 * GeneralLedger BO
 *
 * @author Group 2-3
 * @version 1.1
 */
public class GeneralLedger extends BusinessObject {
	
	public void construct(String accountName, double balance,
			String typeOf) {
	
		this.accountName = accountName;
		this.balance = balance;
		this.typeOf = typeOf;
	}

	@BusinessObjectField
	private String accountName;
	@BusinessObjectField
	private double balance;
	@BusinessObjectField
	private String typeOf;
	
	/**creates a new instance of this object*/
	public GeneralLedger(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}


	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	setDirty();}


	/**
	 * @return the balance
	 */
	public double getBalance() {
		return balance;
	}


	/**
	 * @param balance the balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
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
	
	/**Returns a list of DebitOrCredit objects for each generalledgerid*/
	public List<intex.BusinessObjects.DebitOrCredit> getDebitsAndCredits(
			String generalLedgerId) {
		List<DebitOrCredit> debitsAndCredits=null;
		try {
			debitsAndCredits = BusinessObjectDAO.getInstance().searchForList("DebitOrCredit", new SearchCriteria("generalledgerid", generalLedgerId));
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return debitsAndCredits;
	}


}
