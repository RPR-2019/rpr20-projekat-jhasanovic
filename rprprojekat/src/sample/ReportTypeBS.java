package sample;

public enum ReportTypeBS {
    REPORT1("Generalni izvještaj"),
    REPORT2("Dnevni izvještaj");

    private final String opis;

    private ReportTypeBS(String opis) { this.opis = opis; }

    @Override
    public String toString() { return opis; }
}

