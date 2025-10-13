package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

public class OI {
    //Buttons
    public boolean getY(){
        return gamepad1.y;
    }

    public boolean getX(){
        return gamepad1.x;
    }
    public boolean getA() { return gamepad1.a; }

    public boolean getB() { return gamepad1.b; }

    public boolean getDpadUp(){
        return gamepad1.dpad_up;
    }

    public boolean getDpadDown(){
        return gamepad1.dpad_down;
    }

    public boolean getDpadLeft(){
        return gamepad1.dpad_left;
    }

    public boolean getRightBumper(){
        return gamepad1.right_bumper;
    }

    public boolean getLeftBumper(){
        return gamepad1.left_bumper;
    }

    //Analog inputs
    public double getLeftY(){
        return -gamepad1.left_stick_y;
    }

    public double getLeftX(){
        return gamepad1.left_stick_x;
    }

    public double getRightX(){
        return gamepad1.right_stick_x;
    }

    public double getRightTrigger(){
        return gamepad1.right_trigger;
    }
    public double getLeftTrigger(){
        return gamepad1.left_trigger;
    }
}
