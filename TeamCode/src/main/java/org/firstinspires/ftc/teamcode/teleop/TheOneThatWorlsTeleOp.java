package org.firstinspires.ftc.teamcode.teleop;

import static com.qualcomm.robotcore.hardware.HardwareDevice.Manufacturer.LimelightVision;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.LimelightVision;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.drive.flywheel;
import org.firstinspires.ftc.teamcode.drive.intake;
import org.firstinspires.ftc.teamcode.drive.lift;
import org.firstinspires.ftc.teamcode.drive.servo;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;


@TeleOp(name="TheOneThatWorlsTeleOp")
public class TheOneThatWorlsTeleOp extends LinearOpMode {
    public flywheel spinny;
    private servo purple;
    private boolean pdu;
    private boolean pdd;
    private boolean pdr;
    private boolean pdl;



    @Override
    public void runOpMode() {
        telemetry.addLine("AprilTag Vision Ready");
        telemetry.update();
        purple = new servo(hardwareMap);
        spinny = new flywheel(hardwareMap);
        lift lifty = new lift(hardwareMap);
        MecanumDrive drive = new MecanumDrive(hardwareMap);
        intake spin = new intake(hardwareMap);
        Limelight3A limelight = hardwareMap.get(Limelight3A.class, "limelight");

        LimelightVision vision = new LimelightVision(limelight, telemetry);
        vision.init();
        vision.pipe(0);


        waitForStart();
        ElapsedTime timer = new ElapsedTime();
        while (opModeIsActive()) {
            if(gamepad1.dpad_up && !pdu ){
                vision.kP *= 2;
            }
            pdu= gamepad1.dpad_up;

            if(gamepad1.dpad_down && !pdd){
                vision.kP /= 1.5;
            }
            pdd = gamepad1.dpad_down;

            if(gamepad1.dpad_right && !pdr ){
                vision.kD *= 2;
            }
            pdr= gamepad1.dpad_right ;

            if(gamepad1.dpad_left && !pdl){
                vision.kD /= 1.5;
            }
            pdl = gamepad1.dpad_left;



            double y = -gamepad1.left_stick_y; // forward positive
            double x = -gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x * .7  ;
            if(gamepad1.left_bumper) {

                double rxnew= vision.aim(timer.seconds());
                if(rxnew != 0.0   ){
                    rx = rxnew;
                }
            }
            drive.drive(y, x, rx);
            telemetry.addData("kp" , "%.6f deg",vision.kP);
            telemetry.addData("rx","%.6f deg", rx);
            telemetry.addData("kd","%.6f deg", vision.kD );



            if (gamepad2.a) {
//                double a = 0.0240922;
//                double b = 29.72284;
//                double c = -1456.12794;
//                double distance = vision.getDistance();
//                double tV = a * Math.pow(distance, 2) + b * distance + c;
//                spinny.spinny(tV);
//                double slope = 0.00089509;
//                double intercept = .615;
//                int power = (int) ((slope * vision.getCurrentDistance()) + intercept);
//                spinny.spinny(power);
////                spinny.spinny(.6);
                double distance = vision.getDistance();

                double power = 0.0034390 * distance + 0.467552;


                spinny.spinny(-power);
            }else{
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
            if (gamepad2.left_bumper) {
                purple.red(-1);
                purple.blue(1);
            } else if (gamepad2.right_bumper) {
                purple.red(1);
                purple.blue(-1);
            }else{
                purple.red(0);
                purple.blue(0);

            }
            if (gamepad2.dpad_down) {
                lifty.lift(-1);
            } else if (gamepad2.dpad_up) {
                lifty.lift(1);
            }else{
                lifty.lift(0);
            }


        }
    }

    public void drawRobotPacket (double x, double y, double heading) {
        TelemetryPacket packet = new TelemetryPacket();
        Canvas fieldOverlay= packet.fieldOverlay();
    }

}
