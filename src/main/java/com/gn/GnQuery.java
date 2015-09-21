package com.gn;

/*A utility class for creating and configuring an XML query for POST'ing to
 the Gracenote service*/

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class GnQuery {

    private Document doc;
    private Element root;

    public GnQuery() {

        try {
            this.doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            this.root = doc.createElement("QUERIES");
            this.doc.appendChild(root);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void addAuth(String clientID, String userID) {
        Element auth = doc.createElement("AUTH");
        Element client = doc.createElement("CLIENT");
        Element user = doc.createElement("USER");
        client.setTextContent(clientID);
        user.setTextContent(userID);
        root.appendChild(auth);
        auth.appendChild(client);
        auth.appendChild(user);
    }

    public void addQuery(String cmd) {
        Element query = doc.createElement("QUERY");
        query.setAttribute("CMD", cmd);
        root.appendChild(query);
    }


    public void addQueryMode(String modeStr) {
        Element mode = doc.createElement("MODE");
        mode.setTextContent(modeStr);
        root.getElementsByTagName("QUERY")
                .item(0)
                .appendChild(mode);
    }


    public void addQueryOption(String parameterName, String value) {
        Element option = doc.createElement("OPTION");
        Element parameter = doc.createElement("PARAMETER");
        Element valueElem = doc.createElement("VALUE");
        parameter.setTextContent(parameterName);
        valueElem.setTextContent(value);
        root.getElementsByTagName("QUERY")
                .item(0)
                .appendChild(option);
        option.appendChild(parameter);
        option.appendChild(valueElem);
    }


    public void addQueryClient(String clientID) {
        Element client = doc.createElement("CLIENT");
        client.setTextContent(clientID);
        root.getElementsByTagName("QUERY")
                .item(0)
                .appendChild(client);
    }


    public void addQueryRange(String start, String end) {
        Element queryRange = doc.createElement("RANGE");
        Element rangeStart = doc.createElement("START");
        Element rangeEnd = doc.createElement("END");
        rangeStart.setTextContent(start);
        rangeEnd.setTextContent(end);
        root.getElementsByTagName("QUERY")
                .item(0)
                .appendChild(queryRange);
        queryRange.appendChild(rangeStart);
        queryRange.appendChild(rangeEnd);
    }


    public void addAttributeSeed(String moodID, String eraID, String genreID) {
        Element seed = doc.createElement("SEED");
        seed.setAttribute("TYPE", "ATTRIBUTE");
        if (moodID.isEmpty()) {
        } else {
            Element moodElement = doc.createElement("MOOD");
            moodElement.setAttribute("ID", moodID);
            seed.appendChild(moodElement);
        }
        if (eraID.isEmpty()) {
        } else {
            Element eraElement = doc.createElement("ERA");
            eraElement.setAttribute("ID", eraID);
            seed.appendChild(eraElement);
        }
        if (genreID.isEmpty()) {
        } else {
            Element genreElement = doc.createElement("GENRE");
            genreElement.setAttribute("ID", genreID);
            seed.appendChild(genreElement);
        }
        root.getElementsByTagName("QUERY")
                .item(0)
                .appendChild(seed);
    }

    public void addTextSeed(String artist, String track) {
        Element seed = doc.createElement("SEED");
        seed.setAttribute("TYPE", "TEXT");
        if (artist.isEmpty()) {
        } else {
            Element artistElement = doc.createElement("TEXT");
            artistElement.setAttribute("TYPE", "ARTIST");
            artistElement.setTextContent(artist);
        }
        if (track.isEmpty()) {
        } else {
            Element trackElement = doc.createElement("TEXT");
            trackElement.setAttribute("TYPE", "TRACK");
            trackElement.setTextContent(track);
        }
        root.getElementsByTagName("QUERY")
                .item(0)
                .appendChild(seed);
    }

    public void addQueryEVENT(String eventType, String gnID) {
        Element event = doc.createElement("Event");
        Element gnidTag = doc.createElement("GN_ID");
        event.setAttribute("TYPE", eventType);
        gnidTag.setTextContent(gnID);
        root.getElementsByTagName("QUERY")
                .item(0)
                .appendChild(event);
        event.appendChild(gnidTag);
    }

    public void addQueryFieldValues(String name) {
        Element fieldname = doc.createElement("FIELDNAME");
        fieldname.setTextContent(name);
        root.getElementsByTagName("QUERY")
                .item(0)
                .appendChild(fieldname);
    }

    public void addRadioID(String radioID) {
        Element radio = doc.createElement("RADIO");
        Element myradioid = doc.createElement("ID");
        myradioid.setTextContent(radioID);
        root.getElementsByTagName("QUERY")
                .item(0)
                .appendChild(radio);
        radio.appendChild(myradioid);
    }

    @Override
    public String toString() {
        // create Transformer object
        Transformer transformer = null;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        try {
            assert transformer != null;
            transformer.transform(new DOMSource(doc), result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        // return XML string
        return writer.toString();
    }
}
