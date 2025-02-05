package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "N/A", group = "Robot")
public class DisplayEncoder extends OpMode {

    public DcMotor BL, BR, FR, FL, SL, SR, UL,UR;
    public Servo SWL,SWR,SC,SB;

    @Override
    public void init() {
        // Initialize motors
        BL = hardwareMap.get(DcMotor.class, "BL");
        BR = hardwareMap.get(DcMotor.class, "BR");
        FR = hardwareMap.get(DcMotor.class, "FR");
        FL = hardwareMap.get(DcMotor.class, "FL");

        SL = hardwareMap.get(DcMotor.class, "LH");
        SR = hardwareMap.get(DcMotor.class, "RH");

        UL = hardwareMap.get(DcMotor.class, "4b");
        UR = hardwareMap.get(DcMotor.class, "4b2");

        SWL = hardwareMap.get(Servo.class, "Lc");
        SWR = hardwareMap.get(Servo.class, "Rc");
        SC = hardwareMap.get(Servo.class, "L");
        SB = hardwareMap.get(Servo.class, "R");

        BL.setDirection(DcMotor.Direction.REVERSE);
        BR.setDirection(DcMotor.Direction.REVERSE);
        FR.setDirection(DcMotor.Direction.REVERSE);
        FL.setDirection(DcMotor.Direction.REVERSE);

        SL.setDirection(DcMotor.Direction.REVERSE);
        SR.setDirection(DcMotor.Direction.FORWARD);

        SWL.setDirection(Servo.Direction.FORWARD);
        SWR.setDirection(Servo.Direction.FORWARD);
        SC.setDirection(Servo.Direction.FORWARD);
        SB.setDirection(Servo.Direction.FORWARD);

        UL.setDirection(DcMotor.Direction.FORWARD);
        UR.setDirection(DcMotor.Direction.FORWARD);

        SL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        SR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        SL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        SR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        telemetry.addData("Status", "Initialized Yupi");
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {

        if(gamepad1.left_bumper) {
            setMotors(-1,1,1,-1);
        }

        if(gamepad1.right_bumper) {
            setMotors(1,-1,-1,1);
        }

        if(gamepad1.dpad_up){
            setMotors(1,1,1,1);
        }

        telemetry.addData("Slide Left Encoder", SL.getCurrentPosition());
        telemetry.addData("Slide Right Encoder", SR.getCurrentPosition());
        telemetry.addData("Front Left", FL.getCurrentPosition());
        telemetry.addData("Front Right", FR.getCurrentPosition());
        telemetry.addData("Back Left", BL.getCurrentPosition());
        telemetry.addData("Back Right", BR.getCurrentPosition());
        telemetry.addData("Slide Left Power", SL.getPower());
        telemetry.addData("Slide Right Power", SL.getPower());
        telemetry.addData("Front Left Power", FL.getPower());
        telemetry.addData("Front Right Power", FR.getPower());
        telemetry.addData("Back Left Power", BL.getPower());
        telemetry.addData("Back Right Power", BR.getPower());
        telemetry.update();



    }

    @Override
    public void stop() {
        setMotors(0,0,0,0);
        SL.setPower(0);
        SR.setPower(0);
        UL.setPower(0);
        UR.setPower(0);
    }

    public void setMotors(double fl, double fr, double br, double bl) {
        FL.setPower(fl/4);
        FR.setPower(fr/4);
        BR.setPower(br/4);
        BL.setPower(bl/4);
    }
}
