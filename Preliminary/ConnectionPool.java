/////////////////////////////////////////////////////////////////
///   This file is an example of an Object Relational Mapping in
///   the ISys Core at Brigham Young University.  Students
///   may use the code as part of the 413 course in their
///   milestones following this one, but no permission is given
///   to use this code is any other way.  Since we will likely
///   use this code again in a future year, please DO NOT post
///   the code to a web site, share it with others, or pass
///   it on in any way.



import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * Version 2008-03-03
 *
 * A connection pool for database connections.
 * Note that javax.sql has a standard connection
 * pool interface, but this does not implement
 * it.  This is a bare bones connection pool and
 * is not robust or efficient enough to be used
 * in the real world.  It is just for example
 * purposes, and it assumes that we need simplicity
 * over function.
 *
 * @author Conan C. Albrecht
 * @version 2010-02-01
 */
public class ConnectionPool {

  private static String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/prelim";//TODO is this right? do i even need to have a reference in database development?
  private static String DRIVER_NAME = "com.mysql.jdbc.Driver";
  private static final int MAX_CONNECTIONS = 1;
  private static String USERNAME = "root";
  private static String PASSWORD = "";

  
  //////////////////////////////////////////////
  ///   Singleton code
  
  /** The singleton instance of the class */
  private static ConnectionPool instance = null;
  
  /** Creates a new instance of ConnectionPool */
  private ConnectionPool() {
  }
  
  /** Returns the singleton instance of the ConnectionPool */
  public static synchronized ConnectionPool getInstance() {
    if (instance == null) {
      instance = new ConnectionPool();
    }//if
    return instance;
  }//getInstance
  
  
  /////////////////////////////////////////////
  ///   Connection factory
  
  private Connection createConnection() throws Exception {
    //Logger.global.info("Creating a new database connection in the pool.");
    Class.forName(DRIVER_NAME).newInstance();
    Connection conn = DriverManager.getConnection(DATABASE_URL,USERNAME,PASSWORD);
    conn.setAutoCommit(false);
    return conn;
  }//createConnection
  
  
  //////////////////////////////////////////////
  ///   Public methods
  
  List<Connection> freeConnections = new LinkedList<Connection>();
  List<Connection> usedConnections = new LinkedList<Connection>();
  
  /** Returns a connection to the database */
  public synchronized Connection get() throws DataException {
    // ensure we haven't used too many connections
    if (usedConnections.size() >= MAX_CONNECTIONS) {
      throw new DataException("The database connection pool is out of connections -- a maximum number of " + MAX_CONNECTIONS);
    }//if
    
    try {
      // do we have enough connections to assign one out?
      if (freeConnections.size() == 0) {
        freeConnections.add(createConnection());
      }

      // return the first free connection
      Connection conn = freeConnections.remove(0);
      usedConnections.add(conn);
      //Logger.global.info("Gave out a connection from the pool.  Free size is now: " + freeConnections.size() + "/" + (freeConnections.size() + usedConnections.size()));
      return conn; 
    }catch (Exception e) {
      throw new DataException("An error occurred while retrieving a database connection from the pool", e);
    }
  }//get
  
  /** Releases a connection that was previously in use */
  public synchronized void release(Connection conn) throws DataException {
    try {
      // be sure that this connection was committed (so it is at a fresh, new transaction)
      conn.commit();

      // first remove the connection from the used list
      usedConnections.remove(conn);

      // next add it back to the free connection list
      freeConnections.add(conn);
      //Logger.global.info("Released a connection back to the pool.  Free size is now: " + freeConnections.size() + "/" + (freeConnections.size() + usedConnections.size()));
    }catch (Exception e) {
      throw new DataException("An error occurred while releasing a database connection back to the pool", e);
    }
  }//release
  
  
  
}//class
