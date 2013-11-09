package intex.BusinessObjects;

/**
 * Sale BO
 *
 * @author Group 2-3
 * @version 1.1
 */
public class Sale extends RevenueSource {
	
	public void construct(int quantity, String productId) {
		
		this.quantity = quantity;
		this.productId = productId;
	}


	@BusinessObjectField
	private int quantity=0;
	@BusinessObjectField
	private String productId;
	private Product product;
	
	/**creates a new instance of this object*/
	public Sale(String id) {
		super(id);
	}


	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	setDirty();}


	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	//	setProduct(productId);
	setDirty();}


	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}


//	/**
//	 * @param product the product to set
//	 */
//	public void setProduct(String productId) {
//		try {
//			this.product = BusinessObjectDAO.getInstance().searchForBO("Product", new SearchCriteria("id", productId));
//			} catch (DataException e) {
//			System.out.println("couldn't find the productId");
//			e.printStackTrace();
//				}
//		setDirty();
//	}


	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**gets revenuetype or sku*/
	@Override
	public String getSkuOrRevenueType() {//TODO if this class becomes abstract then, change this method to abstract and put logic in product
		return product.getSku();
		
	}
	

}