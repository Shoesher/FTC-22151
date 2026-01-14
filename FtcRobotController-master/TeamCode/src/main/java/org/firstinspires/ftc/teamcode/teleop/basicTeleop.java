package org.firstinspires.ftc.teamcode.teleop;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.subsystems.Mecanum;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.OI;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

@TeleOp(name="shahriarCode", group="OpMode")

public class basicTeleop extends OpMode {
    OI oi = new OI(gamepad1, gamepad2);

    @Override
    public void init() {

    }

    @Override
    public void loop() {
        oi.periodicUpdate();
    }
}
