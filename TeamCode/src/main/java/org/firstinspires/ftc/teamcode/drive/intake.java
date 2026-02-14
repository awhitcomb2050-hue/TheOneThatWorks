package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class intake {
    private DcMotorEx intankeMotor;

    public intake(HardwareMap hardwareMap) {
        intankeMotor  = hardwareMap.get(DcMotorEx.class, "intankeMotor");
    }
    public void spin(double power) {
        intankeMotor.setPower(power);

    }

    public void stop() {
        spin(0);
    }
}
