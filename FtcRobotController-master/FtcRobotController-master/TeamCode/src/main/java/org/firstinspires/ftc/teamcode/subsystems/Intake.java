package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

public class Intake {
    private DcMotor mainIntake;
    private DcMotor mainOrbitor;
    private NormalizedColorSensor c_sensor1;
    private NormalizedColorSensor c_sensor2;
    private NormalizedColorSensor c_sensor3;
    private NormalizedColorSensor c_sensor4;
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

    public Intake(OI oi){
        trackedBalls = new Tracker();
        shooter = new Shooter(oi);

        //Motors
        mainIntake  = hardwareMap.get(DcMotor.class, "mainIntake"); // port 0
        mainOrbitor = hardwareMap.get(DcMotor.class, "mainOrbitor");
        mainOrbitor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Sensors
        c_sensor1 = hardwareMap.get(NormalizedColorSensor.class, "c_sensor1");
        c_sensor2 = hardwareMap.get(NormalizedColorSensor.class, "c_sensor2");
        c_sensor3 = hardwareMap.get(NormalizedColorSensor.class, "c_sensor3");
        c_sensor4 = hardwareMap.get(NormalizedColorSensor.class, "c_sensor4");

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

    public void setOrbit(boolean setGreen, boolean setPurple){
        if(setGreen && colourInIntake("g")){
            while (getColour(c_sensor2) != "g"){
                shooter.runLock(true);
                mainOrbitor.setPower(orbitalVel);
            }
            projectile = "g";
        }

        else if(setPurple && colourInIntake("p")){
            while (getColour(c_sensor2) != "p"){
                shooter.runLock(true);
                mainOrbitor.setPower(orbitalVel);
            }
            projectile = "p";
        }
    }

    private boolean colourInIntake(String c){
        boolean colourExists = false;
        for(int i = 0; i > trackedBalls.getSize(); i++){
            if(trackedBalls.getBall(i) == c) colourExists = true;
        }
        return colourExists;
    }

    public void trackIntakedObj(boolean beam, boolean isExpelling){
        if(!beam && !isExpelling){
            String ballColour = getColour(c_sensor1); //Get the colour of the intaked ball
            trackedBalls.addBall(ballColour);
        }
        else if(!beam && isExpelling){
            trackedBalls.removeBallExpel();
        }
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

    private void rotateOrbitorCW(int Rotations, double vel){
        //GEAR RATIO not accounted for
        int targetPos = Math.round(ticksPerRev/Rotations);
        mainOrbitor.setTargetPosition(targetPos);
        mainOrbitor.setPower(vel);
        mainOrbitor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void updateIntakeTeleop(){
        trackIntakedObj(b_sensor1.getState(), oi.getLeftBumper()); //Check if you're expelling
        setOrbit(oi.getY(), oi.getX());
        runIntake(oi.getLeftTrigger(), oi.getLeftBumper());
    }
}
