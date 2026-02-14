package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LimelightVision {
    private Limelight3A limelight;
    private Telemetry telemetry;

    public LimelightVision(Limelight3A limelight, Telemetry telemetry) {
        this.limelight = limelight;
        this.telemetry = telemetry;

    }

    // Call this once in your TeleOp's init
    public void init() {
        limelight.pipelineSwitch(0); // Ensure your AprilTag pipeline is at Index 0
        limelight.start();
    }

    // Call in
    public void updateTelemetry() {
        LLResult result = limelight.getLatestResult();

        // Only display if a valid target is seen
        if (result != null && result.isValid()) {
            telemetry.addLine("--- AprilTag Detected ---");
            telemetry.addData("Tag ID", result.getFiducialResults());
            telemetry.addData("Horizontal Offset (tx)", "%.2f deg", result.getTx());
            telemetry.addData("Vertical Offset (ty)", "%.2f deg", result.getTy());
            telemetry.addData("Target Area (ta)", "%.2f%%", result.getTa());
        } else {
            telemetry.addData("Limelight", "Searching for AprilTags...");
        }
    }

    public void stop() {
        limelight.stop();
    }
}


