package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import java.util.Objects;

public class Spindexer {
    private Tracker trackedBalls;
    private Shooter shooter;
    private DcMotorEx mainOrbitor;
    private NormalizedColorSensor c_sensor1;
    private NormalizedColorSensor c_sensor2;
    //Tracking
    public String projectile;

    public Spindexer(){
        trackedBalls = new Tracker();
        shooter = new Shooter();

        //Motors
        mainOrbitor = hardwareMap.get(DcMotorEx.class, "mainOrbitor");
        mainOrbitor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Sensors
        c_sensor1 = hardwareMap.get(NormalizedColorSensor.class, "cs1");
        c_sensor2 = hardwareMap.get(NormalizedColorSensor.class, "cs2");
    }

    public void setOrbit(boolean setGreen, boolean setPurple){
        if(setGreen && trackedBalls.colourInIntake("g")){
            if (!Objects.equals(trackedBalls.getColour(c_sensor2), "g")){
                mainOrbitor.setPower(0.5);
            }
            projectile = "g";
        }

        else if(setPurple && trackedBalls.colourInIntake("p")){
            if (!Objects.equals(trackedBalls.getColour(c_sensor2), "p")){
                mainOrbitor.setPower(0.5);
            }
            projectile = "p";
        }
    }

}
