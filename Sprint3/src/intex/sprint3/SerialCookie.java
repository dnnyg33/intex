package intex.sprint3;

import java.io.Serializable;

import org.apache.http.cookie.Cookie;

public class SerialCookie  implements Serializable {
		 
	    private static final long serialVersionUID = 5327445113190674523L; //arbitrary
	 
	    private String name;
	    private String value;
	    private String domain;
	     
	    public SerialCookie(Cookie cookie){
	        this.name = cookie.getName();
	        this.value = cookie.getValue();
	        this.domain = cookie.getDomain();
	    }
	     
	    public String getName(){
	        return name;
	    }
	     
	    public String getValue(){
	        return value;
	    }
	    public String getDomain(){
	        return domain;
	    }
	}