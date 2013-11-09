import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 */

/**
 * @author dnnyg33
 *
 */
public class Product_StoreDAO {

	List<Product_Store> results;

	/**Singleton Code*/
	private static Product_StoreDAO instance = null;
	
	/**Creates a new instance of storeDAO*/
	private Product_StoreDAO(){
	}
	
	/**Returns the singleton instance of the storeDAO*/
	public static synchronized Product_StoreDAO getInstance(){
		if(instance == null){
		 instance = new Product_StoreDAO();
	}
		return instance;
		
	}
	/** ////////////////////
	///Create*/
	public Product_Store create(){
		String id = GUID.generate();
		Product_Store d = new Product_Store(id);
		
		Cache.getInstance().put(d.getId(), d);
		
		return d;
	}
	
	/** ////////////////////
	///Read*/
	public Product_Store read(String id) throws DataException{
	//check the cache
		Product_Store d =  (Product_Store)Cache.getInstance().get(id);
		if (d != null){
			return d;
		}
		
	//get connection from the pool
		Connection conn = null;
		conn = ConnectionPool.getInstance().get();
	//prepared statement
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try {
		 pstmt = conn.prepareStatement("SELECT * FROM productstore WHERE id=?");
		
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
		
	if (rs.next()){
	 d = new Product_Store(id);
	d.setQuantity(rs.getString("quantity"));//column names from the data base
	d.setProduct(rs.getString("product"));
	d.setStore(rs.getString("store"));
	d.setObjectAlreadyInDB(true);
	d.setDirty(false);
	
	}else{//no object!!;
	throw new DataException("No Product_Store found with the given id");}//forces the caller to have a try catch block
	//TODO should i do?: return null;
	
	}catch (SQLException e) {
		//  Auto-generated catch block
		e.printStackTrace();}
	
	finally {
		//close connectionTODO
		ConnectionPool.getInstance().release(conn);
		}
		return d;
	}
	/** ////////////////////
	///Save*/
	public void save(Product_Store e) throws DataException{
		//is it dirty?
		if(!e.isDirty()){//if not
			return;//don't bother saving
		}
		//otherwise
		Connection conn = ConnectionPool.getInstance().get();
		if(e.isObjectAlreadyInDB()){
			//just update
			//prepare the statement
			try {
				
				PreparedStatement ps = conn.prepareStatement("UPDATE productstore SET quantity =?, product=?, store=? WHERE id =?");
				ps.setString(1, e.getQuantity());
				ps.setString(4, e.getId());
				ps.setString(2, e.getProduct());
				ps.setString(3, e.getStore());
					
				ps.executeUpdate();
			//	e.setObjectAlreadyInDB(true);
				e.setDirty(false);
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			finally {
				ConnectionPool.getInstance().release(conn);
			}//end try/catch
		}
		else{//insert
			//prepare the statement
			try {
				PreparedStatement ps = conn.prepareStatement("INSERT INTO productstore SET id =?, quantity =?, product=?, store=?");
				ps.setString(1, e.getId());
				ps.setString(2, e.getQuantity());
				ps.setString(3, e.getProduct());
				ps.setString(4, e.getStore());
					ps.executeUpdate();
				e.setObjectAlreadyInDB(true);
				e.setDirty(false);
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			finally {
				ConnectionPool.getInstance().release(conn);
			}//end try/catch
		}//else
		//update the cache
		Cache a = Cache.getInstance();
		a.put(e.getId(), e);//TODO is this right?
	}
	
	/** ////////////////////
	///Delete*/
	public void delete(Product_Store d){
	throw new RuntimeException("We don't delete. RTFM");
	}
	
	private Product_Store createFromRS(ResultSet rs){
		
		Product_Store e=null;
		try {
			e = new Product_Store(rs.getString("id"));
			e.setQuantity(rs.getString("quantity"));
			} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
		//put in cache
		return e;
		}
	
	/** ////////////////////
	///Populate the Product_Store list when GUI loads. Also gets called by the searchByLastName method*/
	public List<Product_Store> getAll() throws DataException{
		 List<Product_Store> results = new LinkedList<Product_Store>();
		//get a connection
		Connection conn = null;
		try {
			conn = ConnectionPool.getInstance().get();
			//setup a prepared statement "SELECT * FROM dog"
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM productstore");
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
		Product_Store e = createFromRS(rs);
		results.add(e);
		}
		//TODO put the whole list in cache? or every object?
		} catch (DataException e1) {
			
			e1.printStackTrace();
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		finally{//finally release connection
		ConnectionPool.getInstance().release(conn);}
		return results;
		}	
		
	/**Responds to a search bar. It then calls the getAll method to 
	 * retrieve all Product_Store objects and searches through that list to find a match*/
		@SuppressWarnings("null")
		public List<Product_Store> searchByProductAndStore(String product, String store){
			List<Product_Store> all = new LinkedList<Product_Store>();
			String both = product + store;
			
			try {
				all = getAll();
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<Product_Store> matches = new LinkedList<Product_Store>();
			for(Product_Store temp : all){
				String signature = temp.getProduct()+ "" + temp.getStore();
				if (signature == both){
					matches.add(temp);
					
				}
			}
	
			
				return matches;
		}

}//StoreDAO