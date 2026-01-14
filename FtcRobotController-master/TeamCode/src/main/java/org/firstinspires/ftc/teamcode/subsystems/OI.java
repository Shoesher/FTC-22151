package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.opMode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

public class OI {
    //Initialize
    Mecanum drivetrain = new Mecanum();
    Shooter shooter = new Shooter();
    Intake intake = new Intake();
    Spindexer orbitor = new Spindexer();
    Gamepad controller1;
    Gamepad controller2;
    public OI(Gamepad pad1, Gamepad pad2){
        this.controller1 = pad1;
        this.controller2 = pad2;
    }
    //Main loop
    public void updateDrive(){
        drivetrain.botDrive(controller1.left_stick_y, controller1.left_stick_x, controller1.right_stick_x);
    }

    public void updateShooter(){
        shooter.runShooter(controller1.right_trigger);
    }

    public void updateSpindexer(){
        //You only want to run this once per button press
        orbitor.setOrbit(controller1.xWasPressed(), controller1.yWasPressed());
    }

    public void updateIntake(){
        intake.runIntake(controller1.left_trigger, controller1.left_bumper);
    }

    public void periodicUpdate(){
        updateDrive();
        updateShooter();
        updateSpindexer();
        updateIntake();
    }
}
