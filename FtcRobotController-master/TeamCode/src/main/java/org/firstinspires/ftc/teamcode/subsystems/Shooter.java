package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

public class Shooter {
    private DcMotor mainShooter;
    private DcMotor mainIndexer;
    private Servo lock;
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
        lock = hardwareMap.get(Servo.class, "lock");
        b_sensor2 = hardwareMap.get(DigitalChannel.class, "b_sensor2");
        intake = new Intake(oi);
        tracker = new Tracker();
    }

    public void runShooter(double vel){
        if(vel > 0.1){
            mainShooter.setPower(vel);
            mainIndexer.setPower(-vel);
        }
    }

    private void runIndexer(boolean doIndex){
        if(doIndex){
            mainIndexer.setPower(-indexVel);
        }
    }

    public void runLock(boolean unlockShooter){
        //Pos 0-1 = Angles Deg 0-180
        if(unlockShooter){
            lock.setPosition(altInterval);
        }

        lock.setPosition(-altInterval);
    }

    public void detectShot(boolean beam){
        if(!beam && (intake.projectile == "g" || intake.projectile == "p")){
            tracker.removeBallShot(intake.projectile);
        }
    }

    public void updateShooterTeleop(){
        runShooter(oi.getRightTrigger());
        runIndexer(oi.getRightBumper());
        runLock(oi.getA());
    }
}
