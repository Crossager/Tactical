# Tactical

Welcome to the Tactical project! This repository contains several modules designed to facilitate various aspects of Minecraft plugin development. Each module serves a specific purpose and can be used independently or in combination with others.
Below is a guide to every one of the different modules Tactical has to offer.

# Get started
You need to add the Tactical plugin to your minecraft server to use it. Find the latest release under [Releases](https://github.com/Crossager/Tactical/releases).
It is also possible to shade Tactical into your plugin: [Using Tactical without adding the plugin](#using-tactical-without-adding-the-plugin)
## Repository
[![](https://jitpack.io/v/Crossager/Tactical.svg)](https://jitpack.io/#Crossager/Tactical)\
To add Tactical to your project, add the following repository to your pom.xml. Dependencies for each of the modules are provided along with the documentation below.
Tactical uses Jitpack.io for repository management.

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```
# Navigation

- [TacticalCommands](#tacticalcommands)
- [TacticalCommons](#tacticalcommons)
- [TacticalConfigs](#tacticalconfigs)
- [TacticalGUI](#tacticalgui)
- [TacticalMusic](#tacticalmusic)
- [TacticalProtocol](#tacticalprotocol)
- [Internal Tactical](#internal-tactical)
- [Example Projects](#example-projects)

# TacticalCommands

The TacticalCommands module provides a framework for creating custom commands in Minecraft Bukkit/Spigot plugins. It offers utilities for defining commands, handling command execution, parsing command arguments, and more.
It can register commands dynamically without them being defined in your plugin.yml.

## Get started with TacticalCommands

Step-by-step guide on how to use TacticalCommands, with examples.

### Step 1: Add Dependency

Ensure that you have TacticalCommands added as a dependency in your Bukkit/Spigot plugin's `pom.xml` file:

```xml
<dependency>
    <groupId>com.github.Crossager.Tactical</groupId>
    <artifactId>TacticalCommands</artifactId>
    <version>1.0</version> <!-- Replace with the latest version -->
    <scope>provided</scope>
</dependency>
```

### Step 2: Define Custom Commands

Define custom commands using the `TacticalCommand.create` method.
To register the command, you need a prefix, and an actual command. You can also choose to pass in your plugin instance instead of a prefix.
Lastly, you might not like the dynamic registration, so you also have the option to pass in a plugin command like this:

```java
TacticalCommand.create(plugin, "mycommand")
```
```java
TacticalCommand.create("mycommandprefix", "mycommand")
```
```java 
TacticalCommand.create(plugin.getCommand("mycommand"))
```

### Step 3: Command configuration

Using the `TacticalCommand#options` method, you can specify some internal logic behind the command.
A few options include:\
`#addAlias(String)` Adds an alias to this command\
`#permission(String)` Sets the required permission to execute this command\
`#playerOnly(boolean)` Sets whether this command can only be used by players (Not consoles, other entities, etc). Defaults to false\
`#description(String)` Sets the description for the command\
`#usage(String)` Sets the proper usage for the command, usually only accessed by help menus such as the inbuilt /help\
`#errorHandler(TacticalCommandInvalidArgumentHandler)` Sets the method to be executed in case the user provided invalid arguments to the command\
`#tabCompletionStrategy(TabCompletionStrategy)` Specifies how Tactical is going to handle tab completion

Once you are done configuring this, call the `TacticalCommandOptions#command` method to gain back the original command object.

```java
TacticalCommand.create(plugin, "mycommand")
        .options()
        .addAlias("mycmd")
        .permission("mycommand.use")
        .playerOnly(true)
        .description("Types a word multiple times")
        .command()
```

### Step 4: Command arguments

You can add arguments to your command using the `TacticalCommand#addArgument` method.
Simply use one of the predefined argument types, or create one yourself. Just provide a name for the argument.

Here's how you can add arguments to your command:

```java
TacticalCommand.create(plugin, "mycommand")
    .options()
    .addAlias("mycmd")
    .permission("mycommand.use")
    .playerOnly(true)
    .description("Types a word multiple times")
    .command()
    .addArgument(TacticalCommandArgument.integer("amount").required(true))
    .addArgument(TacticalCommandArgument.string("word").required(true))
```

In the above example, we call `.required(true)`, this tells Tactical that the command cannot be processed if the argument is not present. By default this will be true.

Here's a list of all the command argument types available in TacticalCommands:

**String Values Argument**: Represents a list of string options. This will provide the user with a list of strings to choose from
```java
TacticalCommandArgument.stringValues("punishment", List.of("ban", "mute", "jail"))
```
**String Argument**: Represents a string argument.
```java
TacticalCommandArgument.string("name")
```

**Integer Argument**: Represents an integer argument.
```java
TacticalCommandArgument.integer("age")
```

**Boolean Argument**: Represents a boolean argument.
```java
TacticalCommandArgument.bool("flag")
```

**Decimal Argument**: Represents a decimal argument.
```java
TacticalCommandArgument.decimal("price")
```

**Player Argument**: Represents a player argument. An online player is required to be provided.
```java
TacticalCommandArgument.player("who")
```

**Material Argument**: Represents a material argument.
```java
TacticalCommandArgument.material("block")
```

**Location Argument**: Represents a location argument with default decimal precision of 2.
```java
TacticalCommandArgument.location("where")
```

**Message Argument**: Represents a message argument. This just means that all arguments after this one will be combined into a single string.
```java
TacticalCommandArgument.message("messageContent")
```

**Subcommands Argument**: Represents a set of sub-commands.
```java
TacticalCommandArgument.subCommands(subCommandsList)
```

**Merged Argument**: Merges a list of TacticalCommandArgument instances into a single instance.
```java
TacticalCommandArgument.merge(argumentList)
```

### Step 5: Handling Command Execution

After defining your custom command and adding arguments to it, you need to handle its execution. When a player or console executes your command, TacticalCommands will call your command executor method with the provided arguments.

Here's how you can handle command execution and retrieve arguments:

```java
TacticalCommand.create(plugin, "mycommand")
    .options()
    .addAlias("mycmd")
    .permission("mycommand.use")
    .playerOnly(true)
    .description("Types a word multiple times")
    .command()
    .addArgument(TacticalCommandArgument.integer("amount").required(true))
    .addArgument(TacticalCommandArgument.string("word").required(true))
    .commandExecutor(context -> {
        int amount = context.argument("amount").asInteger(); // Retrieve the integer argument
        String word = context.argument("word").asString(); // Retrieve the string argument
        
        if (amount < 1)
            context.throwIncorrectUsageException(); // They used the command wrong!
        
        // Your command logic here
        for (int i = 0; i < amount; i++) {
            context.playerSender().sendMessage(word); // playerSender() is only available when the sender is a player
        }
    })
    .register();
```

In the above example:

- We define a command with the alias "mycommand" and specify that it can only be executed by players. We add two arguments: an integer argument named "amount" and a string argument named "word".

- In the command executor method, we retrieve the values of the arguments using the `context.argument` method. We specify the name of the argument and its type using methods like `asInteger()` and `asString()`.

- We then use the retrieved values to execute the desired command logic. In this case, we simply print the provided word multiple times based on the specified amount.

- At last, we call the `.register()` function. This is to tell Tactical that we are done configuring, and the command should be registered.

## Command argument preconditions
In the above example, we do a check inside the execution handler, to see if `amount < 1`. There is a better way to do this:
```java
.addArgument(TacticalCommandArgument.integer("amount").required(true).addPrecondition(TacticalCommandArgumentPrecondition.notLowerThan(1))
```

In this case we added a precondition to the argument, this way the parsing of the command will fail before we even get to the execution.

## Subcommands in TacticalCommands

Subcommands are a powerful feature in TacticalCommands that allow you to organize and structure your commands in a hierarchical manner. They enable you to create complex command structures with multiple levels of depth, making your commands more modular and easier to manage.

### What are Subcommands?

In a sense, subcommands are exactly like regular commands, with the exception they cant exist on their own. They exist as an argument of another command, which allows the user to select between different subcommands. This also means that even subcommands can have their own subcommands.
### How to Implement Subcommands

Implementing subcommands in TacticalCommands is straightforward. Here's a step-by-step guide on how to do it:

1. **Create the Primary Command**: Define the primary command using the `TacticalCommand.create` method as usual. This will serve as the parent command for your subcommands.

2. **Define Subcommands**: For each subcommand you want to add, create a separate instance of `TacticalSubCommand`. Each subcommand should have a unique name and its own command logic.

3. **Configure subcommand**: A subcommand can be configured in almost the same way as a regular command. This means adding arguments and adding a command executor.

4. **Add Subcommands to the Primary Command**: Use the `TacticalCommandArgument#subCommands` method to add the subcommands as an argument to the primary command
### Example

Here's an example demonstrating how to implement subcommands in TacticalCommands:

```java
TacticalCommand.create(plugin, "mycommand")
        .options()
        .description("Primary command with subcommands")
        .command()
        .addArgument(TacticalCommandArgument.subCommands(
            TacticalSubCommand.create("subcommand1")
                .commandExecutor(context -> {
                    context.sender().sendMessage("Executing subcommand1");
                }),
            TacticalSubCommand.create("subcommand2")
                .addArgument(TacticalCommandArgument.string("someString"))
                .commandExecutor(context -> {
                    context.sender().sendMessage("Executing subcommand2 with string: " + context.argument("someString").asString());
                })
            )
        )
        .commandExecutor(context -> {
            context.sender().sendMessage("Executing primary command");
        })
        .register();
```

In this example:

- We define a primary command named "mycommand" with a description indicating that it contains subcommands.

- We use the `TacticalCommandArgument.subCommands` method to specify that the primary command accepts subcommands.

- Two subcommands, "subcommand1" and "subcommand2", are added using the `TacticalSubCommand.create` method. Each subcommand has its own command executor method defining its logic.

- The primary command's executor method is also defined, which sends a message indicating that the primary command is being executed.

This setup allows users to execute different subcommands under the "mycommand" namespace, providing a clean and organized way to handle various functionalities within a single command.


# TacticalCommons

The TacticalCommons module contains common utility classes and helper methods that can be used across different parts of your Bukkit/Spigot plugin. It includes functionalities such as logging, configuration management, serialization, and more.
Its main purpose is to reduce boilerplate in the other Tactical modules, hence why most of them depend on this.

```xml
<dependency>
    <groupId>com.github.Crossager.Tactical</groupId>
    <artifactId>TacticalCommons</artifactId>
    <version>1.0</version> <!-- Replace with the latest version -->
    <scope>provided</scope>
</dependency>
```
# TacticalConfigs

TacticalConfigs is a utility library designed to simplify the management of configuration files in Bukkit/Spigot plugins. It provides easy-to-use methods for reading, writing, and managing configuration files, allowing plugin developers to focus on building their features without worrying about the complexities of configuration handling.

## What Does TacticalConfigs Do?

TacticalConfigs offers the following key features:

1. **Flexible Configuration Management**: TacticalConfigs allows you to create, read, update, and delete configuration files effortlessly. It abstracts away the underlying file I/O operations, providing a clean and intuitive interface for interacting with configuration data.

2. **Type-Safe Configuration Access**: With TacticalConfigs, you can define configuration options using strongly-typed Java objects, making it easy to work with configuration values without the need for manual parsing or casting.

3. **Default Configuration Values**: You can specify default values for configuration options, ensuring that your plugin behaves predictably even if certain configuration options are missing or invalid.

4. **Supports several formats**: Currently we offer json, xml and yaml support. It is also very easy to create your own serializers

## Methods and Examples

Let's explore some of the key methods provided by TacticalConfigs and how you can use them with examples:

### Step 1: Add Dependency

Ensure that you have TacticalConfigs added as a dependency in your Bukkit/Spigot plugin's `pom.xml` file:

```xml
<dependency>
    <groupId>com.github.Crossager.Tactical</groupId>
    <artifactId>TacticalConfigs</artifactId>
    <version>1.0</version> <!-- Replace with the latest version -->
    <scope>provided</scope>
</dependency>
```
### Step 2: Creating a Configuration File

First, we create a new `TacticalConfig` instance using the `TacticalConfig.create()` method. We specify that we want to use JSON serialization, and we provide the data folder and filename for the configuration file.

```java
TacticalConfig config = TacticalConfig.create(TacticalConfigSerializer.json(), plugin.getDataFolder(), "mydata.json");
```

### Step 3: Setting Configuration Values

Next, we set some configuration values using the `set()` method. In this example, we set the maximum number of players allowed to 100.

```java
config.set("maxPlayers", 100);
```

### Step 4: Retrieving Configuration Values

We can retrieve configuration values using the `get()` method. In this example, we retrieve the value of "maxPlayers" as an integer.

```java
int maxPlayers = config.get("maxPlayers").asInt();
```

### Step 5: Creating Lists and Sections

We can create lists and sections within the configuration file using the `addList()` and `addSection()` methods, respectively.

```java
TacticalConfigList peopleList = config.addList("people");

TacticalConfigSection john = peopleList.addSection();
john.set("name", "John");
john.set("age", 35);
john.set("mail", "john@mymail.com");

TacticalConfigSection paul = peopleList.addSection();
paul.set("name", "Paul");
paul.set("age", 23);
paul.set("mail", "paul@mymail.com");
```

### Step 6: Saving Configuration

Finally, we save the changes to the configuration file using the `save()` method. This writes the configuration data to the specified file.

```java
config.save();
```

Running the above example will produce the following json file:
```json
{
    "people": [
        {
            "name": "John",
            "mail": "john@mymail.com",
            "age": 35
        },
        {
            "name": "Paul",
            "mail": "paul@mymail.com",
            "age": 23
        }
    ],
    "maxPlayers": 100
}
```
If you had instead chosen yaml or xml, the file would look like this:
```yaml
people:
- name: John
  mail: john@mymail.com
  age: 35
- name: Paul
  mail: paul@mymail.com
  age: 23
maxPlayers: 100
```
```xml
<mydata>
  <people type="list">
    <element type="section">
      <name>
        John
      </name>
      <mail>
        john@mymail.com
      </mail>
      <age>
        35
      </age>
    </element>
    <element type="section">
      <name>
        Paul
      </name>
      <mail>
        paul@mymail.com
      </mail>
      <age>
        23
      </age>
    </element>
  </people>
  <maxPlayers>
    100
  </maxPlayers>
</mydata>
```

### Error Handling

It's important to handle any potential `IOException` that may occur during file I/O operations, such as reading or writing to the configuration file.

```java
try {
    // Configuration code here
} catch (IOException e) {
    // Handle IOException
}
```

By following these steps, you can effectively create, modify, and save configuration data using TacticalConfigs in your Bukkit/Spigot plugin.

# TacticalGUI

TacticalGUI is a versatile plugin that enables Bukkit/Spigot server owners and developers to create immersive graphical user interfaces (GUIs) for their Minecraft servers. With TacticalGUI, you can design custom inventory GUIs, sign GUIs, anvil GUIs, and more, enhancing player interactions and gameplay experiences.


## Key Features

1. **Custom Inventory GUIs**: Design custom inventory GUIs with various components, including buttons, storages, and more.
2. **Sign GUIs**: Create interactive sign GUIs for players to input text or make selections.
3. **Anvil GUIs**: Implement custom anvil GUIs for renaming items or performing other text-based interactions.
4. **Dynamic Animations**: Add dynamic animations to inventory GUI components to create engaging visual effects.
5. **Flexible Configuration**: Easily configure GUI properties, such as size, title, appearance, and behavior.

## Usage
Ensure that you have TacticalGUI added as a dependency in your Bukkit/Spigot plugin's `pom.xml` file:

```xml
<dependency>
    <groupId>com.github.Crossager.Tactical</groupId>
    <artifactId>TacticalGUI</artifactId>
    <version>1.0</version> <!-- Replace with the latest version -->
    <scope>provided</scope>
</dependency>
```

### Creating a Sign GUI

To create a sign GUI, use the `TacticalSignGUI.create()` method. Specify the sign's initial text and customize its appearance and behavior. Here's an example:

```java
TacticalSignGUI.create("Welcome to My Server!", "", "", "") // Starting lines
        .color(DyeColor.BLUE)
        .glowing(true)
        .onClose((player, input) -> {
            // Handle sign input
            player.sendMessage("You created a sign with the following text:");
            for (int i = 0; i < 4; i++) {
                player.sendMessage(input.line(i));
            }
        })
        .open(player);

```

### Creating an Anvil GUI

Create a custom anvil GUI using the `TacticalAnvilInputGUI.create()` method. Customize the GUI title, renaming behavior, and validation logic. Here's an example:

```java
List<String> bannedWords = List.of("profanity", "inappropriate");
        TacticalAnvilInputGUI.create()
        .title(ChatColor.GOLD + "Rename Item")
        .renamingValidator(name -> !bannedWords.contains(name)) // Only validate if no banned words
        .itemNameModifier((itemStack, name) -> {
            if (name.isEmpty()) return; // Do nothing to the item if there is no name
            if (bannedWords.contains(name))
                ItemUtils.setName(itemStack, ChatColor.RED + "Invalid Name");
            else
                ItemUtils.setName(itemStack, ChatColor.WHITE + name);
        })
        .onClose((player, input) -> {
            // Handle renamed item
            player.sendMessage("You renamed the item to: " + input.renamedText());
        })
        .onRenaming((player, renamedText) -> {
            player.sendMessage("Renaming: " + renamedText);
        })
        .onCancel((player, input) -> {
            player.sendMessage("Filthy, you cancelled");
        })
        .open(player);

```

## Creating an Inventory GUI

You can create a custom inventory GUI using the `TacticalInventoryGUI.create()` method. Customize the GUI size, title, and appearance, and add components to it. Here's an example of creating a simple inventory GUI:

### Creating the inventory gui

There are several options for creating an inventory gui. You have to choose between a regular (like a chest, with x amount of rows), dispenser (3x3) or hopper (5x1)
```java
TacticalInventoryGUI.create(6, "My Inventory") // create an inventory with 6 rows, which is also the max
```
```java
TacticalInventoryGUI.createDispenser("My Inventory") // create a dispenser inventory, which is just a 3x3
```
```java
TacticalInventoryGUI.createHopper("My Inventory") // create a hopper inventory, which is just a 5x1
```
### Adding components

#### TacticalStaticGUIComponent
The static gui component is your all-in-one component, and can be created in 2 primary ways.
```java
TacticalStaticGUIComponent.of(new ItemStack(Material.LEATHER_HELMET)) // In this case, it just represents a leather helmet itemstack

TacticalStaticGUIComponent.of(player -> {
    return ItemUtils.setName(new ItemStack(Material.LEATHER_HELMET), player.getName());
}) // Now it will display a leather helmet with the name of whoever is viewing the gui
```
Furthermore, you can make it interactive using the `onClick` event
```java
TacticalStaticGUIComponent.of(player -> {
        return ItemUtils.setName(new ItemStack(Material.LEATHER_HELMET), player.getName());
    }).onClick(clickEvent -> {
        clickEvent.player().sendMessage("Hello, Player!");
    })
```

### Creating a custom name gui

Here is a demonstration on how you can combine multiple guis to create a better flow for the user
In this example, we have created a gui that allows the player to view, and modify their custom name.

```java
TacticalInventoryGUI gui = TacticalInventoryGUI.create(3, "My Inventory");

// This fills the edge of the gui with black stained glass panes
gui.createBorder(TacticalStaticGUIComponent.of(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));

// Sets a component at the coordinates 1,1. The origin is on the top left.
gui.setComponent(1, 1, TacticalStaticGUIComponent.of(player ->
        ItemUtils.setName(
            ItemUtils.setSkullOwner(new ItemStack(Material.PLAYER_HEAD), player.getCustomName()), // Set name to the players custom name
            "§e" + player.getName())
        ));

// Define the input gui for the custom name
TacticalSignGUI customNameInput = TacticalSignGUI.create("", "^^^^", "Input your custom name") // Starting lines
        .onClose((player, input) -> {
            String newName = input.get(0); // Take input from the first line.
            player.setCustomName(newName);
            player.sendMessage("§aYou changed your name to " + newName); // Provide feedback
            gui.updatePlayer(player); // Since we have dynamic elements, we need to let the gui know that it has to rerender the items
            gui.open(player); // Reopen the inventory gui
        });

// Add a button to change your custom name
gui.setComponent(2, 1, TacticalStaticGUIComponent.of(player -> ItemUtils.setName(new ItemStack(Material.OAK_SIGN), "§aChange custom name"))
        .onClick(clickEvent -> {
             customNameInput.open(clickEvent.player()); // open the sign gui for the player
        }));

gui.open(player);
```

### A gui container
The `TacticalGUIContainer` class is an implementation of a component. Its primary purpose is to store other components inside to allow for a tree like gui structure.
```java
TacticalGUIContainer.create(3, 3) // provide the width and height
```

### Animations
Another feature that TacticalGUI offers, is animations. You can animate any item in your inventory however you like it.
To get started, simply find a component that extends `TacticalAnimatable`.\
Examples are always nice to work with, so lets make a leather helmet change colors.
```java
// Define gui
TacticalInventoryGUI gui = TacticalInventoryGUI.create(1, "Leather helmet animation");

// Define our item
TacticalStaticGUIComponent leatherHelmet = TacticalStaticGUIComponent.of(new ItemStack(Material.LEATHER_HELMET));

// Put our leather helmet in the first slot
gui.setComponent(0, 0, leatherHelmet);
```
Now, time to add the animation


```java
// Define our item
TacticalStaticGUIComponent leatherHelmet = TacticalStaticGUIComponent.of(new ItemStack(Material.LEATHER_HELMET));

// Define animation
leatherHelmet.animate(context -> {
    
        }, TacticalAnimator.LINEAR);
```

Here we have an animation context, this provides us with useful information about the animation, mainly the progress.
The progress is how far we are into the animation, and will define how the animated item should look. The second parameter is something called an animator.
This is what defines how the animation should progress over time.

The progress from the animation is a number between 0 and 1, depending on how far we are into the animation, to make the helmet change color accordingly,
we can do as follows:

```java
// Define our item
TacticalStaticGUIComponent leatherHelmet = TacticalStaticGUIComponent.of(new ItemStack(Material.LEATHER_HELMET));

// Define animation
leatherHelmet.animate(context -> {
        ItemStack itemStack = new ItemStack(Material.LEATHER_HELMET); // Create a new helmet item
        LeatherArmorMeta meta = (LeatherArmorMeta) itemStack.getItemMeta(); // Get the leather meta to allow for color changing
        meta.setColor(Color.fromRGB(255 * context.progress(), 0, 0)); // Set the 'redness' of the color according to the progress
        itemStack.setItemMeta(meta);
        return itemStack;
        }, TacticalAnimator.LINEAR);
```

We are now done configuring the item, and can move onto the next step.\
Since we want the animation to appear in real-time, we are going to have to tell the gui where it needs to update the view.\
When we do this, the gui will periodically update the gui for anyone currently viewing it.

```java
// Define gui
TacticalInventoryGUI gui = TacticalInventoryGUI.create(1, "Leather helmet animation");

// Define our item
TacticalStaticGUIComponent leatherHelmet = TacticalStaticGUIComponent.of(new ItemStack(Material.LEATHER_HELMET));

// Define animation
        leatherHelmet.animate(context -> {
        ItemStack itemStack = new ItemStack(Material.LEATHER_HELMET); // Create a new helmet item
        LeatherArmorMeta meta = (LeatherArmorMeta) itemStack.getItemMeta(); // Get the leather meta to allow for color changing
        meta.setColor(Color.fromRGB((int) (255 * context.progress()), 0, 0)); // Set the 'redness' of the color according to the progress
        itemStack.setItemMeta(meta);
        return itemStack;
        }, TacticalAnimator.LINEAR);
        
// Put our leather helmet in the first slot
gui.setComponent(0, 0, leatherHelmet);
        
// Define an animation area
gui.addAnimationArea(3, 0, 0, 0, 0);
```

The first parameter specifies the update interval, which in this case is set to 3 ticks.
The next two parameters of the `addAnimationArea` method represent the x and y of the starting location for the animation area, following are the next two, which are the end
location x and y. Then the gui will update all components in a rectangle between those points.\
In our case, we only want our 1 item to be updated, which is why it says 0,0,0,0. This will draw a 1x1 rectangle at 0,0.

# TacticalMusic

TacticalMusic is a component of the Tactical framework designed to facilitate the management and playback of music and sound effects in Bukkit/Spigot plugins. With TacticalMusic, developers can easily integrate background music, sound effects, and ambient sounds into their plugins to enhance the gaming experience for players.

## Key Features

- **Music Management**: TacticalMusic provides utilities for loading, playing, pausing, and stopping background music tracks within Minecraft servers.

- **Ambient Sounds**: TacticalMusic enables the creation of ambient soundscapes, allowing developers to generate immersive environments with custom audio loops or effects.

## Using TacticalMusic for MIDI Playback

TacticalMusic offers the ability to load MIDI files and convert them into Minecraft-compatible music sequences. This enables developers to enhance gameplay by incorporating custom MIDI tracks as background music within their Bukkit/Spigot plugins.

## Key Concepts

- **TacticalNoteSequence**: Represents a sequence of musical notes parsed from a MIDI file. This sequence can be played, paused, resumed, and configured to loop seamlessly.

- **TacticalMusicPlayer**: Manages the playback of a TacticalNoteSequence, allowing control over playback status, volume, and looping behavior.

## Getting Started

### Step 1: Dependency Setup

Ensure that TacticalMusic is added as a dependency in your Bukkit/Spigot plugin's `pom.xml` file:

```xml
<dependency>
    <groupId>com.github.Crossager.Tactical</groupId>
    <artifactId>TacticalMusic</artifactId>
    <version>1.0</version> <!-- Replace with the latest version -->
    <scope>provided</scope>
</dependency>
```

### Step 2: MIDI File Parsing

Load a MIDI file and parse it into a TacticalNoteSequence using TacticalMusic:

```java
TacticalNoteSequence sequence = TacticalMusic.getInstance().loadFromMidiFile(getDataFolder().toPath().resolve("music.midi"), TacticalMidiParsingOptions.createFromDefault());
```

### Step 3: Playback Configuration

Create a TacticalMusicPlayer to play the parsed sequence for a specific player:

```java
TacticalMusicPlayer musicPlayer = sequence.playFor(player, SoundCategory.MASTER);
```

### Step 4: Control Playback

Pause, resume, or set up looping behavior for the music player as needed:

```java
musicPlayer.pause(); // Pause playback
musicPlayer.resume(); // Resume playback

// When the sequence ends, play it again like a loop
musicPlayer.onEnd(tacticalMusicPlayer -> {
    tacticalMusicPlayer.noteSequence().playFor(player, SoundCategory.MASTER); 
});

// Fast forward to the exact middle of the song. This is done by setting the current tick to the length / 2.
musicPlayer.currentTick(musicPlayer.tickLength() / 2);
```

## Loading midi from shaded resource

You can also just pass an `URL` instead of the new java nio path.

```java
TacticalMusic.getInstance().loadFromMidiFile(getClass().getResource("mysong.midi"), TacticalMidiParsingOptions.createFromDefault());
```

## Loading songs from json

You can also load a sequence from a json file. This may be preferred due to midi not being 100% compatible. Json just gets rid of all the confusion

```java
TacticalMusic.getInstance().loadFromJsonFile(getDataFolder().toPath().resolve("music.json"));
```

You are also able to save sequences into this format using

```java
TacticalMusic.getInstance().saveToJsonFile(sequence, getDataFolder().toPath().resolve("exportedmusic.json"));
```

By following these steps and utilizing the provided example, you can seamlessly integrate MIDI playback functionality into your Bukkit/Spigot plugins using TacticalMusic.

## Limitations
There are many limitations to what can be done using TacticalMusic. Here is a list of stuff to be aware of:
1. **BPM**: TacticalMusic can only play music every tick, this is why it is limited to the following BPMs: 300, 150, 100, 75, 60, 50. It will automatically round it to one of these
2. **High pitch**: Minecraft fundamentally only allows notes from the range C2-C4. TacticalMusic will automatically try to scale down your song to fit in this range. But just know that if the high notes seem to be cut out, this is why.
3. **Lack of instruments**: I do not have the time to seek out every midi instrument out there, so if TacticalMusic lacks one, you are going to have to map it yourself. Do this using the `TacticalMidiParsingOptions`.
   Generally, you may need to adjust your midi files, I really enjoy using this website called [Online Sequencer](https://onlinesequencer.net/).

## Default mappings
When you use `TacticalMidiParsingOptions.createDefault()`, here is what is being applied
```java
options.moveOctaves(true);
options.defaultSound(Sound.BLOCK_NOTE_BLOCK_HARP);

options.soundForInstrument("MIDI", Sound.BLOCK_NOTE_BLOCK_HARP);
options.soundForInstrument("Piano", Sound.BLOCK_NOTE_BLOCK_HARP);
options.soundForInstrument("Grand Piano", Sound.BLOCK_NOTE_BLOCK_HARP);
options.soundForInstrument("Grand Piano 1", Sound.BLOCK_NOTE_BLOCK_HARP);
options.soundForInstrument("Acoustic Grand Piano", Sound.BLOCK_NOTE_BLOCK_HARP);
options.soundForInstrument("Electric Piano", Sound.BLOCK_NOTE_BLOCK_HARP);
options.soundForInstrument("Electric Piano 1", Sound.BLOCK_NOTE_BLOCK_HARP);
options.soundForInstrument("Bass", Sound.BLOCK_NOTE_BLOCK_BASS);
options.soundForInstrument("Acoustic Bass", Sound.BLOCK_NOTE_BLOCK_BASS);
options.soundForInstrument("Acoustic Bass 1", Sound.BLOCK_NOTE_BLOCK_BASS);
options.soundForInstrument("Jazz Bass", Sound.BLOCK_NOTE_BLOCK_BASS);
options.soundForInstrument("Electric Bass", Sound.BLOCK_NOTE_BLOCK_BASS);
options.soundForInstrument("Bass Guitar", Sound.BLOCK_NOTE_BLOCK_BASS);
options.soundForInstrument("Bass Guitar (Classic)", Sound.BLOCK_NOTE_BLOCK_BASS);
options.soundForInstrument("Vibraphone", Sound.BLOCK_NOTE_BLOCK_BELL);
options.soundForInstrument("Xylophone", Sound.BLOCK_NOTE_BLOCK_XYLOPHONE);
options.soundForInstrument("Violin", Sound.BLOCK_NOTE_BLOCK_CHIME);
options.soundForInstrument("8-Bit Sine", Sound.BLOCK_NOTE_BLOCK_BIT);
options.soundForInstrument("8-Bit Square", Sound.BLOCK_NOTE_BLOCK_BIT);
options.soundForInstrument("8-Bit Sawtooth", Sound.BLOCK_NOTE_BLOCK_BIT);
options.soundForInstrument("8-Bit Triangle", Sound.BLOCK_NOTE_BLOCK_BIT);
options.soundForInstrument("Flute", Sound.BLOCK_NOTE_BLOCK_FLUTE);
options.soundForInstrument("Concert Harp", Sound.BLOCK_NOTE_BLOCK_FLUTE);
options.soundForInstrument("French Horn", Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO);
options.soundForInstrument("Guitar", Sound.BLOCK_NOTE_BLOCK_GUITAR);
options.soundForInstrument("Electric Guitar", Sound.BLOCK_NOTE_BLOCK_GUITAR);
options.soundForInstrument("Acoustic Guitar", Sound.BLOCK_NOTE_BLOCK_GUITAR);
options.soundForInstrument("Jazz Guitar", Sound.BLOCK_NOTE_BLOCK_GUITAR);
options.soundForInstrument("Sitar", Sound.BLOCK_NOTE_BLOCK_BANJO);
options.soundForInstrument("Music Box", Sound.BLOCK_NOTE_BLOCK_BANJO);
options.soundForInstrument("Steel Drums", Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE);
options.soundForInstrument("Synth Pluck", Sound.BLOCK_NOTE_BLOCK_PLING);
options.soundForInstrument("Smooth Synth", Sound.BLOCK_NOTE_BLOCK_PLING);

options.drumKitForInstrument("Drum Kit", TacticalMidiDrumKit.createFromDefault());
options.drumKitForInstrument("Electric Drum Kit", TacticalMidiDrumKit.createFromDefault());
```

Similarly for `TacticalMidiDrumKit.createDefault()`:
```java
drumKit.defaultSound(Sound.BLOCK_NOTE_BLOCK_BASEDRUM, TacticalMusicKey.C, 0);

drumKit.soundForKey(5, Sound.ENTITY_ITEM_FRAME_ROTATE_ITEM);
drumKit.soundForKey(9, Sound.BLOCK_CALCITE_STEP);
drumKit.soundForKey(11, Sound.BLOCK_NOTE_BLOCK_BASEDRUM);
drumKit.soundForKey(12, Sound.BLOCK_NOTE_BLOCK_BASEDRUM);
drumKit.soundForKey(13, Sound.BLOCK_CALCITE_STEP);
drumKit.soundForKey(14, Sound.BLOCK_NOTE_BLOCK_SNARE);
drumKit.soundForKey(15, Sound.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_ON);
drumKit.soundForKey(16, Sound.BLOCK_NOTE_BLOCK_SNARE);
drumKit.soundForKey(17, Sound.ENTITY_PLAYER_HURT);
drumKit.soundForKey(18, Sound.BLOCK_NOTE_BLOCK_HAT);
drumKit.soundForKey(20, Sound.ENTITY_CHICKEN_STEP);
drumKit.soundForKey(22, Sound.BLOCK_NOTE_BLOCK_HAT);
drumKit.soundForKey(29, Sound.ENTITY_ENDER_EYE_DEATH);
drumKit.soundForKey(32, Sound.BLOCK_NOTE_BLOCK_COW_BELL);
drumKit.soundForKey(45, Sound.BLOCK_AZALEA_LEAVES_FALL);
drumKit.soundForKey(46, Sound.BLOCK_AZALEA_LEAVES_FALL);

drumKit.soundForKey(7, Sound.BLOCK_CALCITE_STEP);

drumKit.soundForKey(4, Sound.ENTITY_ITEM_FRAME_ROTATE_ITEM);
drumKit.soundForKey(8, Sound.BLOCK_CALCITE_STEP);
drumKit.soundForKey(10, Sound.BLOCK_NOTE_BLOCK_BASEDRUM);
drumKit.soundForKey(11, Sound.BLOCK_NOTE_BLOCK_BASEDRUM);
drumKit.soundForKey(12, Sound.BLOCK_CALCITE_STEP);
drumKit.soundForKey(13, Sound.BLOCK_NOTE_BLOCK_SNARE);
drumKit.soundForKey(14, Sound.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_ON);
drumKit.soundForKey(15, Sound.BLOCK_NOTE_BLOCK_SNARE);
drumKit.soundForKey(16, Sound.ENTITY_PLAYER_HURT);
drumKit.soundForKey(17, Sound.BLOCK_NOTE_BLOCK_HAT);
drumKit.soundForKey(19, Sound.ENTITY_CHICKEN_STEP);
drumKit.soundForKey(21, Sound.BLOCK_NOTE_BLOCK_HAT);
drumKit.soundForKey(28, Sound.ENTITY_ENDER_EYE_DEATH);
drumKit.soundForKey(31, Sound.BLOCK_NOTE_BLOCK_COW_BELL);
drumKit.soundForKey(44, Sound.BLOCK_AZALEA_LEAVES_FALL);
drumKit.soundForKey(45, Sound.BLOCK_AZALEA_LEAVES_FALL);
```

# TacticalProtocol
TacticalProtocol is a very versatile framework that allows for very high customization, deep down into Minecrafts internal protocol. It allows you to easily listen for packets, send packets, and even inject your own custom packets into the minecraft protocol.
#### Pros
Gives full access to the minecraft protocol
#### Cons
Can be a bit intimidating if you do not know what you are doing, but that's just the nature of packets and protocol
Ensure that you have TacticalProtocol added as a dependency in your Bukkit/Spigot plugin's `pom.xml` file:

```xml
<dependency>
    <groupId>com.github.Crossager.Tactical</groupId>
    <artifactId>TacticalProtocol</artifactId>
    <version>1.0</version> <!-- Replace with the latest version -->
    <scope>provided</scope>
</dependency>
```

## PacketData
Packet data is essentially just a series of bytes ready to be sent through network. TacticalProtocol provides two classes to help read/write to these bytes.
These bytes are in a very specific order, and packaged in a certain way.
A quick way to learn how each of the packets are stored, is to either dig into the minecraft source code, or to visit https://wiki.vg/Protocol.
The `PacketWriter` and `PacketReader` classes contain similar functions to read/write certain data from the packets.
## Listening to packets
Listening to packets has never been simpler. Just find your desired `PacketType` and call the function `addPacketListener`.
```java
PacketType.play().in().chat().addPacketListener(packetEvent -> {
        PacketReader reader = packetEvent.data().reader();
        String message = reader.readString();
        long timeStamp = reader.readLong();
        if (message.contains("profanity")) packetEvent.setCancelled(true);
        });
```
In this case, we check if a chat message contains profanity, before the server even realises that the player sent a message.
You are limited to inbound packets, you can even listen to clientbound packets.

## Sending packets
Sending packets is just as simple, and follows almost the same steps
```java
PacketType.play().out().openSignEditor().sendPacket(player, packetWriter -> {
        packetWriter.writeBlockLocation(BlockLocation.fromLocation(location));
        });
```

You are provided with a packetWriter to write all your data. Once you are done writing, the packet gets sent.

## Custom packets
TacticalProtocol offers to ability to inject custom packets into minecraft. This being said, you would still need a modified client to accept these packets.
A custom packet should look something like this:

```java
public class MyCustomPacket implements CustomPacket {
    private final String secretMessage;

    // Create your packet from data
    public MyCustomPacket(String secretMessage) {
        this.secretMessage = secretMessage;
    }

    // Load the data by reading from the PacketReader
    public MyCustomPacket(PacketReader reader) {
        this.secretMessage = reader.readString();
    }

    // Write the data of your packet into the packetWriter
    @Override
    public void write(PacketWriter packetWriter) {
        packetWriter.writeString(secretMessage);
    }
}
```

You still need to inject it, luckily that is quite easy

```java
// Whatever protocol you want the packet injected into
PacketType customPacketType = ProtocolManager.getProtocolManager(Protocol.PLAY, Sender.SERVER)
        .registerCustomPacket(MyCustomPacket.class, MyCustomPacket::new);
```

Now you can treat your custom packet as if it was just any regular packet.

# Internal Tactical
To access the internal implementations and methods of Tactical, add this dependency:
```xml
<dependency>
    <groupId>com.github.Crossager.Tactical</groupId>
    <artifactId>Tactical</artifactId>
    <version>1.0</version> <!-- Replace with the latest version -->
    <scope>provided</scope>
</dependency>
```

Beware, the methods, classes and fields are subject to change, and may do so without notice. None of it is documented.
## Using Tactical without adding the plugin
It is possible to use Tactical by shading it into your plugin. Tactical though needs a `JavaPlugin` instance to operate, you can initialize this by doing this:
```java
new Tactical(plugin).init();
```

This will also initialize static access to all the modules. You can then proceed to use Tactical as normal.

# Example projects
[SpotifyForSpigot](https://github.com/Crossager/SpotifyForSpigot): Combines TacticalMusic, TacticalGUI, TacticalCommands to create a user-friendly ui to play custom music.\
[TacticalExample](https://github.com/Crossager/TacticalExample): Bunch of fooling around, you may find an example of what you need here.