package org.firstinspires.ftc.teamcode.auto;

import static com.pedropathing.api.Paths.curve;
import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.waitUntil;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.waitMs;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.utils.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.functions.flywheel;

@Autonomous(name = "auto", group = "Autonomous")
public class auto extends LinearOpMode {

    private Follower follower;
    private Timer pathTimer;


    private final PoseFactory poseFactory = PoseFactory.degrees();




    private final Pose start = poseFactory.of(4.5, 31.3, 90);
    private final Pose path1 = poseFactory.of(18.2141, 58.5081, 0);
    private final Pose path1Control1 = poseFactory.of(59.7846, 57.9974, 0);
    private final Pose point2 = poseFactory.of(24.905, 58.2603, 0);
    private final Pose point3 = poseFactory.of(34.12, 10.7382, 0);



    public Command autoRoutine() {
        return sequential(
                follow(follower, path1()),
                instant(() -> flywheel.setFlywheel(1.0)),
                waitMs(5000),
                instant(() -> flywheel.setFlywheel(0.0)),
                waitUntil(() -> flywheel.isStopped()),
                //before moving on to path 2
                follow(follower, path2()),
                follow(follower, path3())

        );
    }

    @Override
    public void runOpMode() {
        Scheduler.reset();
        follower.setPose(start);
        follower.update();
         new flywheel(hardwareMap);

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
        return curve(start, path1Control1, path1).constant(path1);
    }

    public Path path2() {
        return line(path1, point2).constant(point2);
    }

    public Path path3() {
        return line(point2, point3).constant(point3);
    }
}
