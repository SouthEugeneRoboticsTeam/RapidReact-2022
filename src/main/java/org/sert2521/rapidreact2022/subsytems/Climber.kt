package org.sert2521.rapidreact2022.subsytems

import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.revrobotics.CANSparkMax
import edu.wpi.first.wpilibj.AnalogPotentiometer
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.Servo
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.rapidreact2022.*
import java.lang.System.currentTimeMillis

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

    private var staticLocked = false
    private var staticLockedUpdate = 0L
    private var variableLocked = false
    private var variableLockedUpdate = 0L

    private var forceLocked = true
    private val startTime = currentTimeMillis()

    init {
        staticClimberMotor.inverted = Sparks.STATIC_CLIMBER.reversed
        variableClimberMotor.inverted = Sparks.VARIABLE_CLIMBER.reversed
        variableActuator.inverted = Talons.VARIABLE_ACTUATOR.reversed

        staticClimberMotor.idleMode = CANSparkMax.IdleMode.kBrake
        variableClimberMotor.idleMode = CANSparkMax.IdleMode.kBrake
        variableActuator.setNeutralMode(NeutralMode.Brake)

        staticClimberMotor.encoder.positionConversionFactor = SparkEncoders.STATIC_CLIMBER.conversionFactor
        variableClimberMotor.encoder.positionConversionFactor = SparkEncoders.VARIABLE_CLIMBER.conversionFactor

        lock()

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
        return if(currentTimeMillis() - staticLockedUpdate >= LOCK_TIME * 1000) {
            staticLocked
        } else {
            false
        }
    }

    fun isVariableLocked(): Boolean {
        return if(currentTimeMillis() - variableLockedUpdate >= LOCK_TIME * 1000) {
            variableLocked
        } else {
            false
        }
    }

    fun forceLock() {
        forceLocked = true
        stop()
    }

    override fun periodic() {
        if(isAtBottomStatic()) {
            staticClimberMotor.encoder.position = 0.0
        }

        if(isAtBottomVariable()) {
            variableClimberMotor.encoder.position = 0.0
        }

        if(currentTimeMillis() - startTime >= (150 - LOCK_TIME) * 1000) {
            forceLock()
        }
    }

    fun setLockStatic(lock: Boolean) {
        if(lock != staticLocked) {
            if(lock) {
                servoStatic?.set(SERVO_LOCK_STATIC)
                staticLocked = true
                staticLockedUpdate = currentTimeMillis()
            } else {
                servoStatic?.set(SERVO_UNLOCK_STATIC)
                staticLocked = false
            }
        }
    }

    fun setLockVariable(lock: Boolean) {
        if(lock != variableLocked) {
            if(lock) {
                servoStatic?.set(SERVO_LOCK_VARIABLE)
                variableLocked = true
                variableLockedUpdate = currentTimeMillis()
            } else {
                servoVariable?.set(SERVO_UNLOCK_VARIABLE)
                variableLocked = false
            }
        }
    }

    fun setStaticSpeed(amount: Double) {
        var offset = 0.0
        if(climbing) {
            offset = CLIMBER_MAINTAIN
        }

        if(isStaticLocked() || (amount < 0.0 && staticHeight <= MIN_CLIMBER_HEIGHT) || (amount > 0.0 && staticHeight >= MAX_CLIMBER_HEIGHT)) {
            staticClimberMotor.set(offset)
        } else {
            staticClimberMotor.set(amount + offset)
        }
    }

    fun setVariableSpeed(amount: Double) {
        var offset = 0.0
        if(climbing) {
            offset = CLIMBER_MAINTAIN
        }

        if(isStaticLocked() || (amount < 0.0 && variableHeight <= MIN_CLIMBER_HEIGHT) || (amount > 0.0 && variableHeight >= MAX_CLIMBER_HEIGHT)) {
            variableClimberMotor.set(offset)
        } else {
            variableClimberMotor.set(amount + offset)
        }
    }

    fun setAngleSpeed(amount: Double) {
        if((amount < 0.0 && variableAngle <= MIN_CLIMBER_ANGLE) || (amount > 0.0 && variableAngle >= MAX_CLIMBER_ANGLE)) {
            variableActuator.set(0.0)
        } else {
            variableActuator.set(amount)
        }
    }

    fun lock() {
        setLockStatic(true)
        setLockVariable(true)
    }

    fun stop() {
        lock()
        setStaticSpeed(0.0)
        setVariableSpeed(0.0)
        setAngleSpeed(0.0)
    }
}