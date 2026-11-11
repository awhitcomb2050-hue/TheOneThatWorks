package org.firstinspires.ftc.teamcode.functions;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
public class shooting {

    public enum shootingState {
        START,
        INTAKE,         // intake on
        VELOCITY,       // flywheel to correct speed
        TRANSFER_SHOOT,// shoot by turning on transfer
        STOP
    }

    private shootingState currentShootingState = shootingState.START;
    private DcMotor intakeMotor;
    private DcMotor transfer;
    private shootingMath shooterMathSystem;
    public ElapsedTime stateTimer = new ElapsedTime();

    public static double idleVelocity = 0;

    public shooting(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        transfer = hardwareMap.get(DcMotor.class, "transfer");
        shooterMathSystem = new shootingMath(hardwareMap);
    }

    public void startSequence() {
        if (currentShootingState == shootingState.START) {
            stateTimer.reset();
            currentShootingState = shootingState.INTAKE;
        }
    }

    public void update(double distanceInches) {
        double targetVelocity = (shooterMathSystem.isAtTargetVelocity())
                ? shooterMathSystem.calculateTargetVelocity(distanceInches)
                : idleVelocity;        switch (currentShootingState) {
            case START:
                stateTimer.startTime();
                intakeMotor.setPower(0);
                transfer.setPower(0);
                shootingMath.flywheel1.setVelocity(idleVelocity);
                shootingMath.flywheel2.setVelocity(idleVelocity);
                if (stateTimer.seconds() >= 1.5) {
                    currentShootingState = shootingState.INTAKE;
                }
                break;

            case INTAKE:
                // intake on
                intakeMotor.setPower(1.0);
                transfer.setPower(0);
                shootingMath.flywheel1.setVelocity(idleVelocity);
                shootingMath.flywheel2.setVelocity(idleVelocity);

                // intake for 1 sec
                if (stateTimer.seconds() >= 2.5) {
                    intakeMotor.setPower(0);
                    currentShootingState = shootingState.VELOCITY;
                }
                break;

            case VELOCITY:
                intakeMotor.setPower(0);
                transfer.setPower(-1);

                shootingMath.flywheel1.setVelocity(targetVelocity);
                shootingMath.flywheel2.setVelocity(targetVelocity);

                if (shooterMathSystem.isAtTargetVelocity()) {
                    currentShootingState = shootingState.TRANSFER_SHOOT;
                }
                break;

            case TRANSFER_SHOOT:
                shootingMath.flywheel1.setVelocity(targetVelocity);
                shootingMath.flywheel2.setVelocity(targetVelocity);
                transfer.setPower(1.0);


                if (stateTimer.seconds() >= 5.5) {
                    transfer.setPower(0);
                    shootingMath.flywheel1.setVelocity(0);
                    shootingMath.flywheel2.setVelocity(0);
                    currentShootingState = shootingState.STOP; // Reset to safe slow idle
                }
            case STOP:
                transfer.setPower(0);
                shootingMath.flywheel1.setVelocity(0);
                shootingMath.flywheel2.setVelocity(0);
                if (stateTimer.seconds() >= 7.0) {
                    currentShootingState = shootingState.START;
                }
                break;
        }
    }

    public shootingState getCurrentState() {
        return currentShootingState;
    }
}
