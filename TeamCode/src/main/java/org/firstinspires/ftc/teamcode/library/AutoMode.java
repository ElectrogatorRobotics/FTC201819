package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class AutoMode extends LinearOpMode {
    protected static final boolean live = false;
    protected static final boolean stand = false;
    protected static final boolean scan = false;

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
        lg = new LandingGearImpl(hardwareMap, drive,this);
        scoop = new ScoopsImpl(hardwareMap, telemetry);

        telemetry.addLine("Retracting!!!");
        telemetry.update();

        tensor = new TensorIDImpl(telemetry, scoop, this);
        GoldPosition gp = GoldPosition.NONE;
        if(scan){
            gp = tensor.getGoldPosition();
        }

        telemetry.addLine("Ready to start... thank you for waiting!");
        telemetry.update();

        waitForStart();

        unhook();

        scoopDown();

        run(gp);

    }

    public void unhook(){
        lg.unhook();
    }

    public void scoopDown(){
        scoop.moveFrontScoop(1000);
    }

    public abstract void run(GoldPosition gp);

    public abstract void head_to_crater();

    public abstract void head_to_depot();

    public abstract void hit_block(GoldPosition gp);
}
