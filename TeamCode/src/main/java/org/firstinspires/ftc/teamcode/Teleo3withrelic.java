package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.util.zip.GZIPInputStream;

/**
 * Created by jgp22 on 11/6/2017.
 */
@TeleOp(name = "Teleop3")
public class Teleo3 extends LinearOpMode {
    Hardware7039 robot = new Hardware7039();
    ElapsedTime runtime = new ElapsedTime();
    double GripperPosition = robot.Gripper_Home;
    double armPosition = robot.TELEOP_ARM;
    final double GripperSpeed = .1;
    final double ARM_SPEED = 0.01;
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        telemetry.addData("Say", "Waiting For Start");
        waitForStart();
        while (opModeIsActive()) {
            robot.LeftMotor.setPower(gamepad1.right_stick_y);
            robot.RightMotor.setPower(gamepad1.left_stick_y);

            while (gamepad1.right_stick_button){
                robot.LeftMotor.setPower(-0.3);
            }
            robot.LeftMotor.setPower(0);

            while (gamepad1.left_stick_button){
                robot.RightMotor.setPower(-0.3);
            }
            robot.RightMotor.setPower(0);

            if (gamepad1.a) {
                robot.GlyphLifter.setPower(-0.6);
            } else if (gamepad1.y) {
                robot.GlyphLifter.setPower(0.6);
            }
            if (gamepad1.left_trigger == 1) {
                robot.GlyphLifter.setPower(0);
            }
            if (gamepad1.x){
                GripperPosition += GripperSpeed;
            }
            else if (gamepad1.b) {
                GripperPosition -= GripperSpeed;
            }
            GripperPosition = Range.clip(GripperPosition, robot.Gripper_Min_Range, robot.Gripper_Max_Range);
            robot.GlyphGripper.setPosition(GripperPosition);
            telemetry.addData("GlyphGripper", GripperPosition);
            telemetry.update();

            if (gamepad1.left_bumper) {
                armPosition += ARM_SPEED;
            }
            else if (gamepad1.right_bumper) {
                armPosition -= ARM_SPEED;
            }
            armPosition = Range.clip(armPosition, robot.ARM_MIN_RANGE, robot.ARM_MAX_RANGE);
            robot.Jewel.setPosition(armPosition);
            telemetry.addData("Jewel", armPosition);
            telemetry.update();

           /* if (gamepad1.right_trigger == 1){
                robot.RightMotor.setDirection(DcMotor.Direction.REVERSE);
                robot.LeftMotor.setDirection(DcMotor.Direction.FORWARD);
            }*/
        }
    }
}
