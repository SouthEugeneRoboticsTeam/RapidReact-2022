package org.sert2521.rapidreact2022.subsytems

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.revrobotics.CANSparkMax
import edu.wpi.first.wpilibj.AnalogPotentiometer
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.Servo
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.commands.IdleClimber

object Climber : SubsystemBase() {
    private val staticClimberMotor = CANSparkMax(Sparks.STATIC_CLIMBER.id, Sparks.STATIC_CLIMBER.type)
    private val variableClimberMotor = CANSparkMax(Sparks.VARIABLE_CLIMBER.id, Sparks.VARIABLE_CLIMBER.type)
    private val variableActuator = WPI_TalonSRX(Talons.VARIABLE_ACTUATOR.id)

    private val staticDownLimitSwitch = DigitalInput(OnOffs.STATIC_CLIMBER_DOWN.id)
    private val variableDownLimitSwitch = DigitalInput(OnOffs.VARIABLE_CLIMBER_DOWN.id)

    private val potentiometer = AnalogPotentiometer(Potentiometers.VARIABLE_CLIMBER_ANGLE.id, Potentiometers.VARIABLE_CLIMBER_ANGLE.maxAngle, Potentiometers.VARIABLE_CLIMBER_ANGLE.zeroAngle)

    private val servoLeft = try { Servo(PWMS.SERVO_LEFT.id) } catch (e: Exception) { null }
    private val servoRight = try { Servo(PWMS.SERVO_RIGHT.id) } catch (e: Exception) { null }

    init {
        staticClimberMotor.inverted = Sparks.STATIC_CLIMBER.reversed
        variableClimberMotor.inverted = Sparks.VARIABLE_CLIMBER.reversed
        variableActuator.inverted = Talons.VARIABLE_ACTUATOR.reversed

        staticClimberMotor.encoder.positionConversionFactor = SparkEncoders.STATIC_CLIMBER.conversionFactor
        variableClimberMotor.encoder.positionConversionFactor = SparkEncoders.VARIABLE_CLIMBER.conversionFactor

        defaultCommand = IdleClimber()
    }

    val staticHeight
        get() = staticClimberMotor.encoder.position

    val variableHeight
        get() = variableClimberMotor.encoder.position

    val variableAngle
        get() = potentiometer.get()

    fun isAtBottomStatic(): Boolean {
        return staticDownLimitSwitch.get()
    }

    fun isAtBottomVariable(): Boolean {
        return variableDownLimitSwitch.get()
    }

    override fun periodic() {
        if(isAtBottomStatic()) {
            staticClimberMotor.encoder.position = 0.0
        }

        if(isAtBottomVariable()) {
            staticClimberMotor.encoder.position = 0.0
        }
    }

    fun setLock(locked: Boolean) {
        if (locked) {
            servoLeft?.set(SERVO_LOCK_LEFT)
            servoRight?.set(SERVO_LOCK_RIGHT)
        } else {
            servoLeft?.set(SERVO_UNLOCK_LEFT)
            servoRight?.set(SERVO_UNLOCK_RIGHT)
        }
    }

    fun elevateStatic(speed: Double): Boolean {
        return if(isAtBottomStatic() && speed > 0) {
            staticClimberMotor.stopMotor()
            true
        }else{
            staticClimberMotor.set(speed)
            false
        }
    }

    fun elevateVariable(speed: Double): Boolean {
        return if(isAtBottomVariable() && speed > 0) {
            variableClimberMotor.stopMotor()
            true
        }else{
            variableClimberMotor.set(speed)
            false
        }
    }

    fun actuateVariable(speed: Double): Boolean {
        return if((MIN_CLIMBER_ANGLE < variableAngle && speed < 0) || (MAX_CLIMBER_ANGLE > variableAngle && speed > 0)) {
            variableActuator.set(speed)
            true
        }else{
            variableActuator.stopMotor()
            false
        }
    }

    fun stop() {
        staticClimberMotor.stopMotor()
        variableClimberMotor.stopMotor()
        variableActuator.stopMotor()
    }
}