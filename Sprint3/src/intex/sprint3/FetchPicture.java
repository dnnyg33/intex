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
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class FetchPicture extends AsyncTask<LonelyPicture,Void,byte[]>{
	JSONObject rjo = null;
	SerialCookie cereal = null;
	public void setCookie(SerialCookie cookie){
		this.cereal= cookie;
	}
	
	
	@Override
	protected byte[] doInBackground(LonelyPicture ...params) {
		DefaultHttpClient client = null;
		
		HttpPost request = new HttpPost("http://"+Domain.network+
				"actions.MobilePictureString.action");
	
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		System.out.println("asyncTask -- should be fetch.getGuid--"+params[0].getGUID());
        nameValuePairs.add(new BasicNameValuePair("guid", params[0].getGUID()));
        try {
        	request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		
	    // open the connection and send
    		client = new DefaultHttpClient();
    		BasicClientCookie tempCookie = new BasicClientCookie(cereal.getName(), cereal.getValue());
    		tempCookie.setDomain(cereal.getDomain());
    		
    		client.getCookieStore().addCookie(tempCookie);
	    	HttpResponse response = null;
	    	HttpContext localContext = new BasicHttpContext();
	    	localContext.setAttribute(ClientContext.COOKIE_STORE, client.getCookieStore());
	    	response = client.execute(request, localContext);
	    	
	    	
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
	
	    String picas64=null;
		try {
			picas64 = (String) rjo.get("picas64");
		} catch (JSONException e) {
			e.printStackTrace();
			System.out.println("LoginActivity.userLoginTask-->rjo.get(status)");
		}
	
		byte[] picture = android.util.Base64.decode(picas64, 0);
		System.out.println("byte[]length"+picture.length);
		return picture;
		
	}
	
}