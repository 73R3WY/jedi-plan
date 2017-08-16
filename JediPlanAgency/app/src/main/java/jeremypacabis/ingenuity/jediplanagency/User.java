package jeremypacabis.ingenuity.jediplanagency;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jeremy Patrick on 8/16/2017.
 * Author: Jeremy Patrick G. Pacabis
 * for jeremypacabis.ingenuity.jediplanagency @ JediPlanAgency
 */

public class User implements Serializable {
    private String id, first_name, last_name, username, password, type, position, rate, sss, hdmf, phic;
    private ArrayList<LogEntry> logEntries;

    public User(String id, String first_name, String last_name, String username, String password, String type, String position, String rate, String sss, String hdmf, String phic, ArrayList<LogEntry> logEntries) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password = password;
        this.type = type;
        this.position = position;
        this.rate = rate;
        this.sss = sss;
        this.hdmf = hdmf;
        this.phic = phic;
        this.logEntries = logEntries;
    }

    public String getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public String getPosition() {
        return position;
    }

    public String getRate() {
        return rate;
    }

    public String getSss() {
        return sss;
    }

    public String getHdmf() {
        return hdmf;
    }

    public String getPhic() {
        return phic;
    }

    public ArrayList<LogEntry> getLogEntries() {
        return logEntries;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setSss(String sss) {
        this.sss = sss;
    }

    public void setHdmf(String hdmf) {
        this.hdmf = hdmf;
    }

    public void setPhic(String phic) {
        this.phic = phic;
    }

    public void setLogEntries(ArrayList<LogEntry> logEntries) {
        this.logEntries = logEntries;
    }
}
