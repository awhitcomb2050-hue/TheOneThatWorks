package org.firstinspires.ftc.teamcode.functions;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class intake {
    private static DcMotorEx intakeA;


    public intake(HardwareMap hardwareMap) {
        intakeA = hardwareMap.get(DcMotorEx.class, "intakeA");

    }

    public static void setIntake(double power) {
        intakeA.setPower(-power);
    }
}
