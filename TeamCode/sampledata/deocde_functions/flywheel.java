//package org.firstinspires.ftc.teamcode.drive.deocde_functions;
//
//import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
//
//import com.qualcomm.hardware.limelightvision.Limelight3A;
//import com.qualcomm.robotcore.hardware.DcMotorEx;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//public class flywheel {
//
//
//    public DcMotorEx shootyMotor1;
//    public DcMotorEx shootyMotor2;
//    private LimelightVision vision;
//
//
//
//    public flywheel(HardwareMap hardwareMap) {
//        shootyMotor1 = hardwareMap.get(DcMotorEx.class, "shootyMotor1");
//        shootyMotor2 = hardwareMap.get(DcMotorEx.class, "shootyMotor2");
//        Limelight3A limelight = hardwareMap.get(Limelight3A.class, "limelight");
//        vision = new LimelightVision(limelight, telemetry);
////        shootyMotor1.setMode(RUN_USING_ENCODER);
////        shootyMotor2.setMode(RUN_USING_ENCODER);
//
//
//
//    }
//
//    public void spinny( double fpower) {
//
//        shootyMotor1.setPower(fpower);
//        shootyMotor2.setPower(fpower);
//
//    }
//
//
//
//
//    public void stop() {
//        spinny(0);
//    }
//}
//
