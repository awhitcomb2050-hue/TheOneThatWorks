package org.firstinspires.ftc.teamcode.auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.telemetry.SelectableOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.drive.flywheel;
import org.firstinspires.ftc.teamcode.i_want_to_die.Constants;

@Autonomous(name = "Main Autonomous Selector", group = "Autonomous")
public class MainAuto extends SelectableOpMode {

    private static Follower follower;
    // Set different coordinates so the robot actually has somewhere to go
    private static final Pose startPose = new Pose(8, 0, 0);
    private static final Pose endPose  = new Pose(16, 0, 0);
    private static final Pose park = new Pose(16, 0, 0);

    private static Path score;
    private static Path parkPath;
    private static flywheel flywheel;
    private static ElapsedTime timer = new ElapsedTime();
    private static int pathState = 0;

    public MainAuto() {
        super("Select Autonomous Routine", s -> {
            s.add("Big Triangle Blue", BigTriangleBlue::new);
        });
    }

    static class BigTriangleBlue extends OpMode {

        @Override
        public void init() {
            pathState = 0;
            follower = Constants.createFollower(hardwareMap);
            flywheel = new flywheel(hardwareMap);
            follower.setStartingPose(startPose);

            score = new Path(new BezierLine(startPose, endPose));
            parkPath = new Path(new BezierLine(endPose, park));
        }

        @Override
        public void loop() {
            follower.update();

            switch (pathState) {
                case 0: // START MOVING
                    follower.followPath(score);
                    pathState = 1;
                    break;

                case 1: // Wait until robot reaches score position
                    if (!follower.isBusy()) {
                        timer.reset();
                        pathState = 2;
                    }
                    break;

                case 2: // Turn on shooty motors for 2 seconds
                    flywheel.spinny(1.0);
                    if (timer.seconds() > 2.0) {
                        flywheel.stop();
                        pathState = 3;
                    }
                    break;

                case 3: // Park
                    follower.followPath(parkPath);
                    pathState = 4;
                    break;

                case 4: // Wait until parked
                    if (!follower.isBusy()) {
                        pathState = 5;
                    }
                    break;
            }

            telemetry.addData("Path State", pathState);
            telemetry.addData("Follower Busy", follower.isBusy());
            telemetry.addData("X", follower.getPose().getX());
            telemetry.addData("Y", follower.getPose().getY());
            telemetry.addData("Heading", follower.getPose().getHeading());
            telemetry.update();
        }
    }
}
