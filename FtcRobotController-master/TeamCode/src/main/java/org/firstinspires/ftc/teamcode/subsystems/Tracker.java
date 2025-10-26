package org.firstinspires.ftc.teamcode.subsystems;



import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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

    public boolean colourInIntake(String c){
        boolean colourExists = false;
        for(int i = 0; i > getSize(); i++){
            if(Objects.equals(getBall(i), c)) colourExists = true;
        }
        return colourExists;
    }

    public String getColour(NormalizedColorSensor sensor){
        //Get colour values
        NormalizedRGBA colours = sensor.getNormalizedColors();
        float currentAlpha = colours.alpha;
        float normR, normG, normB;
        normR = colours.red/currentAlpha;
        normG = colours.green/currentAlpha;
        normB = colours.blue/currentAlpha;

        //Determine colour of object
        if(normG > 0.4 && (normG > normR && normG > normB)) return "g";
        else if(normR > 0.4 && normB > 0.4 && normG < 0.3) return "p";
        else{ return "u";}
    }
}
