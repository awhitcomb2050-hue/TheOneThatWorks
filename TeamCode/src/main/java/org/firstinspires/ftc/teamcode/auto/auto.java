package org.firstinspires.ftc.teamcode.auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.FuturePose;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.apache.commons.math3.geometry.Point;

@Autonomous
public class auto extends OpMode {
    private Follower follower;
    private Timer pathTimer, opModeTimer;

    public enum PathState {
        // START POSITON_END POSITION
        // DRIVE > MOVEMOVENT STATE
        // SHOOT > ATTEMPT TO SCORE THE ARTIFACT
        DRIVE_STARTPO_SHOOT_POS,
        SHOOT_PRELOAD
    }

    PathState pathState;



    private final Pose start = new Pose(10.5, 35, Math.toRadians(90));
    private final Pose point1 = new Pose(10.5, 35, Math.toRadians(0));
    private final Pose point2 = new Pose(56, 35.9, Math.toRadians (90));
    private final Pose point3 = new Pose(32.3393, 35.9574, Math.toRadians (90));
    private final Pose point4= new Pose(32.3126, 106.2093, Math.toRadians (180));
    private final Pose point5 = new Pose(10.1786, 106.3669, Math.toRadians (0));

    private  PathChain startPath;
    private PathChain path1;
    private PathChain path2;
    private PathChain path3;
    private PathChain path4;
    public enum AutoState {
        PATH1, PATH2, PATH3, PATH4, STOP
    }

    public void buildPaths() {
        // Path 1: From Start to Score Position
         startPath= follower.pathBuilder()
                .addPath(new BezierLine((FuturePose) start, (FuturePose) point1))
                .setLinearHeadingInterpolation(start.getHeading(), point1.getHeading())
                .build();
//    @Override
//    public void init() {
//
//    }
//
//    @Override
//    public void loop() {

    }
}
