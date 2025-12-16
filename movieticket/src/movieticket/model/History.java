package movieticket.model;
import java.time.LocalDateTime;


public class History {
	
	private int id;
	private LocalDateTime bookingTime;
	private LocalDateTime cancelTime;
	private String uName;
	private String mName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDateTime getBookingTime() {
		return bookingTime;
	}
	public History(int id, LocalDateTime bookingTime, LocalDateTime cancelTime, String uName, String mName) {
		super();
		this.id = id;
		this.bookingTime = bookingTime;
		this.cancelTime = cancelTime;
		this.uName = uName;
		this.mName = mName;
	}
	@Override
	public String toString() {
		return "History [id=" + id + ", bookingTime=" + bookingTime + ", cancelTime=" + cancelTime + ", uName=" + uName
				+ ", mName=" + mName + "]";
	}
	public void setBookingTime(LocalDateTime bookingTime) {
		this.bookingTime = bookingTime;
	}
	public LocalDateTime getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(LocalDateTime cancelTime) {
		this.cancelTime = cancelTime;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}

		

}
