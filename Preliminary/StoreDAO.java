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
public class StoreDAO {

	List<Store> results;

	/**Singleton Code*/
	private static StoreDAO instance = null;
	
	/**Creates a new instance of storeDAO*/
	private StoreDAO(){
	}
	
	/**Returns the singleton instance of the storeDAO*/
	public static synchronized StoreDAO getInstance(){
		if(instance == null){
		 instance = new StoreDAO();
	}
		return instance;
		
	}
	/** ////////////////////
	///Create*/
	public Store create(){
		String id = GUID.generate();
		Store d = new Store(id);
		
		Cache.getInstance().put(d.getId(), d);
		
		return d;
	}
	
	/** ////////////////////
	///Read*/
	public Store read(String id) throws DataException{
	//check the cache
		Store d =  (Store)Cache.getInstance().get(id);
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
		 pstmt = conn.prepareStatement("SELECT * FROM Store WHERE id=?");
		
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
		
	if (rs.next()){
	 d = new Store(id);
	d.setLocation(rs.getString("location"));//column names from the data base
	d.setManager(rs.getString("manager"));
	d.setAddress(rs.getString("address"));
	d.setCity(rs.getString("city"));
	d.setPhone(rs.getString("phone"));
	d.setState(rs.getString("state"));
	d.setZipcode(rs.getString("zipcode"));
	d.setObjectAlreadyInDB(true);
	d.setDirty(false);
	
	}else{//no object!!;
	throw new DataException("No Store found with the given id");}//forces the caller to have a try catch block
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
	public void save(Store e) throws DataException{
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
				PreparedStatement ps = conn.prepareStatement("UPDATE store SET address=?, city=?, location=?, manager=?, phone=?, state=?, zipcode=? WHERE id=?");
				ps.setString(1, e.getAddress());
				ps.setString(2, e.getCity());
				ps.setString(3, e.getLocation());
				ps.setString(8, e.getId());
				ps.setString(4, e.getManager());
				ps.setString(5, e.getPhone());
				ps.setString(6, e.getState());
				ps.setString(7, e.getZipcode());
				
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
				PreparedStatement ps = conn.prepareStatement("INSERT INTO store SET address=?, city=?, location=?, manager=?, phone=?, state=?, zipcode=?, id=?");
				ps.setString(1, e.getAddress());
				ps.setString(2, e.getCity());
				ps.setString(3, e.getLocation());
				ps.setString(8, e.getId());
				ps.setString(4, e.getManager());
				ps.setString(5, e.getPhone());
				ps.setString(6, e.getState());
				ps.setString(7, e.getZipcode());
				
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
	public void delete(Store d){
	throw new RuntimeException("We don't delete. RTFM");
	}
	
	private Store createFromRS(ResultSet rs){
		
		Store e=null;
		try {
			e = new Store(rs.getString("id"));
			e.setAddress(rs.getString("address"));
			e.setCity(rs.getString("city"));//TODO does this work better than a number
			e.setLocation(rs.getString("location"));
			e.setManager(rs.getString("manager"));
			e.setPhone(rs.getString("phone"));
			e.setState(rs.getString("state"));
			e.setZipcode(rs.getString("zipcode"));
				} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
		//put in cache
		return e;
		}
	
	/** ////////////////////
	///Populate the Store list when GUI loads. Also gets called by the searchByLastName method*/
	public List<Store> getAll() throws DataException{
		 List<Store> results = new LinkedList<Store>();
		//get a connection
		Connection conn = null;
		try {
			conn = ConnectionPool.getInstance().get();
			//setup a prepared statement "SELECT * FROM dog"
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM Store");
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
		Store e = createFromRS(rs);
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
	 * retrieve all Store objects and searches through that list to find a match*/
		public List<Store> searchByLocation(String location){
			List<Store> all = new LinkedList<Store>();
			try {
				all = getAll();
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<Store> matches = new LinkedList<Store>();
			for(Store temp : all){
				if (temp.getLocation().contains(location)){
					matches.add(temp);
					
				}
			}
			return matches;
			//	return matches.get(0).getId();
				
		}
		public String getIdFromLocation(String location){
			List<Store> all = new LinkedList<Store>();
			try {
				all = getAll();
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String id = null;
			for(Store temp : all){
				if (temp.getLocation().contains(location)){
					id = temp.getId();
				}
			}
			return id;
		
		}

}//StoreDAO