import java.util.ArrayList;

public class GestionAvengers {
    private ArrayList<Avengers>
            avengers = new ArrayList<>();


    public void registrarAvengers(Avengers nuevo) throws Exception{
        for (Avengers a: avengers){
            if (a.getId()==nuevo.getId()){
                throw new Exception("El ID ya se está registrado.");
            }
        }
        avengers.add(nuevo);
    }

    public Avengers editarAvengers(int codigo,String nuevoNombre,String nuevaMision, int nuevaPeligrosidad, int nuevoPAgo) throws Exception{
        for (Avengers a: avengers){
            if (a.getId()==codigo){
                a.setNombre(nuevoNombre);
                a.setMision(nuevaMision);
                a.setNivelPeligrosidad(nuevaPeligrosidad);
                a.setPagoMensual(nuevoPAgo);
                return a;
            }
        }
        throw new Exception("Avenger no encontrado.");
    }

    public ArrayList<Avengers> listarAvengers() {
        return avengers;
    }

    public String calcularfondoheroes(int id){
        for (Avengers avenger: avengers){
            if (avenger.getId()==id){
                double fondoHeroes = avenger.getPagoMensual() * 0.08;
                double impuesto = impuestoanual(avenger.getPagoMensual() * 12);
                double neto = avenger.getPagoMensual() - fondoHeroes - impuesto /12;
                return String.format(
                        "Nombre: %s\nPago Mensual: %.2f\nFondo Héroes: %.2f\nImpuesto: %.2f\nNeto: %.2f",
                        avenger.getNombre(),avenger.getPagoMensual(),fondoHeroes,impuesto,neto);
            }
        }
        return "Avenger no encontrado.";
    }

    private double impuestoanual(double anual){
        if (anual <= 50000) return 0;
        if (anual > 50000 && anual <= 100000) return 0.10 * (anual - 50000);
        if (anual > 100000 && anual <= 200000) return 0.20 * (anual - 100000);
        return 0.30 * (anual - 200000);
    }
}
