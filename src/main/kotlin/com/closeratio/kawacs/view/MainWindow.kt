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

import com.closeratio.kawacs.model.Entity
import com.closeratio.kawacs.model.ModelScope
import javafx.geometry.Insets
import javafx.geometry.Pos.CENTER
import javafx.scene.layout.Priority.ALWAYS
import tornadofx.*
import kotlin.Double.Companion.POSITIVE_INFINITY

class MainWindow: Fragment("Kawacs") {

    override val scope = super.scope as ModelScope

    override val root = vbox {

        menubar {
            menu("File") {

            }
            menu("Tools") {

            }
            menu("Debug") {

            }
        }

        hbox(5) {

            padding = Insets(5.0)

            titledpane("Entities", collapsible = false) {
                vbox(5) {
                    padding = Insets(5.0)
                    label("Blue") {
                        alignment = CENTER
                        hgrow = ALWAYS
                        maxWidth = POSITIVE_INFINITY
                    }
                    listview<Entity> {  }

                    label("Red") {
                        alignment = CENTER
                        hgrow = ALWAYS
                        maxWidth = POSITIVE_INFINITY
                    }
                    listview<Entity> {  }
                }
            }

            titledpane("Map", collapsible = false) {

                pane {
                    minWidth = 800.0
                    minHeight = 600.0
                }

            }

        }

    }
}

