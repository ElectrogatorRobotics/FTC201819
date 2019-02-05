package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.library.MotorControls;

//@Autonomous(name = "motor test", group = "Testing")
//@Disabled
public class MotorTesting extends LinearOpMode {
    MotorControls motorControls = new MotorControls();

    @Override
    public void runOpMode() throws InterruptedException {
        motorControls.initaliseMotors(hardwareMap);
        motorControls.initialiseIMU(hardwareMap);

        waitForStart();

        motorControls.driveToTarget(12, 0.1);
//            while (opModeIsActive() && motorControls.backRightDrive.isBusy())
//                    idle();
//
        waitForDrive();
        sleep(500);
        motorControls.driveToTarget(-12, 0.1);
//        while (opModeIsActive() && motorControls.backRightDrive.isBusy())
//            idle();
        waitForDrive();

        telemetry.addData("motor pos", motorControls.backLeftDrive.getTargetPosition());
        telemetry.update();
        sleep(5000);
//        }
    }

    /**
     * if the motors are doing something, let the OS do what ever it needs to do.
     * <p>
     * if the motors are busy,
     *
     * @return false
     */
    public boolean waitForDrive() {
        while (motorControls.frontRightDrive.isBusy() && opModeIsActive())
            idle();
        if (motorControls.frontRightDrive.isBusy())
            return false;
        else return true;
    }
}
