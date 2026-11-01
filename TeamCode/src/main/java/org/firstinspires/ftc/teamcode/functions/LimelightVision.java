package org.firstinspires.ftc.teamcode.functions;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import java.util.List;

public class LimelightVision {
    private Limelight3A limelight;
    private Telemetry telemetry;
    private double currentDistance = 0;
    private double avgTx = 0;
    private double avgTy = 0;
    private boolean targetVisible = false;

    public LimelightVision(Limelight3A limelight, Telemetry telemetry) {
        this.limelight = limelight;
        this.telemetry = telemetry;
    }

    public void init() {
        limelight.start();
    }



    public double getDistance() {
        return currentDistance;
    }

    public void updateTelemetry() {
        LLResult result = limelight.getLatestResult();

        if (result != null && result.isValid()) {
            List<LLResultTypes.FiducialResult> fiducialList = result.getFiducialResults();

            if (fiducialList != null && !fiducialList.isEmpty()) {
                double sumTx = 0;
                double sumTy = 0;
                int count = 0;

                // Loop through visible tags up to a maximum of 4
                for (LLResultTypes.FiducialResult fiducial : fiducialList) {
                    sumTx += fiducial.getTargetXDegrees();
                    sumTy += fiducial.getTargetYDegrees();
                    count++;
                    if (count >= 4) break;
                }


                avgTx = sumTx / count;
                avgTy = sumTy / count;
                targetVisible = true;

                double cameraHeightInches = 13.625;
                double goalHeight = 29.5;
                double mountAngleDegrees = 0; // tilt

                double angleToGoalDegrees = mountAngleDegrees + avgTy;
                double angleToGoalRadians = angleToGoalDegrees * (Math.PI / 180.0);

                double distance = (goalHeight - cameraHeightInches) / (Math.tan(angleToGoalRadians));
                setCurrentDistance(distance);


                telemetry.addLine("--- Multi-AprilTag Detected ---");
                telemetry.addData("Tags Tracked in Middle", count);
                telemetry.addData("Avg Distance to Center", "%.2f inches", Math.abs(currentDistance));
                telemetry.addData("Avg Horizontal Center (tx)", "%.2f deg", avgTx);
                telemetry.addData("Avg Vertical Center (ty)", "%.2f deg", avgTy);
            } else {

                avgTx = result.getTx();
                avgTy = result.getTy();
                targetVisible = true;
            }
        } else {
            targetVisible = false;
            telemetry.addData("Limelight", "Searching for AprilTags...");
        }
    }


    public void setCurrentDistance(double currentDistance) {
        this.currentDistance = currentDistance;
    }
}
