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
        TRANSFER_SHOOT  // shoot by turning on transfer
    }

    private shootingState currentShootingState = shootingState.START;
    private DcMotor intakeMotor;
    private DcMotor transfer;
    private shootingMath shooterMathSystem;
    public ElapsedTime stateTimer = new ElapsedTime();

    public static double idleVelocity = 300.0;

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
        double targetVelocity = (distanceInches > 0)
                ? shooterMathSystem.calculateTargetVelocity(distanceInches)
                : idleVelocity;

        switch (currentShootingState) {
            case START:
                intakeMotor.setPower(0);
                transfer.setPower(0);


                shootingMath.flywheel1.setVelocity(idleVelocity);
                shootingMath.flywheel2.setVelocity(idleVelocity);
                break;

            case INTAKE:
                // intake on
                intakeMotor.setPower(1.0);
                transfer.setPower(0);

                shootingMath.flywheel1.setVelocity(idleVelocity);
                shootingMath.flywheel2.setVelocity(idleVelocity);

                // intake for 1.5 sec
                if (stateTimer.seconds() >= 1.5) {
                    intakeMotor.setPower(0); // Stop intake
                    currentShootingState = shootingState.VELOCITY;
                }
                break;

            case VELOCITY:
                intakeMotor.setPower(0);
                transfer.setPower(0);

                shootingMath.flywheel1.setVelocity(targetVelocity);
                shootingMath.flywheel2.setVelocity(targetVelocity);

                if (shooterMathSystem.isAtTargetVelocity()) {
                    stateTimer.reset(); // Reset timer to track the shot duration
                    currentShootingState = shootingState.TRANSFER_SHOOT;
                }
                break;

            case TRANSFER_SHOOT:

                shootingMath.flywheel1.setVelocity(targetVelocity);
                shootingMath.flywheel2.setVelocity(targetVelocity);


                transfer.setPower(1.0);


                if (stateTimer.seconds() >= 1.5) {
                    transfer.setPower(0);
                    currentShootingState = shootingState.START; // Reset to safe slow idle
                }
                break;
        }
    }

    public shootingState getCurrentState() {
        return currentShootingState;
    }
}