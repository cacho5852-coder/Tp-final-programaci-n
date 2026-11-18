package app;

import java.util.ArrayList;
import java.util.List;
import modelo.Bicicleta;
import modelo.Camioneta;
import modelo.Moto;
import modelo.Vehiculo;

/** Prueba rápida de los vehículos: tiempos de entrega y equals por patente. */
public class PruebaVehiculos {
    public static void main(String[] args) {
        List<Vehiculo> vehiculos = new ArrayList<>();
        vehiculos.add(new Bicicleta("BICI01"));
        vehiculos.add(new Moto("MOT123"));
        vehiculos.add(new Camioneta("CAM456"));

        // mismo método, resultado distinto según el vehículo (polimorfismo)
        for (Vehiculo v : vehiculos) {
            System.out.println(v + " -> 20 km: " + v.calcularTiempoEntrega(20) + " min");
        }

        // debería dar true porque la patente es la misma
        System.out.println(vehiculos.contains(new Moto("MOT123")));
    }
}