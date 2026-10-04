package org.firstinspires.ftc.teamcode.functions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class setPositions {
    private DcMotor testMotor;
    public setPositions(HardwareMap hardwareMap){
        testMotor =  hardwareMap.get(DcMotor.class,"testMotor");
        testMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        testMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        testMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }
    public void runMotor(int position,double speed){
     testMotor.setTargetPosition(position);
     testMotor.setMode((DcMotor.RunMode.RUN_TO_POSITION);
     testMotor.setPower(Math.abs(power));
    }
}

