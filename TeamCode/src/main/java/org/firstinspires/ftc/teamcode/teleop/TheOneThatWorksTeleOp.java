package org.firstinspires.ftc.teamcode.teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.auto.Constants;
import org.firstinspires.ftc.teamcode.functions.Mecanum;
import org.firstinspires.ftc.teamcode.functions.flywheel;
import org.firstinspires.ftc.teamcode.functions.intake;
import org.firstinspires.ftc.teamcode.functions.intakeServo;
//import org.firstinspires.ftc.teamcode.drive.deocde_functions.LimelightVision;

@TeleOp(name="TheOneThatWorksTeleOp")
public class TheOneThatWorksTeleOp extends LinearOpMode {




    @Override
    public void runOpMode() throws InterruptedException {
        Mecanum drive = new Mecanum(hardwareMap);
        intake setIntake = new intake(hardwareMap);
        follower = Constants.createFollower(hardwareMap);
        follower.setPose(new Pose(0, 0, 0)); // pedro 3 uses setPose()
        Limelight3A limelight = hardwareMap.get(Limelight3A.class, "limelight");


        waitForStart();
        while (opModeIsActive()) {



            follower.update();

            // Drive inputs
            double y = gamepad1.left_stick_y;
            double x = -gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            drive.drive(y, x, rx);


            Pose currentPose = follower.pose();
            telemetry.addData("X Position", currentPose.x());
            telemetry.addData("Y Position", currentPose.y());
            telemetry.addData("Heading", Math.toDegrees(currentPose.heading()));

            telemetry.update();



//            if (gamepad2.x) {
//                flywheel.setFlywheel(targetVolecity);
//            } else {
//                flywheel.setFlywheel(targetVolecity);
//            }

        }
    }
}
