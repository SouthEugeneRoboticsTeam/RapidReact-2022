package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.robotPreferences
import org.sert2521.rapidreact2022.subsytems.LEDStrip

class SpiralLED : CommandBase() {
    init {
        addRequirements(LEDStrip)
    }

    override fun initialize() {
        for (i in 0 until robotPreferences.ledLength) {
            LEDStrip.setLEDHSV(i, 275, 92, ((1 - (i.toDouble() / (robotPreferences.ledLength - 1))) * 50).toInt())
        }
    }

    override fun execute() {
        LEDStrip.cycle(40.0)
    }

    override fun end(interrupted: Boolean) {
        LEDStrip.reset()
    }
}
