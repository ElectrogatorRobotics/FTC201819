package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class AutoMode extends LinearOpMode {
    protected static final boolean live = true;
    protected static final boolean stand = false;
    protected static final boolean scan = false;

    protected Drive drive;
    protected DriveV2 drive2;
    protected LandingGear lg;
    protected ScoringArms scoop;
    protected TensorID tensor;

    public void runOpMode() throws InterruptedException{
        // initialise the motors
        telemetry.addLine("Initialising... please wait.");
        telemetry.update();


        drive2 = new DriveV2_Impl();
        drive2.initDrive(hardwareMap);
        drive2.init_bno055IMU(hardwareMap);
        drive = new DriveImpl(drive2, telemetry,this);

        lg = new LandingGearImpl(drive2, drive,this);
        scoop = new ScoringArmsImpl();
        scoop.initScoringSystems(hardwareMap);

        if(live) {
            telemetry.addLine("Retracting!!!");
            drive2.driveServoState(DriveV2.driveServoState.RETRACT);
        }
        telemetry.update();

        //Wait for the system to start auto
        waitForStart();

        scoop.setFrontTargetPosition(130);

        lg.unhook();

        scoop.waitForMoveEnd();

        tensor = new TensorIDImpl(telemetry, this);
        GoldPosition gp = GoldPosition.NONE;
        if(scan){
            gp = tensor.getGoldPosition();
        }

        telemetry.addLine("Ready to start... thank you for waiting!");
        telemetry.update();

        run(gp);

    }

    public abstract void run(GoldPosition gp);

    public abstract void head_to_crater();

    public abstract void head_to_depot();

    public abstract void hit_block(GoldPosition gp);
}
