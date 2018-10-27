/*
 * Copyright (c) 2016, 2017, 2018 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.weave;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    private static final Map<String, Command> COMMAND_MAP = new TreeMap<>();

    private static void addCommand(Command command) {
        COMMAND_MAP.put(command.name.toLowerCase(), command);
    }

    static {
        addCommand(new CommandMergeMinecraft());
        addCommand(new CommandTinyify());
        addCommand(new CommandIntermediary());
        addCommand(new CommandFindMappingErrors());
    }

    public static void error(String message) {
        System.err.println(message);
        System.exit(1);
    }

    public static void main(String[] args) {
        if (args.length == 0
                || !COMMAND_MAP.containsKey(args[0].toLowerCase())
                || !COMMAND_MAP.get(args[0].toLowerCase()).isArgumentCountValid(args.length - 1)) {
            System.out.println("Available commands:");
            for (Command command : COMMAND_MAP.values()) {
                System.out.println("\t" + command.name + " " + command.getHelpString());
            }
            System.out.println();
            return;
        }

        try {
            String[] argsCommand = new String[args.length - 1];
            if (args.length > 1) {
                System.arraycopy(args, 1, argsCommand, 0, argsCommand.length);
            }
            COMMAND_MAP.get(args[0].toLowerCase()).run(argsCommand);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
