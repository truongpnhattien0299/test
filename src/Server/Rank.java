package Server;

import java.io.Serializable;

public class Rank implements Serializable{
    int userId,point,N_match,win,lose;
    String username;
    
    public Rank() {
    	
    }
    
   
    

    public Rank(int userId,String username, int point, int n_match, int win, int lose) {
		super();
		this.userId = userId;
		this.username = username;
		this.point = point;
		N_match = n_match;
		this.win = win;
		this.lose = lose;
	}



	public String getUsername() {
		return username;
	}




	public void setUsername(String username) {
		this.username = username;
	}




	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}



	public int getPoint() {
		return point;
	}



	public void setPoint(int point) {
		this.point = point;
	}



	public int getN_match() {
		return N_match;
	}



	public void setN_match(int n_match) {
		N_match = n_match;
	}



	public int getWin() {
		return win;
	}



	public void setWin(int win) {
		this.win = win;
	}



	public int getLose() {
		return lose;
	}



	public void setLose(int lose) {
		this.lose = lose;
	}
    
    

	

   
}