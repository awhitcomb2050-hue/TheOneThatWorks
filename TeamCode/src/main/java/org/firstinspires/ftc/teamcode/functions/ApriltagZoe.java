package org.firstinspires.ftc.teamcode.functions;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.Telemetry;

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

    public void april23(boolean yes) {
        LLResult result = limelight.getLatestResult();
        if (result != null) {
            if (result.isValid()) {
            }
/*  this is an example of what the logic would look if its determining seeing the correct apirl tag
public void April(int targetId) {
                LLResult result = limelight.getLatestResult();

                if (result != null && result.isValid()) {
                    boolean tagFound = false; // if it can see it set false

                    for (LLResultTypes.FiducialResult target : result.getFiducialResults()) {
                        if (target.getFiducialId() == targetId) {
                            tagFound = true; // if it can see it set true


                        }
                    }
                    */
        }
    }
}