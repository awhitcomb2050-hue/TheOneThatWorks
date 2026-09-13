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
import org.firstinspires.ftc.teamcode.drive.deocde_functions.LimelightVision;

@TeleOp(name="TheOneThatWorksTeleOp")
public class TheOneThatWorksTeleOp extends LinearOpMode {

    private Follower follower;


    @Override
    public void runOpMode() throws InterruptedException {
        Mecanum drive = new Mecanum(hardwareMap);
        intake setIntake = new intake(hardwareMap);
        flywheel setFlywheel = new flywheel(hardwareMap);
        intakeServo setIntakeServo = new intakeServo(hardwareMap);
        follower = Constants.createFollower(hardwareMap);
        follower.setPose(new Pose(0, 0, 0)); // pedro 3 uses setPose() instead of setStartingPose()
        Limelight3A limelight = hardwareMap.get(Limelight3A.class, "limelight");

        LimelightVision vision = new LimelightVision(limelight, telemetry);
        vision.init();
        vision.pipe(0);

        while (opModeIsActive()) {
// Inside your robot initialization or follower setup:


// Tell Pedro 3 to look at your FollowerParameters class for its tuning metrics
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
            telemetry.addData("kp" , "%.6f deg",vision.kP);
            telemetry.addData("rx","%.6f deg", rx);
            telemetry.addData("kd","%.6f deg", vision.kD );
            telemetry.update();


            if (gamepad2.a) {
                intake.setIntake(0.7);
            } else {
                intake.setIntake(0);
            }


            if (gamepad2.b) {
                intakeServo.setIntakeA(1);
            } else {
                intakeServo.setIntakeA(0);
            }


            if (gamepad2.x) {
                flywheel.setFlywheel(1);
            } else {
                flywheel.setFlywheel(0);
            }

        }
    }
}
