public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private int ventasMes1;
    private int ventasMes2;
    private int ventasMes3;

    public Producto(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;

        // Ventas iniciales en cero
        this.ventasMes1 = 0;
        this.ventasMes2 = 0;
        this.ventasMes3 = 0;
    }

    // Constructor cuando ya tiene ventas acumuladas
    public Producto(int id, String nombre, double precio,
                    int ventasMes1, int ventasMes2, int ventasMes3) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.ventasMes1 = ventasMes1;
        this.ventasMes2 = ventasMes2;
        this.ventasMes3 = ventasMes3;
    }

    // Métodos getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getVentasMes1() { return ventasMes1; }
    public int getVentasMes2() { return ventasMes2; }
    public int getVentasMes3() { return ventasMes3; }

    // Cambiar el precio del producto
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // Registrar venta según el mes indicado
    public void registrarVenta(int mes, int cantidad) {
        switch (mes) {
            case 1 -> ventasMes1 += cantidad;
            case 2 -> ventasMes2 += cantidad;
            case 3 -> ventasMes3 += cantidad;
            default -> {}
        }
    }

    // Suma total de ventas de los 3 meses
    public int getVentasTotales() {
        return ventasMes1 + ventasMes2 + ventasMes3;
    }

    @Override
    public String toString() {
        return "Producto id: " + id +
                ", nombre: " + nombre +
                String.format(", precio: %.2f", precio) +
                ", ventas M1: " + ventasMes1 +
                ", ventas M2: " + ventasMes2 +
                ", ventas M3: " + ventasMes3;
    }
}
