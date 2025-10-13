package org.firstinspires.ftc.teamcode.teleop;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.subsystems.Mecanum;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.OI;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

@TeleOp(name="basicTeleop", group="Linear OpMode")

public class basicTeleop extends LinearOpMode {
    OI oi = new OI();
    Mecanum drive = new Mecanum(oi);
    Shooter shooter = new Shooter(oi);
    Intake intake = new Intake(oi);

    @Override
    public void runOpMode() {
        waitForStart();

        while (opModeIsActive()) {
            drive.updateDriveTeleop();
            shooter.updateShooterTeleop();
            intake.updateIntakeTeleop();
        }
    }
}
