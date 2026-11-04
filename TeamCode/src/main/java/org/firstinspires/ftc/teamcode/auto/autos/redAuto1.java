package org.firstinspires.ftc.teamcode.auto.autos;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.auto.Constants;

public class redAuto1 {

    @Autonomous(name = "redAuto1", group = "Autonomous")
    public class AutoPath extends LinearOpMode {

        private Follower follower;

        private final PoseFactory poseFactory = PoseFactory.degrees();

        private final Pose start = poseFactory.of(141.5, 111.9123, 180);
        private final Pose point1 = poseFactory.of(85.8283, 111.5761, 180);
        private final Pose point2 = poseFactory.of(129.4558, 51.9715, -92.5813);
        private final Pose point2Control1 = poseFactory.of(131.2624, 92.0421, 0);

        // 1. Define your target locations as Poses
        Pose shootingSpot = new Pose(24, 12, Math.toRadians(180));
        Pose parkingSpot = new Pose(48, 12, Math.toRadians(90));

        // 2. Define the steps for your autonomous routine
        private enum AutoStep { DRIVE_TO_SHOOT, SPIN_UP, LAUNCH, PARK, DONE }
        private AutoStep currentStep = AutoStep.DRIVE_TO_SHOOT;

        private ElapsedTime timer = new ElapsedTime();

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
                            currentStep = AutoStep.SPIN_UP;
                        }
                        break;

                    case SPIN_UP:
                        // TIMING PAUSE: Keep feeding the same pose so the robot holds still
                        follower.setPose(shootingSpot);

                        // Wait 0.5 seconds for flywheels to reach full RPM
                        if (timer.seconds() >= 0.5) {
                            shootingSpot.fire();     // Trigger the servo/actuator
                            timer.reset();      // Start next pause timer
                            currentStep = AutoStep.LAUNCH;
                        }
                        break;

                    case LAUNCH:
                        // TIMING PAUSE: Continue holding the position while the object exits
                        follower.setPose(shootingSpot);

                        // Wait 0.3 seconds for the mechanism to finish launching
                        if (timer.seconds() >= 0.3) {
                            shooter.stop();     // Turn off shooter components
                            currentStep = AutoStep.PARK; // Move to the next location
                        }
                        break;

                    case PARK:
                        // Provide the new target coordinates to drive away
                        follower.setPose(parkingSpot);

                        if (follower.atTargetPose()) {
                            currentStep = AutoStep.DONE;
                        }
                        break;

                    case DONE:
                        // Stop the drivetrain entirely
                        follower.holdPoint(parkingSpot);
                        break;
                }
            }
        }

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

                telemetry.update();
            }
        }

        public Path path1() {
            return Paths.line(start, point1).linear(start, point1);
        }

        public Path path2() {
            return Paths.curve(point1, point2Control1, point2).tangent();
        }
    }

}
