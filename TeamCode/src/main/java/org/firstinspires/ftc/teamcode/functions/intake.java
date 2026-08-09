package org.firstinspires.ftc.teamcode.functions;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class intake {
    private DcMotorEx intakeA;
    private DcMotorEx intakeB; // intake w/wheels

    public intake(HardwareMap hardwareMap) {
        intakeA = hardwareMap.get(DcMotorEx.class, "intakeA");
        intakeB = hardwareMap.get(DcMotorEx.class, "intakeB");
    }

    public void spin(double power) {
        intakeA.setPower(-power);
        intakeB.setPower(-power);
    }
}
