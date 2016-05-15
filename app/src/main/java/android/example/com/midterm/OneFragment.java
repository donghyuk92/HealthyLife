package android.example.com.midterm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class OneFragment extends Fragment {
    private ArrayAdapter<String> adapter;
    private Spinner obj;
    private int songid = R.raw.aqua;


    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.onefragment, container, false);

        ArrayList list = new ArrayList();
        list.add("My Height");
        list.add("My Weiight");
        list.add("Start Music!");
        list.add("Pause Music!");
        list.add("Stop Music!!");
        list.add("Exit App..");

        ListView listView = (ListView) view.findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:

                        if(obj==null) {

                        }

                        Intent intent = new Intent(MyService.ACTION_PLAY);
                        intent.putExtra("songid",songid);
                        getActivity().startService(intent);

                        break;
                    case 3:

                            Intent intent1 = new Intent(MyService.PAUSE_PLAY);
                            getActivity().startService(intent1);
                        break;
                    case 4:
                        Intent intent2 = new Intent(MyService.STOP_PLAY);
                        getActivity().startService(intent2);
                        break;
                    case 5:
                        Toast.makeText(getActivity(), "Application Finished with Service", Toast.LENGTH_LONG).show();
                        getActivity().finish();
                        break;
                }
            }
        });

        String[] tmp = getResources().getStringArray(R.array.spinner);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, tmp);
        obj = (Spinner) view.findViewById(R.id.spinner);
        obj.setAdapter(adapter1);
        obj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        songid = R.raw.aqua;
                        Intent intent = new Intent(MyService.CHANGE_PLAY);
                        intent.putExtra("songname", songid);
                        getActivity().startService(intent);
                        break;
                    case 1:
                        songid = R.raw.itsme;
                        Intent intent1 = new Intent(MyService.CHANGE_PLAY);
                        intent1.putExtra("songname", songid);
                        getActivity().startService(intent1);
                        break;
                    case 2:
                        songid = R.raw.kal;
                        Intent intent2 = new Intent(MyService.CHANGE_PLAY);
                        intent2.putExtra("songname", songid);
                        getActivity().startService(intent2);
                        break;
                    case 3:
                        songid = R.raw.oba;
                        Intent intent3 = new Intent(MyService.CHANGE_PLAY);
                        intent3.putExtra("songname", songid);
                        getActivity().startService(intent3);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
}
