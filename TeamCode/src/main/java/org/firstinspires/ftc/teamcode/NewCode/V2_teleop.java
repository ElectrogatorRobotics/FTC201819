package org.firstinspires.ftc.teamcode.NewCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NewCode.V2_library.DriveV2;

@TeleOp (name = "New robot teleop")
public class V2_teleop extends LinearOpMode {
    DriveV2 drive = null;

    double frontLeftDrive, frontRightDrive, backRightDrive, backLeftDrive;
    double maxDrive = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        drive.initDrive(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            double adjFactorFront, adjFactorBack;
            double throtle;
            // calculate the throttle position that will be used when calculating the motor powers
            throtle = Math.max(gamepad1.left_trigger, .4);

            /**
             * Calculate the power of each motor by multiplying the left Y-axes and the left X-axes that are
             * used for driving normal by the throttle value that we calculated above. The right X-axes is
             * not multiplied by the throttle, because it is used for sliding sideways and can not be controlled
             * efficiently with the throttle due to the high power requirements of sliding.
             */
            frontRightDrive = ((gamepad1.left_stick_y * throtle) + (gamepad1.left_stick_x * throtle) + gamepad1.right_stick_x * throtle);
            frontLeftDrive = ((gamepad1.left_stick_y * throtle) - (gamepad1.left_stick_x * throtle) - gamepad1.right_stick_x * throtle);
            backRightDrive = ((gamepad1.left_stick_y * throtle) + (gamepad1.left_stick_x * throtle) - gamepad1.right_stick_x * throtle);
            backLeftDrive = ((gamepad1.left_stick_y * throtle) - (gamepad1.left_stick_x * throtle) + gamepad1.right_stick_x * throtle);



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

            // set the motor powers
            drive.setDriveSpeed(frontRightDrive, backRightDrive, frontLeftDrive, backLeftDrive);
        }
    }
    void setMaxDrive(double motor) {
        if (Math.abs(motor) > maxDrive) {
            maxDrive = Math.abs(motor);
        }
    }
}
