package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by jgp22 on 12/22/2017.
 */

public class QuadCopterTeleop extends LinearOpMode
{
    AircraftQuadCopterHardware Drone = new AircraftQuadCopterHardware();
    double Hook_Position = 0.00;
    static final double Hook_Speed = .01;
    static final double Hook_Starting_Position = 0.00;
    //public boolean Hover;
    public void runOpMode(){
        waitForStart();

        while (opModeIsActive()){
            Drone.RightTopCorner.setPower(gamepad1.left_stick_y);
            Drone.RightBottomCorner.setPower(gamepad1.left_stick_y);
            Drone.LeftBottomCorner.setPower(gamepad1.right_stick_y);
            Drone.LeftTopCorner.setPower(gamepad1.right_stick_y);



            if (gamepad1.left_trigger == 1){
                Hook_Position += Hook_Speed;
            }

            if (gamepad1.right_trigger == 1){
                Hook_Position -= Hook_Speed;
            }

            Drone.Hook.setPosition(Hook_Position);

            if (gamepad1.a){
               // Hover = true;
                Hover();
            }
        }

    }


    public void Hover(){
        if (opModeIsActive()){

                double CurrSpeed1 = Drone.LeftBottomCorner.getPower();
                double CurrSpeed2 = Drone.LeftTopCorner.getPower();
                double CurrSpeed3 = Drone.RightBottomCorner.getPower();
                double CurrSpeed4 = Drone.RightTopCorner.getPower();
                Drone.LeftBottomCorner.setPower(CurrSpeed1);
                Drone.RightTopCorner.setPower(CurrSpeed4);
                Drone.LeftTopCorner.setPower(CurrSpeed2);
                Drone.RightBottomCorner.setPower(CurrSpeed3);



        }

    }
}
