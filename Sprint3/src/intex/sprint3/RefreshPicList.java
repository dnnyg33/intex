package intex.sprint3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class RefreshPicList extends AsyncTask<Void, Void, JSONObject>{
	JSONObject rjo = null;
	SerialCookie cereal=null;
	
	public void setCookie(SerialCookie cookie){
		this.cereal=cookie;
	}
	
	@Override
	protected JSONObject doInBackground(Void... params) {
		
		DefaultHttpClient client = null;
		
		HttpPost request = new HttpPost("http://"+Domain.network+
				"actions.MobileRefreshPicList.action");	
	   try {
        	
        
	    System.out.println("about to go to the try");
	    
	    // open the connection and send
    		client = new DefaultHttpClient();
	    	
	    	BasicClientCookie tempCookie = new BasicClientCookie(cereal.getName(), cereal.getValue());
    		tempCookie.setDomain(cereal.getDomain());
    		client.getCookieStore().addCookie(tempCookie);
    		HttpResponse response = client.execute(request);
	    	
	    	
	    	StatusLine statusLine = response.getStatusLine();
	        int statusCode = statusLine.getStatusCode();
	      System.out.println("status code ="+statusCode);
	        if (statusCode == 200) {
	          HttpEntity entity = response.getEntity();
	          InputStream content = entity.getContent();
	          StringBuilder builder = new StringBuilder();
	          BufferedReader reader = new BufferedReader(new InputStreamReader(content));
	          String line;
	          while ((line = reader.readLine()) != null) {
	            builder.append(line);
	          }
	          rjo = new JSONObject(builder.toString());
	        } else {
	          System.out.println("Got a response that wasn't 200");
	          
	        }	        
			//response = client.execute(post);
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (JSONException e) {
			System.out.println("caught in JSONException"+ new Date());
			e.printStackTrace();
		}
	
	  
		//turn json into list of lonely pics
		
		
		
//		  ArrayList<LonelyPicture> picList = new ArrayList<LonelyPicture>();
//		 JSONArray captions = null;
//		
//			 JSONArray guids;
//			try {
//				guids = (JSONArray) rjo.get("guids");
//			
//			  captions = (JSONArray) rjo.get("captions");
//			 for(int i =0; i< guids.length();i++){
//				
//				 LonelyPicture lp = new LonelyPicture();
//				 lp.setGUID((String) guids.get(i));
//				 lp.setCaption((String) captions.get(i));
//				 picList.add(lp);
//			
//			 }
//			 } catch (JSONException e) {
//					e.printStackTrace();
//			 }	
				 
			
		return rjo;
	
	}
}
