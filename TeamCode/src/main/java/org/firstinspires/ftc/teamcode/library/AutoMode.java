package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class AutoMode extends LinearOpMode {
    protected static final boolean live = true;
    protected static final boolean unhook = true;
    protected static final boolean scan = true;

    protected Drive drive;
    protected DriveV2 drive2;
    protected LandingGear lg;
    protected ScoringArms scoop;
    protected TensorID tensor;
    protected Marker mark;
    protected  ElapsedTime runtime = new ElapsedTime();

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

        mark = new Marker(scoop,telemetry, this);

        tensor = new TensorIDImpl(telemetry, this);



        if(live) {
            telemetry.addLine("Retracting!!!");
            drive2.driveServoState(DriveV2.driveServoState.RETRACT);
        }

        telemetry.addLine("Ready to run.....");
        telemetry.update();

        //Wait for the system to start auto
        waitForStart();

        scoop.setFrontTargetPosition(150);
        if(unhook)lg.stand_up();
        scoop.waitForMoveEnd();

        telemetry.addData("Final scoop position",scoop.getIntakeArmPosition());
        telemetry.addLine("Scanning....");
        telemetry.update();

        GoldPosition gp = GoldPosition.NONE;
        if(scan){
            gp = tensor.getGoldPosition();
            switch(gp){
                case LEFT:
                    telemetry.addLine("LEFT");
                    break;
                case CENTER:
                    telemetry.addLine("CENTER");
                    break;
                case RIGHT:
                    telemetry.addLine("RIGHT");
                    break;
                case NONE:
                    telemetry.addLine("NONE");
                    break;
            }
        }

        telemetry.addLine("Unhooking....");
        telemetry.update();

        //scoop.setFrontTargetPosition(150);
        if(unhook)lg.unhook();
//        drive.turn(0);
//        sleep(500);
        //scoop.waitForMoveEnd();
        run(gp);

    }

    public abstract void run(GoldPosition gp);

    public abstract void head_to_crater();

    public abstract void head_to_depot();

    public abstract void hit_block(GoldPosition gp);
}
