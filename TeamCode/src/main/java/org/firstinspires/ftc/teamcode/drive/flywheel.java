package org.firstinspires.ftc.teamcode.drive;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class flywheel {

    private DcMotorEx shootyMotor1;
    private DcMotorEx shootyMotor2;
    private LimelightVision vision;
    private final double slope = 0.0083;
    private final double intercept = 0.3008;

    public flywheel(HardwareMap hardwareMap) {
        shootyMotor1 = hardwareMap.get(DcMotorEx.class, "shootyMotor1");
        shootyMotor2 = hardwareMap.get(DcMotorEx.class, "shootyMotor2");
        Limelight3A limelight = hardwareMap.get(Limelight3A.class, "limelight");
        vision = new LimelightVision(limelight, telemetry);


        shootyMotor1.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        shootyMotor2.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
    }

//        public void spinny(double distance) {
//        double power = (slope * vision.getCurrentDistance()) + intercept;
//
//
//        power = Math.max(0, Math.min(1, power));
//
//        shootyMotor1.setPower(power);
//        shootyMotor2.setPower(power);
//    }
    public void spinny(double power) {
        shootyMotor1.setPower(power);
        shootyMotor2.setPower(power);
    }


    public void stop() {
        spinny(0);
    }
}

