package Server;

import DAL.User;

public class Room {
	
	private int id;
	private Worker player1 = null;
	private Worker player2 = null;
	private String user1=null;
	private String user2=null;

	
	
	public String getUser1() {
		return user1;
	}





	public void setUser1(String user1) {
		this.user1 = user1;
	}





	public String getUser2() {
		return user2;
	}





	public void setUser2(String user2) {
		this.user2 = user2;
	}





	public Room(int id) {
		this.id = id;
		
	}
	
	

	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Worker getPlayer1() {
		return player1;
	}
	public void setPlayer1(Worker player1) {
		this.player1 = player1;
	}
	public Worker getPlayer2() {
		return player2;
	}
	public void setPlayer2(Worker player2) {
		this.player2 = player2;
	}
	
}
