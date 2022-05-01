private final Pattern hexPattern = Pattern.compile("<#([A-Fa-f0-9]){6}>");
public String applyColor(String message){
    Matcher matcher = hexPattern.matcher(message);
    while (matcher.find()) {
        final ChatColor hexColor = net.md_5.bungee.api.ChatColor.of(matcher.group().substring(1, matcher.group().length() - 1));
        final String before = message.substring(0, matcher.start());
        final String after = message.substring(matcher.end());
        message = before + hexColor + after;
        matcher = hexPattern.matcher(message);
    }
    return ChatColor.translateAlternateColorCodes('&', message);
}
