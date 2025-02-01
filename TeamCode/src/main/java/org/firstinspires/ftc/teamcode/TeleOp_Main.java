package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TeleOp_Main", group = "Robot")
public class TeleOp_Main extends OpMode {

    public DcMotor BL, BR, FR, FL, SL, SR, UL,UR;
    public boolean d_up, d_down, d_left, d_right;
    public Servo SWL,SWR,SC,SB;
    boolean a_pressed = false;  // Keep track of whether the button was toggled or not
    boolean last_a_state = false;
    boolean b_pressed = false;  // Keep track of whether the button was toggled or not
    boolean last_b_state = false;

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
        SR.setDirection(DcMotor.Direction.REVERSE);

        SWL.setDirection(Servo.Direction.FORWARD);
        SWR.setDirection(Servo.Direction.FORWARD);
        SC.setDirection(Servo.Direction.FORWARD);
        SB.setDirection(Servo.Direction.FORWARD);

        UL.setDirection(DcMotor.Direction.FORWARD);
        UR.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Status", "Initialized Yupi");
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        d_up = gamepad1.dpad_up;
        d_down = gamepad1.dpad_down;
        d_right = gamepad1.dpad_right;
        d_left = gamepad1.dpad_left;
        boolean current_a_state = gamepad1.a;
        boolean current_b_state = gamepad1.b;


        if (d_up) {
            setMotors(1, 1, 1, 1);  // forward
        } else if (d_down) {
            setMotors(-1, -1, -1, -1);  // down
        } else if (d_left) {
            setMotors(-1, 1, -1, 1);  // left
        } else if (d_right) {
            setMotors(1, -1, 1, -1);  // right
        } else {
            setMotors(0, 0, 0, 0);
        }

        if(gamepad1.left_bumper) {
            setMotors(-1,1,1,-1);
        }

        if(gamepad1.right_bumper) {
            setMotors(1,-1,-1,1);
        }

        SL.setPower(gamepad1.right_stick_y);
        SR.setPower(gamepad1.right_stick_y);

        UL.setPower(gamepad1.left_stick_y);
        UR.setPower(gamepad1.left_stick_y);

        if(gamepad1.right_trigger > .2) {
            SWL.setPosition(.96);
            SWR.setPosition(.04);
        }

        if(gamepad1.left_trigger > .2) {
            SWL.setPosition(.4);
            SWR.setPosition(.6);
        }

        if (current_a_state && !last_a_state) {  // If the A button was just pressed
            a_pressed = !a_pressed;  // Toggle the claw
        }
        if (current_b_state && !last_b_state) {  // If the A button was just pressed
            b_pressed = !b_pressed;  // Toggle the claw
        }

        if (a_pressed) {
            SC.setPosition(0);  // Open the claw
        } else {
            SC.setPosition(0.3);  // Close the claw
        }

        if (b_pressed) {
            SB.setPosition(0.1);  // up the basket // higher down // lower up
        } else {
            SB.setPosition(0.7);  // down the basket
        }

        last_a_state = current_a_state;
        last_b_state = current_b_state;
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
