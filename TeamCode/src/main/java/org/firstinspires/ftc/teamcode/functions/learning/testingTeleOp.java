package org.firstinspires.ftc.teamcode.functions.learning;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="testingTeleOp")
public class testingTeleOp extends LinearOpMode {
    private shootingMath shooterControl;
    private double targetVelocity = 0.0001;
    private boolean lastDpadUp = false;
    private boolean lastDpadDown = false;


    @Override
    public void runOpMode() throws InterruptedException {
        Limelight3A limelight = hardwareMap.get(Limelight3A.class, "limelight");
        ApriltagZoe vision = new ApriltagZoe(limelight, telemetry);

        shooterControl = new shootingMath(hardwareMap);

        vision.init();

        waitForStart();

        while (opModeIsActive()) {

            vision.april23();
            telemetry.update();
            if (gamepad1.dpad_up /*&& !lastDpadUp*/) {
                targetVelocity += 100;
            }
            if (gamepad1.dpad_down  /* && !lastDpadDown*/) {
                targetVelocity -= 100;
                if (targetVelocity < 0) targetVelocity = 0; // stops reverse spinning
            }

            lastDpadUp = gamepad1.dpad_up;
            lastDpadDown = gamepad1.dpad_down;

            double calculatedPower = shooterControl.findFlywheelPower(targetVelocity);

            if (gamepad1.left_bumper) {
                shootingMath.flywheel.setPower(calculatedPower);
            } else {
                shootingMath.flywheel.setPower(0);
            }

            telemetry.addData("Target Velocity (Ticks/s)", targetVelocity);
            telemetry.addData("Actual Velocity (Ticks/s)", shootingMath.flywheel.getVelocity());
            telemetry.addData("Calculated Motor Power", calculatedPower);
            telemetry.update();
        }
    }
}

