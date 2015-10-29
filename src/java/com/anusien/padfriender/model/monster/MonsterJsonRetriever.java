package com.anusien.padfriender.model.monster;

import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MonsterJsonRetriever {
    private static final Logger logger = Logger.getLogger(MonsterJsonRetriever.class);

    @VisibleForTesting
    protected static final String JsonURL = "https://www.padherder.com/api/monsters/";

    @Nullable
    public String getJson() {
        final String jsonFromUrl = getJsonFromUrl(JsonURL);
        if(jsonFromUrl != null) {
            return jsonFromUrl;
        }

        return getJsonFromCachedFile();
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