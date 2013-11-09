/////////////////////////////////////////////////////////////////
///   This file is an example of an Object Relational Mapping in
///   the ISys Core at Brigham Young University.  Students
///   may use the code as part of the 413 course in their
///   milestones following this one, but no permission is given
///   to use this code is any other way.  Since we will likely
///   use this code again in a future year, please DO NOT post
///   the code to a web site, share it with others, or pass
///   it on in any way.

package intex;
import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * Creates the database.  You can run this file to run the CreateDB.sql
 * file using the Connection Pool.
 *
 * - All comments in the CreateDB.sql file must end with semicolons (;)
 * - DROP TABLE errors are ignored when the table didn't exist
 * - Other errors stop the code immediately so you can fix them before
 * -   more SQL is run.
 *
 * Don't forget to change the connection string variables at the top of
 * the ConnectionPool.java file and the SQL_FILE variable in this file.
 *
 * Place "CreateDB.main(null)" in your tester's constructor to automatically
 * run the SQL script each time you start testing.
 *
 * @author Conan Albrecht
 * @version 1.1
*/
public class CreateDB {

    /** Set this to the full path of your file */
//    public static final String SQL_FILE = "/Users/dnnyg33/Dropbox/Eclipse/Workspace/Sprint1/src/intex/CreateDB.sql";
    public static final String SQL_FILE = "/Users/dnnyg33/Developer/Git/repos/intex2/Sprint1/src/intex/CreateDB.sql";//TODO change back before implementation
//    public static final String SQL_FILE = "\\Program Files (x86)\\Apache Software Foundation\\Tomcat 7.0\\webapps\\ROOT\\CreateDB.sql";
    /** Drops everything from the database and recreates it */
    public static void main(String args[]) throws Exception {

        // get our connection
        Connection conn = ConnectionPool.getInstance().get();
        try{

            // start through the file
            Scanner scanner = new Scanner(new File(SQL_FILE)).useDelimiter(";");
            while (scanner.hasNext()) {
                String cmd = scanner.next().trim();
                if (cmd.startsWith("--") && cmd.contains("\n")) {
                    System.err.println(">>> Failed  >>> " + cmd);
                    System.err.println(">>> Reason  >>> You forgot to end a comment line with a semicolon (;).");
                    System.exit(1);

                } else if (cmd.startsWith("--") || cmd.isEmpty()) {
                    System.err.println(">>> Comment >>> " + cmd);

                }else if (cmd.toLowerCase().contains("drop table")) {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(cmd);
                        stmt.close();
                        System.err.println(">>> Success >>> " + cmd);


                    }catch (SQLException e) {
                        if (e.getMessage().contains("not exist")) {
                            System.err.println(">>> Skipped >>> " + cmd);
                        }else{
                            System.err.println(">>> Failed  >>> " + cmd);
                            e.printStackTrace();
                            System.exit(1);
                        }//if
                    }

                }else {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(cmd);
                        stmt.close();
                        System.err.println(">>> Success >>> " + cmd);

                    }catch (SQLException e) {
                        System.err.println(">>> Failed  >>> " + cmd);
                        e.printStackTrace();
                        System.exit(1);
                    }

                }//if

                conn.commit();
            }//while

        }finally {
            ConnectionPool.getInstance().release(conn);

        }

    }//main

}//CreateDB class
