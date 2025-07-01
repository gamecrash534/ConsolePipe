package xyz.gamecrash.consolepipe.config;

public class Permissions {
    public static final String PERMISSION_BASE = "consolepipe";

    public static final String PERMISSION_COMMAND = PERMISSION_BASE + ".command";
    public static final String PERMISSION_COMMAND_PIPE = PERMISSION_COMMAND + ".pipe";
    public static final String PERMISSION_COMMAND_UNPIPE = PERMISSION_COMMAND + ".unpipe";
    public static final String PERMISSION_COMMAND_LIST = PERMISSION_COMMAND + ".list";
    public static final String PERMISSION_COMMAND_RELOAD = PERMISSION_COMMAND + ".reload";
    public static final String PERMISSION_COMMAND_PIPE_PLAYER = PERMISSION_COMMAND_PIPE + ".other";
    public static final String PERMISSION_COMMAND_UNPIPE_PLAYER = PERMISSION_COMMAND_UNPIPE + ".other";
    public static final String PERMISSION_COMMAND_FILTER = PERMISSION_COMMAND + ".filter";
    public static final String PERMISSION_COMMAND_FILTER_PLAYER = PERMISSION_COMMAND_FILTER + ".other";

    public static final String PERMISSION_COMMAND_LOGS = PERMISSION_COMMAND + ".logs";
    public static final String PERMISSION_COMMAND_LOGS_INFO = PERMISSION_COMMAND_LOGS + ".info";
    public static final String PERMISSION_COMMAND_LOGS_LINE = PERMISSION_COMMAND_LOGS + ".line";
    public static final String PERMISSION_COMMAND_LOGS_LIST = PERMISSION_COMMAND_LOGS + ".list";
    public static final String PERMISSION_COMMAND_LOGS_SEARCH = PERMISSION_COMMAND_LOGS + ".search";
    public static final String PERMISSION_COMMAND_LOGS_SELECT = PERMISSION_COMMAND_LOGS + ".select";
    public static final String PERMISSION_COMMAND_LOGS_UPLOAD = PERMISSION_COMMAND_LOGS + ".upload";
}
