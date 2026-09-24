package org.firstinspires.ftc.teamcode.functions;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ApriltagZoe {
    private Limelight3A limelight;
    private Telemetry telemetry;

    public ApriltagZoe(Limelight3A limelight, Telemetry telemetry) {
        this.limelight = limelight;
        this.telemetry = telemetry;
    }

    public void init(){
        limelight.start();
    }
    public void april23(boolean yes) {
        LLResult result = limelight.getLatestResult();
        if (result != null) {
            if (result.isValid()) {
            }
            }
        }
    }