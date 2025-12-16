package movieticket.model;

public class Movies {
	
	private int mId;
	private String mName;
	private double ticketRate;
	private String genre;
	public int getmId() {
		return mId;
	}
	public void setmId(int mId) {
		this.mId = mId;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public double getTicketRate() {
		return ticketRate;
	}
	public void setTicketRate(double ticketRate) {
		this.ticketRate = ticketRate;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public Movies(int mId, String mName, double ticketRate, String genre) {
		super();
		this.mId = mId;
		this.mName = mName;
		this.ticketRate = ticketRate;
		this.genre = genre;
	}
	@Override
	public String toString() {
		return "Movies [mId=" + mId + ", mName=" + mName + ", ticketRate=" + ticketRate + ", genre=" + genre + "]";
	}
	
	
	

	

}
