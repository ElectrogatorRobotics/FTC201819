package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

public class TensorIDImpl implements TensorID {

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private static final String VUFORIA_KEY = "Ac3oCab/////AAABmREMMJWRLkxMm4OITflE1z8RQ4ee4yZidzAzHq4xSudcJwaeO7SVeH1B9T/xDMkrokkJZADdb50jlZXPf27E+CCSQ+JtWreq2NDmSjcKwUbouJVs2WwCh4JJNhPubfFKNGBhTbKoMGbnyOeXnnVNtS2LkIYG1OCjUJ2tlIo/sHkmXJXyySbyxTpBAbvNhucWMLrz/xL/VAH01ZOsiEcqWzIFpSuRpcdtcxb8TDAYqnmGDeaDtbp/KtKUdDqSpudckUxjUvLzI7vDDuUKF99sqeD4HDtZ5DG8kEcov4zGdAX/TWjNrh/uR65Ee0mA+Xb/thqVbByP/E4RJ71J6do2RuAGfR4RuLqkhvk961KJK1/G";
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;

    TFObjectDetector tfod;
    VuforiaLocalizer vuforia;
    private Telemetry telemetry;

    boolean ready = false;
    LinearOpMode lom;
    HardwareMap hardwareMap;
    GoldPosition gp = GoldPosition.NONE;

    public TensorIDImpl(Telemetry telem, LinearOpMode lo){
        init(telem, lo);
    }

    @Override
    public boolean init(Telemetry telem,LinearOpMode lop) {
        telemetry = telem;
        lom = lop;

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CAMERA_CHOICE;
        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // initialise TensorFlow
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            //int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
             //       "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//            TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
            TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters();
            tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
            tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
            ready = true;
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }
        telemetry.update();
        return ready;
    }

    @Override
    public GoldPosition getGoldPosition() {
        /**
         * Find out where the gold mineral is.
         */
        /** Activate Tensor Flow Object Detection. */
        if (tfod != null) {
            tfod.activate();
        }

        if (lom.opModeIsActive()) {
            while (tfod != null && lom.opModeIsActive()) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());

                    // changed this to 2 instead of 3
                    if (updatedRecognitions.size() == 3) {
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

                        // we already know there are 3 minerals if we get to here
                        // first if we can't see the gold mineral, it has to be on the right
                        telemetry.addData("Gold = ",goldMineralX);
                        telemetry.addData("White1 = ",silverMineral1X);
                        telemetry.addData("White2 = ",silverMineral2X);

                        // Check if the gold is less then both silvers, if it is, it is left
                        // Next check if the gold is greater then both silvers, if it is it is right
                        // Otherwise it is in the middle.
                        if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                            telemetry.addData("Gold Mineral Position", "Left");
                            gp = GoldPosition.LEFT;
                            telemetry.update();
                            break; // drop out of loop
                        } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                            telemetry.addData("Gold Mineral Position", "Right");
                            gp = GoldPosition.RIGHT;
                            telemetry.update();
                            break; // drop out of loop
                        } else {
                            telemetry.addData("Gold Mineral Position", "Center");
                            gp = GoldPosition.CENTER;
                            telemetry.update();
                            break; // drop out of loop
                        }
                    }
                    tfod.deactivate();
                    telemetry.update();
                }
            }
        }
        return gp;
    }

    public void setHardwareMap(HardwareMap hm){
        hardwareMap = hm;
    }
}
