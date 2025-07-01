## ConsolePipe

----
This plugin allows to simply "pipe" the console outputs to a specific player, e.g. for debugging purposes, so that you don't always need to switch between multiple windows or turn your head by 180° just to view that other unpleasantly placed monitor.
It is also pretty insecure, because you might accidentally view stuff that should stay in the console. :)


----
#### Commands:

`/consolepipe` 

- `list` : List currently "piped" players, who receive console outputs
- `pipe` : "pipe" yourself to the console, to receive the outputs
  - `[player]` : "pipe" the output to a specific player
- `unpipe` : "unpipe" yourself from the console, to not receive outputs anymore
  - `[player]` : "unpipe" the output to a specific player
- `reload` : Reload the plugin configs and remove all piped players
- `filter`
  - `<filter regex>` : Filter the console output for yourself, to only see messages that don't match the regex
  - `[player] <filter regex>` : Filter the console output for a specific player

Planned:
- `logs`
  - `list` : Lists all available logs
    - `[type: log|error]` : Filter by type, either `log` or `error`
    - `[nameReg]` : Filter by name, using a regex
  - `select` `<log>` :  Selects a log to view
  - `info` : Shows info about the selected log
  - `line` `<line>` : Prints a specific line from the selected log
  - `upload` : Uploads the selected log to a pastebin service
    - `section <startLine> <endLine>` : Uploads a specific section of the log
  - `search` : Searches the log with the given regex and prints the results