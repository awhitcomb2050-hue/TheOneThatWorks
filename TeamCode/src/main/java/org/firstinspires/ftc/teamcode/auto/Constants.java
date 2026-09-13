package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.RevHubIMU;
import com.pedropathing.revhub.localizers.TwoWheelConfig;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;

public class Constants {
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("leftFront");
        c.frontRightName.set("rightFront");
        c.backLeftName.set("leftRear");
        c.backRightName.set("rightRear");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    }); // so can use auto tuner this is somewhat like what you'll have after using it
    public static TwoWheelConfig localizerConfig; // need to use auto tuner for everything to input code


    public static Follower createFollower(HardwareMap hardwareMap) {
        return null;
    } // so errors are made in other files

    @Config // Allows FtcDashboard/Panels to see these values live
    public static class FollowerParameters {

        public static double drive_kP = 0.020504;
        public static double drive_kD = 0.0008;
        public static double drive_kI = 0.0;


        public static PIDCoefficients translationalPIDF = new PIDCoefficients(
                drive_kP,drive_kI,drive_kD);


    }
}