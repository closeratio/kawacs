--[[
This file is part of Kawacs.

Kawacs is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Kawacs is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Kawacs.  If not, see <http://www.gnu.org/licenses/>.
]]

-- Thanks to all the contributors to DCS simple radio (https://github.com/ciribob/DCS-SimpleRadioStandalone)
-- Their export script was a big help in creating this one

-- In order to use this file, put the following line in your Export.lua:
-- local kawacs=require('lfs');dofile(dcsSr.writedir() .. 'Scripts/KawacsExport.lua')

-- Imports
local socket = require("socket")

-- Global vars
local KW = {}

-- Constants
KW.BROADCAST_PORT = 22312

-- Setup
package.cpath = package.cpath..";./LuaSocket/?.dll;"

package.path = package.path .. ";"
	.. "./Scripts/?.lua;"
	.. "./LuaSocket/?.lua;"

-- Logging
KW.logFile = io.open(lfs.writedir() .. "Logs/Kawacs.log", "w")

function KW.log(str)
    if KW.logFile then
        KW.logFile:write(str .. "\n")
        KW.logFile:flush()
    end
end

-- Serialisation
KW.json = loadfile("Scripts/JSON.lua")()

-- Sockets
KW.broadcastSocket = socket.udp()

-- Previous exports
KW.prevExport = {}
KW.prevExport.LuaExportAfterNextFrame = LuaExportAfterNextFrame

-- Entry function (called by DCS)
LuaExportAfterNextFrame = function()

	-- Main functionality
    local status, result = pcall(function()
    	KW.onFrame()
    end)

    if not status then
        KW.log('ERROR LuaExportBeforeNextFrame Kawacs: ' .. result)
    end

	-- Call the original export
    status, result = pcall(function()
        if KW.prevExport.LuaExportBeforeNextFrame then
            KW.prevExport.LuaExportBeforeNextFrame()
        end
    end)

    if not status then
        KW.log('ERROR Calling other LuaExportBeforeNextFrame from another script: ' .. result)
    end
end

-- Call on each frame, sends out the current data to the client
KW.onFrame = function()

	-- Hoover up data
	local data = {}
	data.worldObjects = LoGetWorldObjects()
	data.targetInfo = LoGetTargetInformation()
	data.lockedTargetInfo = LoGetLockedTargetInformation()

	-- Serialise
	local encodedData = KW.json:encode(data)

	-- Send
	KW.broadcastSocket:sendto(encodedData, "127.0.0.1", KW.BROADCAST_PORT)
end