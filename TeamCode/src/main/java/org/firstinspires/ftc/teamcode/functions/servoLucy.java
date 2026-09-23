package org.firstinspires.ftc.teamcode.functions;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class servoLucy {

    public static CRServo servoLucy;


    public servoLucy(HardwareMap hardwareMap) {
        servoLucy = hardwareMap.get(CRServo.class, "Servo");


        if (gamepad1.a) servoLucy.setDirection(180);
    }
}
