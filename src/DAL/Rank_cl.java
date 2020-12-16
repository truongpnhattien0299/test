package DAL;

import java.io.Serializable;

public class Rank_cl implements Serializable,Comparable<Rank_cl>{
    int userId,point,N_match,win,lose;
    String username;
    
    public Rank_cl() {
    	
    }
    
   
    

    public Rank_cl(int userId,String username, int point, int n_match, int win, int lose) {
		super();
		this.userId = userId;
		this.username = username;
		this.point = point;
		N_match = n_match;
		this.win = win;
		this.lose = lose;
	}

    
    @Override
    public int compareTo(Rank_cl rank_cl) {
    	if (point == rank_cl.point)
			return 0;
		else if (point == rank_cl.point)
			return 1;
		else
			return -1;
    }


	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}




	public void setUsername(String username) {
		this.username = username;
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