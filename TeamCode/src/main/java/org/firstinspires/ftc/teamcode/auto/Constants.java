package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.revhub.localizers.Encoder;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


import com.pedropathing.follower.Follower;
import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.TwoWheelConfig;
import com.pedropathing.revhub.localizers.TwoWheelLocalizer;

@Config
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
    });


    public static TwoWheelConfig localizerConfig = new TwoWheelConfig(c -> {
        c.yPodName.set("leftRear");
        c.xPodName.set("rightRear");
        c.imuName.set("imu");


        RevHubOrientationOnRobot.LogoFacingDirection up = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection backward = RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD;

        c.yPodDirection.set(Encoder.FORWARD);
        c.xPodDirection.set(Encoder.FORWARD);


        c.xPodOffset.set(0.0);
        c.yPodOffset.set(0.0);
        c.xPodOffset.set(0.0);
        c.yPodOffset.set(0.0);


        c.forwardTicksToInches.set(1.0);
        c.strafeTicksToInches.set(1.0);
    });


    public static ForesightConfig foresightConfig = new ForesightConfig(c -> {


        c.forwardTranslational.set(Controller.proportional(0.1));
        c.strafeTranslational.set(Controller.proportional(0.1));


        c.headingFeedback.set(Controller.proportional(0.1));


        c.maxVelocityConstraint.set(1.0);
    });


    public static Follower createFollower(HardwareMap hardwareMap) {
        TwoWheelLocalizer localizer = new TwoWheelLocalizer(hardwareMap, localizerConfig);

        Mecanum drivetrain = new Mecanum(hardwareMap, drivetrainConfig);

        Foresight foresight = new Foresight(foresightConfig);

        return new Follower(localizer, drivetrain, foresight);
    }
}
