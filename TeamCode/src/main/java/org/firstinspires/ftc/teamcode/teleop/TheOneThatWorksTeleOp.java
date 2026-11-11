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
            telemetry.addLine("---- TUNING CONTROLS ---");
            telemetry.addData("D-pad Up/Down", "Adjusts Velocity Offset");
            telemetry.addData("distance", limelightVisionSystem.getDistance());
            telemetry.addData("Manual Speed Offset", "%.1f ticks/sec", velocityOffset);
            telemetry.addData("Current State", shooter.getCurrentState());

            telemetry.addLine("---- Shooter ---");
            telemetry.addData("Shooter State", shooter.getCurrentState());
            telemetry.addData("Target Velocity", "%.1f ticks/sec", targetVelocity);
            telemetry.addData("Current Velocity", "%.1f ticks/sec", shootingMath.flywheel1.getVelocity());
            telemetry.addData("State Timer", "%.1f seconds", shooter.stateTimer.seconds());
            telemetry.update();
            limelightVisionSystem.updateTelemetry();
            if (gamepad1.a) {
                shooter.startSequence();
            }

            // Drive inputs
            double y = gamepad1.left_stick_y;
            double x = -gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            drive.drive(y, x, rx);
            shooter.update(limelightVisionSystem.getDistance());

            telemetry.update();

            if (gamepad2.a) {
                shooter.startSequence();

            }
            if (gamepad2.dpad_up && !lastDpadUp) {
                velocityOffset += 25.0;
                if (gamepad2.dpad_down && !lastDpadDown) {
                    velocityOffset -= 25.0; // Decrease speed if shots are overshooting
                }

                lastDpadUp = gamepad2.dpad_up;
                lastDpadDown = gamepad2.dpad_down;


            telemetry.update();


                




            }
        }
    }
}
