package org.firstinspires.ftc.teamcode.functions;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

@Config
public class shootingMath {

    public static DcMotorEx flywheel1;
    public static DcMotorEx flywheel2;

    // Polynomial Coefficients for: Ax^2 + Bx + C
    public static double coeffA = -0.05;
    public static double coeffB = 12.5;
    public static double velocityInterceptC = 800.0;

    // Allowed tolerance window (encoder ticks/second) to verify the wheels are up to speed
    public static double velocityTolerance = 50.0;
    private double lastTargetVelocity = 0;


    public shootingMath(HardwareMap hardwareMap) {
        flywheel1 = hardwareMap.get(DcMotorEx.class, "flywheel1");
        flywheel2 = hardwareMap.get(DcMotorEx.class, "flywheel2");

        // NOTE: For built-in velocity control, you MUST use RUN_USING_ENCODER!
        flywheel1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flywheel1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        flywheel2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flywheel2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    public boolean isAtTargetVelocity() {
        double currentVelocity1 = flywheel1.getVelocity();
        double currentVelocity2 = flywheel2.getVelocity();
        double averageVelocity = (currentVelocity1 + currentVelocity2) / 2.0;

        return Math.abs(lastTargetVelocity - averageVelocity) <= velocityTolerance;
    }


    public double calculateTargetVelocity(double distance) {
        // target Velocity = Ax^2 + Bx + C
        double targetVelocity = (coeffA * Math.pow(distance, 2)) + (coeffB * distance) + velocityInterceptC;

        if (targetVelocity < velocityInterceptC) {
            targetVelocity = velocityInterceptC;
        }

        // Cache the target velocity globally so isAtTargetVelocity() can inspect it
        this.lastTargetVelocity = targetVelocity;

        return targetVelocity;
    }
}
