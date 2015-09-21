package com.gn;

import java.io.IOException;
import java.math.BigInteger;

public class GnRhythm {
    public static String register(String clientID) {
        GnQuery query = new GnQuery();
        query.addQuery("REGISTER");
        query.addQueryClient(clientID);
        String queryXML = query.toString();
        String response = null;
        try {
            response = GnApiUtil._httpPostRequest(_gnurl(clientID), queryXML);
        } catch (IOException ignored) {
        }
        return response;
    }


    public static String createRadio(String clientID, String userID, String artistName, String trackName, String moodId, String eraId, String genreId, String popularity, String similarity) {
        GnQuery query = new GnQuery();
        query.addAuth(clientID, userID);
        query.addQuery("RADIO_CREATE");

        if (trackName != null) {
            if (artistName != null) {
                if (!artistName.isEmpty() && !trackName.isEmpty()) {
                    query.addTextSeed(artistName, trackName);
                }
            }
        }

        if (!(moodId.isEmpty() & eraId.isEmpty() & genreId.isEmpty())) {
            query.addAttributeSeed(moodId, eraId, genreId);

        }
        query.addQueryOption("RETURN_COUNT", String.valueOf(BigInteger.TEN));
        if (popularity != null) {
            if (popularity.isEmpty()) {
            } else {
                query.addQueryOption("FOCUS_POPULARITY", popularity);
            }
        }
        if (similarity != null) {
            if (similarity.isEmpty()) {
            } else {
                query.addQueryOption("FOCUS_SIMILARITY", similarity);
            }
        }

        query.addQueryOption("RETURN_PROFILE", "MOOD");
        query.addQueryOption("RETURN_SETTINGS", "YES");
        String queryXML = query.toString();
        String response = null;
        try {
            response = GnApiUtil._httpPostRequest(_gnurl(clientID), queryXML);
        } catch (IOException ignored) {
        }
        return response;
    }

    public static String radioEvent(String clientID, String userID, String radioID, String gnID, String event) {
        GnQuery query = new GnQuery();
        query.addAuth(clientID, userID);
        query.addQuery("RADIO_EVENT");
        query.addRadioID(radioID);
        query.addQueryEVENT(event, gnID);
        query.addQueryOption("RETURN_COUNT", String.valueOf(BigInteger.ONE));
        query.addQueryOption("SELECT_EXTENDED", "COVER,REVIEW,ARTIST_BIOGRAPHY,ARTIST_IMAGE,ARTIST_OET,MOOD,TEMPO,LINK");
        String queryXML = query.toString();
        String response = null;
        try {
            response = GnApiUtil._httpPostRequest(_gnurl(clientID), queryXML);
        } catch (IOException ignored) {
        }
        return response;
    }

    public static String radioSetting(String clientID, String userID, String radioID, String filter_mood, String popularity, String similarity) {
        GnQuery query = new GnQuery();
        query.addAuth(clientID, userID);
        query.addQuery("RADIO_SETTING");
        query.addRadioID(radioID);

        if (filter_mood != null) {
            query.addQueryOption("FILTER_MOOD", filter_mood);
        }
        if (popularity != null) {
            if (!popularity.equals("")) query.addQueryOption("FOCUS_POPULARITY", popularity);
        }
        if (similarity != null) {
            if (!similarity.equals("")) query.addQueryOption("FOCUS_SIMILARITY", similarity);
        }
        query.addQueryOption("RETURN_PROFILE", "MOOD");
        query.addQueryOption("RETURN_COUNT", String.valueOf(BigInteger.TEN.intValue()));

        String queryXML = query.toString();
        String response = null;
        try {
            response = GnApiUtil._httpPostRequest(_gnurl(clientID), queryXML);
        } catch (IOException ignored) {
        }
        return response;
    }

    public static String radioFieldvalues(String clientID, String userID, String FieldName) {
        GnQuery query = new GnQuery();
        query.addAuth(clientID, userID);
        query.addQuery("FIELDVALUES");
        query.addQueryFieldValues(FieldName);
        String queryXML = query.toString();
        String response = null;
        try {
            response = GnApiUtil._httpPostRequest(_gnurl(clientID), queryXML);
        } catch (IOException ignored) {
        }
        return response;
    }

    public static String radioLookahead(String clientID, String userID, String radioID, String limit) {
        GnQuery query = new GnQuery();
        query.addAuth(clientID, userID);
        query.addQuery("RADIO_LOOKAHEAD");
        query.addRadioID(radioID);
        query.addQueryOption("RETURN_COUNT", limit);
        query.addQueryOption("SELECT_EXTENDED", "COVER,REVIEW,ARTIST_BIOGRAPHY,ARTIST_IMAGE,ARTIST_OET,MOOD,TEMPO,LINK");
        String queryXML = query.toString();
        String response = null;
        try {
            response = GnApiUtil._httpPostRequest(_gnurl(clientID), queryXML);
        } catch (IOException ignored) {
        }
        return response;
    }

    protected static String _gnurl(String clientID) {
        String clientIDprefix = clientID.split("-")[0];
        return "https://c" + clientIDprefix + ".web.cddbp.net/webapi/json/1.0/";
    }
}

