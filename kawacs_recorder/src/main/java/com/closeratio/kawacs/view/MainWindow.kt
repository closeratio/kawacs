/*
 * This file is part of Kawacs.
 *
 * Kawacs is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kawacs is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Kawacs.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.closeratio.kawacs.view

import com.closeratio.kawacs.NetworkListener
import com.closeratio.kawacs.model.ModelScope
import com.closeratio.kawacs.model.PlaybackStatus.*
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.Priority.ALWAYS
import javafx.scene.paint.Color
import org.kordamp.ikonli.javafx.FontIcon
import org.kordamp.ikonli.material.Material
import tornadofx.*
import kotlin.Double.Companion.POSITIVE_INFINITY

class MainWindow: Fragment("Kawacs Recorder") {

    private lateinit var fileTextField: TextField

    private lateinit var stopButton: Button
    private lateinit var pauseButton: Button
    private lateinit var playButton: Button
    private lateinit var recordButton: Button

    override val scope = super.scope as ModelScope

    private val networkListener = NetworkListener(scope.model)

    override val root = vbox {
        val model = scope.model

        menubar {
            menu("File") {
                item("Exit") {
                    action {
                        FX.primaryStage.close()
                    }
                }
            }
        }

        vbox(5) {
            padding = Insets(5.0)

            hbox(5) {
                alignment = Pos.CENTER
                label("File:")

                fileTextField = textfield {
                    isEditable = false
                    prefWidth = 200.0
                }

                button("Change...") {
                    action {

                    }
                }
            }

            hbox(5) {
                hgrow = ALWAYS
                maxWidth = POSITIVE_INFINITY

                stopButton = button(graphic = FontIcon(Material.STOP)) {
                    hgrow = ALWAYS
                    maxWidth = POSITIVE_INFINITY
                    disableProperty().bind(model.statusProperty.isEqualTo(STOPPED))

                    action {
                        model.status = STOPPED
                    }
                }

                pauseButton = button(graphic = FontIcon(Material.PAUSE)) {
                    hgrow = ALWAYS
                    maxWidth = POSITIVE_INFINITY
                    disableProperty().bind(model.statusProperty.isEqualTo(STOPPED)
                            .or(model.statusProperty.isEqualTo(PAUSED))
                            .or(model.statusProperty.isEqualTo(RECORDING)))

                    action {
                        model.status = PAUSED
                    }
                }

                playButton = button(graphic = FontIcon(Material.PLAY_ARROW)) {
                    hgrow = ALWAYS
                    maxWidth = POSITIVE_INFINITY
                    disableProperty().bind(model.statusProperty.isEqualTo({PLAYING})
                            .or(model.statusProperty.isEqualTo(RECORDING)))

                    action {
                        model.status = PLAYING
                    }
                }

                separator {
                    orientation = Orientation.VERTICAL
                }

                recordButton = button(graphic = FontIcon(Material.FIBER_MANUAL_RECORD)) {
                    hgrow = ALWAYS
                    maxWidth = POSITIVE_INFINITY

                    (graphic as FontIcon).fill = Color.RED

                    disableProperty().bind(model.statusProperty.isEqualTo(PLAYING)
                            .or(model.statusProperty.isEqualTo(PAUSED))
                            .or(model.statusProperty.isEqualTo(RECORDING)))

                    action {
                        model.status = RECORDING
                    }
                }
            }

        }

    }

    init {
        FX.primaryStage.onCloseRequest = EventHandler {
            networkListener.shutdown()
        }
    }

}

