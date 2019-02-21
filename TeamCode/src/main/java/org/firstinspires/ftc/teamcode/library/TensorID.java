package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public interface TensorID {

    boolean initTensorID(Telemetry telem, Scoops scop, LinearOpMode lop);

    GoldPosition getGoldPosition();

    void setHardwareMap(HardwareMap hm);
}
