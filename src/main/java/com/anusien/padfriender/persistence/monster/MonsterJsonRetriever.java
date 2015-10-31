package com.anusien.padfriender.persistence.monster;

import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MonsterJsonRetriever {
    private static final Logger logger = Logger.getLogger(MonsterJsonRetriever.class);

    @VisibleForTesting
    protected static final String JsonURL = "https://www.padherder.com/api/monsters/";

    private Lock lock = new ReentrantLock();
    private CachedJson cache;

    private class CachedJson {
        @Nonnull private final String json;
        @Nonnull private final Date accessed;

        public CachedJson(@Nonnull final String json, @Nonnull final Date accessed) {
            this.json = json;
            this.accessed = accessed;
        }

        @Nonnull
        public String getJson() {
            return this.json;
        }

        @Nonnull
        public Date getAccessed() {
            return this.accessed;
        }
    }

    @Nullable
    public String getJson() {
        try {
            lock.lock();
            if (cache != null) {
                return cache.getJson();
            }

            final CachedJson json;
            // No cache, so do a load and read it into the cache
            final String jsonFromUrl = getJsonFromUrl(JsonURL);
            if (jsonFromUrl != null) {
                json = new CachedJson(jsonFromUrl, new Date());
            } else {
                final String jsonFromFile = getJsonFromCachedFile();
                if(jsonFromFile == null) {
                    // No URL or file, we're hosed
                    return null;
                }
                json = new CachedJson(jsonFromFile, new GregorianCalendar(1970, 1, 1).getTime()); // always want the latest on the internet to win
            }

            this.cache = json;
            return this.cache.getJson();
        } finally {
            lock.unlock();
        }
    }


    @Nullable
    @VisibleForTesting
    protected String getJsonFromUrl(@Nonnull final String url) {
        InputStream in = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36");
            in = connection.getInputStream();
            return IOUtils.toString(in);
        } catch (IOException e) {
            logger.error("Error trying to download Json from " + url, e);
            return null;
        } finally {
            if (in != null) {
                IOUtils.closeQuietly(in);
            }
        }
    }

    @Nullable
    @VisibleForTesting
    protected String getJsonFromCachedFile() {
        final InputStream in = MonsterJsonRetriever.class.getClassLoader().getResourceAsStream("monsters.json");
        try {
            return IOUtils.toString(in);
        } catch (IOException e) {
            logger.error("Failed reading Json from cached file");
            return null;
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
}