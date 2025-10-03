package fr.uvsq.cprog.collex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

public class Dns {
    private final List<DnsItem> items = new ArrayList<>();
    private final Path dbFile;

    /**
     * Construit un DNS et charge la base de données depuis le fichier indiqué
     * dans le fichier properties (par ex. config.properties).
     */
    public Dns() throws IOException {
        Properties props = new Properties();
        props.load(Files.newBufferedReader(Paths.get("config.properties")));
        this.dbFile = Paths.get(props.getProperty("dns.file"));

        // Charger les lignes
        List<String> lignes = Files.readAllLines(dbFile);
        for (String ligne : lignes) {
            String[] parts = ligne.split(" ");
            if (parts.length == 2) {
                NomMachine nom = new NomMachine(parts[0]);
                AdresseIP ip = new AdresseIP(parts[1]);
                items.add(new DnsItem(nom, ip));
            }
        }
    }

    /** Retourne un item par adresse IP. */
    public DnsItem getItem(AdresseIP ip) {
        return items.stream()
                .filter(i -> i.getAdresseIP().equals(ip))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Adresse IP inconnue"));
    }

    /** Retourne un item par nom de machine. */
    public DnsItem getItem(NomMachine nom) {
        return items.stream()
                .filter(i -> i.getNomMachine().equals(nom))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Nom de machine inconnu"));
    }

    /** Retourne tous les items d’un domaine. */
    public List<DnsItem> getItems(String domaine) {
        return items.stream()
                .filter(i -> i.getNomMachine().getDomaine().equals(domaine))
                .collect(Collectors.toList());
    }

    /** Ajoute un item et met à jour le fichier. */
    public void addItem(AdresseIP ip, NomMachine nom) throws IOException {
        // Vérifier que l’IP ou le nom n’existe pas déjà
        Optional<DnsItem> existingIp = items.stream().filter(i -> i.getAdresseIP().equals(ip)).findFirst();
        Optional<DnsItem> existingNom = items.stream().filter(i -> i.getNomMachine().equals(nom)).findFirst();

        if (existingIp.isPresent()) {
            throw new IllegalArgumentException("ERREUR : L'adresse IP existe deja !");
        }
        if (existingNom.isPresent()) {
            throw new IllegalArgumentException("ERREUR : Le nom de machine existe deja !");
        }

        DnsItem item = new DnsItem(nom, ip);
        items.add(item);

        // Sauvegarder dans le fichier
        List<String> lignes = items.stream()
                .map(i -> i.getNomMachine().toString() + " " + i.getAdresseIP().toString())
                .collect(Collectors.toList());
        Files.write(dbFile, lignes);
    }
}
