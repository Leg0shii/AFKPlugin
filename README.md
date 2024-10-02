# AFKPlugin

AFKPlugin is a Minecraft Bukkit/Spigot plugin designed to manage and track players' AFK (Away From Keyboard) status. It automatically detects inactive players, notifies them, and can kick them from the server after a specified period. Additionally, it records AFK statistics in a MySQL database, allowing players and administrators to query and manage AFK data.

## Features

- **Automatic AFK Detection:** Monitors player activity and marks them as AFK after a configurable period of inactivity.
- **Customizable Messages:** Sends personalized messages to players when they become AFK or return from AFK.
- **Automatic Kicking:** Optionally kicks players who remain AFK for too long.
- **Database Integration:** Stores AFK data in a MySQL database for persistent tracking and statistics.
- **Commands:**
  - `/afk stats [player]` - View AFK statistics for yourself or another player.
  - `/afk reset <player>` - Reset AFK stats for a specific player.
  - `/afk resetglobal` - Reset AFK stats for all players.

## Installation

1. **Prerequisites:**
   - A Bukkit/Spigot compatible Minecraft server.
   - Java 16 or higher.
   - A MySQL database server.

2. **Download:**
   - Obtain the latest version of `AFKPlugin.jar`.

3. **Setup:**
   - Place the `AFKPlugin.jar` file into your server's `plugins` directory.
   - Start the server to generate the configuration file.

4. **Configuration:**
   - Locate the `AFKConfiguration.yaml` file in the `AFKTimes` folder within the `plugins` directory.
   - Edit the configuration values as needed:
     - `secondsTillAFK`: Time in seconds before a player is marked as AFK.
     - `checkIntervall`: Interval in seconds for checking AFK status.
     - `kickValue`: Time in seconds after which AFK players are kicked.
     - Customize messages (`kickMessage`, `afkMessage`, `afkSinceMessage`, `noAFK`).
     - Database settings (`host`, `port`, `user`, `password`, `database`).

5. **Database Setup:**
   - Ensure your MySQL database is running and accessible.
   - Update the database credentials in the `AFKConfiguration.yaml`.
   - The plugin will automatically create necessary tables upon enabling.

6. **Permissions:**
   - Assign appropriate permissions to players and admins:
     - `mania.afk.bypass` - Exempt from AFK detection.
     - `mania.afk.stats` - View own AFK stats.
     - `mania.afk.stats.others` - View others' AFK stats.
     - `mania.afk.stats.reset` - Reset own or others' AFK stats.
     - `mania.afk.stats.reset.admin` - Reset all AFK stats globally.

## Usage

- **Commands:**
  - `/afk` - Display AFK command syntax.
  - `/afk stats [player]` - View AFK stats for yourself or another player.
  - `/afk reset <player>` - Reset AFK stats for a specific player.
  - `/afk resetglobal` - Reset all AFK stats.
