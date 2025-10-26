package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Objects;

public class Shooter {
    private DcMotor mainShooter;
    private DcMotor mainIndexer;
    private DigitalChannel b_sensor2;
    private double indexVel = 0.5;
    private double altInterval = 0.5;
    private OI oi;
    private Intake intake;
    private Tracker tracker;

    //Constructor
    public Shooter(OI oi){
        mainShooter = hardwareMap.get(DcMotor.class, "mainShooter");
        mainIndexer = hardwareMap.get(DcMotor.class, "mainIndexer");
        intake = new Intake(oi);
        tracker = new Tracker();

        b_sensor2 = hardwareMap.get(DigitalChannel.class, "b_sensor2");
        b_sensor2.setMode(DigitalChannel.Mode.INPUT);
    }

    public void runShooter(double vel){
        if(vel > 0.1){
            mainShooter.setPower(vel);
        }
    }


    public void detectShot(boolean beam){
        if(!beam && (Objects.equals(intake.projectile, "g") || Objects.equals(intake.projectile, "p"))){
            tracker.removeBallShot(intake.projectile);
        }
    }

    public void updateShooterTeleop(){
        runShooter(oi.getRightTrigger());
        detectShot(b_sensor2.getState());
    }
}
