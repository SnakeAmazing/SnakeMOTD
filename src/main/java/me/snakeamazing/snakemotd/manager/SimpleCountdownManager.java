package me.snakeamazing.snakemotd.manager;

import com.velocitypowered.api.scheduler.ScheduledTask;
import me.snakeamazing.snakemotd.SnakeMOTD;
import me.snakeamazing.snakemotd.file.ConfigurationDelegate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class SimpleCountdownManager implements CountdownManager {

    private final SnakeMOTD snakeMOTD;
    private final ConfigurationDelegate config;

    private ScheduledTask task;

    public SimpleCountdownManager(SnakeMOTD snakeMOTD, ConfigurationDelegate config) {
        this.snakeMOTD = snakeMOTD;
        this.config = config.getNode("config");
        init();
    }

    @Override
    public void init() {
        task = snakeMOTD.getServer().getScheduler()
                .buildTask(snakeMOTD, () -> {
                    if (getTimeInSeconds() == 0) {
                        stop();
                    }
                })
                .repeat(1L, TimeUnit.SECONDS)
                .schedule();
    }

    @Override
    public void stop() {
        if (task != null) {
            task.cancel();
            snakeMOTD.getServer().getCommandManager().executeAsync(snakeMOTD.getServer().getConsoleCommandSource(), config.getString("finish-command"));
        }
    }

    @Override
    public int getTimeInSeconds() {
        String stopDate = config.getUnColoredString("date");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date date = null;

        try {
            format.setTimeZone(TimeZone.getTimeZone(config.getUnColoredString("timezone")));
            date = format.parse(stopDate);
        } catch (ParseException exception) {
            exception.printStackTrace();
        }

        if (date != null) {
            Date current = new Date();
            long diff = date.getTime() - current.getTime();
            System.out.println(diff + " " + TimeUnit.MILLISECONDS.toMinutes(diff));
            if (diff > 0L) {
                return (int) TimeUnit.MILLISECONDS.toSeconds(diff);
            }
        }
        System.out.println("date is null");
        return 0;
    }


    public String getTime() {
        String dateStop = config.getUnColoredString("date");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date date = null;
        try {
            format.setTimeZone(TimeZone.getTimeZone(config.getUnColoredString("timezone")));
            date = format.parse(dateStop);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date current = new Date();
        if (date == null) {
            return config.getUnColoredString("time-value-end");
        }

        long diff = date.getTime() - current.getTime();

        System.out.println(diff + " " + TimeUnit.MILLISECONDS.toMinutes(diff));

        if (diff >= 0L) {
            long days = TimeUnit.MILLISECONDS.toDays(diff);
            long hours = TimeUnit.MILLISECONDS.toHours(diff) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(diff));
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diff));

            return config.getUnColoredString("time-formatted")
                    .replace("%days%", String.valueOf(days))
                    .replace("%hours%", String.valueOf(hours))
                    .replace("%minutes%", String.valueOf(minutes));
        }

        return config.getUnColoredString("time-value-end");
    }
}
