package org.firstinspires.ftc.teamcode.functions;

import static org.firstinspires.ftc.teamcode.functions.intakeServo.intakeA;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class flywheel {
    public static DcMotorEx flywheel;


    public flywheel(HardwareMap hardwareMap) {
        flywheel = hardwareMap.get(DcMotorEx.class, "intakeA");

    }

    public static void setFlywheel(double power) {
        flywheel.setPower(power);

    }
    public static boolean isStopped() {
        return flywheel.getPower() == 0.0;
    }

}

