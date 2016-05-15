package android.example.com.midterm.PhotoDiary;

public class Diary {

	private int id;
	private String date;
	private String weekdate;
	private String image;
	private String content;

	public Diary(int id, String date, String weekdate, String image, String content) {
		super();
		this.id = id;
		this.date = date;
		this.weekdate = weekdate;
		this.image = image;
		this.content = content;
	}

	public Diary() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getWeekdate() {
		return weekdate;
	}

	public void setWeekdate(String weekdate) {
		this.weekdate = weekdate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Diary [id=" + id + ", date=" + date + ", weekdate=" + weekdate + ", image=" + image + ", content=" + content + "]";
	}

}
