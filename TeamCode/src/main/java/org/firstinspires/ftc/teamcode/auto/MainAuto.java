package org.firstinspires.ftc.teamcode.auto;

import static org.firstinspires.ftc.teamcode.auto.MainAuto.redbig.pathState.end;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.telemetry.SelectableOpMode;
import com.pedropathing.util.Timer;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.LimelightVision;
import org.firstinspires.ftc.teamcode.drive.flywheel;
import org.firstinspires.ftc.teamcode.drive.intake;
import org.firstinspires.ftc.teamcode.drive.servo;
import org.firstinspires.ftc.teamcode.i_want_to_die.Constants;
import org.firstinspires.ftc.teamcode.i_want_to_die.Tuning;

@Autonomous(name = "Main Autonomous Selector", group = "Autonomous")
public class MainAuto extends SelectableOpMode {


    public MainAuto() {
        super("Select Autonomous Routine", s -> {
            s.add("Big Triangle Blue", bluebig::new);
            s.add("Small Triangle Blue", bluesmall::new);
            s.add("Big Triangle Red", redbig::new);
            s.add("Small Triangle Red", redsmall::new);
        });
    }


    @Autonomous
    public static class redsmall extends OpMode {
        private Follower follwer;
        private Timer pathTimer, opModeTimer;
        public flywheel spinny;

        public intake spin;
        public LimelightVision vision;

        public enum pathState {
            // start to end
            //drive moment
            // shoot
            drivetoshoot,
            shootpreload,
            germ,
            inhale,
            shootagain,
            end;


        }

        static pathState pathState;
        private final Pose startPose = new Pose(95.200, 7.311, Math.toRadians(90));
        private final Pose shootPose = new Pose(84.324, 13.906, Math.toRadians(63));
        private final Pose getmore = new Pose(105.190, 58.17, Math.toRadians(180));
        private final Pose shoot2 = new Pose(4.478, 55.885, Math.toRadians(90));
        private final Pose parkPose = new Pose(98.18357878868662, 72.4006887541896, Math.toRadians(90));


        private PathChain drive, get, shoottwo, park;


        public void buildPaths() {
            drive = Tuning.follower.pathBuilder()
                    .addPath(new BezierLine(startPose, shootPose))
                    .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                    .build();
            get = Tuning.follower.pathBuilder()
                    .addPath(new BezierLine(shootPose, getmore))
                    .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                    .build();
            shoottwo = Tuning.follower.pathBuilder()
                    .addPath(new BezierLine(getmore, shoot2))
                    .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                    .build();
            park = Tuning.follower.pathBuilder()
                    .addPath(new BezierLine(shoot2, parkPose))
                    .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                    .build();


        }

        @Override
        public void init() {
            pathState = pathState.drivetoshoot;
            pathTimer = new Timer();
            opModeTimer = new Timer();
            spinny = new flywheel(hardwareMap);
            spin = new intake(hardwareMap);
            Limelight3A limelight = hardwareMap.get(Limelight3A.class, "limelight");
            Tuning.follower = Constants.createFollower(hardwareMap);
            buildPaths();
            Tuning.follower.setPose(startPose);
        }

        public void setPathState(pathState newState) {
            pathState = newState;
            pathTimer.resetTimer();
        }

        public void statePathUpdate() {
            switch (pathState) {
                case drivetoshoot:
                    Tuning.follower.followPath(drive, true); // goes from start to shoot
                    setPathState(pathState.shootpreload);
                    break;
                case shootpreload:
                    if (Tuning.follower.isBusy()) {
                        pathTimer.resetTimer();
                        break;
                    }
                    double distance = vision.getDistance();
                    double power = (0.0034390 * distance) + 0.467552;
                    spinny.spinny(power);

                    //after 6 and a hald seconds spin up
                    if (pathTimer.getElapsedTimeSeconds() > 6.5) {
                        spin.spin(1);
                    }


                    // after spin shot stop than move on
                    if (pathTimer.getElapsedTimeSeconds() > 7.5) {
                        spin.spin(0);
                        spinny.spinny(0);
                        setPathState(pathState.germ);
                    }
                    break;
                case germ:
                    if (!Tuning.follower.isBusy()) {
                        Tuning.follower.followPath(get, true); // shoot to get more
                        setPathState(pathState.inhale);

                    }
                    break;
                case inhale:
                    if (!Tuning.follower.isBusy()) {
                        servo.red(1);
                        servo.blue(1);
                    }
                    if (pathTimer.getElapsedTimeSeconds() > .6) {
                        setPathState(redsmall.pathState.shootagain);
                    }
                    break;
                case shootagain:
                    if (!Tuning.follower.isBusy()) {
                        Tuning.follower.followPath(shoottwo, true); // shoot to get more
                        setPathState(pathState.end);

                    }
                    break;
                case end:
                    if (!Tuning.follower.isBusy()) {
                        Tuning.follower.followPath(park, true); // shootagain to park
                        setPathState(pathState.end);

                    }
            }


        }

        public void start() {
            opModeTimer.resetTimer();
            setPathState(pathState);


        }

        @Override
        public void loop() {
            statePathUpdate();
            Tuning.follower.update();
            telemetry.addData("Path State", pathState.toString());
            telemetry.addData("x", Tuning.follower.getPose().getX());
            telemetry.addData("y", Tuning.follower.getPose().getY());
            telemetry.addData("heading", Tuning.follower.getPose().getHeading());
            telemetry.addData("Path state", pathState.toString());
            telemetry.update();


        }

    }


    @Autonomous
    static class bluesmall extends OpMode {
        private Follower follwer;
        private Timer pathTimer, opModeTimer;
        public flywheel spinny;
        public servo purple;
        public intake spin;
        public LimelightVision vision;

        public enum pathState {

            drivetoshoot,

            shootpreload,
            germ,
            inhale,
            shootagain,
            end;


        }

        static pathState pathState;
        private final Pose startPose = new Pose(52.101, 9.625, Math.toRadians(90));
        private final Pose shootPose = new Pose(63.614, 16.644, Math.toRadians(117));
        private final Pose getmore = new Pose(37.531, 58.637, Math.toRadians(0));
        private final Pose shoot2 = new Pose(63.627, 16.209, Math.toRadians(117));
        private final Pose parkPose = new Pose(40.715, 69.009, Math.toRadians(117));

        private PathChain drive, get, shoottwo, park;

        public void buildPaths() {
            //drive then shoot
            drive = Tuning.follower.pathBuilder()
                    .addPath(new BezierLine(startPose, shootPose))
                    .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                    .build();
            get = Tuning.follower.pathBuilder()
                    .addPath(new BezierLine(shootPose, getmore))
                    .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                    .build();
            shoottwo = Tuning.follower.pathBuilder()
                    .addPath(new BezierLine(getmore, shoot2))
                    .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                    .build();
            park = Tuning.follower.pathBuilder()
                    .addPath(new BezierLine(shoot2, parkPose))
                    .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                    .build();

        }

        public void statePathUpdate() {
            switch (pathState) {
                case drivetoshoot:
                    Tuning.follower.followPath(drive, true); // goes from start to shoot
                    setPathState(pathState.shootpreload);
                    break;
                case shootpreload:
                    if (Tuning.follower.isBusy()) {
                        pathTimer.resetTimer();
                        break;
                    }
                    double distance = vision.getDistance();
                    double power = (0.0034390 * distance) + 0.467552;
                    spinny.spinny(power);

                    //after 6 and a hald seconds spin up
                    if (pathTimer.getElapsedTimeSeconds() > 6.5) {
                        spin.spin(1);
                    }


                    // after spin shot stop than move on
                    if (pathTimer.getElapsedTimeSeconds() > 7.5) {
                        spin.spin(0);
                        spinny.spinny(0);
                        setPathState(pathState.germ);
                    }
                    break;
                case germ:
                    if (!Tuning.follower.isBusy()) {
                        Tuning.follower.followPath(get, true); // shoot to get more
                        setPathState(pathState.inhale);

                    }
                    break;
                case inhale:
                    if (!Tuning.follower.isBusy()) {
                        servo.red(1);
                        servo.blue(1);
                    }
                    if (pathTimer.getElapsedTimeSeconds() > .6) {
                        setPathState(bluesmall.pathState.shootagain);
                    }
                    break;
                case shootagain:
                    if (!Tuning.follower.isBusy()) {
                        Tuning.follower.followPath(shoottwo, true); // shoot to get more
                        setPathState(pathState.end);

                    }
                    break;
                case end:
                    if (!Tuning.follower.isBusy()) {
                        Tuning.follower.followPath(park, true); // shootagain to park
                        setPathState(pathState.end);

                    }


            }
        }

        public void setPathState(pathState newState) {
            pathState = newState;
            pathTimer.resetTimer();
        }

        @Override
        public void init() {
            pathState = pathState.drivetoshoot;
            pathTimer = new Timer();
            opModeTimer = new Timer();
            spinny = new flywheel(hardwareMap);
            spin = new intake(hardwareMap);
            Limelight3A limelight = hardwareMap.get(Limelight3A.class, "limelight");
            Tuning.follower = Constants.createFollower(hardwareMap);
            buildPaths();
            Tuning.follower.setPose(startPose);
        }

        public void start() {
            opModeTimer.resetTimer();
            setPathState(pathState);


        }

        @Override
        public void loop() {
            statePathUpdate();
            Tuning.follower.update();
            telemetry.addData("Path State", pathState.toString());
            telemetry.addData("x", Tuning.follower.getPose().getX());
            telemetry.addData("y", Tuning.follower.getPose().getY());
            telemetry.addData("heading", Tuning.follower.getPose().getHeading());
            telemetry.addData("Path state", pathState.toString());
            telemetry.update();


        }
    }

    @Autonomous
    static class bluebig extends OpMode {
        private Follower follwer;
        private Timer pathTimer, opModeTimer;
        public flywheel spinny;

        public intake spin;
        public LimelightVision vision;

        public enum pathState {
            // start to end
            //drive moment
            // shoot
            drivetoshoot,

            shootpreload,
            germ,
            inhale,
            shootagain,
            end;

        }

        static pathState pathState;
        private final Pose startPose = new Pose(28.375430539609653, 130.451205510907, Math.toRadians(143));
        private final Pose shootPose = new Pose(69.88981696727295, 61.14743216647197, Math.toRadians(129));
        private final Pose getmore = new Pose(36.30388691103015, 58.93545581231573, Math.toRadians(1));
        private final Pose shoot2 = new Pose(69.91462660972167, 124.93283154467014, Math.toRadians(129));
        private final Pose parkPose = new Pose(50.69465488809418, 124.93283154467014, Math.toRadians(129));

        private PathChain drive, get, shoottwo, park;

        public void buildPaths() {
            //drive then shoot
            drive = Tuning.follower.pathBuilder()
                    .addPath(new BezierLine(startPose, shootPose))
                    .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                    .build();
            get = Tuning.follower.pathBuilder()
                    .addPath(new BezierLine(shootPose, getmore))
                    .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                    .build();
            shoottwo = Tuning.follower.pathBuilder()
                    .addPath(new BezierLine(getmore, shoot2))
                    .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                    .build();
            park = Tuning.follower.pathBuilder()
                    .addPath(new BezierLine(shoot2, parkPose))
                    .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                    .build();

        }

        public void setPathState(pathState newState) {
            pathState = newState;
            pathTimer.resetTimer();
        }

        public void statePathUpdate() {
            switch (pathState) {
                case drivetoshoot:
                    Tuning.follower.followPath(drive, true); // goes from start to shoot
                    setPathState(pathState.shootpreload);
                    break;
                case shootpreload:
                    if (Tuning.follower.isBusy()) {
                        pathTimer.resetTimer();
                        break;
                    }
                    double distance = vision.getDistance();
                    double power = (0.0034390 * distance) + 0.467552;
                    spinny.spinny(power);

                    //after 6 and a hald seconds spin up
                    if (pathTimer.getElapsedTimeSeconds() > 6.5) {
                        spin.spin(1);
                    }


                    // after spin shot stop than move on
                    if (pathTimer.getElapsedTimeSeconds() > 7.5) {
                        spin.spin(0);
                        spinny.spinny(0);
                        setPathState(pathState.germ);
                    }
                    break;
                case germ:
                    if (!Tuning.follower.isBusy()) {
                        Tuning.follower.followPath(get, true); // shoot to get more
                        setPathState(pathState.inhale);

                    }
                    break;
                case inhale:
                    if (!Tuning.follower.isBusy()) {
                        servo.red(1);
                        servo.blue(1);
                    }
                    if (pathTimer.getElapsedTimeSeconds() > .6) {
                        setPathState(pathState.shootagain);
                    }
                    break;
                case shootagain:
                    if (!Tuning.follower.isBusy()) {
                        Tuning.follower.followPath(shoottwo, true); // shoot to get more
                        setPathState(pathState.end);

                    }
                    break;
                case end:
                    if (!Tuning.follower.isBusy()) {
                        Tuning.follower.followPath(park, true); // shootagain to park
                        setPathState(pathState.end);

                    }


            }
        }

        @Override
        public void init() {
            pathState = pathState.drivetoshoot;
            pathTimer = new Timer();
            opModeTimer = new Timer();
            spinny = new flywheel(hardwareMap);
            spin = new intake(hardwareMap);
            Limelight3A limelight = hardwareMap.get(Limelight3A.class, "limelight");
            Tuning.follower = Constants.createFollower(hardwareMap);
            buildPaths();
            Tuning.follower.setPose(startPose);
        }

        public void start() {
            opModeTimer.resetTimer();
            setPathState(pathState);


        }

        @Override
        public void loop() {
            statePathUpdate();
            Tuning.follower.update();
            telemetry.addData("Path State", pathState.toString());
            telemetry.addData("x", Tuning.follower.getPose().getX());
            telemetry.addData("y", Tuning.follower.getPose().getY());
            telemetry.addData("heading", Tuning.follower.getPose().getHeading());
            telemetry.addData("Path state", pathState.toString());
            telemetry.update();


        }
    }//blue big end

    @Autonomous
    static class redbig extends OpMode {
        private Follower follwer;
        private Timer pathTimer, opModeTimer;
        public flywheel spinny;
        public intake spin;
        public LimelightVision vision;


        public enum pathState {
            // start to end
            //drive moment
            // shoot
            drivetoshoot,

            shootpreload,
            germ,
            inhale,
            shootagain,
            end;


        }


        static pathState pathState;
        private final Pose startPose = new Pose(112.860, 130.168, Math.toRadians(36));
        private final Pose shootPose = new Pose(72.618, 59.134, Math.toRadians(50));
        private final Pose getmore = new Pose(105.190, 58.17, Math.toRadians(180));
        private final Pose shoot2 = new Pose(72.61825487944891, 59.134328358208954, Math.toRadians(50));
        private final Pose parkPose = new Pose(98.18357878868662, 72.4006887541896, Math.toRadians(0));

        private PathChain drive, get, shoottwo, park;


        public void buildPaths() {
            drive = Tuning.follower.pathBuilder()
                    .addPath(new BezierLine(startPose, shootPose))
                    .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                    .build();
            get = Tuning.follower.pathBuilder()
                    .addPath(new BezierLine(shootPose, getmore))
                    .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                    .build();
            shoottwo = Tuning.follower.pathBuilder()
                    .addPath(new BezierLine(getmore, shoot2))
                    .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                    .build();
            park = Tuning.follower.pathBuilder()
                    .addPath(new BezierLine(shoot2, parkPose))
                    .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                    .build();


        }

        public void setPathState(pathState newState) {
            pathState = newState;
            pathTimer.resetTimer();
        }

        @Override
        public void init() {
            pathState = pathState.drivetoshoot;
            pathTimer = new Timer();
            opModeTimer = new Timer();
            spinny = new flywheel(hardwareMap);
            spin = new intake(hardwareMap);
            Limelight3A limelight = hardwareMap.get(Limelight3A.class, "limelight");
            Tuning.follower = Constants.createFollower(hardwareMap);
            buildPaths();
            Tuning.follower.setPose(startPose);
        }

        public void statePathUpdate() {
            switch (pathState) {
                case drivetoshoot:
                    Tuning.follower.followPath(drive, true); // goes from start to shoot
                    setPathState(pathState.shootpreload);
                    break;
                case shootpreload:
                    if (Tuning.follower.isBusy()) {
                        pathTimer.resetTimer();
                        break;
                    }
                    double distance = vision.getDistance();
                    double power = (0.0034390 * distance) + 0.467552;
                    spinny.spinny(power);

                    //after 6 and a hald seconds spin up
                    if (pathTimer.getElapsedTimeSeconds() > 6.5) {
                        spin.spin(1);
                    }


                    // after spin shot stop than move on
                    if (pathTimer.getElapsedTimeSeconds() > 7.5) {
                        spin.spin(0);
                        spinny.spinny(0);
                        setPathState(pathState.germ);
                    }
                    break;
                case germ:
                    if (!Tuning.follower.isBusy()) {
                        Tuning.follower.followPath(get, true); // shoot to get more
                        setPathState(pathState.inhale);

                    }
                    break;
                case inhale:
                    if (!Tuning.follower.isBusy()) {
                        servo.red(1);
                        servo.blue(1);
                    }
                    if (pathTimer.getElapsedTimeSeconds() > .6) {
                        setPathState(pathState.shootagain);
                    }
                    break;
                case shootagain:
                    if (!Tuning.follower.isBusy()) {
                        Tuning.follower.followPath(shoottwo, true); // shoot to get more
                        setPathState(end);

                    }
                    break;
                case end:
                    if (!Tuning.follower.isBusy()) {
                        Tuning.follower.followPath(park, true); // shootagain to park
                        setPathState(end);

                    }
            }


        }

        public void start() {
            opModeTimer.resetTimer();
            setPathState(pathState);


        }

        @Override
        public void loop() {
            statePathUpdate();
            Tuning.follower.update();
            telemetry.addData("Path State", pathState.toString());
            telemetry.addData("x", Tuning.follower.getPose().getX());
            telemetry.addData("y", Tuning.follower.getPose().getY());
            telemetry.addData("heading", Tuning.follower.getPose().getHeading());
            telemetry.addData("Path state", pathState.toString());
            telemetry.update();


        }
    }

} // main end
