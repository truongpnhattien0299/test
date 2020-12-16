package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Server {
	protected static String key;
	private static ServerSocket server = null;
    public static int port = 1234;
    public static int numThread = 40;
    public static ArrayList<Worker> workers = new ArrayList<>();
    public static ArrayList<Room> rooms = new ArrayList<>();
    public static Integer[] arr_rd;
    public static Integer[] point_bonus;
    public static BufferedReader stdIn = null; 
    public static int minute,choose;
    private static final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private static final String alphaUpperCase = alpha.toUpperCase(); // A-Z
    private static final String digits = "0123456789"; // 0-9
    private static final String specials = "~=+%^*/()[]{}/!@#$?|";
    private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
    private static final String ALL = alpha + alphaUpperCase + digits + specials;
    private static Random generator = new Random();
    
  
    public static void main(String args[]) throws IOException 
    { 
    	
    	 String sb = "";
	        for (int i = 0; i < 3; i++) {
	            int number = randomNumber(0, ALPHA_NUMERIC.length() - 1);
	            char ch = ALPHA_NUMERIC.charAt(number);
	            sb=sb+ch;
	        }
	       
	    
           key=sb; 
    	
    	
    	stdIn = new BufferedReader(new InputStreamReader(System.in));
    	
    	
    	
    	while(true){
            System.out.print("Nhap so phut :");
            String str = stdIn.readLine();
            if (str.trim().equals("")) {
                System.out.println("Ban nhap rong, xin vui long nhap lai.");
                continue;
            }
            try {
                minute = Integer.parseInt(str);
            } catch (NumberFormatException ex) {
                System.out.println("Ban nhap so khong hop le, xin vui long chi nhap so.");
                continue;
            }
            break;
            
        }
    
    	System.out.println("so phut : " +minute);
    	
    	Random_So rd = new Random_So();
		arr_rd= rd.so();
		point_bonus=rd.so();
        ExecutorService executor = Executors.newFixedThreadPool(numThread);
        try
        { 
            server = new ServerSocket(port); 
            System.out.println("Server binding at port " + port); 
            System.out.println("Waiting for a client ..."); 
            for(int i=0;i<10;i++)
            {
                Room room = new Room(i);
                rooms.add(room);
            }
            int id=0;
            while(true)
            {
                Socket socket = server.accept(); 
                String user="";
                Worker worker = new Worker(socket, Integer.toString(id),user) {};
                executor.execute(worker);
                workers.add(worker);
                id++;
            }
        }catch(IOException i) 
        { 
            System.err.println(i); 
        } 
        finally{
            if(server!=null)
                server.close();
        } 
    } 
    
    
    public String encrypt(String strToEncrypt, String myKey) {
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] key = myKey.getBytes("UTF-8");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return java.util.Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return null;
	}

	public String decrypt(String strToDecrypt, String myKey) {
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] key = myKey.getBytes("UTF-8");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(java.util.Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return null;
	}
	public static int randomNumber(int min, int max) {
        return generator.nextInt((max - min) + 1) + min;
    }
}
