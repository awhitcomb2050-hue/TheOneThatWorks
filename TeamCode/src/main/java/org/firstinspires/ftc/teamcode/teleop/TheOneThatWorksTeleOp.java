package org.firstinspires.ftc.teamcode.teleop;

import static com.qualcomm.robotcore.hardware.HardwareDevice.Manufacturer.LimelightVision;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Flywheel;
import org.firstinspires.ftc.teamcode.drive.LimelightVision;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.drive.intake;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;


@TeleOp(name="TheOneThatWorksTeleOp")
public class TheOneThatWorksTeleOp extends LinearOpMode {


//    private Limelight3A limelight;
//    private AprilTagVision tagVision;

    private Flywheel spinny;
    private MecanumDrive drive;
    private intake spin;
    private LimelightVision vision;
    private Limelight3A limelight;

    @Override
    public void runOpMode() {
//        tagVision = new AprilTagVision();
//        tagVision.init(hardwareMap);


        telemetry.addLine("AprilTag Vision Ready");
        telemetry.update();

        spinny = new Flywheel(hardwareMap);
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

            // --- Flywheel Control ---
            if (gamepad2.a) {
                spinny.spin(1);
            } else if (gamepad2.b) {
                spinny.spin(-1);
            } else {
                spinny.stop();
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



        }
    }
}

