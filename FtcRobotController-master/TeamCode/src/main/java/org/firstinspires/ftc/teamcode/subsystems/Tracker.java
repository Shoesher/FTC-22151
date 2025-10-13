package org.firstinspires.ftc.teamcode.subsystems;



import java.util.ArrayList;
import java.util.List;


public class Tracker {
    private List<String> trackedBalls = new ArrayList<>();

    public void addBall(String c){
        trackedBalls.add(c);
    }

    public void removeBallExpel(){
        if (!trackedBalls.isEmpty()) trackedBalls.remove(trackedBalls.size()-1);
    }

    public void removeBallShot(String c){
        if(!trackedBalls.isEmpty()) trackedBalls.remove(c);
    }

    public int getSize(){
        return trackedBalls.size();
    }

    public String getBall(int i){
        return trackedBalls.get(i);
    }
}
