package org.firstinspires.ftc.teamcode.functions.learning; // Keep your original package name

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
@Config
public class shootingMath {
    public static DcMotorEx flywheel;

    public double kP = 0.0005;

    public double pError = 0;
    public double pTime = 0;

    public shootingMath(HardwareMap hardwareMap) {
        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        flywheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public double findFlywheelPower(double targetVelocity) {
        double currentVelocity = flywheel.getVelocity();

        double error = targetVelocity - currentVelocity;

        double currentTime = System.currentTimeMillis();
        double deltaTime = currentTime - pTime;

        if (deltaTime <= 0) {
            deltaTime = 0;
        }

        pError = error;
        pTime = currentTime;

        double pTerm = kP * error;

        double currentPower = flywheel.getPower();

        double outputPower = currentPower + pTerm;

        if (outputPower > 1.0) outputPower = 1.0;
        if (outputPower < -1.0) outputPower = -1.0;

        return outputPower;
    }
}
