package TeamA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

import am.ik.voicetext4j.EmotionalSpeaker;
import net.arnx.jsonic.JSON;

import static spark.Spark.*;


public class Main {
	public static void main(String[] args) {
		System.setProperty("voicetext.apikey", "API KEY");
		enableCORS("*","*","*");
		
		get("/hello", (req, res) -> "Hello World");
		get("/user/:id", (request, response) -> {
		    return getUserData(request.params("id"));
		});
		
		get("/users", (request, response) -> {
		    return getUsersData();
		});
		
		get("/farms", (request, response) -> {
		    return getFarmsData();
		});
		
		get("/markets", (request, response) -> {
		    return getMarketsData();
		});
		
		get("/recipe", (request, response) -> {
			try {
				getData();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		    return "";
		});
		
		get("/say/sad/:word", (request, response) -> {
			EmotionalSpeaker.TAKERU.ready().pitch(200).speed(115).very().sad().speak(request.params("word")).get();
		    return request.params("word");
		});
		get("/say/angry/:word", (request, response) -> {
			EmotionalSpeaker.TAKERU.ready().pitch(200).speed(115).very().angry().speak(request.params("word")).get();
		    return request.params("word");
		});
		get("/say/happy/:word", (request, response) -> {
			EmotionalSpeaker.TAKERU.ready().pitch(200).speed(115).very().happy().speak(request.params("word")).get();
		    return request.params("word");
		});
	}
	
	private static void enableCORS(final String origin, final String methods, final String headers) {

	    options("/*", (request, response) -> {

	        String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
	        if (accessControlRequestHeaders != null) {
	            response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
	        }

	        String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
	        if (accessControlRequestMethod != null) {
	            response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
	        }

	        return "OK";
	    });

	    before((request, response) -> {
	        response.header("Access-Control-Allow-Origin", origin);
	        response.header("Access-Control-Request-Method", methods);
	        response.header("Access-Control-Allow-Headers", headers);
	        response.type("application/json");
	    });
	}
	
	private static String getMarketsData() {
		Connection con = null;
        PreparedStatement ps = null;
        try {
			Class.forName("com.mysql.jdbc.Driver");
	        con = DriverManager.getConnection("jdbc:mysql://localhost/TeamA_db","root","");
	        String sql = "SELECT * FROM marketmst;";
	        ps = con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        String marketid = "";
        	String marketname = "";
        	double latitude = 0;
        	double longitude = 0;
        	String ret = "[";
        	rs.last();
            int number_of_row = rs.getRow();
            rs.beforeFirst();
            int count = 0;
	        while(rs.next()) {
	        	marketid = rs.getString("marketid");
	        	marketname = rs.getString("marketname");
	        	latitude = rs.getDouble("latitude");
	        	longitude = rs.getDouble("longitude");
	        	if (count != 0) 
	        		ret += ",";
	        	ret += "{\"userid\":\"" + marketid + 
	        			"\", \"username\":\"" + marketname + 
	        			"\", \"latitude\":\"" + latitude +
	        			"\", \"longitude\":\"" + longitude + "\"}";
	        	if (count + 1 == number_of_row)
	        		ret += "]";
	        	count++;
	        }
	        return ret;
        } catch (Exception e) {
        	return "";
        }
	}
	
	private static String getFarmsData() {
		Connection con = null;
        PreparedStatement ps = null;
        try {
			Class.forName("com.mysql.jdbc.Driver");
	        con = DriverManager.getConnection("jdbc:mysql://localhost/TeamA_db","root","");
	        String sql = "SELECT * FROM farmmst;";
	        ps = con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        String farmid = "";
        	String farmname = "";
        	double latitude = 0;
        	double longitude = 0;
        	String vegetable = "";
        	String ret = "[";
        	rs.last();
            int number_of_row = rs.getRow();
            rs.beforeFirst();
            int count = 0;
	        while(rs.next()) {
	        	farmid = rs.getString("farmid");
	        	farmname = rs.getString("farmname");
	        	latitude = rs.getDouble("latitude");
	        	longitude = rs.getDouble("longitude");
	        	vegetable = rs.getString("vegetable");
	        	if (count != 0) 
	        		ret += ",";
	        	ret += "{\"userid\":\"" + farmid + 
	        			"\", \"username\":\"" + farmname + 
	        			"\", \"latitude\":\"" + latitude +
	        			"\", \"longitude\":\"" + longitude +
	        			"\", \"marriage\":\"" + vegetable + "\"}";
	        	if (count + 1 == number_of_row)
	        		ret += "]";
	        	count++;
	        }
	        return ret;
        } catch (Exception e) {
        	return "";
        }
	}
	
	private static String getUsersData() {
		Connection con = null;
        PreparedStatement ps = null;
        try {
			Class.forName("com.mysql.jdbc.Driver");
	        con = DriverManager.getConnection("jdbc:mysql://localhost/TeamA_db","root","");
	        String sql = "SELECT * FROM usermst;";
	        ps = con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        String userid = "";
        	String username = "";
        	double latitude = 0;
        	double longitude = 0;
        	String marriage = "";
        	String kids = "";
        	String skill = "";
        	String home = "";
        	String ret = "[";
        	rs.last();
            int number_of_row = rs.getRow();
            rs.beforeFirst();
            int count = 0;
	        while(rs.next()) {
	        	userid = rs.getString("userid");
	        	username = rs.getString("username");
	        	latitude = rs.getDouble("latitude");
	        	longitude = rs.getDouble("longitude");
	        	marriage = rs.getString("marriage");
	        	kids = rs.getString("kids");
	        	skill = rs.getString("skill");
	        	home = rs.getString("home");
	        	if (count != 0) 
	        		ret += ",";
	        	ret += "{\"userid\":\"" + userid + 
	        			"\", \"username\":\"" + username + 
	        			"\", \"latitude\":\"" + latitude +
	        			"\", \"longitude\":\"" + longitude +
	        			"\", \"marriage\":\"" + marriage +
	        			"\", \"kids\":\"" + kids +
	        			"\", \"skill\":\"" + skill +
	        			"\", \"home\":\"" + home + "\"}";
	        	if (count + 1 == number_of_row)
	        		ret += "]";
	        	count++;
	        }
	        return ret;
        } catch (Exception e) {
        	return "";
        }
	}

	private static String getUserData(String id) {
		Connection con = null;
        PreparedStatement ps = null;
        try {
			Class.forName("com.mysql.jdbc.Driver");
	        con = DriverManager.getConnection("jdbc:mysql://localhost/TeamA_db","root","");
	        String sql = "SELECT * FROM usermst where userid=" + id + ";";
	        ps = con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        String userid = "";
        	String username = "";
        	double latitude = 0;
        	double longitude = 0;
        	String marriage = "";
        	String kids = "";
        	String skill = "";
        	String home = "";
	        while(rs.next()) {
	        	userid = rs.getString("userid");
	        	username = rs.getString("username");
	        	latitude = rs.getDouble("latitude");
	        	longitude = rs.getDouble("longitude");
	        	marriage = rs.getString("marriage");
	        	kids = rs.getString("kids");
	        	skill = rs.getString("skill");
	        	home = rs.getString("home");
	        }
	        return "{\"userid\":\"" + userid + 
	        		"\", \"username\":\"" + username + 
	        		"\", \"latitude\":\"" + latitude +
	        		"\", \"longitude\":\"" + longitude +
	        		"\", \"marriage\":\"" + marriage +
	        		"\", \"kids\":\"" + kids +
	        		"\", \"skill\":\"" + skill +
	        		"\", \"home\":\"" + home + "\"}";
        } catch (Exception e) {
        	return "";
        }
	}

	private static void getData() throws InterruptedException, ExecutionException {
		Connection con = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/TeamA_db","root","");
            String sql = "SELECT * FROM recipes;";
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.last();
            int number_of_row = rs.getRow();
            rs.beforeFirst();
            int count = 0;
            while(rs.next()) {
                String process = rs.getString("PROCESS");

                String ret = executePost(process);
                Recipe recipe = JSON.decode(ret, Recipe.class);
                String voice = "";
            	
                for (String[] r : recipe.getWord_list()[0]) {
                	for (String r2 : r) {
                		voice += r2 + "、";
                	}
                }
                System.out.println(voice);
                
                count++;
        		if (count > 1 && count != number_of_row)
        			EmotionalSpeaker.TAKERU.ready().pitch(200).speed(115).very().angry().speak("お次は！").get();
        		else if (count == 1) {
        			EmotionalSpeaker.TAKERU.ready().pitch(200).speed(115).very().angry().speak("ざっくり言うと！").get();
        			EmotionalSpeaker.TAKERU.ready().pitch(200).speed(115).very().angry().speak("まずは！").get();
        		} else if (count == number_of_row)
        			EmotionalSpeaker.TAKERU.ready().pitch(200).speed(115).very().angry().speak("最後に！").get();
        		
                EmotionalSpeaker.TAKERU.ready().pitch(200).speed(115).very().angry().speak(voice).get();
        		try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps != null){
                    ps.close();
                }
                if(con != null){
                    con.close();
                }
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
	}
	
	private static String executePost(String sentence) {
		URL url = null;
		String GOO_URL = "https://labs.goo.ne.jp/api/morph";
		String ID = "c5da67ce2e9c2ceef3eb09c183e2bf3611a4997512de5ba8bcade61a65a7f3d7";
		//String sentence = "じゃがいもはレンジで2〜3分加熱して火を通しておきます。";
        try{
            url = new URL(GOO_URL);
            URLConnection connection =url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            OutputStream os = connection.getOutputStream();

            String postStr = "{\"app_id\": \"" + ID + "\",\"sentence\": \"" + sentence + "\""
            		+ ",\"pos_filter\": \"名詞\",\"info_filter\": \"form\"}";//名詞|動詞語幹


            PrintStream ps = new PrintStream(os);
            ps.print(postStr); //データをPOSTする
            ps.close();

            InputStream is = connection.getInputStream(); //結果を取得
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String s, ret = null;
            while ((s = reader.readLine()) != null) {
            	ret = s;
            	System.out.println(s);
            }
            reader.close();
            return ret;

        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}
}
