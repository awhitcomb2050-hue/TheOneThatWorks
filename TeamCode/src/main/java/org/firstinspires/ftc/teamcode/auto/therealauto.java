package org.firstinspires.ftc.teamcode.auto;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

@Autonomous(name="therealauto")
public class therealauto extends LinearOpMode {


    @Override
    public void runOpMode() {
        MecanumDrive drive = new MecanumDrive(hardwareMap);
        telemetry.addLine("i want to die");
        telemetry.update();
        waitForStart();

            drive.drive(-1, 0, 0);
            sleep(200);
            drive.drive(0, 0, 0);



    }
}



