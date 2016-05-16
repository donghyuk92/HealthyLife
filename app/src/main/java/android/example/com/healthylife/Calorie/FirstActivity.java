package android.example.com.healthylife.Calorie;

import android.content.Intent;
import android.example.com.healthylife.R;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FirstActivity extends AppCompatActivity {

	private EditText edt1;
	private TextView txtResult1;
	private EditText edt2;
	private EditText edt3;
	private TextView txtResult2;
	private ImageView btnStart;
	private Button calc1;
	private Button calc2;
    private LinearLayout layout;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calorie_first);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);


		edt1 = (EditText) findViewById(R.id.edt1);
		txtResult1 = (TextView) findViewById(R.id.txtResult1);

		edt2 = (EditText) findViewById(R.id.edt2);
		edt3 = (EditText) findViewById(R.id.edt3);
		txtResult2 = (TextView) findViewById(R.id.txtResult2);

		btnStart = (ImageView) findViewById(R.id.btnStart);
		btnStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(FirstActivity.this, SecondActivity.class));
			}
		});
		calc1= (Button) findViewById(R.id.calc1);
		calc1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					if(!edt1.getText().toString().equals("") &&
							Float.parseFloat(edt1.getText().toString()) > 100 &&
							Float.parseFloat(edt1.getText().toString()) < 300 ) {

						txtResult1.setText(String.format("%.2f", (Float.parseFloat(edt1.getText().toString()) - 100 ) * 0.9));

					} else {
						txtResult1.setText("0");
					}
			}
		});
		calc2= (Button) findViewById(R.id.calc2);
		calc2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!edt2.getText().toString().equals("") &&
						!edt3.getText().toString().equals("") &&
						Float.parseFloat(edt2.getText().toString()) > 0 &&
						Float.parseFloat(edt2.getText().toString()) < 300 &&
						Float.parseFloat(edt3.getText().toString()) > 0 &&
						Float.parseFloat(edt3.getText().toString()) < 300) {
					txtResult2.setText(String.valueOf(Float.parseFloat(edt2.getText().toString()) * Float.parseFloat(edt3.getText().toString())));
				} else {
					txtResult2.setText("0");
				}
			}
		});
    }
}