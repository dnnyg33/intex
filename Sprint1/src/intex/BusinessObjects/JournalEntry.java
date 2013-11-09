package intex.BusinessObjects;

import intex.DataException;
import intex.SearchCriteria;

import java.util.Date;
import java.util.List;

/**
 * JournalEntry BO
 *
 * @author Group 2-3
 * @version 1.1
 */
public class JournalEntry extends BusinessObject {
	
	public void construct(Date dateOf) {
		this.dateOf = dateOf;
	}

	@BusinessObjectField
	private java.util.Date dateOf;
	@BusinessObjectField
	private String transId;
	
	/**creates a new instance of this object*/
	public JournalEntry(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

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
	/**Returns a list of DebitOrCredit objects with a certain JEid*/
	public List<intex.BusinessObjects.DebitOrCredit> getDebitsAndCredits(
			String journalEntryId) {
		List<DebitOrCredit> debitsAndCredits=null;
		try {
			debitsAndCredits = BusinessObjectDAO.getInstance().searchForList("DebitOrCredit", new SearchCriteria("journalentryid", journalEntryId));
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return debitsAndCredits;
	}

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
