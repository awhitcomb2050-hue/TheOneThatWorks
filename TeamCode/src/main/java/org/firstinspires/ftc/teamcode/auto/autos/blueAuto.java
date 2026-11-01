package org.firstinspires.ftc.teamcode.auto.autos;


import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.auto.Constants;

@Autonomous(name = "blueAuto", group = "Autonomous")
public class blueAuto extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(131.9067, 75.821, 180);
    private final Pose point1 = poseFactory.of(84.9, 109.4315, -90);
    private final Pose point1Control1 = poseFactory.of(112.7707, 106.2025, 0);
    private final Pose point2 = poseFactory.of(122.0253, 108.227, -90);
    private final Pose point3Start = poseFactory.of(122.0253, 108.227, -90);
    private final Pose point3 = poseFactory.of(127.0017, 44.6185, 180);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                follow(follower, point1()),
                follow(follower, point2()),
                follow(follower, path3())
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

    public Path point1() {
        return Paths.curve(start, point1Control1, point1).linear(start, point1);
    }

    public Path point2() {
        return Paths.line(point1, point2).constant(point2);
    }

    public Path path3() {
        return Paths.line(point3Start, point3).linear(point3Start, point3);
    }
}
