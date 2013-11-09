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

public class UploadPicture extends AsyncTask<String,Void,String>{
		JSONObject rjo = null;
		SerialCookie cereal = null;
		private String caption = null;
		private String picId;
		public void setCookie(SerialCookie cookie){
			this.cereal= cookie;
		}
		public void setCaption(String caption){
			this.caption=caption;
		}
		
		
		@Override
		protected String doInBackground(String ...params) {
			DefaultHttpClient client = null;
			
			HttpPost request = new HttpPost("http://"+Domain.network
					+"actions.MobileUpload.action");
//			
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        nameValuePairs.add(new BasicNameValuePair("picas64", params[0]));
	        nameValuePairs.add(new BasicNameValuePair("caption", caption));
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
		
		    String result=null;
		    String picid = null;
//		    String caption = null;
			try {
				result = (String) rjo.get("toast");
				picid = (String) rjo.get("picid");
//				caption = (String) rjo.get("caption");
			} catch (JSONException e) {
				e.printStackTrace();
				System.out.println("LoginActivity.userLoginTask-->rjo.get(status)");
			}
		
//			setCaption(caption);
			setPicId(picid);
			return result;
			
		}
		
//		@Override
//		protected void onPostExecute(final String success) {
//			
//			MyBitmap.showProgress(false);
//
//			if (success) {
//				
////				super.onPostExecute(result);
//	            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//	            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
//	            intent.putExtra("JSON", rjo.toString());
//	            
//	           
//	            intent.putExtra("cookie", cereal);
//	            //intent.putExtra("intex.sprint3.LoginActivity.cs", cs);
//	           
//	            getApplicationContext().startActivity(intent);
//	            finish();
//			} else {
//				mPasswordView
//						.setError(getString(R.string.error_incorrect_password));
//				mPasswordView.requestFocus();
//			}
//		}
		private void setPicId(String picid) {
			this.picId = picid;
			
		}
		/**
		 * @return the picId
		 */
		public String getPicId() {
			return picId;
		}
		/**
		 * @return the caption
		 */
		public String getCaption() {
			return caption;
		}
		
	

}
