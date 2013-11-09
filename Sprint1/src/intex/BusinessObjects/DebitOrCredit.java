package intex.BusinessObjects;

import intex.DataException;
import intex.SearchCriteria;

/**
 * DebitOrCredit BO
 * 
 * @author Group 2-3
 * @version 1.1
 */
public class DebitOrCredit extends BusinessObject {

	public void construct (boolean dcBoolean, String glName,
			String journalEntryId, double amount, String generalLedgerId) {
	
		this.dcBoolean = dcBoolean;
		this.glName = glName;
		this.generalLedgerId = generalLedgerId;
		this.journalEntryId = journalEntryId;
		this.amount = amount;
	}


	@BusinessObjectField
	private boolean dcBoolean;
	@BusinessObjectField
	private String glName;
	@BusinessObjectField
	private String generalLedgerId;//
	@BusinessObjectField
	private String journalEntryId;
	private JournalEntry journalEntry;
	@BusinessObjectField
	private double amount;
	private GeneralLedger generalLedger;
	
	
  /** Creates a new instance of this object */
	public DebitOrCredit(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the dcBoolean
	 */
	public boolean isDcBoolean() {
		return dcBoolean;
	}


	/**
	 * @param dcBoolean the dcBoolean to set
	 */
	public void setDcBoolean(boolean dcBoolean) {
		this.dcBoolean = dcBoolean;
	setDirty();}


	/**
	 * @return the glName
	 */
	public String getGlName() {
		return glName;
	}


	/**
	 * @param glName the glName to set
	 */
	public void setGlName(String glName) {
		this.glName = glName;
		
	setDirty();}


	/**
	 * @return the journalEntryId
	 */
	public String getJournalEntryId() {
		return journalEntryId;
	}


	/**
	 * @param journalEntryId the journalEntryId to set
	 */
	public void setJournalEntryId(String journalEntryId) {
		this.journalEntryId = journalEntryId;
	//	setJournalEntry(journalEntryId);
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
	 * @return the journalEntry
	 */
	public JournalEntry getJournalEntry() {
		return journalEntry;
	}


	/**
	 * @param journalEntry the journalEntry to set
	 */
	public void setJournalEntry(String journalEntryId) {
		try {
			this.journalEntry = BusinessObjectDAO.getInstance().searchForBO("JournalEntry", new SearchCriteria("id", journalEntryId));
		} catch (DataException e) {
			System.out.println("couldn't find the journalEntryId");
			e.printStackTrace();
		}
		setDirty();
	}

	/**
	 * @param GeneralLedger the generalLedger to set
	 */
	public void setGeneralLedger(String generalLedgerId) {
		try {
			this.generalLedger = BusinessObjectDAO.getInstance().searchForBO("GeneralLedger", new SearchCriteria("id", generalLedgerId));
		} catch (DataException e) {
			System.out.println("couldn't find the generalLedger");
			e.printStackTrace();
		}
		setDirty();
	}


	/**
	 * @return the generalLedger
	 */
	public GeneralLedger getGeneralLedger() {
		return generalLedger;
	}


	/**
	 * @return the generalLedgerId
	 */
	public String getGeneralLedgerId() {
		return generalLedgerId;
	}


	/**
	 * @param generalLedgerId the generalLedgerId to set
	 */
	public void setGeneralLedgerId(String generalLedgerId) {
		this.generalLedgerId=generalLedgerId;
		setGeneralLedger(generalLedgerId);
		setDirty();
	}







}
