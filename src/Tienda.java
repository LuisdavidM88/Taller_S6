import java.util.ArrayList;
import java.util.List;

// Implementa la interfaz y administra los productos
public class Tienda implements BusquedaProducto {
    private List<Producto> listado;

    public Tienda() {
        // Lista dinámica para guardar los productos
        listado = new ArrayList<Producto>();
    }

    // Agregar un producto a la tienda
    public void agregar(Producto p) {
        listado.add(p);
    }

    // Editar un producto usando búsqueda binaria
    @Override
    public boolean editarPorId(int id, Producto dato) {
        int i = 0;
        int s = listado.size() - 1;
        int c;

        // Búsqueda binaria en lista ordenada por id
        while (i <= s) {
            c = (i + s) / 2;

            if (id == listado.get(c).getId()) {
                listado.set(c, dato); // Reemplaza el producto
                return true;
            } else if (id < listado.get(c).getId()) {
                s = c - 1;
            } else {
                i = c + 1;
            }
        }
        return false; // No encontrado
    }

    // Buscar un producto por id (búsqueda binaria)
    @Override
    public Producto buscarPorId(int id) {
        int i = 0;
        int s = listado.size() - 1;
        int c;

        while (i <= s) {
            c = (i + s) / 2;

            if (id == listado.get(c).getId()) {
                return listado.get(c);
            } else if (id < listado.get(c).getId()) {
                s = c - 1;
            } else {
                i = c + 1;
            }
        }
        return null; // No encontrado
    }

    // Buscar por nombre (búsqueda lineal)
    @Override
    public Producto buscarPorNombre(String nombre) {
        for (Producto p : listado) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    // Obtener todos los productos
    public List<Producto> todos() {
        return listado;
    }

    // Suma de ventas totales usando recursividad
    public int sumarVentasTotales(int i) {
        if (i == listado.size())
            return 0;
        else
            return listado.get(i).getVentasTotales() + sumarVentasTotales(i + 1);
    }

    // Contador de productos usando recursividad
    public int conteo(int i) {
        if (i == listado.size())
            return 0;
        else
            return 1 + conteo(i + 1);
    }
}
