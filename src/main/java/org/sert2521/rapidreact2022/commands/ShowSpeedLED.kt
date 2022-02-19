package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.LED_LENGTH
import org.sert2521.rapidreact2022.robotPreferences
import org.sert2521.rapidreact2022.subsytems.LEDStrip
import org.sert2521.rapidreact2022.subsytems.Shooter

class ShowSpeedLED : CommandBase() {
    init {
        addRequirements(LEDStrip)
    }

    override fun initialize() {
        for (i in 0 until LED_LENGTH) {
            LEDStrip.setLEDHSV(i, 275, 94, ((1 - (i.toDouble() / (LED_LENGTH - 1))) * 50).toInt())
        }
    }

    override fun execute() {
        LEDStrip.cycle((Shooter.wheelSpeed / robotPreferences.shooterRPM) * 20.0)
    }

    override fun end(interrupted: Boolean) {
        LEDStrip.reset()
    }
}
