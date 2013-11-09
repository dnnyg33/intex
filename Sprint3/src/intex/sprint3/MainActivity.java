package intex.sprint3;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	SerialCookie cereal=null;
	 
	 private static boolean changeToBack =true;
	 public static LonelyPicture fetch=null;
	 public List<LonelyPicture> picList = null;
	 
	 static ArrayList<LonelyPicture> printList = null;
	 
	 private File fullResPicture;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	static ListView picListView = null;
	Bundle savedInstanceState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.savedInstanceState=savedInstanceState;
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		cereal = (SerialCookie) getIntent().getSerializableExtra("cookie");
		
		printList = new ArrayList<LonelyPicture>();
		
		
		
	}
	
	
	/**Left button on left screen. This allows the user to select multiple pictures to be added 
	 * to the printList. this button changes dynamically when clicked to cancel
	 */
	public void printButtonClicked(View view){
//		System.out.println("index-->"+index);
//		System.out.println("printButtonCLicked--fetch.getid"+fetch.getGUID());
		Button b1 = (Button) findViewById(R.id.pic_list_print);
		 Button b2 = (Button) findViewById(R.id.show_and_back_button);
		 ListView picListView = (ListView) findViewById(R.id.picListView);
		
		 
		 if(b1.getText().toString().contains("Select Many")){
			 System.out.println("going thorugh the select many if statemtne");
			 picListView.clearChoices();
			 picListView.requestLayout();
		 picListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		 b1.setText("Cancel");
		 b2.setText("Print");
		 printList.clear();
		 }else {//they clicked cancel
			 picListView.clearChoices();
			 picListView.requestLayout();
			 b1.setText("Select Many");
			 b2.setText("Show");
			 picListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			 
			 
		 }
	}
	
	
	/**triggered by the user clicking yes to the printConfirmationDialog
	 * this method executes an async task to send the printList
	 *  (which has already been loaded and can contain multiple photos) 
	 *  to the server and create print orders and other sale items
	 *  a result is sent back containing a receipt string, which is shown to user via alert dialog.
	 */
	private void printConfirmed(final Boolean restart, final JSONObject json){
		
			System.out.println("going in to the buy");
			// print the list of selected pictures
			Log.e("Gonna run a task background task to server", "print "+printList.size()+ " pictures");
			PrintPictureListTask pplt = new PrintPictureListTask();
			pplt.setCookie(cereal);
			@SuppressWarnings("unchecked")
			AsyncTask<ArrayList<LonelyPicture>, Void, String> backgroundTask = pplt.execute(printList);
			String receipt=null;
			try {
				  receipt = backgroundTask.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			
//			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Thank you");
			builder.setMessage(receipt)
			.setPositiveButton("Ok",  new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					System.out.println("positive button");
					if(restart){//happens when user just uploaded picture
						Intent intent = getIntent();
						intent.putExtra("JSON", json.toString());
						finish();
						startActivity(intent);
					 	
					}
					
				}
			});
			
			AlertDialog dialog = builder.create();
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();	//TODO one alert
	}
	
	
	/**
	 * 	right screen, middle button.
	 * kicks off implicit camera intent.
	 * upon return it sets the image as drawable into the image viewer above.
	 * this action also enables the upload button
	 */
	public void takeNewPicture(View view){	
	Button upload = (Button) findViewById(R.id.upload);
		upload.setEnabled(true);
		
		final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	      intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getTempFile(this)) ); //calls getTempFile and creates directory where the image will save
	      startActivityForResult(intent, TAKE_PHOTO_CODE);
	      System.out.println("clicked onPhotoButton");
		//set result to image view
	}
	
	private static final int TAKE_PHOTO_CODE = 1;
	
    /**
     * this is the file path of the new bitmap
     * @param context
     * @return
     */
    private File getTempFile(Context context){
      //it will return /sdcard/image.tmp
      final File path = new File( Environment.getExternalStorageDirectory(), context.getPackageName() );
      if(!path.exists()){
        path.mkdir();
      }
      Log.e("getTempFile--path", path+"");
      Log.e("getTempFile--file", path.getPath());
      return new File(path, "image.tmp");
    }
    

    
    
    
/**
 * When the user ok's a picture this method runs and sets the image just taken to the image viewer on screen
 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
     if(resultCode==RESULT_OK && requestCode==TAKE_PHOTO_CODE){
    	 Log.e("OnActivityResult", "going through if");
    	  ImageView iv = (ImageView) findViewById(R.id.image_view_upload);
    	  int reqWidth = iv.getHeight();
    	  int reqHeight = iv.getWidth();
           fullResPicture = getTempFile(this);
           Log.e("onActivityResult--size of full res",fullResPicture.getTotalSpace()+"");
           
           //make scaled down version of fullResPicture and display it--to avoid OOM error
          Bitmap capturebmp=null;
          capturebmp = MyBitmap.decodeSampledBitmapFromFile(fullResPicture, fullResPicture.getPath(), reqWidth, reqHeight);
		  Log.e("onActivityResult--size of scaled bitmap",capturebmp.getByteCount()+"");
		          
          
          iv.setImageBitmap(capturebmp);

      
        
     	}
    }
    

    
	
	/**right screen, left button.
	 * this method takes the drawable from the image view and converts it to a string.
	 * the string is then sent (via async task) to server where it is saved in new photo object
	 * a result string is toasted showing the time and success of toast.
	 * the pictures guid (retrieved from server) and caption are set in new LonelyPicture object which is loaded into printList, should user want to print it.
	 * A success enables the print button, and disables the upload button.
	 * Finally, the picListView is updated to show the new picture (calls refreshPicListView)
	 * @param view
	 */
	public void uploadClicked(View view){
//		MyBitmap progress = new MyBitmap();
    	
		Button print = (Button) findViewById(R.id.print);
		print.setEnabled(true);
		
		//Depending on user settings, either upload full image or smaller one 
		//grab image from imageView
		ImageView iv = (ImageView) findViewById(R.id.image_view_upload);
		BitmapDrawable d = (BitmapDrawable) iv.getDrawable();
		Bitmap b = ((BitmapDrawable)d).getBitmap();
		b= MyBitmap.scaleBitmap(b, 500, 500);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		b.compress(Bitmap.CompressFormat.PNG, 1, stream);
		byte[] array = stream.toByteArray();
		Log.e("BYTE{{}>.SIZE", array.length+"");
		
		//grab image from file(fullResPicture)
//		 byte[] array=null;
//		try {
//			System.out.println("making bit array from FullResPicture");
//			array = MyBitmap.makeByteArray(fullResPicture);
//			System.out.println("an array was made of size-->"+array.length);
//		} catch (IOException e1) {
//			System.out.println("failed to make bit array from FullResPicture");
//			e1.printStackTrace();
//		}
		 String pictureString = android.util.Base64.encodeToString(array, Base64.DEFAULT);
		
		//kick off async task to send string to server. show progress in meantime
		 System.out.println("heading into upload picture");
		UploadPicture upload = new UploadPicture();
		upload.setCookie(cereal);
		EditText caption = (EditText) findViewById(R.id.caption);
		
		String captionString = null;
		if (caption.getText().length()==0){
			captionString = new SimpleDateFormat("dd-MMM-yy h:mm", Locale.US).format(new Date());
		}else{
			captionString = caption.getText().toString();
			Log.e("captionString", captionString);
		}
		
		upload.setCaption(captionString);
//		progress.showProgress(true, getResources());
		AsyncTask<String, Void, String> backgroundTask = upload.execute(pictureString);
//		progress.showProgress(false, getResources());// may fail here
		String result=null;
		try {
			result = backgroundTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
		caption.setText("");
		 LonelyPicture singlePrint = new LonelyPicture();
		singlePrint.setGUID(upload.getPicId());
		singlePrint.setCaption(upload.getCaption());
		printList.clear();
		printList.add(singlePrint);
		refreshPicListView();
		Button uploadBtn = (Button) findViewById(R.id.upload);
		uploadBtn.setEnabled(false);
	}
	
	
	/**not yet working
	 * called by uploadClicked
	 * After the user upload's a photo the picList fragment needs 
	 * to be updated to show that newly added picture
	 */
	private void refreshPicListView(){
		
				RefreshPicList refresh = new RefreshPicList();
				refresh.setCookie(cereal);
				AsyncTask<Void, Void, JSONObject> asyncRefresh =  refresh.execute();
			JSONObject json = null;
				try {
					json = (JSONObject) asyncRefresh.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				//find fragment by id then call the onCreateView?
//				PicListFragment pf = (PicListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_pic_list);
				
				//pf.loadListViewAgain();
				//need an adapter to set the content 
				
				//refresh view
				ListView picListView = (ListView) findViewById(R.id.picListView);
				picListView.invalidate();
				picListView.requestLayout();
				
				
				
				
				
				
				//a very ugly and clunky way to do it, but it works.
				confirmPrintDialog(true, json);

	}
	
	
	/**right screen, right button
	 * XML Accessory method only 
	 * Only enabled, once upload has been clicked.
	 * By this point, the printList will have been loaded, and the confirmDialog will handle all actual tasks
	 * @param view
	 */
	public void singlePrint(View view){
		confirmPrintDialog(false, null);
	}
	
	
	/**only called locally--by 
	 * this method shows a confirmation dialog that asks the user to confirm they want to purchase photos.
	 */
	private void confirmPrintDialog(final boolean restart, final JSONObject json){
		String msg="Are you sure you want to purchase "+ printList.size() +" photo(s)";
		if(restart){
			msg="Would you like to purchase this photo now?";
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Confirm Purchase");
		builder.setMessage(msg)
		.setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.out.println("positive button");
				printConfirmed(restart, json);
				
				
				
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.out.println("negative button");
				if(restart){//happens when user just uploaded picture
					Intent intent = getIntent();
					intent.putExtra("JSON", json.toString());
					finish();
					startActivity(intent);
				 	
				}
				
			}
		});
		AlertDialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(false);//TODO
		dialog.show();
		
	}
	
	
	/** right button, left screen
	 * when user clicks this button it will do one of two things
	 * either it shows the (one) selected picture, or it prints whatever pictures are in the printList
	 * before, actually printing however there is an alert dialog requesting confirmation.
	 * @param view
	 */
	public void showButtonClicked(View view){
		
		Button right = (Button) findViewById(R.id.show_and_back_button);
		Button left = (Button) findViewById(R.id.pic_list_print);
		ListView picListView = (ListView) findViewById(R.id.picListView);
		
		if(right.getText().toString().contains("Print")){//If they're actually clicking this button when it says print 
			// confirm purchase dialog
			 confirmPrintDialog(false, null);
			
			//reset buttons and list selection capabilities
			picListView.clearChoices();
			 picListView.requestLayout();
			 left.setText("Select Many");
			 right.setText("Show");
			 picListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			
			
			
		}else{//they've clicked the button while it says show
			
		//get the selected picture and set it in the image view
		 
		//async task to get the picture and decode (pass in the selected pic)
		 
		 FetchPicture mAuthTask = new FetchPicture();
		  mAuthTask.setCookie(cereal);
			AsyncTask<LonelyPicture, Void, byte[]> backgroundTask = mAuthTask.execute(fetch);
			byte[] picBytesFromServer=null;//
			try {
				picBytesFromServer = backgroundTask.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			System.out.println("about to decode");
			
			Bitmap bitmap = BitmapFactory.decodeByteArray(picBytesFromServer, 0, picBytesFromServer.length); //the other ints are beginning and ending of what to parse
			
			ImageView iv = (ImageView) findViewById(R.id.image_view_show);
			iv.setImageBitmap(bitmap);
		
		android.widget.ViewFlipper vf = null;
		vf = (ViewFlipper) findViewById(R.id.vf);
		Button showButton = (Button) findViewById(R.id.show_and_back_button);
		
		if(changeToBack){//when showing a picture, disable the left button
			changeToBack=false;
			showButton.setText("Back");
			left.setEnabled(false);
		}else{
			changeToBack=true;
			showButton.setText("Show");
			left.setEnabled(true);
		}
		
		vf.showNext();
		}
		
	}	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}


	
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			switch (position){
			case 0:
				return new PicListFragment();
			case 1:
				return new UploadFragment();
				
			default:
				return null;
			}
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return "My Pictures";
			case 1:
				return "Upload";
			}
			return null;
		}
		
	} 
	
//	public  class PicListFragment extends Fragment {
//		
//		ListView picListView = null;
//		
//		public PicListFragment(){
//			
//		}
//		
//	    @Override
//	    public void onCreate(Bundle savedInstanceState) {
//	        super.onCreate(savedInstanceState);
//	    }
//	 
//	    
//	    
//	    @Override
//	    public void onActivityCreated(Bundle savedInstanceState) {
//	        super.onActivityCreated(savedInstanceState);
//	        
//	        Log.e("PicListFragment onCreateView", "#2");
//	        Intent intent = getActivity().getIntent();
//			 String JSON = intent.getStringExtra("JSON");
//			 JSONObject rjo=null;
//			  ArrayList<LonelyPicture> picList = new ArrayList<LonelyPicture>();//
//			 JSONArray captions = null;
//			try {
//				rjo = new JSONObject(JSON);
//				
//				 JSONArray guids = (JSONArray) rjo.get("guids");
//				  captions = (JSONArray) rjo.get("captions");
//				 for(int i =0; i< guids.length();i++){
//					
//					 LonelyPicture lp = new LonelyPicture();
//					 lp.setGUID((String) guids.get(i));
//					 lp.setCaption((String) captions.get(i));
//					 picList.add(lp);
//					 
//				 }
//				 
//			} catch (JSONException e) {
//				System.out.println("Viewflipper-->caught in json exception");
//				e.printStackTrace();
//			}
//
//			 ArrayAdapter<LonelyPicture> adapter = new ArrayAdapter<LonelyPicture>(getActivity().getApplicationContext(),
//			   android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, picList);
//			 
//
//			 picListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//			 picListView.setAdapter(adapter);
//			 
//			//will this really work?
//			
//				 picListView = (ListView) getActivity().findViewById(R.id.picListView);
//				picListView.setOnItemClickListener(new OnItemClickListener() {
//					
//					 public void onItemClick(AdapterView<?> a, View v, int i, long l){
//						  fetch = (LonelyPicture) picListView.getItemAtPosition(i);
//						printList.add((LonelyPicture) picListView.getItemAtPosition(i));
//						 System.out.println("onListViewClickListene--Fetch.getGUID"+fetch.getGUID());
//					 }});
//			 
//				 
//				 
//	    }
//	 
//	    @Override
//	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//	            Bundle savedInstanceState) {
//	        View view = inflater.inflate(R.layout.pic_list, container, false);
//	        Log.e("PicListFragment onActivityCreated", "#3");
//	          picListView = (ListView) view.findViewById(R.id.picListView);
//	      
//	        return view;
//	    }
//	}
	
	public static class UploadFragment extends Fragment {
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        Log.e("Test", "uploadFragmentOnCreate");
	    }
	 
	    @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);
	        Button print = (Button) getActivity().findViewById(R.id.print);
			Button upload = (Button) getActivity().findViewById(R.id.upload);
			print.setEnabled(false);
			upload.setEnabled(false);
	 
	    }
	 
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View view = inflater.inflate(R.layout.upload, container, false);
	        System.out.println("finishing uploadFragment oncreateView");
	        return view;
	    }
	}
	    
		
	



}
