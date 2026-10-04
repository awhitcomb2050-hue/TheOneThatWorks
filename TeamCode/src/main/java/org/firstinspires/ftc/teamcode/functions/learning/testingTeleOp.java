package org.firstinspires.ftc.teamcode.functions.learning;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="testingTeleOp")
public class testingTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Limelight3A limelight = hardwareMap.get(Limelight3A.class, "limelight");

        ApriltagZoe vision = new ApriltagZoe(limelight, telemetry);

        vision.init();

        waitForStart();

        while (opModeIsActive()) {

            vision.april23();
            telemetry.update();

        }
    }
}
