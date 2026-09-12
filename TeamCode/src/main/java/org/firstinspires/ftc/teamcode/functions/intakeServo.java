package org.firstinspires.ftc.teamcode.functions;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class intakeServo {

    public static CRServo intakeA;



    public intakeServo(HardwareMap hardwareMap){
        intakeA = hardwareMap.get(CRServo.class,"Servo");
    }

    public static void setIntakeA(double power) {

        intakeA.setPower(power);
    }
}
