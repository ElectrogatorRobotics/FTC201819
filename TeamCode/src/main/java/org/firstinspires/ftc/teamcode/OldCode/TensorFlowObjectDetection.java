/* Copyright (c) 2018 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.OldCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.OldCode.library.Drive;
import org.firstinspires.ftc.teamcode.OldCode.library.DriveImpl;
import org.firstinspires.ftc.teamcode.OldCode.library.GoldPosition;
import org.firstinspires.ftc.teamcode.OldCode.library.ImageTargetVisible;
import org.firstinspires.ftc.teamcode.OldCode.library.LandingGear;
import org.firstinspires.ftc.teamcode.OldCode.library.LandingGearImpl;
import org.firstinspires.ftc.teamcode.OldCode.library.Marker;
import org.firstinspires.ftc.teamcode.OldCode.library.Scoops;
import org.firstinspires.ftc.teamcode.OldCode.library.ScoopsImpl;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;

/**
 * This 2018-2019 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the gold and silver minerals.
 * <p>
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 * <p>
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */
@Autonomous(name = "Autonomous")
@Disabled
public class TensorFlowObjectDetection extends LinearOpMode {
    private static final String VUFORIA_KEY = "Ac3oCab/////AAABmREMMJWRLkxMm4OITflE1z8RQ4ee4yZidzAzHq4xSudcJwaeO7SVeH1B9T/xDMkrokkJZADdb50jlZXPf27E+CCSQ+JtWreq2NDmSjcKwUbouJVs2WwCh4JJNhPubfFKNGBhTbKoMGbnyOeXnnVNtS2LkIYG1OCjUJ2tlIo/sHkmXJXyySbyxTpBAbvNhucWMLrz/xL/VAH01ZOsiEcqWzIFpSuRpcdtcxb8TDAYqnmGDeaDtbp/KtKUdDqSpudckUxjUvLzI7vDDuUKF99sqeD4HDtZ5DG8kEcov4zGdAX/TWjNrh/uR65Ee0mA+Xb/thqVbByP/E4RJ71J6do2RuAGfR4RuLqkhvk961KJK1/G";
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    // Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
    // We will define some constants and conversions here
    private static final float mmPerInch = 25.4f;
    private static final float mmFTCFieldWidth = (12 * 6) * mmPerInch;       // the width of the FTC field (from the center point to the outer panels)
    private static final float mmTargetHeight = (6) * mmPerInch;          // the height of the center of the target image above the floor

    // Select which camera you want use.  The FRONT camera is the one on the same side as the screen.
    // Valid choices are:  BACK or FRONT
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;

    private OpenGLMatrix lastLocation = null;
    private boolean targetVisible = false;

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    VuforiaLocalizer vuforia;
    TFObjectDetector tfod;

    // import some labels to help in determining where things are on the field
    ImageTargetVisible imageTarget;
    GoldPosition goldPosition;

    Drive drive = new DriveImpl();
    LandingGear landingGear = new LandingGearImpl();
    Scoops scoops = new ScoopsImpl();
    Marker marker = new Marker();
    ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addLine("Initialising... please wait.");
        telemetry.update();

        drive.setTelemetry(telemetry);
        drive.initMotors(hardwareMap);
        drive.initialiseIMU(hardwareMap);
        drive.passLinearOp(this);
        landingGear.init(hardwareMap, drive, this);
        scoops.init(hardwareMap, telemetry);

        // initialise Vuforia
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
         * If no camera monitor is desired, use the parameterless constructor instead (commented out below).
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CAMERA_CHOICE;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // initialise TensorFlow
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        // Load the data sets that for the trackable objects. These particular data
        // sets are stored in the 'assets' part of our application.
        VuforiaTrackables targetsRoverRuckus = this.vuforia.loadTrackablesFromAsset("RoverRuckus");
        VuforiaTrackable blueRover = targetsRoverRuckus.get(0);
        blueRover.setName("Blue-Rover");
        VuforiaTrackable redFootprint = targetsRoverRuckus.get(1);
        redFootprint.setName("Red-Footprint");
        VuforiaTrackable frontCraters = targetsRoverRuckus.get(2);
        frontCraters.setName("Front-Craters");
        VuforiaTrackable backSpace = targetsRoverRuckus.get(3);
        backSpace.setName("Back-Space");

        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targetsRoverRuckus);

        /**
         * In order for localization to work, we need to tell the system where each target is on the field, and
         * where the phone resides on the robot.  These specifications are in the form of <em>transformation matrices.</em>
         * Transformation matrices are a central, important concept in the math here involved in localization.
         * See <a href="https://en.wikipedia.org/wiki/Transformation_matrix">Transformation Matrix</a>
         * for detailed information. Commonly, you'll encounter transformation matrices as instances
         * of the {@link OpenGLMatrix} class.
         *
         * If you are standing in the Red Alliance Station looking towards the center of the field,
         *     - The X axis runs from your left to the right. (positive from the center to the right)
         *     - The Y axis runs from the Red Alliance Station towards the other side of the field
         *       where the Blue Alliance Station is. (Positive is from the center, towards the BlueAlliance station)
         *     - The Z axis runs from the floor, upwards towards the ceiling.  (Positive is above the floor)
         *
         * This Rover Ruckus sample places a specific target in the middle of each perimeter wall.
         *
         * Before being transformed, each target image is conceptually located at the origin of the field's
         *  coordinate system (the center of the field), facing up.
         */

        /**
         * To place the BlueRover target in the middle of the blue perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Then, we translate it along the Y axis to the blue perimeter wall.
         */
        OpenGLMatrix blueRoverLocationOnField = OpenGLMatrix
                .translation(0, mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0));
        blueRover.setLocation(blueRoverLocationOnField);

        /**
         * To place the RedFootprint target in the middle of the red perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Second, we rotate it 180 around the field's Z axis so the image is flat against the red perimeter wall
         *   and facing inwards to the center of the field.
         * - Then, we translate it along the negative Y axis to the red perimeter wall.
         */
        OpenGLMatrix redFootprintLocationOnField = OpenGLMatrix
                .translation(0, -mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180));
        redFootprint.setLocation(redFootprintLocationOnField);

        /**
         * To place the FrontCraters target in the middle of the front perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Second, we rotate it 90 around the field's Z axis so the image is flat against the front wall
         *   and facing inwards to the center of the field.
         * - Then, we translate it along the negative X axis to the front perimeter wall.
         */
        OpenGLMatrix frontCratersLocationOnField = OpenGLMatrix
                .translation(-mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90));
        frontCraters.setLocation(frontCratersLocationOnField);

        /**
         * To place the BackSpace target in the middle of the back perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Second, we rotate it -90 around the field's Z axis so the image is flat against the back wall
         *   and facing inwards to the center of the field.
         * - Then, we translate it along the X axis to the back perimeter wall.
         */
        OpenGLMatrix backSpaceLocationOnField = OpenGLMatrix
                .translation(mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90));
        backSpace.setLocation(backSpaceLocationOnField);

        /**
         * Create a transformation matrix describing where the phone is on the robot.
         *
         * The coordinate frame for the robot looks the same as the field.
         * The robot's "forward" direction is facing out along X axis, with the LEFT side facing out along the Y axis.
         * Z is UP on the robot.  This equates to a bearing angle of Zero degrees.
         *
         * The phone starts out lying flat, with the screen facing Up and with the physical top of the phone
         * pointing to the LEFT side of the Robot.  It's very important when you test this code that the top of the
         * camera is pointing to the left side of the  robot.  The rotation angles don't work if you flip the phone.
         *
         * If using the rear (High Res) camera:
         * We need to rotate the camera around it's long axis to bring the rear camera forward.
         * This requires a negative 90 degree rotation on the Y axis
         *
         * If using the Front (Low Res) camera
         * We need to rotate the camera around it's long axis to bring the FRONT camera forward.
         * This requires a Positive 90 degree rotation on the Y axis
         *
         * Next, translate the camera lens to where it is on the robot.
         * In this example, it is centered (left to right), but 110 mm forward of the middle of the robot, and 200 mm above ground level.
         */

        // TODO: 1/18/2019 find phone location on robot
        final int CAMERA_FORWARD_DISPLACEMENT = -171; // Camera is 171 mm back form the center
        final int CAMERA_VERTICAL_DISPLACEMENT = 431; // Camera is ~431 mm off the flour
        final int CAMERA_LEFT_DISPLACEMENT = 140;     // Camera is 140 mm left of the center

        OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES,
                        CAMERA_CHOICE == FRONT ? 90 : -90, 0, 0));

        /**  Let all the trackable listeners know where the phone is.  */
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        }

        telemetry.addLine("Vuforia is initialised! \n Ready to start!");
        telemetry.update();
        waitForStart();

        runtime.reset();

        if (opModeIsActive()) {
//            scoops.setFrontScoopPos(0.5);
//            sleep(1000);
//            scoops.setFrontScoopPos(0.0);
            scoops.backScoopDump();

            /**
             * Find out where the gold mineral is.
             */
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }

            if (opModeIsActive()) {
                while (tfod != null && opModeIsActive() && runtime.seconds() < 5) { // time out after 5 seconds
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());

                        // changed this to 2 instead of 3
                        if (updatedRecognitions.size() == 2) {
                            int goldMineralX = -1;
                            int silverMineral1X = -1;
                            int silverMineral2X = -1;
                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    goldMineralX = (int) recognition.getLeft();
                                } else if (silverMineral1X == -1) {
                                    silverMineral1X = (int) recognition.getLeft();
                                } else {
                                    silverMineral2X = (int) recognition.getLeft();
                                }
                            }

                            // we already know there are 2 minerals if we get to here
                            // first if we can't see the gold mineral, it has to be on the right
                            if (goldMineralX == -1) {
                                telemetry.addData("Gold Mineral Position", "Right");
                                telemetry.update();
                                goldPosition = GoldPosition.RIGHT;
                                break;
                            // if the gold mineral is less then the silver mineral, it is on the left, otherwise it is in the center.
                            } else if (goldMineralX > silverMineral1X) {
                                telemetry.addData("Gold Mineral Position", "Center");
                                telemetry.update();
                                goldPosition = GoldPosition.CENTER;
                                break;
                            } else {
                                telemetry.addData("Gold Mineral Position", "Left");
                                telemetry.update();
                                goldPosition = GoldPosition.LEFT;
                                break;
                            }

                        }
                        telemetry.update();
                    }

                }
                scoops.backScoopDown();

                deploy();

                sleep(1000); //debugging

                telemetry.addLine("turning 45 deg ccw");
                telemetry.update();
                sleep(1000);
                drive.turn(-45);
                drive.forward(24);

            // disable TensorFlow
                tfod.deactivate();

                /**
                 * now it is time to detect the image target to find out which side where the robot is.
                 */
                telemetry.addLine("activating Vuforia");
                telemetry.update();

                targetsRoverRuckus.activate();
                targetVisible = false;
                while (!targetVisible && opModeIsActive()) {
                    for (VuforiaTrackable trackable : allTrackables) {
                        if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                            telemetry.addData("Visible Target", trackable.getName());
                            targetVisible = true;
                            if (trackable.getTrackables() == targetsRoverRuckus.get(0)) {
                                // rover target is visible
                                imageTarget = ImageTargetVisible.BLUE_ROVER;
                            } else if (trackable.getTrackables() == targetsRoverRuckus.get(1)) {
                                // footprint target is visible
                                imageTarget = ImageTargetVisible.RED_FOOTPRINT;
                            } else if (trackable.getTrackables() == targetsRoverRuckus.get(2)) {
                                // craters target is visible
                                imageTarget = ImageTargetVisible.FRONT_CRATERS;
                            } else {
                                // space target is visible
                                imageTarget = ImageTargetVisible.BACK_SPACE;
                            }

                            // getUpdatedRobotLocation() will return null if no new information is available since
                            // the last time that call was made, or if the trackable is not currently visible.
                            OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
                            if (robotLocationTransform != null) {
                                lastLocation = robotLocationTransform;
                            }
                            break;
                        }
                    }
                    // display the chordates of the image that is visible
                    if (targetVisible) {
                        // express position (translation) of robot in inches.
                        VectorF translation = lastLocation.getTranslation();
                        telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                                translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);

                        // express the rotation of the robot in degrees.
                        Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
                        telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
                    } else {
                        telemetry.addData("Visible Target", "none");
                    }
                    telemetry.update();
                    if (targetVisible)
                        break;
                }
            }
            // deactivate Vuforia
            targetsRoverRuckus.deactivate();
            // drive to 32 inches from the center of the field
            driveToDistanceFromTarget(lastLocation, 32);
            // drive to the mineral
            driveToGold(goldPosition, imageTarget);
            marker.KickOutTheMrker();
            //back out of the depo so our alliance partner can score
            moveOutOfDepo(imageTarget);
        }
    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters();
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }

    void driveToGold (GoldPosition goldPosition, ImageTargetVisible imageTarget) {
        int distnaceToGold = 24;
        switch (goldPosition) {
            case LEFT:
                drive.turn(-90);
                drive.forward(distnaceToGold);
                pickupGold();

                if (imageTarget == ImageTargetVisible.RED_FOOTPRINT || imageTarget == ImageTargetVisible.BLUE_ROVER && opModeIsActive()) {
                    telemetry.addLine("taking the long way around");
//                    telemetry.addLine(imageTarget.name()+ "is visible");
                    telemetry.update();
                    drive.turn(30);
                    drive.forward(30);
                } else {
                    drive.forward(-24);
                    drive.turn(-90);
                    drive.forward(48);
                    drive.turn(135);
                    drive.forward(24);
                }
                break;

            case CENTER:
                drive.forward(14.5);
                drive.turn(-90);
                pickupGold();

                if (imageTarget == ImageTargetVisible.RED_FOOTPRINT || imageTarget == ImageTargetVisible.BLUE_ROVER && opModeIsActive()) {
                    telemetry.addLine("taking the long way around");
//                    telemetry.addLine(imageTarget.name()+ "is visible");
                    telemetry.update();
                    drive.forward(60);
                    drive.turn(180);
                } else {
                    drive.forward(-24);
                    drive.turn(-90);
                    drive.forward(62.5);
                    drive.turn(135);
                    drive.forward(24);
                }
                break;

            case RIGHT:
                drive.forward(29);
                drive.turn(-90);
                pickupGold();

                if (imageTarget == ImageTargetVisible.RED_FOOTPRINT || imageTarget == ImageTargetVisible.BLUE_ROVER && opModeIsActive()) {
                    telemetry.addLine("taking the long way around");
//                    telemetry.addLine(imageTarget.name()+ "is visible");
                    telemetry.update();
                    drive.turn(-30);
                    drive.forward(30);
                } else {
                    drive.forward(-24);
                    drive.turn(-90);
                    drive.forward(77);
                    drive.turn(135);
                    drive.forward(24);
                }
                break;

                default:
                    if (imageTarget == ImageTargetVisible.RED_FOOTPRINT || imageTarget == ImageTargetVisible.BLUE_ROVER) {
                        telemetry.addLine("Can't find minerals! \n driving to depo");
                        telemetry.update();

                        drive.forward(24);
                        drive.turn(-90);
                        drive.forward(60);
                        drive.turn(180);
                        marker.KickOutTheMrker();
                        drive.forward(24); // move out of depo
                    } else if (imageTarget == ImageTargetVisible.FRONT_CRATERS || imageTarget == ImageTargetVisible.BACK_SPACE) {
                        telemetry.addLine("Can't find minerals! \n driving to depo");
                        telemetry.update();

                        drive.forward(48);
                        drive.turn(180);
                        marker.KickOutTheMrker();
                        drive.forward(24); // move out of depo
                    } else {
                        telemetry.addLine("nothing found killing in 2 seconds");
                        telemetry.update();
                        sleep(2000);
                        stop(); // kill the opmode, no image or minerals found.
                    }
        }
    }

    void driveToDistanceFromTarget(OpenGLMatrix lastLocation, double distanceFromCenterOfField) {
        VectorF traslation;
        double distanceFromTarget = 0, distanceToDrive = 0;
        traslation = lastLocation.getTranslation();
        distanceFromTarget = traslation.get(1) / mmPerInch; // get the Y distance from the target in inches
        distanceToDrive = (distanceFromCenterOfField-distanceFromTarget);
        drive.forward(distanceToDrive);
    }

    void moveOutOfDepo (ImageTargetVisible imageTarget) {
        telemetry.addLine("Moving out of depo");
        telemetry.update();

        drive.forward(24);
    }

    void pickupGold () {
        scoops.runRubberBandWheel(1.0);
        scoops.setFrontScoopPos(0.25);
        sleep(500);
        scoops.setFrontScoopPos(0.0);
        scoops.runRubberBandWheel(0.0);
        scoops.setFrontScoopPos(-0.5);
        sleep(500);
        scoops.setFrontScoopPos(0.0);
    }

    void deploy () {
        scoops.backScoopDown();
        landingGear.stand_up();
        landingGear.deploy();
    }
}
