package org.firstinspires.ftc.teamcode.teleop;

import static com.qualcomm.robotcore.hardware.HardwareDevice.Manufacturer.LimelightVision;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.LimelightVision;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.drive.flywheel;
import org.firstinspires.ftc.teamcode.drive.intake;
import org.firstinspires.ftc.teamcode.drive.servo;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;


@TeleOp(name="TheOneThatWorksTeleOp")
public class TheOneThatWorksTeleOp extends LinearOpMode {
    private flywheel spinny;
    private servo purple;
    private MecanumDrive drive;
    private intake spin;
    private LimelightVision vision;
    private Limelight3A limelight;

    @Override
    public void runOpMode() {
        telemetry.addLine("AprilTag Vision Ready");
        telemetry.update();
        purple = new servo(hardwareMap);
        spinny = new flywheel(hardwareMap);
        drive = new MecanumDrive(hardwareMap);
        spin = new intake(hardwareMap);
        Limelight3A limelight = hardwareMap.get(Limelight3A.class, "limelight");
        vision = new LimelightVision(limelight, telemetry);
        vision.init();

        waitForStart();


        waitForStart();
        while (opModeIsActive()) {

            double y = -gamepad1.left_stick_y; // forward positive
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;
            drive.drive(y, x, rx);

//             --- flywheel Control ---
//            if (gamepad2.a) {
////                double power = vision.getTargetPower();
////                spinny.spinny(power);
//            } else if (gamepad2.b) {
//                spinny.spinny(-1);
//            } else {
//                spinny.stop();
//            }
//            }
            if (gamepad2.a) {
            } else if (gamepad2.b) {
                spinny.spinny(-.75);
            }else{
                spinny.spinny(0);
            }

            // --- Intake Control ---
            if (gamepad2.x) {
                spin.spin(-1);
            } else if (gamepad2.y) {
                spin.spin(1);
            } else {
                spin.stop();
            }
            vision.updateTelemetry();
            telemetry.update();
            if (gamepad2.left_bumper) {
                purple.red(1);
                purple.blue(-1);
            } else if (gamepad2.right_bumper) {
                purple.red(-1);
                purple.blue(1);
            }else{
                purple.red(0);
                purple.blue(0);
            }

        }
    }
}
