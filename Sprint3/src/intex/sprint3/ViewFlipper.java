package intex.sprint3;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewFlipper extends Activity {
	
	android.widget.ViewFlipper vf = null;
	ListView picListView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_flipper);
		 vf =  (android.widget.ViewFlipper)findViewById(R.id.vf);
		 
		 picListView = (ListView) findViewById(R.id.picListView);
		 
		 Intent intent = getIntent();
		 String JSON = intent.getStringExtra("JSON");
		 JSONObject rjo=null;
		 ArrayList<LonelyPicture> picList = new ArrayList<LonelyPicture>();
		 JSONArray captions = null;
		try {
			rjo = new JSONObject(JSON);
			
			 JSONArray guids = (JSONArray) rjo.get("guids");
			  captions = (JSONArray) rjo.get("captions");
			 for(int i =0; i< guids.length();i++){
				
				 LonelyPicture lp = new LonelyPicture();
				 lp.setGUID((String) guids.get(i));
				 lp.setCaption((String) captions.get(i));
				 picList.add(lp);
			 }
			 
		} catch (JSONException e) {
			System.out.println("Viewflipper-->caught in json exception");
			e.printStackTrace();
		}
		
		
		 
	//	 picList.add(object)
		int size = picList.size();
		 String[] values = new String[size];
				for(int i =0;i<size; i++){
					values[i]=picList.get(i).getCaption();
				}

		 // Define a new Adapter
		 // First parameter - Context
		 // Second parameter - Layout for the row
		 // Third parameter - ID of the TextView to which the data is written
		 // Forth - the Array of data

		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		   android.R.layout.simple_list_item_multiple_choice, values);

		 //not sure what these do
		 picListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		 picListView.setItemsCanFocus(false);

		 // Assign adapter to ListView
		 picListView.setAdapter(adapter);
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_view_flipper, menu);
		return true;
	}


	public void showPicture(){//when the user clicks on a single picture
		
		
	}
	 
	 
}