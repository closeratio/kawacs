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

package com.closeratio.kawacs.model

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty

import tornadofx.getValue
import tornadofx.setValue

class Model {

    val statusProperty = SimpleObjectProperty<PlaybackStatus>(PlaybackStatus.STOPPED)
    var status by statusProperty

    val recordingTimeProperty = SimpleDoubleProperty()
    var recordingTime by recordingTimeProperty

}

