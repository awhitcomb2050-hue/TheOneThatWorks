package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Locale;

public class LimelightVision {
    private Limelight3A limelight;
    private Telemetry telemetry;
    private double currentDistance = 0;


    public LimelightVision(Limelight3A limelight, Telemetry telemetry) {
        this.limelight = limelight;
        this.telemetry = telemetry;

    }

    public void init() {
        limelight.pipelineSwitch(0); // 0 for april tags
        limelight.start();
    }

    public double getDistance() {
        return currentDistance;
    }

    public void updateTelemetry() {
        LLResult result = limelight.getLatestResult();

        // Only display if a valid target is seen
        if (result != null && result.isValid()) {
            double ty = result.getTy(); // Vertical offset
            double cameraHeightInches = 16.625;
            double goalHeight = 43;
            double mountAngleDegrees = 0; //tilt

            // Calculate Distance
            double angleToGoalRadians = Math.toRadians(mountAngleDegrees + ty);
            double distance = (goalHeight - cameraHeightInches) / Math.tan(angleToGoalRadians);
            setCurrentDistance(distance);

            telemetry.addData("Distance to Tag", "%.2f inches", Math.abs(distance));
            telemetry.addLine("--- AprilTag Detected ---");
            telemetry.addData("Tag ID", result.getFiducialResults());
            telemetry.addData("Horizontal Offset (tx)", "%.2f deg", result.getTx());
            telemetry.addData("Vertical Offset (ty)", "%.2f deg", result.getTy());
            telemetry.addData("Target Area (ta)", "%.2f%%", result.getTa());
        } else {
            telemetry.addData("Limelight", "Searching for AprilTags...");
        }

    }
    public double getTargetPower() {
        double slope = 0.01;
        double intercept = 0.3;

        return (slope * Math.abs(currentDistance)) + intercept;
    }

    public double getCurrentDistance() {
        return currentDistance;
    }



    public void setCurrentDistance(double currentDistance) {
        this.currentDistance = currentDistance;
    }
}


