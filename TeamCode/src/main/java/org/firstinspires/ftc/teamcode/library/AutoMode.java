package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class AutoMode extends LinearOpMode {
    protected static final boolean live = false;
    protected static final boolean stand = false;
    protected static final boolean scan = true;

    protected Drive drive;
    protected LandingGear lg;
    protected Marker mark;
    protected Scoops scoop;
    protected TensorID tensor;

    public void runOpMode() throws InterruptedException{
        // initialise the motors
        telemetry.addLine("Initialising... please wait.");
        telemetry.update();

        drive = new DriveImpl(hardwareMap,telemetry,this);
        mark = new Marker(hardwareMap,telemetry);
        lg = new LandingGearImpl(hardwareMap,drive,this);
        scoop = new ScoopsImpl(hardwareMap, telemetry);

        telemetry.addLine("Retracting!!!");
        telemetry.update();
        if(live) lg.retract();///!!!Illegal?

        tensor = new TensorIDImpl(telemetry, scoop, this);
        GoldPosition gp = GoldPosition.UNKNOWN;
        if(scan){
            gp = tensor.getGoldPosition();
        }

        telemetry.addLine("Ready to start... thank you for waiting!");
        telemetry.update();

        waitForStart();

        unhook();

        run(gp);

    }

    public void unhook(){
        if(live) {
            if(stand)lg.stand_up();
            else{
                lg.deploy();
            }
            drive.turn(-20);
            drive.forward(2);
            if(stand)
                lg.deploy();
            drive.turn(20);
        }
    }

    public abstract void run(GoldPosition gp);

    public abstract void head_to_crater();

    public abstract void head_to_depot();

    public abstract void hit_block(GoldPosition gp);
}
