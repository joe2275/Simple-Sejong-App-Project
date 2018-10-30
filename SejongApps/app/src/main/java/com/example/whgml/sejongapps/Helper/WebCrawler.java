package com.example.whgml.sejongapps.Helper;

import android.os.StrictMode;

import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class WebCrawler {

    private String content;

    public WebCrawler(int n)
    {
        content = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            setSSL();
            String url = "https://en.wikipedia.org/w/index.php?title=" + n + "&action=edit";
            Document doc = Jsoup.connect(url).get();

            Elements elem = doc.getElementsByAttributeValue("tabindex", "1");
            StringBuilder htmlString = new StringBuilder(elem.toString());

            StringBuilder contentString = new StringBuilder();

            int index = htmlString.indexOf("*", 0);
            int prevIndex = index;
            while(true) {
                prevIndex = index;
                index = htmlString.indexOf("\n", index) + 1;
                if(index == -1 || prevIndex == -1)
                    break;
                String line = htmlString.substring(prevIndex, index);
                if(!line.contains("*"))
                {
                    break;
                }
                contentString.append(htmlString.substring(prevIndex, index));
            }


            content = contentString.toString();

        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch(KeyManagementException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String getContent()
    {
        return content;
    }

    public void setSSL() throws NoSuchAlgorithmException, KeyManagementException
    {
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }};
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultHostnameVerifier(
                new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                }
        );
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }
}
