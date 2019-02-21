package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.library.Drive;
import org.firstinspires.ftc.teamcode.library.DriveImpl;
import org.firstinspires.ftc.teamcode.library.ElectorgatorHardware;
import org.firstinspires.ftc.teamcode.library.LandingGear;
import org.firstinspires.ftc.teamcode.library.LandingGearImpl;
import org.firstinspires.ftc.teamcode.library.Marker;
import org.firstinspires.ftc.teamcode.library.Scoops;
import org.firstinspires.ftc.teamcode.library.ScoopsImpl;

/**
 * Created by Luke on 10/1/2017.
 */

@TeleOp(name = "DRIVE!")
public class TeleOp_Mecanum extends LinearOpMode {
    ElectorgatorHardware hardware = new ElectorgatorHardware();

    static final double WALL_SCALE_FACTOR = 0.5; //set other side to half power;

    double frontLeftDrive, frontRightDrive, backRightDrive, backLeftDrive;
    boolean startRelic = false;
    double maxDrive = 0;

    Drive drive;
    LandingGear lg;
    Marker mark;
    Scoops scoop;

    @Override
    public void runOpMode() throws InterruptedException {
        // initialise the motors
        telemetry.addLine("Initialising... please wait.");
        telemetry.update();

        drive = new DriveImpl(hardwareMap,telemetry,this);

        mark = new Marker(hardwareMap,telemetry);

        lg = new LandingGearImpl(hardwareMap,drive,this);

        scoop = new ScoopsImpl(hardwareMap, telemetry);

        double adjFactorFront, adjFactorBack;
        double throtle;
        double servoVal = 0;

        telemetry.addLine("Ready to start... thank you for waiting!");
        telemetry.update();

        waitForStart();

        double gp2LT = 0, gp2RT = 0;
        boolean enable_scoop = true;

        while (opModeIsActive()) {
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

            if(gamepad1.a) {
                adjFactorBack *= WALL_SCALE_FACTOR;
            }
            else if(gamepad1.b){
                adjFactorFront *= WALL_SCALE_FACTOR;
            }
            hardware.frontLeftDrive.setPower(drive.setMotorSpeed(frontLeftDrive * adjFactorFront, DriveImpl.MotorControlMode.LINEAR_CONTROL));
            hardware.frontRightDrive.setPower(drive.setMotorSpeed(frontRightDrive * adjFactorFront, DriveImpl.MotorControlMode.LINEAR_CONTROL));
            hardware.backLeftDrive.setPower(drive.setMotorSpeed(backLeftDrive * adjFactorBack, DriveImpl.MotorControlMode.LINEAR_CONTROL));
            hardware.backRightDrive.setPower(drive.setMotorSpeed(backRightDrive * adjFactorBack, DriveImpl.MotorControlMode.LINEAR_CONTROL));


            if (gamepad2.a && gamepad2.right_bumper) {
                lg.stand_up();
                enable_scoop = true;
            } else if (gamepad2.b && gamepad2.right_bumper) {
                lg.deploy();
                enable_scoop = true;
            } else if (gamepad2.x && gamepad2.right_bumper) {
                enable_scoop = false;
//                scoop.backScoopDown();
//                scoop.frontScoopTransfer();
                lg.retract();
            }

//            if (gamepad2.a) {
//                scoop.runRubberBandWheel(1.0);
//            } else scoop.runRubberBandWheel(0.5);

            scoop.runRubberBandWheel((gamepad2.right_stick_y+1)/2);
            if (gamepad2.dpad_down) {
                mark.KickOutTheMrker();
            }
            // display the motor speeds

            if (enable_scoop) {
//                gp2LT = Math.max(gamepad2.left_trigger,.2);
//                gp2RT = (gamepad2.right_trigger*-1)+1;
//                gp2LT = gamepad2.left_stick_y;
//                gp2RT = gamepad2.right_stick_y;

//                scoop.setFrontScoopPos(gp2LT);
//                scoop.setBackScoopPos(gp2RT);
//                scoop.setFrontScoopPos((gp2LT * .5) + .5);
//              scoop.setBackScoopPos((gp2RT * .5) + .5);

                if (gamepad2.left_bumper) {
                    scoop.backScoopCycle();
                }
                scoop.checkCycling();
//                if(gamepad2.left_trigger > .5){
//                    scoop.frontScoopCycle();
//                }
                servoVal = (gamepad2.left_stick_y);
                scoop.setFrontScoopPos(servoVal*0.8);
//            if(gamepad1.left_bumper)scoop.frontScoopDown();
//            if(gamepad1.right_bumper)scoop.frontScoopTransfer();
            }

            telemetry.addData("Front right drive target = ", "%1.2f", frontRightDrive);
//            telemetry.addData("Front right drive speed = ", "%1.2f", hardware.frontRightDrive.getPower());
            telemetry.addData("Front right drive pstn = ", hardware.frontRightDrive.getCurrentPosition());
            telemetry.addData("Front left drive target  = ", "%1.2f", frontLeftDrive);
//            telemetry.addData("Front left drive speed  = ", "%1.2f", hardware.frontLeftDrive.getPower());
            telemetry.addData("Front left drive pstn  = ", hardware.frontLeftDrive.getCurrentPosition());
            telemetry.addData("Back right drive target  = ", "%1.2f", backRightDrive);
//            telemetry.addData("Back right drive speed  = ", "%1.2f", hardware.backRightDrive.getPower());
            telemetry.addData("Back right drive pstn  = ", hardware.backRightDrive.getCurrentPosition());
            telemetry.addData("Back left drive target   = ", "%1.2f", backLeftDrive);
//            telemetry.addData("Back left drive speed   = ", "%1.2f", hardware.backLeftDrive.getPower());
            telemetry.addData("Back left drive pstn   = ", hardware.backLeftDrive.getCurrentPosition());
// 	        telemetry.addData("Throttle                = ", "%1.2f", drive.throttleControl(gamepad1.left_trigger, drive.MIN_SPEED));
            telemetry.addData("Throttle                 = ", "%1.2f", throtle);
//            telemetry.addData("Front Scoop              = ", "%1.2f",gp2LT);
            telemetry.addData("Front Scoop              = ", servoVal);

//            telemetry.addData("Back Scoop               = ", "%1.2f",gp2RT);
            telemetry.update();
        }
    }

    void setMaxDrive(double motor) {
        if (Math.abs(motor) > maxDrive) {
            maxDrive = Math.abs(motor);
        }
    }
}
