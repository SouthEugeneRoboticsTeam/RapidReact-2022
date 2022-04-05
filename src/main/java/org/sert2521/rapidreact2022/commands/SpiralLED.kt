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
            LEDStrip.setLEDHSV(i, 300, 255, ((1 - (i.toDouble() / (robotPreferences.ledLength - 1))) * 255).toInt()) // incresed scalar from 50 to 60 and s from 92 to 100, h from 275 to 280

        }
    }

    override fun execute() {
        LEDStrip.cycle(4.0)
    }

    override fun end(interrupted: Boolean) {
        LEDStrip.reset()
    }
}
