package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.functions.Mecanum;

@TeleOp(name="TheOneThatWorksTeleOp")
public class TheOneThatWorksTeleOp extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        Mecanum drive = new Mecanum(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // forward positive
            double x = -gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;
            drive.drive(y, x, rx);

        }
    }
}
