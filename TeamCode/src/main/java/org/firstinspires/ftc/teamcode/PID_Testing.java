package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.motors.RevRobotics20HdHexMotor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

/**
 * Created by luke on 11/12/2018.
 */

@TeleOp(name = "PID testing", group = "testing")
@Disabled
public class PID_Testing extends LinearOpMode {
    DcMotorEx motorEx;

    private static final double P = 2.5;
    private static final double I = 0.1;
    private static final double D = 0.2;

    @Override
    public void runOpMode() throws InterruptedException {
        motorEx = (DcMotorEx)hardwareMap.get(DcMotor.class, "motor");

        waitForStart();

        while (opModeIsActive()) {
            PIDCoefficients old_PID = motorEx.getPIDCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);
            PIDCoefficients new_PID = new PIDCoefficients(P, I, D);
            motorEx.setPIDCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new_PID);
            motorEx.setMotorDisable();
        }
    }
}
