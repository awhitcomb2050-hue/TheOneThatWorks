package org.firstinspires.ftc.teamcode.functions;
import static org.firstinspires.ftc.teamcode.functions.shooting.shootingState.START;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
@Config
public class shooting {

    // internal states
    public enum shootingState {
        START,
        INTAKE,
        TRANSFER,
        SHOOT
    }

    private shootingState currentShootingState = START;
    private DcMotor intakeMotor;
    private DcMotor transfer;
    private DcMotor flywheel1;
    private DcMotor flywheel2;
    private ElapsedTime stateTimer = new ElapsedTime();


    public shooting(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        transfer = hardwareMap.get(DcMotor.class, "transfer");
        flywheel1 = hardwareMap.get(DcMotor.class, "flywheel1");
        flywheel2 = hardwareMap.get(DcMotor.class, "flywheel1");
    }


    // to trigger the sequence
    public void startSequence() {
        if (currentShootingState == shootingState.START) {
            intakeMotor.setPower(1.0);  // Turn on intake
            stateTimer.reset();          // Reset timer for the intake phase
            currentShootingState = shootingState.INTAKE;
        }
    }

    // logic
    public void update() {
        switch (currentShootingState) {
            case START:
                // idle waiting for start
                intakeMotor.setPower(0);
                transfer.setPower(0);
                flywheel1.setPower(.2);
                flywheel2.setPower(.2);
                break;

            case INTAKE:
                // run intake for 2s
                if (stateTimer.seconds() >= 2.0) {
                    intakeMotor.setPower(0);     // stop intake
                    flywheel1.setPower(1.0);
                    flywheel2.setPower(1.0); // start the flywheel early
                    stateTimer.reset();          // reset timer for transfer phase
                    currentShootingState = shootingState.TRANSFER;
                }
                break;

            case TRANSFER:
                // waiting 1s for flywheel to reach full speed then start transfer
                if (stateTimer.seconds() >= 1.0) {
                    transfer.setPower(0.8);      // feed into the shooter
                    stateTimer.reset();          // reset timer for the actual shot duration
                    currentShootingState = shootingState.SHOOT;
                }
                break;

            case SHOOT:
                // let it shoot
                if (stateTimer.seconds() >= 1.5) {
                    currentShootingState = shootingState.START;
                }
                break;
        }
    }
}