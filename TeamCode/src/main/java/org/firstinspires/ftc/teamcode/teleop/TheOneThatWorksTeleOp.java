package org.firstinspires.ftc.teamcode.teleop;


import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.functions.Mecanum;
import org.firstinspires.ftc.teamcode.functions.shooting;
import org.firstinspires.ftc.teamcode.functions.shootingMath;
import org.firstinspires.ftc.teamcode.functions.LimelightVision;

@TeleOp(name="TheOneThatWorksTeleOp")
public class TheOneThatWorksTeleOp extends LinearOpMode {
    private LimelightVision limelightVisionSystem;
    private shootingMath shooterControl;
    private double targetVelocity = 0.0001;
    private boolean lastDpadUp = false;
    private boolean lastDpadDown = false;
    private double velocityOffset = 0.0;


    double distanceInches = limelightVisionSystem.getDistance();

    @Override
    public void runOpMode() throws InterruptedException {
        Mecanum drive = new Mecanum(hardwareMap);
//        follower = Constants.createFollower(hardwareMap);
//        follower.setPose(new Pose(0, 0, 0)); // pedro 3 uses setPose()
        Limelight3A limelightVision = hardwareMap.get(Limelight3A.class, "limelight");
        limelightVisionSystem = new LimelightVision(limelightVision, telemetry);
        limelightVisionSystem.init();
        shootingMath shooterTuning = new shootingMath(hardwareMap);
        shooting shooter = new shooting(hardwareMap);


        waitForStart();

        while (opModeIsActive()) {
            telemetry.update();
            limelightVisionSystem.updateTelemetry();

            // Drive inputs
            double y = gamepad1.left_stick_y;
            double x = -gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            drive.drive(y, x, rx);


            telemetry.update();

            if (gamepad2.a) {
                shooter.startSequence();
            }
            if (gamepad1.dpad_up && !lastDpadUp) {
                velocityOffset += 25.0;
                if (gamepad1.dpad_down && !lastDpadDown) {
                    velocityOffset -= 25.0; // Decrease speed if shots are overshooting
                }

                lastDpadUp = gamepad1.dpad_up;
                lastDpadDown = gamepad1.dpad_down;

                // 4. Trigger Sequence (Press A to start the intake -> spool -> shoot automated chain)
                if (gamepad1.a) {
                    shooter.startSequence();
                }


                shooter.update(distanceInches);


                telemetry.addLine("=== TUNING CONTROLS ===");
                telemetry.addData("D-pad Up/Down", "Adjusts Velocity Offset");
                telemetry.addData("distance", limelightVisionSystem.getDistance());
                telemetry.addData("Manual Speed Offset", "%.1f ticks/sec", velocityOffset);
                telemetry.addData("Current State", shooter.getCurrentState());
                telemetry.update();

            }
        }
    }
}
