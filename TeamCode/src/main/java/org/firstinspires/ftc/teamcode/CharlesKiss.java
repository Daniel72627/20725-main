package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Charles :)", group = "Robot")
public class CharlesKiss extends OpMode {

    public DcMotor BL, BR, FR, FL, SL, SR, UL,UR;
    public Servo SWL,SWR,SC,SB;

    public final int MaxExtension = 250;
    public final int MinExtension = 0;

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
        BR.setDirection(DcMotor.Direction.FORWARD);
        FR.setDirection(DcMotor.Direction.FORWARD);
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
        SL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        SR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        SR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        telemetry.addData("Status", "Initialized Yupi");
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {


        int currentPosition = SL.getCurrentPosition();
        int currentPosition1 = SR.getCurrentPosition();

        if (currentPosition1 >= MaxExtension || currentPosition >= MaxExtension) {

            if(gamepad1.left_stick_x < 0 ) {
                FL.setPower(gamepad1.left_stick_x);
                FR.setPower(gamepad1.left_stick_x);
            } else {
                SL.setPower(0);
                SR.setPower(0);
            }


        } else if (currentPosition1 <= MinExtension || currentPosition <= MinExtension) {

            if (gamepad1.left_stick_x > 0) {
                SL.setPower(gamepad1.left_stick_x);
                SR.setPower(gamepad1.left_stick_x);
            } else {
                SL.setPower(0);
                SR.setPower(0);
            }

        }

        FR.setPower(gamepad1.right_stick_y);
        BR.setPower(gamepad1.right_stick_y);

        FL.setPower(gamepad1.left_stick_y);
        BL.setPower(gamepad1.left_stick_y);

        UL.setPower(gamepad2.right_stick_y);
        UR.setPower(gamepad2.right_stick_y);

        // -----------------------------
        // Arm

        if(gamepad1.b) {
            SWL.setPosition(.96);
            SWR.setPosition(.04);
        }
        if(gamepad1.a) {
            SWL.setPosition(.4);
            SWR.setPosition(.6);
        }

        //-------------------------------------------------
        // Basket

        if(gamepad2.dpad_up) {
            SB.setPosition(.1); // basket
        }
        if(gamepad2.dpad_down) {
            SB.setPosition(.7); // basket
        }

        //---------------------------------------
        // Claw

        if(gamepad1.x) {
            SC.setPosition(0);
        }
        if(gamepad1.y) {
            SC.setPosition(.3);
        }

        //------------------------------------------------------------------------------------
        // Side to side
        if(gamepad1.left_bumper) {
            setMotors(1, -1,1,-1);
        }
        if(gamepad1.right_bumper) {
            setMotors(-1,1,-1,1);
        }


        telemetry.addData("Slide Left Encoder", SL.getCurrentPosition());
        telemetry.addData("Slide Right Encoder", SR.getCurrentPosition());
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

    public void setMotors(int fl, int fr, int br, int bl) {
        FL.setPower(fl);
        FR.setPower(fr);
        BR.setPower(br);
        BL.setPower(bl);
    }
}
