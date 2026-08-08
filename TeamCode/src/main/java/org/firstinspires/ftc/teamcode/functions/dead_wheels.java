package org.firstinspires.ftc.teamcode.functions;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class dead_wheels {
    private DcMotorEx forwardEncoder;
    private DcMotorEx strafeEncoder;

    public dead_wheels(HardwareMap hardwareMap) {
        forwardEncoder = hardwareMap.get(DcMotorEx.class, "forward");
        strafeEncoder = hardwareMap.get(DcMotorEx.class, "strafe");

        forwardEncoder.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        strafeEncoder.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }

}
