package android.example.com.midterm.PhotoDiary;

import java.io.File;
import java.util.Comparator;

import android.text.format.DateFormat;

public class CustomComparator implements Comparator<File> {
    @Override
    public int compare(File o1, File o2) {
    	
    	String time1 = new DateFormat().format("yyyy/MM/dd hh:mm:ss", o1.lastModified()).toString();
    	String time2 = new DateFormat().format("yyyy/MM/dd hh:mm:ss", o2.lastModified()).toString();
        return time2.compareTo(time1);
    }
}
