package org.firstinspires.ftc.teamcode.functions;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class servoLucy {

    public static CRServo servoLucy;


    public servoLucy(HardwareMap hardwareMap) {
        servoLucy = hardwareMap.get(CRServo.class, "Servo");


        if (gamepad1.a) servoLucy.setDirection(180);
        /* when writing in function files you often won't include the gamepad part, you'll see something similar to this
            public static void setIntake (-what your calling the task)(double power(this is the variable that you fill in when writing in the teleop)) {
            intakeA.setPower(-power);
        }
        make sure to look at some of the example files I have like intakeServo or in the sample data servo.java  */
    }
}
