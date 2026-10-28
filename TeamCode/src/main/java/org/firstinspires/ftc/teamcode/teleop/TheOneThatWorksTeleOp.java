package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.functions.Mecanum;
import org.firstinspires.ftc.teamcode.functions.learning.shootingMath;
//import org.firstinspires.ftc.teamcode.drive.deocde_functions.LimelightVision;

@TeleOp(name="TheOneThatWorksTeleOp")
public class TheOneThatWorksTeleOp extends LinearOpMode {
    private shootingMath shooterControl;
    private double targetVelocity = 0.0001;
    private boolean lastDpadUp = false;
    private boolean lastDpadDown = false;



    @Override
    public void runOpMode() throws InterruptedException {
        Mecanum drive = new Mecanum(hardwareMap);
//        follower = Constants.createFollower(hardwareMap);
//        follower.setPose(new Pose(0, 0, 0)); // pedro 3 uses setPose()
        Limelight3A limelight = hardwareMap.get(Limelight3A.class, "limelight");


        waitForStart();
        while (opModeIsActive()) {
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


            //follower.update();

            // Drive inputs
            double y = gamepad1.left_stick_y;
            double x = -gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            drive.drive(y, x, rx);


//            Pose currentPose = follower.pose();
//            telemetry.addData("X Position", currentPose.x());
//            telemetry.addData("Y Position", currentPose.y());
//            telemetry.addData("Heading", Math.toDegrees(currentPose.heading()));

            telemetry.update();



//            if (gamepad2.x) {
//                flywheel.setFlywheel(targetVolecity);
//            } else {
//                flywheel.setFlywheel(targetVolecity);
//            }

        }
    }
}
