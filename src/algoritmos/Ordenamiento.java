package algoritmos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import modelo.Pedido;

/** Algoritmos de ordenamiento e intercalación de pedidos. */
public class Ordenamiento {
    private Ordenamiento() {
    }

    /**
     * Ordena la lista de menor a mayor con el método de inserción.
     * Es estable: si dos pedidos empatan, mantienen su orden original.
     */
    public static void ordenar(List<Pedido> lista, Comparator<Pedido> criterio) {
        for (int i = 1; i < lista.size(); i++) {
            Pedido actual = lista.get(i);
            int j = i - 1;

            while (j >= 0 && criterio.compare(lista.get(j), actual) > 0) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, actual);
        }
    }

    /**
     * Intercala dos listas ya ordenadas con el mismo criterio y devuelve
     * una lista nueva también ordenada (las originales no se modifican).
     */
    public static List<Pedido> intercalar(List<Pedido> a, List<Pedido> b, Comparator<Pedido> criterio) {
        List<Pedido> resultado = new ArrayList<>();
        int i = 0;
        int j = 0;

        while (i < a.size() && j < b.size()) {
            if (criterio.compare(a.get(i), b.get(j)) <= 0) {
                resultado.add(a.get(i++));
            } else {
                resultado.add(b.get(j++));
            }
        }
        while (i < a.size()) {
            resultado.add(a.get(i++));
        }
        while (j < b.size()) {
            resultado.add(b.get(j++));
        }
        return resultado;
    }
}