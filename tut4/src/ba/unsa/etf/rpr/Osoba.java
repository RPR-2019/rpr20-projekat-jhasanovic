package ba.unsa.etf.rpr;

public class Osoba {
    private String ime;
    private String prezime;

    Osoba(String ime,String prezime){
        this.ime=ime;
        this.prezime=prezime;
    }

    @Override
    public String toString() {
        return "Osoba{" +
                "ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                '}';
    }
}
