public class Avengers {
    private String id;
    private String nombre;
    private String mision;
    private int nivelPeligrosidad;
    private double pagoMensual;

    public Avengers(String id, String nombre, String mision, int nivelPeligrosidad, double pagoMensual) {
        this.id = id;
        this.nombre = nombre;
        this.mision = mision;
        this.nivelPeligrosidad = nivelPeligrosidad;
        this.pagoMensual = pagoMensual;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMision() {
        return mision;
    }

    public void setMision(String mision) {
        this.mision = mision;
    }

    public int getNivelPeligrosidad() {
        return nivelPeligrosidad;
    }

    public void setNivelPeligrosidad(int nivelPeligrosidad) {
        this.nivelPeligrosidad = nivelPeligrosidad;
    }

    public double getPagoMensual() {
        return pagoMensual;
    }

    public void setPagoMensual(double pagoMensual) {
        this.pagoMensual = pagoMensual;
    }
}
