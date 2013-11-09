package intex.BusinessObjects;

import java.sql.Connection;
import java.util.List;

import intex.ConnectionPool;
import intex.DataException;
import intex.SearchCriteria;

/**
 * StoreProduct BO
 *
 * @author Group 2-3
 * @version 1.1
 */
public class StoreProduct extends BusinessObject {
	
	@BusinessObjectField
	private String storeId;
	private Store store;
	@BusinessObjectField
	private String productId;
	private Product product;
	@BusinessObjectField
	private String shelfLocation;
	@BusinessObjectField
	private int quantityOnHand;
	
	/**creates a new instance of this object*/
	public StoreProduct(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}


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
	 * @return the productID
	 */
	public String getProductId() {
		return productId;
	}


	/**
	 * @param productID the productID to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
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
	 * @return the quantityOnHand
	 */
	public int getQuantityOnHand() {
		return quantityOnHand;
	}


	/**
	 * @param quantityOnHand the quantityOnHand to set
	 */
	public void setQuantityOnHand(int quantityOnHand) {
		this.quantityOnHand = quantityOnHand;
	setDirty();}


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


	/**
	 * @return the product
	 */
	public PProduct getProduct() throws DataException {
		PProduct p=BusinessObjectDAO.getInstance().read(getProductId());
//		Connection conn = ConnectionPool.getInstance().get();
//		try {
//			BusinessObjectDAO.getInstance().initialize(p, Product.class, conn);
//		} catch (DataException e) {
//			System.out.println("StoreProduct.getProduct() not working");
//			e.printStackTrace();
//		} catch (Exception e) {
//			System.out.println("caught in exception");
//			e.printStackTrace();
//		} 
//		ConnectionPool.getInstance().release(conn);
		
		return p;
	}

	  /** 
	   * @return the dealer of the product
	   */
	  public Store getDealer() throws DataException {
	   Store s = BusinessObjectDAO.getInstance().read(getStoreId());//as of here s is not initialized. Don't know why, but it's not.
	   Connection conn = ConnectionPool.getInstance().get(); //this is for the call to .initialize
	   try {
		BusinessObjectDAO.getInstance().initialize(s, Store.class , conn);//TODO let the world know i am a genius.
	} catch (Exception e) {
		System.out.println("caught...probably cuase of store");
		e.printStackTrace();
	}// store is now initialized.
	   ConnectionPool.getInstance().release(conn);
	
		  return s;
	  }
	  
	  
	  
//	/**
//	 * @param product the product to set
//	 */
//	public void setProduct(String productId) {
//		try {
//			this.product = BusinessObjectDAO.getInstance().searchForBO("Product", new SearchCriteria("id",
//					productId));
//		} catch (DataException e) {
//			System.out.println("couldn't find the productId");
//			e.printStackTrace();
//		}
//		setDirty();
//	}
	/**returns a list of stores that sell this storeproduct*/
	public List<intex.BusinessObjects.Store> getDealers(String productId) {
		List<Store> dealers=null;
		try {
			dealers = BusinessObjectDAO.getInstance().searchForList("StoreProduct", new SearchCriteria("productid", productId));
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dealers;
	}

}
