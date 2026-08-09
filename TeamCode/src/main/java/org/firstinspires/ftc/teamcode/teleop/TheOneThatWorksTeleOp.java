package org.firstinspires.ftc.teamcode.teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.auto.Constants;
import org.firstinspires.ftc.teamcode.functions.Mecanum;
import org.firstinspires.ftc.teamcode.functions.intake;

@TeleOp(name="TheOneThatWorksTeleOp")
            public class TheOneThatWorksTeleOp extends LinearOpMode {

                private Follower follower;

                @Override
                public void runOpMode() throws InterruptedException {
                    Mecanum drive = new Mecanum(hardwareMap);
                    intake spin = new intake(hardwareMap);

                    // Initialize the Follower which handles X/Y localization
                    follower = Constants.createFollower(hardwareMap);
                    follower.setStartingPose(new Pose(0, 0, 0));

                    waitForStart();

                    // Start TeleOp drive tracking
                    follower.startTeleopDrive();

                    while (opModeIsActive()) {
                        // Update the position math
                        follower.update();

                        double y = gamepad1.left_stick_y;
                        double x = -gamepad1.left_stick_x;
                        double rx = gamepad1.right_stick_x;

                        drive.drive(y, x, rx);

                        // Get and display coordinates
                        Pose currentPose = follower.getPose();
                        telemetry.addData("X Position", currentPose.getX());
                        telemetry.addData("Y Position", currentPose.getY());
                        telemetry.addData("Heading", Math.toDegrees(currentPose.getHeading()));
                        telemetry.update();
                        if(gamepad2.a) {
                            spin.spin(.7);
                        }else{
                            spin.spin(0);

                        }
                    }
                }
            }