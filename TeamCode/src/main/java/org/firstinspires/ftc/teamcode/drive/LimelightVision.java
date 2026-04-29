package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LimelightVision {
    private Limelight3A limelight;
    private Telemetry telemetry;
    private double currentDistance = 0;
    public double kI = 0;
    public double kP = 1;
    public double kD = 0;


    public LimelightVision(Limelight3A limelight, Telemetry telemetry) {
        this.limelight = limelight;
        this.telemetry = telemetry;

    }

    public void init() {
        limelight.start();
    }
    public void pipe(double number) {
        limelight.pipelineSwitch((int) number); // 0 for april tags for 24 red?
    }

    public double getDistance() {
        return currentDistance;
    }

    public void updateTelemetry() {
        LLResult result = limelight.getLatestResult();

        // Only display if a valid target is seen
        if (result != null && result.isValid()) {
            double ty = result.getTy(); // Vertical offset
            double cameraHeightInches = 13.625;
            double goalHeight = 29.5;
            double mountAngleDegrees = 0; //tilt


            // Calculate Distance
            double angleToGoalDegrees =  mountAngleDegrees +ty;
            double angleToGoalRadians = angleToGoalDegrees * (3.14159/180);

            double distance = (goalHeight - cameraHeightInches) / (Math.tan(angleToGoalRadians));
            setCurrentDistance(distance);

            telemetry.addData("Distance to Tag", "%.2f inches", Math.abs(currentDistance));
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
    public double aim(){
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            return kP * result.getTx();
        }else{
            return 0;
        }


    }



    public void setCurrentDistance(double currentDistance) {
        this.currentDistance = currentDistance;
    }
}


