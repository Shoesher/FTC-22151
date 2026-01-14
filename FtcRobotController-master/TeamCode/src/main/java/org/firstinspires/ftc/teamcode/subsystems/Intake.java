package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

import java.util.Objects;

public class Intake {
    private DcMotorEx mainIntake;
    private NormalizedColorSensor c_sensor1;
    private NormalizedColorSensor c_sensor2;
    private DigitalChannel b_sensor1;
    private OI oi;
    private Tracker trackedBalls;
    private Shooter shooter;

    //In class vars
    private String[] reference = {"p","p","g"};
    private double outputVel = -0.5;
    private double orbitalVel = 0.5;
    private int ticksPerRev = 1440;
    public String projectile;

    public Intake(){
        trackedBalls = new Tracker();
        shooter = new Shooter();

        //Motors
        mainIntake  = hardwareMap.get(DcMotorEx.class, "mainIntake"); // port 0

        //Sensors
        c_sensor1 = hardwareMap.get(NormalizedColorSensor.class, "c_sensor1");
        c_sensor2 = hardwareMap.get(NormalizedColorSensor.class, "c_sensor2");

        b_sensor1 = hardwareMap.get(DigitalChannel.class, "beamBreak");
        b_sensor1.setMode(DigitalChannel.Mode.INPUT);
    }

    public void runIntake(double vel, boolean expel){
        if(trackedBalls.getSize() > 3 && vel > 0.1){
                mainIntake.setPower(vel);
        }

        else if(expel){
            mainIntake.setPower(outputVel);
        }
    }

    public void trackIntakedObj(boolean beam, boolean isExpelling){
        if(!beam && !isExpelling){
            String ballColour = trackedBalls.getColour(c_sensor1); //Get the colour of the intaked ball
            trackedBalls.addBall(ballColour);
        }
        else if(!beam && isExpelling){
            trackedBalls.removeBallExpel();
        }
    }
}