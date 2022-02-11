public void ScoreboardMonitor() {
    new BukkitRunnable() {
        @Override
        public void run() {
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (online.hasPermission("scoreboard.default")) {
                    if (!Main.hideScoreboard.get(online.getUniqueId()) || Main.hideScoreboard.get(online.getUniqueId()) == null) {
                        ScoreboardManager sm = m.getServer().getScoreboardManager();
                        org.bukkit.scoreboard.Scoreboard b = sm.getNewScoreboard();

                        Objective o = b.registerNewObjective("Score1", "board1");
                        o.setDisplaySlot(DisplaySlot.SIDEBAR);

                        ArrayList<String> list = (ArrayList<String>) m.getConfig().getStringList("Scoreboards.default.scoreboard");

                        int i = list.size();

                        o.setDisplayName(ChatColor.translateAlternateColorCodes('&', rgb(m.getConfig().getString("Scoreboards.default.title"))));
                        for (String str : list) {

                            // Do something with lines

                            Score score = o.getScore(ChatColor.translateAlternateColorCodes('&', placeholderAPI));

                            if (str.equals("empty")) score = o.getScore(ChatColor.RED.toString()); // empty line on scoreboard

                            score.setScore(i);
                            i--;
                        }
                        online.setScoreboard(b);
                    }
                }
            }
        }
    }.runTaskTimer(m, 0L, 20L);
}
