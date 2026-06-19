//package org.firstinspires.ftc.teamcode.drive.deocde_functions;
//
//import com.qualcomm.robotcore.hardware.CRServo;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//public class servo {
//    public static CRServo red,blue ;
//
//
//    public servo(HardwareMap hardwareMap){
//        blue = hardwareMap.get(CRServo.class, "blue");
//        red = hardwareMap.get(CRServo.class, "red");
//        red.setDirection(DcMotorSimple.Direction.REVERSE);
//
//        blue.setDirection(DcMotorSimple.Direction.REVERSE);
//
//    }
//    public static void red(double power){
//        red.setPower(power);
//    }
//    public static void blue(double power ){
//        blue.setPower(power);
//    }
//}
