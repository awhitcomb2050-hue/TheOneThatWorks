package org.firstinspires.ftc.teamcode.functions.learning;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import java.util.List;

public class ApriltagZoe {
    private Limelight3A limelight;
    private Telemetry telemetry;

    public ApriltagZoe(Limelight3A limelight, Telemetry telemetry) {
        this.limelight = limelight;
        this.telemetry = telemetry;
    }

    public void init() {
        limelight.start();
    }

    public void april23() {
        LLResult result = limelight.getLatestResult();
        int targetId = 23;
        boolean tagFound = false;

        if (result != null && result.isValid()) {
            List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();

            if (fiducialResults != null) {
                for (LLResultTypes.FiducialResult target : fiducialResults) {
                    if (target.getFiducialId() == targetId) {
                        tagFound = true;
                        break;
                    }
                }
            }
        }

        // print data
        if (tagFound) {
            telemetry.addData("Status", "AprilTag 23 found");
        } else {
            telemetry.addData("Status", "AprilTag 23 not found :((( so sad ");
        }
    }
}
