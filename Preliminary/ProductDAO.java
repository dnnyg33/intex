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
public class ProductDAO {

	List<Product> results;

	/**Singleton Code*/
	private static ProductDAO instance = null;
	
	/**Creates a new instance of ProductDAO*/
	private ProductDAO(){
	}
	
	/**Returns the singleton instance of the ProductDAO*/
	public static synchronized ProductDAO getInstance(){
		if(instance == null){
		 instance = new ProductDAO();
	}
		return instance;
		
	}
	/** ////////////////////
	///Create*/
	public Product create(){
		String id = GUID.generate();
		Product d = new Product(id);
		
		Cache.getInstance().put(d.getId(), d);
		
		return d;
	}
	
	/** ////////////////////
	///Read*/
	public Product read(String id) throws DataException{
	//check the cache
		Product d =  (Product)Cache.getInstance().get(id);
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
		 pstmt = conn.prepareStatement("SELECT * FROM Product WHERE id=?");
		
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
		
	if (rs.next()){
	 d = new Product(id);
	d.setProductName(rs.getString("productname"));//column names from the data base
	d.setManufacturer(rs.getString("manufacturer"));
	d.setDescription(rs.getString("description"));
	d.setAvgCost(rs.getString("avgcost"));
	d.setObjectAlreadyInDB(true);
	d.setDirty(false);
	
	}else{//no object!!;
	throw new DataException("No product found with the given id");}//forces the caller to have a try catch block
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
	public void save(Product e) throws DataException{
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
				PreparedStatement ps = conn.prepareStatement("UPDATE product SET avgcost=?, description=?, manufacturer=?, productname=? WHERE id=?");
				ps.setString(1, e.getAvgCost());
				ps.setString(2, e.getDescription());
				ps.setString(3, e.getManufacturer());
				ps.setString(4, e.getProductName());
				ps.setString(5, e.getId());
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
				PreparedStatement ps = conn.prepareStatement("INSERT INTO product SET avgcost=?, description=?, manufacturer=?, productname=?, id=?");
				ps.setString(1, e.getAvgCost());
				ps.setString(2, e.getDescription());
				ps.setString(3, e.getManufacturer());
				ps.setString(4, e.getProductName());
				ps.setString(5, e.getId());
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
	public void delete(Product d){
	throw new RuntimeException("We don't delete. RTFM");
	}
	
	private Product createFromRS(ResultSet rs){
		
		Product e=null;
		try {
			e = new Product(rs.getString("id"));
			e.setAvgCost(rs.getString("avgcost"));
			e.setDescription(rs.getString("description"));//TODO does this work better than a number
			e.setManufacturer(rs.getString("manufacturer"));
			e.setProductName(rs.getString("productname"));
				} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
		//put in cache
		return e;
		}
	
	/** ////////////////////
	///Populate the Product list when GUI loads. Also gets called by the searchByLastName method*/
	public List<Product> getAll() throws DataException{
		 List<Product> results = new LinkedList<Product>();
		//get a connection
		Connection conn = null;
		try {
			conn = ConnectionPool.getInstance().get();
			//setup a prepared statement "SELECT * FROM dog"
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM Product");
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
		Product e = createFromRS(rs);
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
	 * retrieve all Product objects and searches through that list to find a match*/
		public List<Product> searchByProductName(String productName){
			List<Product> all = new LinkedList<Product>();
			try {
				all = getAll();
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		List<Product> matches = new LinkedList<Product>();
		for(Product temp : all){
			if (temp.getProductName().contains(productName)){
				matches.add(temp);
				
			}
		}
					return matches;
		}

	public String getIdFromProductName(String productName) {
		List<Product> all = new LinkedList<Product>();
		try {
			all = getAll();
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		String id = null;
		for(Product temp : all){
			if (temp.getProductName().contains(productName)){
				id = temp.getId();
			}
		}
		return id;
				
	}

}//ProductDAO