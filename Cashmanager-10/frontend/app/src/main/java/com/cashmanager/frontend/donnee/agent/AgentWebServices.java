package com.cashmanager.frontend.donnee.agent;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.SSLContext;

public class AgentWebServices {
    public enum TypesAppel {
        GET,
        POST,
        PUT,
        UPDATE
    }

    /**
     * Protocole d'appel du Web Service.
     */
    private static final String PROTOCOLE_APPEL = "http";

    /**
     * Séparateur de l'URL et du numéro de port.
     */
    private static final String SEPARATEUR_URL_PORT = ":";

    /**
     * Propriété de requête HTTP pour le type de contenu.
     */
    private static final String PROPRIETE_REQUETE_TYPE_CONTENU = "Content-Type";

    /**
     * Type de contenu JSON.
     */
    private static final String TYPE_CONTENU_JSON = "application/json; charset=utf-8";

    /**
     * Jeu de caractères des Web Services.
     */
    private static final String JEU_CARACTERES_WEB_SERVICES = "UTF-8";

    /**
     * Délimiteur de réponse de requête HTTPS.
     */
    private static final String DELIMITEUR_REPONSE_REQUETE = "\\A";

    /**
     * Contexte d'utilisation de l'agent.
     */
    private Context contexte;

    /**
     * Type d'appel du Web Service.
     */
    private TypesAppel typeAppel;

    /**
     * URL du Web Service.
     **/
    private String url;

    /**
     * URI du Web Service.
     **/
    private String uri;

    /**
     * Attribut port.
     */
    private Integer port;

    public Context getContexte() {
        return contexte;
    }

    public void setContexte(Context contexte) {
        this.contexte = contexte;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * Constructeur
     *
     * @param contexte
     * @param typeAppel
     * @param url
     * @param port
     */
    public AgentWebServices(Context contexte, TypesAppel typeAppel, String url, Integer port) throws IOException {
        this.contexte = contexte;
        this.typeAppel = typeAppel;
        this.url = url;
        this.port = port;
    }


}
