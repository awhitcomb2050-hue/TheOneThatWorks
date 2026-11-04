package org.firstinspires.ftc.teamcode.auto.autos;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import static org.firstinspires.ftc.teamcode.auto.autos.redAuto1.AutoPath.AutoStep.DRIVE_TO_SHOOT;
import static org.firstinspires.ftc.teamcode.auto.autos.redAuto1.AutoPath.AutoStep.LAUNCH;
import static org.firstinspires.ftc.teamcode.auto.autos.redAuto1.AutoPath.AutoStep.SPIN_UP;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.auto.Constants;

public class redAuto2 {package org.firstinspires.ftc.teamcode;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static schedule;
import static com.pedropathing.ivy.commands.Commands.*;
import static sequential;
import static follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

    @Autonomous(name = "redAuto2", group = "Autonomous")
    public class AutoPath extends LinearOpMode {

        private Follower follower;

        private final PoseFactory poseFactory = PoseFactory.degrees();

        private final Pose start = poseFactory.of(133.5011, 53.7741, 180);
        private final Pose path1 = poseFactory.of(85.0351, 123.7942, -90);
        private final Pose path1Control1 = poseFactory.of(125.8597, 102.1674, 0);
        private final Pose point2Start = poseFactory.of(85.0351, 123.7942, -90);
        private final Pose point2 = poseFactory.of(125.6536, 49.2133, -90);
        private final Pose point2Control1 = poseFactory.of(135.0285, 101.9931, 0);

        // Autonomous routine
        public Command autoRoutine() {
            return sequential(
                    follow(follower, path1()),
                    follow(follower, path2())
            );
        }

        @Override
        public void runOpMode() {
            Scheduler.reset();
            follower = Constants.create(hardwareMap);
            follower.setPose(start);
            follower.update();

            waitForStart();
            schedule(autoRoutine());

            while (opModeIsActive()) {
                follower.update();
                Scheduler.execute();

                telemetry.addData("x", follower.pose().x());
                telemetry.addData("y", follower.pose().y());
                telemetry.addData("heading", follower.pose().heading());

                if (follower.currentPath() != null) {
                    telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
                    telemetry.addData("Path number", follower.pathIndex());
                }
                Pose shootingSpot = new Pose(24, 12, Math.toRadians(180));
                Pose parkingSpot = new Pose(48, 12, Math.toRadians(90));

                // 2. Define the steps for your autonomous routine
                private enum AutoStep { DRIVE_TO_SHOOT, SPIN_UP, LAUNCH, PARK, DONE }
                private redAuto1.AutoPath.AutoStep currentStep = redAuto1.AutoPath.AutoStep.DRIVE_TO_SHOOT;

                @Override
                public void runOpMode() {
                    // ... Drivetrain and hardware initialization ...

                    waitForStart();
                    timer.reset();

                    while (opModeIsActive() && !isStopRequested()) {
                        // ALWAYS update the follower at the very top of your loop
                        follower.update();

                        switch (currentStep) {
                            case DRIVE_TO_SHOOT:
                                // Actively command the pose follower to go to the shooting spot
                                follower.setPose(shootingSpot);

                                // Check if the robot has arrived within your error thresholds
                                if (follower.setPose();) {
                                shootingSpot.spin();   // Start the motor
                                timer.reset();      // Start the pause timer
                                currentStep = SPIN_UP;
                            }
                            break;

                            case SPIN_UP:
                                // TIMING PAUSE: Keep feeding the same pose so the robot holds still
                                follower.setPose(shootingSpot);

                                // Wait 0.5 seconds for flywheels to reach full RPM
                                if (timer.seconds() >= 0.5) {
                                    shootingSpot.fire();     // Trigger the servo/actuator
                                    timer.reset();      // Start next pause timer
                                    currentStep = LAUNCH;
                                }
                                break;

                            case LAUNCH:
                                // TIMING PAUSE: Continue holding the position while the object exits
                                follower.setPose(shootingSpot);

                                // Wait 0.3 seconds for the mechanism to finish launching
                                if (timer.seconds() >= 0.3) {
                                    shooter.stop();     // Turn off shooter components
                                    currentStep = redAuto1.AutoPath.AutoStep.PARK; // Move to the next location
                                }
                                break;

                            case PARK:
                                // Provide the new target coordinates to drive away
                                follower.setPose(parkingSpot);

                                if (follower.parkingSpot()) {
                                    currentStep = redAuto1.AutoPath.AutoStep.DONE;
                                }
                                break;

                            case DONE:
                                // Stop the drivetrain entirely
                                follower.holdPoint(parkingSpot);
                                break;
                telemetry.update();
            }
        }

        public Path path1() {
            return Paths.curve(start, path1Control1, path1).linear(start, path1);
        }

        public Path path2() {
            return Paths.curve(point2Start, point2Control1, point2).linear(point2Start, point2);
        }
    }

}
