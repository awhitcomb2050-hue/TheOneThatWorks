package org.firstinspires.ftc.teamcode.auto.procedures;

import com.pedropathing.tuning.autotune.Inputs;
import com.pedropathing.tuning.autotune.Procedure;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class PinpointTuner extends Procedure {

    public PinpointTuner() {
        super("Pinpoint Tuner", "A procedure for configuring the goBILDA Pinpoint Odometry Computer.");
    }

    @Override
    public void run() throws InterruptedException {
        Inputs setup = inputs("Pinpoint Setup", "Set Pinpoint configuration parameters");
        Inputs.Field<String> pinpointName = setup.s("Pinpoint HardwareMap Name").withDefault("pinpoint");
        Inputs.Field<GoBildaPinpointDriver.GoBildaOdometryPods> podType = setup.e("Odometry Pod Type", GoBildaPinpointDriver.GoBildaOdometryPods.class).withDefault(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        Inputs.Field<DistanceUnit> offsetUnits = setup.e("Offset Units", DistanceUnit.class).withDefault(DistanceUnit.INCH);
        Inputs.Field<Double> xPodOffset = setup.d("X (Forward) Pod Offset").withDefault(0.0);
        Inputs.Field<Double> yPodOffset = setup.d("Y (Strafe) Pod Offset").withDefault(0.0);
        Inputs.Field<GoBildaPinpointDriver.EncoderDirection> xPodDirection = setup.e("X Pod Direction", GoBildaPinpointDriver.EncoderDirection.class).withDefault(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        Inputs.Field<GoBildaPinpointDriver.EncoderDirection> yPodDirection = setup.e("Y Pod Direction", GoBildaPinpointDriver.EncoderDirection.class).withDefault(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        Inputs.Field<PinpointLocalizer.ResetMode> resetMode = setup.e("Reset Mode", PinpointLocalizer.ResetMode.class).withDefault(PinpointLocalizer.ResetMode.RECALIBRATE_IMU);
        awaitInputs(setup);

        result("pinpointName", pinpointName.get());
        result("podType", podType.get());
        result("offsetUnits", offsetUnits.get());
        result("xPodOffset", xPodOffset.get());
        result("yPodOffset", yPodOffset.get());
        result("xPodDirection", xPodDirection.get());
        result("yPodDirection", yPodDirection.get());
        result("resetMode", resetMode.get());

        code(Language.JAVA,
                "public static PinpointConfig localizerConfig = new PinpointConfig(c -> {\n" +
                        "    c.name.set(\"" + pinpointName.get() + "\");\n" +
                        "    c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods." + podType.get().name() + ");\n" +
                        "    c.offsetUnits.set(DistanceUnit." + offsetUnits.get().name() + ");\n" +
                        "    c.xPodOffset.set(" + xPodOffset.get() + ");\n" +
                        "    c.yPodOffset.set(" + yPodOffset.get() + ");\n" +
                        "    c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection." + xPodDirection.get().name() + ");\n" +
                        "    c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection." + yPodDirection.get().name() + ");\n" +
                        "    c.resetMode.set(PinpointLocalizer.ResetMode." + resetMode.get().name() + ");\n" +
                        "});"
        );
    }
}