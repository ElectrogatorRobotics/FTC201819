
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.library.AutoMode;
import org.firstinspires.ftc.teamcode.library.Drive;
import org.firstinspires.ftc.teamcode.library.GoldPosition;
import org.firstinspires.ftc.teamcode.library.LandingGear;

/**
 * Created by mira on 11/26/2018.
 */

@Autonomous(name = "Crater")
//@Disabled
public class Auto_CraterDirect extends AutoMode {

    @Override
    public void run(GoldPosition gp) {
        scoop.setFrontTargetPosition(90);
        sleep(1000);
        drive.forward(8);
        sleep(2000);
    }

    @Override
    public void head_to_crater() {

    }

    @Override
    public void head_to_depot() {

    }

    @Override
    public void hit_block(GoldPosition gp) {

    }
}
