package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.library.GoldPosition;
import org.firstinspires.ftc.teamcode.library.TensorID;
import org.firstinspires.ftc.teamcode.library.TensorIDImpl;

@TeleOp
@Disabled
public class TensorTest extends LinearOpMode {
    TensorID tensor;
    GoldPosition goldPosition;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addLine("Initialising TensorFlow");
        telemetry.update();

        tensor = new TensorIDImpl(telemetry, this);

        telemetry.addLine("Ready to start");
        telemetry.update();

        waitForStart();

        telemetry.addData("Gold Position", goldPosition);
        telemetry.update();
        while (opModeIsActive()/* && goldPosition == GoldPosition.NONE*/) {
            goldPosition = tensor.getGoldPosition();
            telemetry.addData("Gold Position", goldPosition);
            telemetry.update();
        }
        telemetry.addData("Gold Position", goldPosition);
        telemetry.update();

        sleep(5000);
        stop();
    }
}
