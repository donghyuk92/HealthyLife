package android.example.com.midterm;

import android.content.Intent;
import android.example.com.midterm.Alarm.Alarm;
import android.example.com.midterm.Calorie.FirstActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements OnClickListener {

	int mCurrentFragmentIndex;
	public final static int FRAGMENT_ONE = 0;
	public final static int FRAGMENT_TWO = 1;
	public final static int FRAGMENT_THREE = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button bt_oneFragment = (Button) findViewById(R.id.bt_oneFragment);
		bt_oneFragment.setOnClickListener(this);
		Button bt_twoFragment = (Button) findViewById(R.id.bt_twoFragment);
		bt_twoFragment.setOnClickListener(this);
		Button bt_threeFragment = (Button) findViewById(R.id.bt_threeFragment);
		bt_threeFragment.setOnClickListener(this);

		mCurrentFragmentIndex = FRAGMENT_ONE;

		fragmentReplace(mCurrentFragmentIndex);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		switch (id) {
			case R.id.title1:
				mCurrentFragmentIndex = FRAGMENT_ONE;
				fragmentReplace(mCurrentFragmentIndex);
				break;
			case R.id.title2:
				mCurrentFragmentIndex = FRAGMENT_TWO;
				fragmentReplace(mCurrentFragmentIndex);
				break;
			case R.id.title3:
				mCurrentFragmentIndex = FRAGMENT_THREE;
				fragmentReplace(mCurrentFragmentIndex);
				break;
			case R.id.calorie:
				Intent intent = new Intent(this, FirstActivity.class);
				startActivity(intent);
				break;
		}


		return super.onOptionsItemSelected(item);
	}

	public void fragmentReplace(int reqNewFragmentIndex) {

		Fragment newFragment = null;

		newFragment = getFragment(reqNewFragmentIndex);

		// replace fragment
		final FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();

		transaction.replace(R.id.ll_fragment, newFragment);

		// Commit the transaction
		transaction.commit();

	}

	private Fragment getFragment(int idx) {
		Fragment newFragment = null;

		switch (idx) {
			case FRAGMENT_ONE:
				newFragment = new OneFragment();
				break;
			case FRAGMENT_TWO:
				newFragment = new TwoFragment();
				break;
			case FRAGMENT_THREE:
				newFragment = new ThreeFragment();
				break;
		}

		return newFragment;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

			case R.id.bt_oneFragment:
				mCurrentFragmentIndex = FRAGMENT_ONE;
				fragmentReplace(mCurrentFragmentIndex);
				break;
			case R.id.bt_twoFragment:
				mCurrentFragmentIndex = FRAGMENT_TWO;
				fragmentReplace(mCurrentFragmentIndex);
				break;
			case R.id.bt_threeFragment:
				mCurrentFragmentIndex = FRAGMENT_THREE;
				fragmentReplace(mCurrentFragmentIndex);
				break;

		}
	}
}
