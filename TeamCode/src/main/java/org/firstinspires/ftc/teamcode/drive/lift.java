package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class lift {
    public DcMotorEx lifty;
    public lift(HardwareMap hardwareMap) {
        lifty = hardwareMap.get(DcMotorEx.class, "lifty");
    }
    public void lift(double power) {
        lifty.setPower(power);
    }
}
