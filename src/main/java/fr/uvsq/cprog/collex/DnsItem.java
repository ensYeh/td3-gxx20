package fr.uvsq.cprog.collex;

public class DnsItem {
    private final NomMachine nomMachine;
    private final AdresseIP adresseIP;

    public DnsItem(NomMachine nomMachine, AdresseIP adresseIP) {
        if (nomMachine == null || adresseIP == null) {
            throw new IllegalArgumentException("NomMachine et AdresseIP ne peuvent pas etre null");
        }
        this.nomMachine = nomMachine;
        this.adresseIP = adresseIP;
    }

    public NomMachine getNomMachine() {
        return nomMachine;
    }

    public AdresseIP getAdresseIP() {
        return adresseIP;
    }

    @Override
    public String toString() {
        return adresseIP + " " + nomMachine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DnsItem)) return false;
        DnsItem other = (DnsItem) o;
        return nomMachine.equals(other.nomMachine) && adresseIP.equals(other.adresseIP);
    }

    @Override
    public int hashCode() {
        return nomMachine.hashCode() ^ adresseIP.hashCode();
    }
}
