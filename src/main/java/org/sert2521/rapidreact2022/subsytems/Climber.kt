package org.sert2521.rapidreact2022.subsytems

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.revrobotics.CANSparkMax
import edu.wpi.first.wpilibj.AnalogPotentiometer
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.Servo
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.rapidreact2022.*
//import org.sert2521.rapidreact2022.commands.IdleClimber

object Climber : SubsystemBase() {
    private val staticClimberMotor = CANSparkMax(Sparks.STATIC_CLIMBER.id, Sparks.STATIC_CLIMBER.type)
    private val variableClimberMotor = CANSparkMax(Sparks.VARIABLE_CLIMBER.id, Sparks.VARIABLE_CLIMBER.type)
    private val variableActuator = WPI_TalonSRX(Talons.VARIABLE_ACTUATOR.id)

    private val staticDownLimitSwitch = DigitalInput(OnOffs.STATIC_CLIMBER_DOWN.id)
    private val variableDownLimitSwitch = DigitalInput(OnOffs.VARIABLE_CLIMBER_DOWN.id)

    private val potentiometer = AnalogPotentiometer(Potentiometers.VARIABLE_CLIMBER_ANGLE.id, Potentiometers.VARIABLE_CLIMBER_ANGLE.maxAngle, Potentiometers.VARIABLE_CLIMBER_ANGLE.zeroAngle)

    private val servoStatic = try { Servo(PWMS.SERVO_STATIC.id) } catch (e: Exception) { null }
    private val servoVariable = try { Servo(PWMS.SERVO_VARIABLE.id) } catch (e: Exception) { null }

    private val climbing = false

    init {
        staticClimberMotor.inverted = Sparks.STATIC_CLIMBER.reversed
        variableClimberMotor.inverted = Sparks.VARIABLE_CLIMBER.reversed
        variableActuator.inverted = Talons.VARIABLE_ACTUATOR.reversed

        staticClimberMotor.encoder.positionConversionFactor = SparkEncoders.STATIC_CLIMBER.conversionFactor
        variableClimberMotor.encoder.positionConversionFactor = SparkEncoders.VARIABLE_CLIMBER.conversionFactor

        //defaultCommand = IdleClimber()
    }

    val staticHeight
        get() = staticClimberMotor.encoder.position

    val variableHeight
        get() = variableClimberMotor.encoder.position

    val variableAngle
        get() = potentiometer.get()

    fun isAtBottomStatic(): Boolean {
        return !staticDownLimitSwitch.get()
    }

    fun isAtBottomVariable(): Boolean {
        return !variableDownLimitSwitch.get()
    }

    fun isStaticLocked(): Boolean {
        if (servoStatic == null) {
            return false
        }

        return SERVO_LOCK_STATIC - SERVO_TOLERANCE <= servoStatic.get() && servoStatic.get() <= SERVO_LOCK_STATIC + SERVO_TOLERANCE
    }

    fun isVariableLocked(): Boolean {
        if (servoVariable == null) {
            return false
        }

        return SERVO_LOCK_VARIABLE - SERVO_TOLERANCE <= servoVariable.get() && servoVariable.get() <= SERVO_LOCK_VARIABLE + SERVO_TOLERANCE
    }

    //Doesn't work
    override fun periodic() {
        if(isAtBottomStatic()) {
            staticClimberMotor.encoder.position = 0.0
        }

        if(isAtBottomVariable()) {
            variableClimberMotor.encoder.position = 0.0
        }
    }

    //Doesn't work
    fun setLockStatic(lock: Boolean) {
        if(lock) {
            servoStatic?.set(SERVO_LOCK_STATIC)
        } else {
            servoStatic?.set(SERVO_UNLOCK_STATIC)
        }
    }

    fun setLockVariable(lock: Boolean) {
        if(lock) {
            servoVariable?.set(SERVO_LOCK_VARIABLE)
        } else {
            servoVariable?.set(SERVO_UNLOCK_VARIABLE)
        }
    }
}