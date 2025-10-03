package fr.uvsq.cprog.collex;

import java.util.regex.Pattern;

public class AdresseIP {
    private final String ip;

    private static final Pattern IPV4_PATTERN = Pattern.compile(
        "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.|$)){4}$"
    );

    public AdresseIP(String ip) {
        if (ip == null || !IPV4_PATTERN.matcher(ip).matches()) {
            throw new IllegalArgumentException("Adresse IP invalide : " + ip);
        }
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    @Override
    public String toString() {
        return ip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdresseIP)) return false;
        AdresseIP other = (AdresseIP) o;
        return ip.equals(other.ip);
    }

    @Override
    public int hashCode() {
        return ip.hashCode();
    }
}
