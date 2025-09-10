package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class MecanumDrive extends LinearOpMode {
    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightFront ;
    private DcMotor rightBack;

    private MecanumDrive(){
        leftFront  = hardwareMap.get(DcMotor.class, "frontLeft"); // port 0
        rightFront = hardwareMap.get(DcMotor.class, "frontRight"); //port 1
        leftBack  = hardwareMap.get(DcMotor.class, "backLeft"); // port 2
        rightBack = hardwareMap.get(DcMotor.class, "backRight");

        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
    }

    //Field oriented 
    

    //Bot oriented
    public void setPower(double y, double x, double theta){
        // Calculated values
        double leftFrontPower  = (x + y + theta);
        double rightFrontPower = (x - y - theta);
        double leftBackPower   = (x - y + theta);
        double rightBackPower  = (x + y - theta);

        // Send calculated power to wheels
        leftFront.setPower(leftFrontPower);
        rightFront.setPower(rightFrontPower);
        leftBack.setPower(leftBackPower);
        rightBack.setPower(rightBackPower);
    }

    //Field oriented


    public void switchOrientation(boolean fieldPOV, double y, double x, double theta){
        if(fieldPOV){
            setPower(x,y,theta);
        }
        else{
            
        }
    }
}