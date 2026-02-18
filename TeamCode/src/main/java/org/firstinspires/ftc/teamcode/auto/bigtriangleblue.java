package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Autonomous
public class bigtriangleblue  extends LinearOpMode {
    private DcMotorEx leftFront, rightFront, leftRear, rightRear;
    private DcMotorEx shootyMotor1, shootyMotor2;
    private DcMotorEx intankeMotor;


    @Override
    public void runOpMode() throws InterruptedException {
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
        leftRear = hardwareMap.get(DcMotorEx.class, "leftRear");
        rightRear = hardwareMap.get(DcMotorEx.class, "rightRear");
        shootyMotor1 = hardwareMap.get(DcMotorEx.class, "shootyMotor1");
        shootyMotor2 = hardwareMap.get(DcMotorEx.class, "shootyMotor2");
        intankeMotor = hardwareMap.get(DcMotorEx.class, "intankeMotor");
        leftFront.setDirection(DcMotorEx.Direction.REVERSE);
        leftRear.setDirection(DcMotorEx.Direction.REVERSE);
        rightFront.setDirection(DcMotorEx.Direction.FORWARD);
        rightRear.setDirection(DcMotorEx.Direction.FORWARD);




        waitForStart();
        if (opModeIsActive()) {
            leftFront.setPower(-1);
            rightFront.setPower(-1);
            leftRear.setPower(-1);
            rightRear.setPower(-1);
            shootyMotor1.setPower(1);
            shootyMotor2.setPower(1);
            sleep(80);
            intankeMotor.setPower(1);
            shootyMotor1.setPower(1);
            shootyMotor2.setPower(1);

            sleep(1000);
            stop();


        }
    }
}
