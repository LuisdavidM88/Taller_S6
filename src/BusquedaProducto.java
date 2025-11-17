public interface BusquedaProducto {
    // Editar un producto usando su id (retorna true si lo encuentra)
    boolean editarPorId(int id, Producto dato);
    // Buscar y devolver un producto por id
    Producto buscarPorId(int id);
    // Buscar y devolver un producto por nombre
    Producto buscarPorNombre(String nombre);
}
