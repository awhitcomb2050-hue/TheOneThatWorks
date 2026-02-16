package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class servo {
    private CRServo blue, red;

    public servo(HardwareMap hardwareMap){
        blue = hardwareMap.get(CRServo.class, "blue");
        red = hardwareMap.get(CRServo.class, "red");

    }
    public void red(double power ){
        red.setPower(power);
    }
    public void blue(double power ){
        blue.setPower(power);
    }
}
