package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

//------------------------------------------------------------------------------
//
// DiamondBotHardware_IRI
//

/**
 * Provides a single hardware access point between custom op-modes and the
 * OpMode class for the Diamond Blades Robot.
 *
 * This class prevents the custom op-mode from throwing an exception at runtime.
 * If any hardware fails to map, a warning will be shown via telemetry data,
 * calls to methods will fail, but will not cause the application to crash.
 * @author SSI Robotics/Max Davy/Diamond Blades
 * @version 2016-06-17-15-48
 */
/**
 * each new motor needs the following code:
 * 1)
    private DcMotor v_motor_your_motor;
 * 2) in init():
    try
    {
        v_motor_your_motor = hardwareMap.dcMotor.get ("your_motor_map");
    }
    catch (Exception p_exeception)
    {
        m_warning_message ("your_motor_map");
        DbgLog.msg (p_exception.getLocalizedMessage ());

        v_motor_your_motor = null;
    }
 * 3)
    //--------------------------------------------------------------------------
    //get the power of the your motor
    double a_your_motor_power ()
    {
    double l_return = 0.0;//set to zero
    if (v_motor_your_motor != null)//if your motor mapped
    {
        l_return = v_motor_your_motor.getPower ();//set to the motor power
    }
    return l_return;//return the power
    }
 * 4)
    //--------------------------------------------------------------------------
    //Set your encoder to run, if the mode is appropriate
    public void run_using_your_motor_encoder()
    {
        if (v_motor_your_motor != null)
        {
            v_motor_your_motor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        }
    }
 * 5)
    //--------------------------------------------------------------------------
    //Set the your motor encoder to run, if the mode is appropriate.
    public void run_without_your_motor_encoder ()
    {
        if (v_motor_left_drive != null)
        {
            if (v_motor_left_drive.getMode () == DcMotorController.RunMode.RESET_ENCODERS)
            {
                v_motor_left_drive.setMode( DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
            }
        }
    }
 * 6)
    //--------------------------------------------------------------------------
    //Reset the your motor encoder.
    public void reset_your_motor_encoder ()
    {
        if (v_motor_left_drive != null)
        {
            v_motor_left_drive.setMode( DcMotorController.RunMode.RESET_ENCODERS);
        }
    }
 * 7)
    //--------------------------------------------------------------------------
    //Read the encoder count on your motor
    int a_your_motor_encoder_count ()
    {
        int l_return = 0;
        if (v_motor_your_motor != null)
        {
            l_return = v_motor_your_motor.getCurrentPosition ();
        }
        return l_return;
    }
 * 8)
    //--------------------------------------------------------------------------
    //get whether your motor encoder has reached the specified value
    boolean has_your_motor_encoder_reached(double p_count)
    {
        boolean l_return = false;// Assume failure.
        if (v_motor_your_motor != null)
        {
            if (Math.abs (v_motor_your_motor.getCurrentPosition ()) > p_count)// Has the encoder reached the specified values?
            {
                l_return = true;// Set the status to a positive indication.
            }
        }
        return l_return;// Return the status.
    }
 * 9)
    //--------------------------------------------------------------------------
    //get whether your motor encoder has finished reseting
    boolean has_your_motor_encoder_reset()
    {
        boolean l_return = false;// Assume failure.
        if (a_your_motor_encoder_count() == 0)// Has your motor encoder reached zero?
        {
            l_return = true;// Set the status to a positive indication.
        }
        return l_return;// Return the status.
    }
 * 10)
    //--------------------------------------------------------------------------
    //get your motor's power level
    double a_your_motor_power()
    {
        double l_return = 0.0;//assume it's stopped
        if (v_motor_your_motor != null)//if it's mapped
        {
            l_return = v_motor_your_motor.getPower ();//return power
        }
        return l_return;
    }
 *11)
    //--------------------------------------------------------------------------
    //set your motor power
    void m_your_motor_power (double p_level)
    {
        if (v_motor_your_motor != null)
        {
            v_motor_your_motor.setPower (p_level);
        }
    }
 */
public class DiamondBotHardware_IRI extends OpMode

{
    //--------------------------------------------------------------------------
    //
    // v_warning_generated
    //
    /**
     * Indicate whether a message is a available to the class user.
     */
    private boolean v_warning_generated = false;

    //--------------------------------------------------------------------------
    //
    // v_warning_message
    //
    /**
     * Store a message to the user if one has been generated.
     */
    private String v_warning_message;
    //--------------------------------------------------------------------------
    // motor variable declaration
    private DcMotor v_motor_left_drive;//left drive motor
    private DcMotor v_motor_right_drive;//right drive motor
    private DcMotor v_motor_arm;//arm motor
    private DcMotor v_motor_winder;//winch motor
    private DcMotor v_motor_tape;//tape shooter motor
    private DcMotor v_motor_flippers;//flipper motor

    //--------------------------------------------------------------------------
    // servo variable declaration
    private Servo v_servo_bumper;//front bumper
    private Servo v_servo_back_bumper;//back bumper
    private Servo v_servo_triggers;//triggers
    private Servo v_servo_other_triggers;//triggers

    //--------------------------------------------------------------------------
    public DiamondBotHardware_IRI()//The system calls this member when the class is instantiated.
    {
    }

    //--------------------------------------------------------------------------
    //initiation
    @Override public void init ()//this gets called as soon as the opmode is started (after the 'run' button is clicked)

    {
        //
        // Use the hardwareMap to associate class members to hardware ports.
        //
        // Note that the names of the devices (i.e. arguments to the get method)
        // must match the names specified in the configuration file created by
        // the FTC Robot Controller (Settings-->Configure Robot).
        //
        // The variable below is used to provide telemetry data to a class user.
        //
        double initial_bumper_position = 0.5;
        double initial_back_bumper_position = 0.5;
        v_warning_generated = false;
        v_warning_message = "Can't map; ";
        //--------------------------------------------------------------------------
        //motor map: for each motor, try to map; if not, send error.
        try
        {
            v_motor_left_drive = hardwareMap.dcMotor.get ("left_drive");
        }
        catch (Exception p_exeception)
        {
            m_warning_message ("left_drive");
            DbgLog.msg (p_exeception.getLocalizedMessage ());

            v_motor_left_drive = null;
        }

        try
        {
            v_motor_right_drive = hardwareMap.dcMotor.get ("right_drive");
            v_motor_right_drive.setDirection (DcMotor.Direction.REVERSE);
        }
        catch (Exception p_exeception)
        {
            m_warning_message ("right_drive");
            DbgLog.msg (p_exeception.getLocalizedMessage ());

            v_motor_right_drive = null;
        }
        try
        {
            v_motor_arm = hardwareMap.dcMotor.get ("arm");
        }
        catch (Exception p_exeception)
        {
            m_warning_message ("arm");
            DbgLog.msg (p_exeception.getLocalizedMessage ());

            v_motor_arm = null;
        }

        try
        {
            v_motor_winder = hardwareMap.dcMotor.get ("winder");
        }
        catch (Exception p_exeception)
        {
            m_warning_message ("winder");
            DbgLog.msg (p_exeception.getLocalizedMessage ());

            v_motor_winder = null;
        }

        try
        {
            v_motor_tape = hardwareMap.dcMotor.get ("tape");
        }
        catch (Exception p_exeception)
        {
            m_warning_message ("tape");
            DbgLog.msg (p_exeception.getLocalizedMessage ());

            v_motor_tape = null;
        }

        try
        {
            v_motor_flippers = hardwareMap.dcMotor.get ("flippers");
        }
        catch (Exception p_exeception)
        {
            m_warning_message ("flippers");
            DbgLog.msg (p_exeception.getLocalizedMessage ());

            v_motor_flippers = null;
        }

        //--------------------------------------------------------------------------
        //servo map: same as the motor map
        try
        {
            v_servo_bumper = hardwareMap.servo.get ("bumper");
            //v_servo_bumper.setPosition (initial_bumper_position);
        }
        catch (Exception p_exeception)
        {
            m_warning_message ("bumper");
            DbgLog.msg (p_exeception.getLocalizedMessage ());

            v_servo_bumper = null;
        }

        try
        {
            v_servo_back_bumper = hardwareMap.servo.get ("back_bumper");
            //v_servo_back_bumper.setPosition (initial_back_bumper_position);
        }
        catch (Exception p_exeception)
        {
            m_warning_message ("back_bumper");
            DbgLog.msg (p_exeception.getLocalizedMessage ());

            v_servo_back_bumper = null;
        }

        try
        {
            v_servo_triggers = hardwareMap.servo.get ("triggers");
            //v_servo_back_bumper.setPosition (initial_back_bumper_position);
        }
        catch (Exception p_exeception)
        {
            m_warning_message ("triggers");
            DbgLog.msg (p_exeception.getLocalizedMessage ());

            v_servo_triggers = null;
        }

        try
        {
            v_servo_other_triggers = hardwareMap.servo.get ("other_triggers");
            //v_servo_back_bumper.setPosition (initial_back_bumper_position);
        }
        catch (Exception p_exeception)
        {
            m_warning_message ("other_triggers");
            DbgLog.msg (p_exeception.getLocalizedMessage ());

            v_servo_other_triggers = null;
        }
    } // init

    //--------------------------------------------------------------------------
    //read warning_generated
    boolean a_warning_generated ()

    {
        return v_warning_generated;

    }

    //--------------------------------------------------------------------------
    //read warning_message
    String a_warning_message ()

    {
        return v_warning_message;

    }

    //--------------------------------------------------------------------------
    //set warning_message
    void m_warning_message (String p_exception_message)

    {
        if (v_warning_generated)
        {
            v_warning_message += ", ";
        }
        v_warning_generated = true;
        v_warning_message += p_exception_message;

    }

    //--------------------------------------------------------------------------
    //this gets called when opmode is enabled (after the 'init' button is clicked)
    @Override public void start ()

    {
        //
        // Only actions that are common to all Op-Modes (i.e. both automatic and
        // manual) should be implemented here.
        //
        // This method is designed to be overridden.
        //

    } // start

    //--------------------------------------------------------------------------
    //
    // loop
    //
    /**
     * Perform any actions that are necessary while the OpMode is running.
     *
     * The system calls this member repeatedly while the OpMode is running.
     */
    @Override public void loop ()

    {
        //
        // Only actions that are common to all OpModes (i.e. both auto and\
        // manual) should be implemented here.
        //
        // This method is designed to be overridden.
        //

    } // loop

    //--------------------------------------------------------------------------
    //gets called whn opmode is disabled.
    @Override public void stop ()
    {
        //
        // Nothing needs to be done for this method.
        //

    }

    //--------------------------------------------------------------------------
    //Scale the joystick input using a nonlinear algorithm.
    float scale_motor_power (float p_power)
    {
        float l_scale = 0.0f;// Assume no scaling.

        float l_power = Range.clip (p_power, -1, 1);// Ensure the values are legal.

        float[] l_array =
            { 0.00f, 0.06f, 0.12f, 0.18f, 0.24f
            , 0.30f, 0.36f, 0.42f, 0.48f, 0.54f
            , 0.60f, 0.66f, 0.72f, 0.78f, 0.84f
            , 0.90f, 0.96f
            };

        // Get the corresponding index for the specified argument/parameter.
        int l_index = (int)(l_power * 16.0);
        if (l_index < 0)
        {
            l_index = -l_index;
        }
        else if (l_index > 16)
        {
            l_index = 16;
        }

        if (l_power < 0)
        {
            l_scale = -l_array[l_index];
        }
        else
        {
            l_scale = l_array[l_index];
        }

        return l_scale;

    }
    //--------------------------------------------------------------------------
    //separate scaling for the arm motor (in case the arm power needs to be lower)
    float scale_arm_motor_power (float p_power)
    {
        float l_scale = 0.0f;// Assume no scaling.

        float l_power = Range.clip (p_power, -1, 1);// Ensure the values are legal.

        float[] l_array =
                { 0.00f, 0.06f, 0.12f, 0.18f, 0.24f
                , 0.30f, 0.36f, 0.42f, 0.48f, 0.54f
                , 0.60f, 0.66f, 0.72f, 0.78f, 0.84f
                , 0.90f, 1.00f
                };

        // Get the corresponding index for the specified argument/parameter.
        int l_index = (int)(l_power * 16.0);
        if (l_index < 0)
        {
            l_index = -l_index;
        }
        else if (l_index > 16)
        {
            l_index = 16;
        }

        if (l_power < 0)
        {
            l_scale = (float) (-l_array[l_index]*.8);
        }
        else
        {
            l_scale = (float) (l_array[l_index]*.8);
        }

        return l_scale;

    }

    //--------------------------------------------------------------------------
    //get the power of the left drive motor
    double a_left_drive_power ()
    {
        double l_return = 0.0;//set to zero

        if (v_motor_left_drive != null)//if the motor drive mapped
        {
            l_return = v_motor_left_drive.getPower ();//set to the motor power
        }

        return l_return;//return the power

    }

    //--------------------------------------------------------------------------
    //get the power of the right drive motor
    double a_right_drive_power ()
    {
        double l_return = 0.0;//set to zero

        if (v_motor_right_drive != null)//if the motor mapped
        {
            l_return = v_motor_right_drive.getPower ();//set to the motor power
        }

        return l_return;//return the power

    }

    //--------------------------------------------------------------------------
    //set the power of both drive motors
    void set_drive_power (double p_left_power, double p_right_power)

    {
        if (v_motor_left_drive != null)//if the left motor is mapped
        {
            v_motor_left_drive.setPower (p_left_power);//set the left drive power
        }
        if (v_motor_right_drive != null)//if the right motor is mapped
        {
            v_motor_right_drive.setPower(p_right_power);//set the right drive power
        }

    }

    //--------------------------------------------------------------------------
    //Set the arm encoder to run, if the mode is appropriate
    public void run_using_arm_encoder()

    {
        if (v_motor_arm != null)
        {
            v_motor_arm.setMode
                    (DcMotorController.RunMode.RUN_USING_ENCODERS
                    );
        }

    }

    //--------------------------------------------------------------------------
    //Set the winch encoder to run, if the mode is appropriate.
    public void run_using_winder_encoder()

    {
        if (v_motor_winder != null)
        {
            v_motor_winder.setMode
                    (DcMotorController.RunMode.RUN_USING_ENCODERS
                    );
        }

    }

    //--------------------------------------------------------------------------
    //Set the flipper encoder to run, if the mode is appropriate.
    public void run_using_flipper_encoder()

    {
        if (v_motor_flippers != null)
        {
            v_motor_flippers.setMode
                    (DcMotorController.RunMode.RUN_USING_ENCODERS
                    );
        }

    }

    //--------------------------------------------------------------------------
    //Set the left drive wheel encoder to run, if the mode is appropriate.
    public void run_using_left_drive_encoder ()

    {
        if (v_motor_left_drive != null)
        {
            v_motor_left_drive.setMode
                    ( DcMotorController.RunMode.RUN_USING_ENCODERS
                    );
        }

    }

    //--------------------------------------------------------------------------
    //Set the right drive wheel encoder to run, if the mode is appropriate.
    public void run_using_right_drive_encoder ()

    {
        if (v_motor_right_drive != null)
        {
            v_motor_right_drive.setMode
                    ( DcMotorController.RunMode.RUN_USING_ENCODERS
                    );
        }

    }
    //--------------------------------------------------------------------------
    //Set the right drive wheel encoder to run, if the mode is appropriate.
    public void run_using_tape_encoder ()

    {
        if (v_motor_tape != null)
        {
            v_motor_tape.setMode
                    ( DcMotorController.RunMode.RUN_USING_ENCODERS
                    );
        }

    }
    //--------------------------------------------------------------------------
    //Set all encoders to run, if the mode is appropriate.
    public void run_using_encoders ()

    {
        // Call other members to perform the action on all motors.
        run_using_left_drive_encoder ();
        run_using_right_drive_encoder();
        run_using_arm_encoder();
        run_using_winder_encoder();
        run_using_tape_encoder();
        run_using_flipper_encoder();
    }

    //--------------------------------------------------------------------------
    //Set the left drive wheel encoder to run, if the mode is appropriate.
    public void run_without_left_drive_encoder ()

    {
        if (v_motor_left_drive != null)
        {
            if (v_motor_left_drive.getMode () ==
                DcMotorController.RunMode.RESET_ENCODERS)
            {
                v_motor_left_drive.setMode
                    ( DcMotorController.RunMode.RUN_WITHOUT_ENCODERS
                    );
            }
        }

    }

    //--------------------------------------------------------------------------
    //Set the right drive wheel encoder to run, if the mode is appropriate.
    public void run_without_right_drive_encoder ()

    {
        if (v_motor_right_drive != null)
        {
            if (v_motor_right_drive.getMode () ==
                DcMotorController.RunMode.RESET_ENCODERS)
            {
                v_motor_right_drive.setMode
                    ( DcMotorController.RunMode.RUN_WITHOUT_ENCODERS
                    );
            }
        }

    }

    //--------------------------------------------------------------------------
    //Set both drive wheel encoders to run, if the mode is appropriate.
    public void run_without_drive_encoders ()

    {
        // Call other members to perform the action on both motors.
        run_without_left_drive_encoder ();
        run_without_right_drive_encoder ();

    }

    //--------------------------------------------------------------------------
    //Reset the left drive wheel encoder.
    public void reset_left_drive_encoder ()

    {
        if (v_motor_left_drive != null)
        {
            v_motor_left_drive.setMode
                ( DcMotorController.RunMode.RESET_ENCODERS
                );
        }

    }

    //--------------------------------------------------------------------------
    //Reset the right drive wheel encoder.
    public void reset_right_drive_encoder ()

    {
        if (v_motor_right_drive != null)
        {
            v_motor_right_drive.setMode
                    ( DcMotorController.RunMode.RESET_ENCODERS
                    );
        }

    }

    //--------------------------------------------------------------------------
    //Reset the arm motor encoder.
    public void reset_arm_encoder ()

    {
        if (v_motor_arm != null)
        {
            v_motor_arm.setMode
                    ( DcMotorController.RunMode.RESET_ENCODERS
                    );
        }

    }
    //--------------------------------------------------------------------------
    //Reset the winch motor encoder.
    public void reset_winder_encoder ()

    {
        if (v_motor_winder != null)
        {
            v_motor_winder.setMode
                    ( DcMotorController.RunMode.RESET_ENCODERS
                    );
        }

    }
    //--------------------------------------------------------------------------
    //Reset the tape shooter motor encoder.
    public void reset_tape_encoder ()

    {
        if (v_motor_tape != null)
        {
            v_motor_tape.setMode
                    ( DcMotorController.RunMode.RESET_ENCODERS
                    );
        }

    }

    //--------------------------------------------------------------------------
    //Reset the flipper motor encoder.
    public void reset_flipper_encoder ()

    {
        if (v_motor_flippers != null)
        {
            v_motor_flippers.setMode
                    ( DcMotorController.RunMode.RESET_ENCODERS
                    );
        }

    }

    //--------------------------------------------------------------------------
    //Reset both drive wheel encoders.
    public void reset_drive_encoders ()

    {
        // Reset the motor encoders on the drive wheels.
        reset_left_drive_encoder();
        reset_right_drive_encoder();

    }

    //--------------------------------------------------------------------------
    //Reset all motor encoders
    public void reset_all_encoders ()

    {
        // Call other members to perform the action on all motors
        reset_left_drive_encoder ();
        reset_right_drive_encoder();
        reset_arm_encoder();
        reset_winder_encoder();
        reset_tape_encoder();
        reset_flipper_encoder();
    }

    //--------------------------------------------------------------------------
    //Read the encoder count on the left drive motor
    int a_left_encoder_count ()
    {
        int l_return = 0;

        if (v_motor_left_drive != null)
        {
            l_return = v_motor_left_drive.getCurrentPosition ();
        }

        return l_return;

    }

    //--------------------------------------------------------------------------
    //Read the encoder count on the right drive motor
    int a_right_encoder_count ()

    {
        int l_return = 0;

        if (v_motor_right_drive != null)
        {
            l_return = v_motor_right_drive.getCurrentPosition ();
        }

        return l_return;

    }
    //--------------------------------------------------------------------------
    //Read the encoder count on the arm motor
    int a_arm_encoder_count()

    {
        int l_return = 0;

        if (v_motor_arm != null)
        {
            l_return = v_motor_arm.getCurrentPosition ();
        }

        return l_return;

    }
    //--------------------------------------------------------------------------
    //Read the encoder count on the winch motor
    int a_winder_encoder_count()

    {
        int l_return = 0;

        if (v_motor_winder != null)
        {
            l_return = v_motor_winder.getCurrentPosition ();
        }

        return l_return;

    }
    //--------------------------------------------------------------------------
    //Read the encoder count on the tape motor
    int a_tape_encoder_count ()

    {
        int l_return = 0;

        if (v_motor_tape != null)
        {
            l_return = v_motor_tape.getCurrentPosition ();
        }

        return l_return;

    }

    //--------------------------------------------------------------------------
    //Read the encoder count on the flipper motor
    int a_flipper_encoder_count ()

    {
        int l_return = 0;

        if (v_motor_flippers != null)
        {
            l_return = v_motor_flippers.getCurrentPosition ();
        }

        return l_return;

    }


    //--------------------------------------------------------------------------
    //get whether arm encoder has reached the specified value
    boolean has_arm_encoder_reached(double p_count)

    {
        // Assume failure.
        boolean l_return = false;

        if (v_motor_arm != null)
        {
            // Has the encoder reached the specified values?
            if (Math.abs (v_motor_arm.getCurrentPosition ()) > p_count)
            {
                // Set the status to a positive indication.
                l_return = true;
            }
        }
        // Return the status.
        return l_return;

    }

    //--------------------------------------------------------------------------
    //get whether winch encoder has reached the specified value
    boolean has_winch_encoder_reached(double p_count)

    {
        boolean l_return = false;// Assume failure.

        if (v_motor_winder != null)
        {
            if (Math.abs (v_motor_winder.getCurrentPosition ()) > p_count)// Have the encoders reached the specified values?
            {
                l_return = true;// Set the status to a positive indication.
            }
        }

        return l_return;// Return the status.
    }

    //--------------------------------------------------------------------------
    //get whether the arm encoder has finished reseting
    boolean has_arm_encoder_reset()
    {
        boolean l_return = false;// Assume failure.

        if (a_arm_encoder_count() == 0)// Has the left encoder reached zero?
        {
            l_return = true;// Set the status to a positive indication.
        }

        return l_return;// Return the status.

    }

    //--------------------------------------------------------------------------
    //get whether the winch encoder is finished resetting
    boolean has_winch_encoder_reset()
    {
        boolean l_return = false;// Assume failure.

        if (a_winder_encoder_count() == 0)// Has the right encoder reached zero?
        {
            l_return = true;// Set the status to a positive indication.
        }

        return l_return;// Return the status.

    }

    //--------------------------------------------------------------------------
    //get whether the flipper encoder is finished resetting
    boolean has_flipper_encoder_reset()
    {
        boolean l_return = false;// Assume failure.

        if (a_flipper_encoder_count() == 0)// Has the right encoder reached zero?
        {
            l_return = true;// Set the status to a positive indication.
        }

        return l_return;// Return the status.

    }

    //--------------------------------------------------------------------------
    //get the arm motor's power level
    double a_arm_power()
    {
        double l_return = 0.0;//assume it's stopped

        if (v_motor_arm != null)//if it's mapped
        {
            l_return = v_motor_arm.getPower ();//return power
        }

        return l_return;

    }
    //--------------------------------------------------------------------------
    //get the winch motor's power level
    double a_winch_power()
    {
        double l_return = 0.0;//assume it's stopped

        if (v_motor_winder != null)//if it's mapped
        {
            l_return = v_motor_winder.getPower ();//return power
        }

        return l_return;

    }

    //--------------------------------------------------------------------------
    //get the tape motor's power level
    double a_tape_power ()
    {
        double l_return = 0.0;//assume it's stopped

        if (v_motor_tape != null)//if the motor's mapped
        {
            l_return = v_motor_tape.getPower ();//return it's power
        }

        return l_return;

    }

    //--------------------------------------------------------------------------
    //get the flipper motor's power level
    double a_flipper_power ()
    {
        double l_return = 0.0;//assume it's stopped

        if (v_motor_flippers != null)//if the motor's mapped
        {
            l_return = v_motor_flippers.getPower ();//return it's power
        }

        return l_return;

    }

    //--------------------------------------------------------------------------
    //set arm power
    void m_arm_power (double p_level)
    {
        if (v_motor_arm != null)
        {
            v_motor_arm.setPower (p_level);
        }

    }
    //--------------------------------------------------------------------------
    //set winder power
    void m_winder_power (double p_level)
    {
        if (v_motor_winder != null)
        {
            v_motor_winder.setPower (p_level);
        }

    } // m_left_arm_power

    //--------------------------------------------------------------------------
    //set tape power
    void m_tape_power (double p_level)
    {
        if (v_motor_tape != null)
        {
            v_motor_tape.setPower (p_level);
        }

    }

    //--------------------------------------------------------------------------
    //set flipper motor power
    void m_flipper_power (double p_level)
    {
        if (v_motor_flippers != null)
        {
            v_motor_flippers.setPower (p_level);
        }

    }

    //--------------------------------------------------------------------------
    //Indicate whether the left drive motor's encoder has reached a value.
    boolean has_left_drive_encoder_reached (double p_count)

    {

        boolean l_return = false;// Assume failure

        if (v_motor_left_drive != null)
        {
            if (Math.abs (v_motor_left_drive.getCurrentPosition ()) > p_count)// Has the encoder reached the specified values
            {
                l_return = true;// Set the status to a positive indication.
            }
        }

        return l_return;// Return the status.

    }

    //--------------------------------------------------------------------------
    //Indicate whether the right drive motor's encoder has reached a value.
    boolean has_right_drive_encoder_reached (double p_count)

    {
        boolean l_return = false;// Assume failure.

        if (v_motor_right_drive != null)
        {
            if (Math.abs (v_motor_right_drive.getCurrentPosition ()) > p_count)// Have the encoders reached the specified values?
            {
                l_return = true;// Set the status to a positive indication.
            }
        }

        return l_return;// Return the status.

    }

    //--------------------------------------------------------------------------
    //Indicate whether both the drive motors' encoders have reached a value.
    boolean have_drive_encoders_reached( double p_left_count, double p_right_count)
    {
        boolean l_return = false;// Assume failure.

        if (has_left_drive_encoder_reached(p_left_count) &&
                has_right_drive_encoder_reached (p_right_count))// Have the encoders reached the specified values?
        {
            l_return = true;// Set the status to a positive indication.
        }

        return l_return;// Return the status.

    }

    //--------------------------------------------------------------------------
    //Indicate whether the left drive encoder is finished resetting
    boolean has_left_drive_encoder_reset ()
    {
        boolean l_return = false;// Assume failure.

        if (a_left_encoder_count() == 0)// Has the left encoder reached zero?
        {
            l_return = true;// Set the status to a positive indication.
        }

        return l_return;// Return the status.

    }

    //--------------------------------------------------------------------------
    //Indicate whether the left drive encoder has been completely reset.
    boolean has_right_drive_encoder_reset ()
    {
        boolean l_return = false;// Assume failure.

        if (a_right_encoder_count() == 0)// Has the right encoder reached zero?
        {
            l_return = true;// Set the status to a positive indication.
        }

        return l_return;// Return the status.

    } // has_right_drive_encoder_reset

    //--------------------------------------------------------------------------
    //get the front bumper position
    double a_bumper_position ()
    {
        double l_return = 0.0;

        if (v_servo_bumper != null)
        {
            l_return = v_servo_bumper.getPosition ();
        }

        return l_return;

    }

    //--------------------------------------------------------------------------
    //get the back bumper position
    double a_back_bumper_position ()
    {
        double l_return = 0.0;

        if (v_servo_back_bumper != null)
        {
            l_return = v_servo_back_bumper.getPosition ();
        }

        return l_return;

    }
    //--------------------------------------------------------------------------
    //get the trigger position
    double a_triggers_position ()
    {
        double l_return = 0.0;

        if (v_servo_triggers != null)
        {
            l_return = v_servo_triggers.getPosition ();
        }

        return l_return;

    }
    //--------------------------------------------------------------------------
    //get the front bumper position
    double a_other_triggers_position ()
    {
        double l_return = 0.0;

        if (v_servo_other_triggers != null)
        {
            l_return = v_servo_other_triggers.getPosition ();
        }

        return l_return;

    }


    //--------------------------------------------------------------------------
    //Change the bumper positions.
    void m_bumper_position (double p_position)
    {
        double l_position = Range.clip
            ( p_position
            , Servo.MIN_POSITION
            , Servo.MAX_POSITION
            );// Ensure the specific value is legal.

        if (v_servo_bumper != null)
        {
            v_servo_bumper.setPosition (l_position);// Set the value.
        }

    }
    //--------------------------------------------------------------------------
    //Change the back bumper position
    void m_back_bumper_position(double p_position)
    {
        double l_position = Range.clip
                ( p_position
                        , Servo.MIN_POSITION
                        , Servo.MAX_POSITION
                );// Ensure the specific value is legal.

        if (v_servo_back_bumper != null)
        {
            v_servo_back_bumper.setPosition (l_position);// Set the value.
        }

    }
    //--------------------------------------------------------------------------
    //Change the triggers positions
    void m_triggers_position (double p_position)
    {
        double l_position = Range.clip
                ( p_position
                        , Servo.MIN_POSITION
                        , Servo.MAX_POSITION
                );// Ensure the specific value is legal.

        if (v_servo_triggers != null)
        {
            v_servo_triggers.setPosition (l_position);// Set the value.
        }

    }
    //--------------------------------------------------------------------------
    //Change the triggers positions
    void m_other_triggers_position (double p_position)
    {
        double l_position = Range.clip
                ( p_position
                        , Servo.MIN_POSITION
                        , Servo.MAX_POSITION
                );// Ensure the specific value is legal.

        if (v_servo_other_triggers != null)
        {
            v_servo_other_triggers.setPosition (l_position);// Set the value.
        }

    }

} // PushBotHardware
