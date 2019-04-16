package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.library.DriveV2;
import org.firstinspires.ftc.teamcode.library.DriveV2_Impl;
import org.firstinspires.ftc.teamcode.library.ScoringArms;
import org.firstinspires.ftc.teamcode.library.ScoringArmsImpl;

@TeleOp
public class TeleopV2 extends LinearOpMode {
    DriveV2 drive = new DriveV2_Impl();
//    ScoringArms scoringArms = new ScoringArmsImpl();
    ScoringArmsImpl scoringArms;

    double frontLeftDrive, frontRightDrive, backRightDrive, backLeftDrive;
    double maxDrive = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        scoringArms = new ScoringArmsImpl();
        drive.initDrive(hardwareMap);
        scoringArms.initScoringSystems(hardwareMap);
        double adjFactorFront, adjFactorBack;
        double throtle;

        waitForStart();

        while (opModeIsActive()) {
            // calculate the throttle position that will be used when calculating the motor powers
            throtle = Math.max(gamepad1.left_trigger, .4);

            /**
             * Calculate the power of each motor by multiplying the left Y-axes and the left X-axes that are
             * used for driving normal by the throttle value that we calculated above. The right X-axes is
             * not multiplied by the throttle, because it is used for sliding sideways and can not be controlled
             * efficiently with the throttle due to the high power requirements of sliding.
             */
            frontRightDrive = ((gamepad1.left_stick_y * throtle) + (gamepad1.left_stick_x * throtle) + (gamepad1.right_stick_x) * throtle);
            frontLeftDrive = ((gamepad1.left_stick_y * throtle) - (gamepad1.left_stick_x * throtle) - (gamepad1.right_stick_x) * throtle);
            backRightDrive = ((gamepad1.left_stick_y * throtle) + (gamepad1.left_stick_x * throtle) - (gamepad1.right_stick_x) * throtle);
            backLeftDrive = ((gamepad1.left_stick_y * throtle) - (gamepad1.left_stick_x * throtle) + (gamepad1.right_stick_x) * throtle);

            /**
             * The motor powers can be calculated to be higher than 1.0 and less than -1.0, so rater than just
             * clipping the values to 1.0 if they are above 1.0 or -1.0 if below -1.0, we decided to scale the
             * values down to preserve the control resolution that we would have if the motor powers were divided
             * by 2.
             */
            // error adjustment based on the front right drive wheel
            maxDrive = Math.abs(frontRightDrive);
            setMaxDrive(frontLeftDrive);
            setMaxDrive(backRightDrive);
            setMaxDrive(backLeftDrive);
            // we set the adjustment factor to 1 so it does not change the motor powers if if they are in the 1.0 to -1.0 range
            adjFactorFront = 1;
            adjFactorBack = 1;
            // we need to use the abs value to of the max drive power to determine if we need to scale the motor powers down or not
            if (maxDrive > 1) {
                adjFactorFront = (1 / maxDrive);
                adjFactorBack = (1 / maxDrive);
            }

            drive.setDriveSpeed(-frontRightDrive, -backRightDrive, -frontLeftDrive, -backLeftDrive);

            if (gamepad2.x && gamepad2.right_bumper)
                drive.driveServoState(DriveV2.driveServoState.RETRACT);
            if (gamepad2.b && gamepad2.right_bumper)
                drive.driveServoState(DriveV2.driveServoState.DRIVE);
            if (gamepad2.a && gamepad2.right_bumper)
                drive.driveServoState(DriveV2.driveServoState.STRAIGHT);

            scoringArms.setIntakeMotorPower(gamepad2.right_stick_y);
//            scoringArms.setScoringArmServoPosition(gamepad2.left_trigger);
            scoringArms.setIntakeArmMotorPower(gamepad2.left_stick_y);
            scoringArms.setScoringArmServoPosition(gamepad2.left_bumper);
//            scoringArms.intakeMotor.setPower(gamepad2.right_stick_y);

            telemetry.addData("intake motor power", scoringArms.intakeArmMotor.getPower())
                    .addData("intake arm motor power", scoringArms.intakeArmMotor.getPower())
                    .addData("scoring arm servo position", scoringArms.scoringArmServo.getPosition());

            telemetry.addData("front right drive motor", frontRightDrive)
                    .addData("front left drive motor", frontLeftDrive)
                    .addData("back right drive motor", backRightDrive)
                    .addData("back left drive motor", backLeftDrive);
            telemetry.update();
        }
    }

    void setMaxDrive(double motor) {
        if (Math.abs(motor) > maxDrive) {
            maxDrive = Math.abs(motor);
        }
    }
}
