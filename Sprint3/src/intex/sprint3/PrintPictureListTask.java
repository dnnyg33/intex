package intex.sprint3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class PrintPictureListTask extends AsyncTask<ArrayList<LonelyPicture>,Void,String>{
		JSONObject rjo = null;
		SerialCookie cereal=null;
		
		
		
		@Override
		protected String doInBackground(ArrayList<LonelyPicture> ...params) {
			
			DefaultHttpClient client = null;
			ArrayList<LonelyPicture> printList = params[0];
			
			HttpPost request = new HttpPost("http://"+Domain.network+
					"actions.MobilePrint.action");
			
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			int i =0;
			for( i =0; i<printList.size();i++){
				 nameValuePairs.add(new BasicNameValuePair("guid"+i, printList.get(i).getGUID()));
			}
			 nameValuePairs.add(new BasicNameValuePair("size", i+""));
			 
			   
	        try {
	        	request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
		    // open the connection and send
	    		client = new DefaultHttpClient();
	    		BasicClientCookie tempCookie = new BasicClientCookie(cereal.getName(), cereal.getValue());
	    		tempCookie.setDomain(cereal.getDomain());
	    		
	    		client.getCookieStore().addCookie(tempCookie);
		    	HttpResponse response = null;
		    	response = client.execute(request);
		    	
		    	
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
			} catch (ClientProtocolException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (JSONException e) {
				System.out.println("caught in JSONException"+ new Date());
				e.printStackTrace();
			}
		
		    String receipt=null;
			try {
				receipt = (String) rjo.get("receipt");
			} catch (JSONException e) {
				e.printStackTrace();
				System.out.println("LoginActivity.userLoginTask-->rjo.get(status)");
			}
		
			return receipt;
			
		}

		public void setCookie(SerialCookie cereal){
			this.cereal=cereal;	
			}
	
}