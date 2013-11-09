package intex.sprint3;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public  class PicListFragment extends Fragment {
	
	ListView picListView = null;
	 ArrayAdapter<LonelyPicture> adapter;
	
	public PicListFragment(){
		
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("PicListFragment OnCreate", "#1?");
//        Log.e("PICLISTFRAGMENT NAME", this.getClass());
    }
 
    public void loadListViewAgain(){
    	
    	adapter.notifyDataSetChanged();
    	ListView picListView = (ListView) getActivity().findViewById(R.id.picListView);
    	picListView.destroyDrawingCache();
     	picListView.setVisibility(ListView.INVISIBLE);
     	picListView.setVisibility(ListView.VISIBLE);
     	Log.e("loadListView", "no longer visible the list");
    }
    
    
    public void loadListView(ArrayList<LonelyPicture> picList){
    	
		
		 // Define a new Adapter
		 // First parameter - Context
		 // Second parameter - Layout for the row
		 // Third parameter - ID of the TextView to which the data is written
		 // Forth - the Array of data
    	
				

		 adapter = new ArrayAdapter<LonelyPicture>(getActivity().getApplicationContext(),
		   android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, picList);
		 

		 picListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		 picListView.setAdapter(adapter);
		
		 
		 //picListView = (ListView) findViewById(R.id.picListView);
		//will this really work?
		
			 picListView = (ListView) getActivity().findViewById(R.id.picListView);
			 picListView.setOnItemClickListener(new OnItemClickListener() {
					
				 public void onItemClick(AdapterView<?> a, View v, int i, long l){
					 MainActivity.fetch = (LonelyPicture) picListView.getItemAtPosition(i);
					MainActivity.printList.add((LonelyPicture) picListView.getItemAtPosition(i));
					Button right = (Button) getActivity().findViewById(R.id.show_and_back_button);
					right.setEnabled(true);
					Log.e("From picListFragment--guid", MainActivity.fetch.getGUID());
					
				 }});
		 
    }
    
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
       
        Log.e("PicListFragment onCreateView", "#2");
        Intent intent = getActivity().getIntent();
		 String JSON = intent.getStringExtra("JSON");
		 JSONObject rjo=null;
		  ArrayList<LonelyPicture> picList = new ArrayList<LonelyPicture>();//TODO remove arraylist?
		 JSONArray captions = null;
		try {
			rjo = new JSONObject(JSON);
			
			 JSONArray guids = (JSONArray) rjo.get("guids");
			  captions = (JSONArray) rjo.get("captions");
			  System.out.println("picListFragment--onACtivitiyCreated-->caption.get(0)"+captions.get(0));//
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
        loadListView(picList);
    
		 
        Button right = (Button) getActivity().findViewById(R.id.show_and_back_button);
		right.setEnabled(false);
			 
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pic_list, container, false);
        Log.e("PicListFragment onActivityCreated", "#3");
          picListView = (ListView) view.findViewById(R.id.picListView);
      
        return view;
    }


		
	}
