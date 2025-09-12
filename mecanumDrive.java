package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.OI;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.bosch.BNO055IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class mecanumDrive {
    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightFront ;
    private DcMotor rightBack;
    private OI oi;
    BNO055IMU imu;
    double[] optimizedVal = new double[4];

    public mecanumDrive(OI oi){
        leftFront  = hardwareMap.get(DcMotor.class, "frontLeft"); // port 0
        rightFront = hardwareMap.get(DcMotor.class, "frontRight"); //port 1
        leftBack  = hardwareMap.get(DcMotor.class, "backLeft"); // port 2
        rightBack = hardwareMap.get(DcMotor.class, "backRight");

        leftFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);

        this.oi.oi;

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.imuOrientationOnRobot = new Orientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES, 0, 0, 0, 0);
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }

    //Bot oriented
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

    //Helper methods
    private double getYaw(){
        double currentYaw = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        return currentYaw;
    }

    private void optimizeValues(double power1,double power2,double power3, double power4){
        scale = Math.max(Math.max(power1, power2), Math.max(power3, power4));
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

    public void updateDrive(){
        switchOrientation(oi.getY(), oi.getLeftY(), oi.getLeftX(), oi.getRightX());
    }
}