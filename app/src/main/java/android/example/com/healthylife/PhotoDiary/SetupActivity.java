package android.example.com.healthylife.PhotoDiary;

import android.content.Intent;
import android.example.com.healthylife.PhotoDiary.util.ImageCache;
import android.example.com.healthylife.PhotoDiary.util.ImageFetcher;
import android.example.com.healthylife.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SetupActivity  extends FragmentActivity{
	
	// Global mutable variables
	private static final String IMAGE_CACHE_DIR = "images";
	public static final String EXTRA_IMAGE = "extra_image";

	private DBDiary db;
	private String date = "";
	
	private EditText edtContent;
	
	private ImageFetcher mImageFetcher;
    private ImageView mPager;
	public static String imageLink = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_check);
		
		// Init database instance
		db = new DBDiary(getApplicationContext());
		
		// Get current date
		date = getIntent().getStringExtra("Date");
		
		// Set text for textview
		TextView txtDate = (TextView)findViewById(R.id.txtDate);
		txtDate.setText(date.replace("/", " / "));
		
		// Init imageview, click to show list image
		mPager = (ImageView) findViewById(R.id.pager);
		mPager.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SetupActivity.this, ImageGridActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		
		// Init edittext
		edtContent = (EditText)findViewById(R.id.edtContent);
		
		// Init ImageFetcher
		final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = displayMetrics.heightPixels;
        final int width = displayMetrics.widthPixels;

        final int longest = (height > width ? height : width) / 2;
        
        ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(this, IMAGE_CACHE_DIR);
        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory
        
		mImageFetcher = new ImageFetcher(this, longest);
        mImageFetcher.addImageCache(getSupportFragmentManager(), cacheParams);
        mImageFetcher.setImageFadeIn(false);
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if(!imageLink.equals("")) { // Load image if user select if from list
	        
	        mImageFetcher.loadImage(imageLink, mPager);
	        
		} else { // Load image if this first time user open this date's detail
			Diary diary = db.getOneRow(date);
			if(diary != null) {
				Diary existed = db.getOneRow(date);
				edtContent.setText(existed.getContent());
				
				if(!existed.getImage().equals("")) {
					imageLink = existed.getImage();
					mImageFetcher.loadImage(existed.getImage(), mPager);
				}
			}
		}
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mImageFetcher.clearCache();
		mImageFetcher.flushCache();
		mImageFetcher.closeCache();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		Log.e("Setup Activity", "Destroy");
		// Clear cache and data
		imageLink = "";
		mImageFetcher.clearCache();
		mImageFetcher.flushCache();
		mImageFetcher.closeCache();
		mImageFetcher = null;
		System.gc();
	}
	
	public void clearData(View view) {
		
		// Clear image and content
		edtContent.setText("");
		
		imageLink = "";
		mPager.setImageResource(R.drawable.no_image);
	}
	
	public void save(View view) {
		// save image and content to database
		db.UPDATE(db.getOneRow(date).getId(), imageLink, edtContent.getText().toString());
		finish();
	}
	public void finish(View view) {
		finish();
	}
}
