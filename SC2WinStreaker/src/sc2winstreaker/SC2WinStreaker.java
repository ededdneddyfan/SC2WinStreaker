/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sc2winstreaker;

import java.io.PrintWriter;
import java.net.URL;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Seth
 */
class Loop extends TimerTask {
    public void run() {
        //Load Specific Profile
        String s = "http://us.battle.net/api/sc2/profile/285741/1/borp/matches";
        try {
            URL url = new URL(s);
            Scanner scan = new Scanner(url.openStream());
            String str = new String();
            int wins = 0;
            
            while (scan.hasNext()){
                str += scan.nextLine();
            }
            scan.close();
            //Properly format JSON
            str = str.substring(str.indexOf("["));

            JSONArray arr = new JSONArray(str);
            for (int i = 0; i < arr.length(); i++){
                JSONObject temp = arr.getJSONObject(i);
                if (temp.getString("decision").equals("WIN")){
                    wins++;
                } else {
                    break;
                }
            }
            System.out.println("WIN STREAK: " + wins);
            //Write to file
            PrintWriter out = new PrintWriter("winStreak.txt");
            out.println("WIN STREAK: " + wins);
            out.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
public class SC2WinStreaker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Timer timer = new Timer();
        //Repeat every 5 Min
        timer.schedule(new Loop(), 0, 3000 * 60);
    }
    
}
