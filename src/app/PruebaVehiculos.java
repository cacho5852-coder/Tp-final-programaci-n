package app;

import java.util.ArrayList;
import java.util.List;
import modelo.Bicicleta;
import modelo.Camioneta;
import modelo.Moto;
import modelo.Vehiculo;

public class PruebaVehiculos {
    public static void main(String[] args) {
        List<Vehiculo> vehiculos = new ArrayList<>();
        vehiculos.add(new Bicicleta("BICI01"));
        vehiculos.add(new Moto("MOT123"));
        vehiculos.add(new Camioneta("CAM456"));

        // Polimorfismo: mismo mensaje, distinto comportamiento
        for (Vehiculo v : vehiculos) {
            System.out.println(v + " -> 20 km: " + v.calcularTiempoEntrega(20) + " min");
        }

        // equals por patente
        System.out.println(vehiculos.contains(new Moto("MOT123"))); // true
    }
}
