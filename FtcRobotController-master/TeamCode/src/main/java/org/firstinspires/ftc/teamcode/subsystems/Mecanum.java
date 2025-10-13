package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;


public class Mecanum {
    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightFront ;
    private DcMotor rightBack;
    private OI oi;
    double[] optimizedVal = new double[4];
    IMU imu;
    IMU.Parameters parameters;

    //Constructor
    public Mecanum(OI oi){
        leftFront  = hardwareMap.get(DcMotor.class, "frontLeft"); // port 0
        rightFront = hardwareMap.get(DcMotor.class, "frontRight"); //port 1
        leftBack  = hardwareMap.get(DcMotor.class, "backLeft"); // port 2
        rightBack = hardwareMap.get(DcMotor.class, "backRight");

        leftFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);

        imu = hardwareMap.get(IMU.class, "imu");

        parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
        ));

        imu.initialize(parameters);
    }

    public void botDrive(double y, double x, double theta){
        // Calculated values
        double leftFrontPower  = (x + y + theta);
        double rightFrontPower = (x - y - theta);
        double leftBackPower   = (x - y + theta);
        double rightBackPower  = (x + y - theta);

        //Optimize the values
        optimizeValues(leftFrontPower, rightFrontPower, leftBackPower, rightBackPower);

        //Apply powers to motors
        leftFront.setPower(optimizedVal[0]);
        rightFront.setPower(optimizedVal[1]);
        leftBack.setPower(optimizedVal[2]);
        rightBack.setPower(optimizedVal[3]);
    }

    //Field oriented
    public void fieldDrive(double y, double x, double theta, double yaw){
        //Transform vectors for field orientation
        double fieldX = x*Math.cos(yaw) + y*Math.sin(yaw);
        double fieldY = -x*Math.cos(yaw) + y*Math.sin(yaw);

        //Calculated values
        double leftFrontPower  = (fieldX + fieldY + theta);
        double rightFrontPower = (fieldX - fieldY - theta);
        double leftBackPower   = (fieldX - fieldY + theta);
        double rightBackPower  = (fieldX + fieldY - theta);

        //Optimize the values
        optimizeValues(leftFrontPower, rightFrontPower, leftBackPower, rightBackPower);

        //Apply power to motors
        leftFront.setPower(optimizedVal[0]);
        rightFront.setPower(optimizedVal[1]);
        leftBack.setPower(optimizedVal[2]);
        rightBack.setPower(optimizedVal[3]);
    }

    private double getYaw(){
        return imu.getRobotYawPitchRollAngles().getYaw();
    }

    private void optimizeValues(double power1,double power2,double power3, double power4){
        double scale = Math.max(Math.max(power1, power2), Math.max(power3, power4));
        optimizedVal[0] = power1 /= scale;
        optimizedVal[1] = power2 /= scale;
        optimizedVal[2] = power3/= scale;
        optimizedVal[3] = power4 /= scale;
    }

    public void switchOrientation(boolean fieldPOV, double y, double x, double theta){
        if(!fieldPOV){
            botDrive(y,x,theta);
        }
        else{
            fieldDrive(y,x,theta, getYaw());
        }
    }

    public void updateDriveTeleop(){
        switchOrientation(oi.getDpadLeft(), oi.getLeftY(), oi.getLeftX(), oi.getRightX());
    }
}