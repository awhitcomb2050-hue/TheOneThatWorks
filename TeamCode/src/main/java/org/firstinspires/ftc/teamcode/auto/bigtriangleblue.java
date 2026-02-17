package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Autonomous
public abstract class bigtriangleblue  extends LinearOpMode {
    private DcMotorEx leftFront, rightFront, leftRear, rightRear;
    private DcMotorEx shootyMotor1, shootyMotor2;
    private DcMotorEx intakeMotor;


    @Override
    public void runOpMode() throws InterruptedException {
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
        leftRear = hardwareMap.get(DcMotorEx.class, "leftRear");
        rightRear = hardwareMap.get(DcMotorEx.class, "rightRear");
        shootyMotor1 = hardwareMap.get(DcMotorEx.class, "shootyMotor1");
        shootyMotor2 = hardwareMap.get(DcMotorEx.class, "shootyMotor2");
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");




        waitForStart();
        if (opModeIsActive()) {
            leftFront.setPower(1);
            rightFront.setPower(1);
            leftRear.setPower(1);
            rightRear.setPower(1);
            sleep(1500);
            shootyMotor1.setPower(1);
            shootyMotor2.setPower(1);
            intakeMotor.setPower(1);
            sleep(1000);
            stop();


        }
    }
}
