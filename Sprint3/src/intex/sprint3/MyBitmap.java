package intex.sprint3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**Contains convenience methods used when uploading pictures to the server*/
public class MyBitmap extends FragmentActivity{
	View mUpdateStatusView= null;
	
	
	public static Bitmap scaleBitmap(Bitmap bitmap, int wantedWidth, int wantedHeight) {
        Bitmap output = Bitmap.createBitmap(wantedWidth, wantedHeight, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Matrix m = new Matrix();
        m.setScale((float) wantedWidth / bitmap.getWidth(), (float) wantedHeight / bitmap.getHeight());
        canvas.drawBitmap(bitmap, m, new Paint());
        
        return output;
    }

//	    public static Bitmap ScaleBitmap(Bitmap originalImage, int wantedWidth, int wantedHeight)
//	    {
//	        Bitmap output = Bitmap.CreateBitmap(wantedWidth, wantedHeight, Bitmap.Config.Argb8888);
//	        Canvas canvas = new Canvas(output);
//	        Matrix m = new Matrix();
//	        m.setScale((float)wantedWidth / originalImage.getWidth(), (float)wantedHeight / originalImage.getHeight());
//	        canvas.DrawBitmap(originalImage, m, new Paint());
//	        return output;
//	    }

	
	

    public static byte[] makeByteArray (File file) throws IOException {

//      if ( file.length() > MAX_FILE_SIZE ) {
//          throw new FileTooBigException(file);
//      }


      byte []buffer = new byte[(int) file.length()];
      InputStream ios = null;
      try {
          ios = new FileInputStream(file);
          if ( ios.read(buffer) == -1 ) {
              throw new IOException("EOF reached while trying to read the whole file");
          }        
      } finally { 
          try {
               if ( ios != null ) 
                    ios.close();
          } catch ( IOException e) {
          }
      }

      return buffer;
  }
	
    public static Bitmap decodeSampledBitmapFromFile(File file, String pathName,
            int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
       

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathName, options);
    }
    
    /**called by decodeSampledBitmapFromFile*/
    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        // Calculate ratios of height and width to requested height and width
        final int heightRatio = Math.round((float) height / (float) reqHeight);
        final int widthRatio = Math.round((float) width / (float) reqWidth);

        // Choose the smallest ratio as inSampleSize value, this will guarantee
        // a final image with both dimensions larger than or equal to the
        // requested height and width.
        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
    }

    return inSampleSize;
}
	
    
    /**
	 * Shows a progress UI and hides the upload fragment.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show, Resources res) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = res.getInteger(
					android.R.integer.config_shortAnimTime);

			mUpdateStatusView = findViewById(R.id.upload_status);
			mUpdateStatusView.setVisibility(View.VISIBLE);
			mUpdateStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mUpdateStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mUpdateStatusView.setVisibility(View.VISIBLE);
			mUpdateStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mUpdateStatusView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mUpdateStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mUpdateStatusView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
}
