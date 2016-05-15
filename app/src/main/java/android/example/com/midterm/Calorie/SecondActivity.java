package android.example.com.midterm.Calorie;

import android.example.com.midterm.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SecondActivity extends AppCompatActivity {

	private ImageView tbl1;
	private ImageView tbl2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calorie_second);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// Init widget
		tbl1 = (ImageView)findViewById(R.id.tbl1);
		tbl2 = (ImageView)findViewById(R.id.tbl2);

		Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.tbl_calorie_1_2);
		tbl1.setImageBitmap(bitmap);

		Bitmap bitmap2 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.tbl_calorie_2_2);
		tbl2.setImageBitmap(bitmap2);

		tbl1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				// Ensure you call it only once :
				tbl1.getViewTreeObserver().removeGlobalOnLayoutListener(this);

				int width = tbl1.getWidth();
				int height = tbl1.getWidth() * 2068/1000;
				
				LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(width, height);
				
				tbl1.setLayoutParams(lp1);

			}
		});
		tbl2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				// Ensure you call it only once :
				tbl2.getViewTreeObserver().removeGlobalOnLayoutListener(this);

				int width = tbl2.getWidth();
				int height = tbl2.getWidth() * 329 / 100;

				LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(width, height);

				tbl2.setLayoutParams(lp2);

			}
		});
	}
}
