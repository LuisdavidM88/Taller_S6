import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Ventana {
    private JPanel panel;
    private JTabbedPane tabbedPane;
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JComboBox<String> cboMes;
    private JSpinner spiCantidadVenta;
    private JButton btnAgregarProducto;
    private JButton btnRegistrarVenta;
    private JButton btnEditarPrecio;
    private JTextField txtIdBusqueda;
    private JTextField txtNombreBusqueda;
    private JButton btnBuscarId;
    private JButton btnBuscarNombre;
    private JList lstProductos;
    private JButton btnMostrar;
    private JLabel lblSumatoriaVentas;
    private JLabel lblConteoProductos;

    // Objeto que administra los productos de la tienda
    Tienda tienda = new Tienda();

    // Guarda el último id utilizado, para mantener orden
    int codigo = 0;

    // Índice del producto seleccionado en la lista
    int indice;

    public Ventana() {

        // Configura el spinner para registrar la cantidad de venta
        SpinnerNumberModel spCantidad = new SpinnerNumberModel(
                1,  // valor inicial
                1,  // valor mínimo
                100,// valor máximo
                1   // paso de incremento
        );
        spiCantidadVenta.setModel(spCantidad);

        // BOTÓN: AGREGAR PRODUCTO

        btnAgregarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Lee los datos ingresados en los campos de texto
                int id = Integer.parseInt(txtId.getText());
                String nombre = txtNombre.getText();

                double precio;
                try {
                    // Convierte el texto a número decimal
                    precio = Double.parseDouble(txtPrecio.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Ingrese un precio válido (solo números).");
                    return;
                }

                // El precio no puede ser negativo
                if (precio < 0) {
                    JOptionPane.showMessageDialog(null,
                            "El precio no puede ser negativo.");
                    return;
                }

                // Valida que el id sea mayor al último registrado (para mantener orden)
                if (id <= codigo) {
                    JOptionPane.showMessageDialog(null,
                            "Id inválido. Debe ser mayor que " + codigo);

                    // Limita la tienda a un máximo de 3 productos
                } else if (tienda.todos().size() >= 3) {
                    JOptionPane.showMessageDialog(null,
                            "Solo se permiten 3 productos en la tienda.");
                } else {
                    // Crea el producto y lo agrega a la tienda
                    Producto p = new Producto(id, nombre, precio);
                    tienda.agregar(p);

                    // Actualiza el último código usado
                    codigo = id;

                    JOptionPane.showMessageDialog(null, "Producto ingresado");
                }
            }
        });


        // BOTÓN: EDITAR PRECIO DE PRODUCTO

        btnEditarPrecio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Lee id y nombre desde los campos
                int id = Integer.parseInt(txtId.getText());
                String nombre = txtNombre.getText();

                double precio;
                try {
                    precio = Double.parseDouble(txtPrecio.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Ingrese un precio válido (solo números).");
                    return;
                }

                if (precio < 0) {
                    JOptionPane.showMessageDialog(null,
                            "El precio no puede ser negativo.");
                    return;
                }

                // Busca el producto actual por id
                Producto existente = tienda.buscarPorId(id);
                if (existente == null) {
                    JOptionPane.showMessageDialog(null,
                            "No existe un producto con ese id");
                    return;
                }

                // Crea un nuevo producto con el precio actualizado
                // pero conservando las ventas de los 3 meses
                Producto actualizado = new Producto(
                        id,
                        nombre,
                        precio,
                        existente.getVentasMes1(),
                        existente.getVentasMes2(),
                        existente.getVentasMes3()
                );

                // Aplica la actualización usando búsqueda binaria
                if (tienda.editarPorId(id, actualizado)) {
                    JOptionPane.showMessageDialog(null,
                            "Precio del producto actualizado");

                    // Refresca la lista y el resumen
                    mostrarProductosEnLista();
                    actualizarResumen();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "No se pudo editar (error en búsqueda binaria)");
                }
            }
        });


        // LISTA: seleccionar producto para mostrar sus datos

        lstProductos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Solo actúa cuando hay un elemento seleccionado
                if (lstProductos.getSelectedIndex() != -1) {
                    indice = lstProductos.getSelectedIndex();

                    // Obtiene el producto seleccionado
                    Producto p = tienda.todos().get(indice);

                    // Muestra sus datos en los campos de texto
                    txtId.setText("" + p.getId());
                    txtNombre.setText(p.getNombre());
                    txtPrecio.setText(String.valueOf(p.getPrecio()));
                }
            }
        });


        // BOTÓN: REGISTRAR VENTA

        btnRegistrarVenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Se debe ingresar el id para registrar ventas
                if (txtIdBusqueda.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Ingrese el ID del producto para registrar la venta");
                    return;
                }

                int id = Integer.parseInt(txtIdBusqueda.getText());
                Producto p = tienda.buscarPorId(id);

                if (p == null) {
                    JOptionPane.showMessageDialog(null,
                            "No existe un producto con ese ID");
                    return;
                }

                // El índice del combo inicia en 0, por eso se suma 1
                int mesSeleccionado = cboMes.getSelectedIndex() + 1;

                // Obtiene la cantidad desde el spinner
                int cantidad = Integer.parseInt(spiCantidadVenta.getValue().toString());

                // Registra la venta en el mes correspondiente
                p.registrarVenta(mesSeleccionado, cantidad);

                JOptionPane.showMessageDialog(null,
                        "Venta registrada para: " + p.getNombre());

                // Actualiza la lista y el resumen
                mostrarProductosEnLista();
                actualizarResumen();
            }
        });


        // BOTÓN: BUSCAR POR ID

        btnBuscarId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int id = Integer.parseInt(txtIdBusqueda.getText());
                Producto p = tienda.buscarPorId(id);

                if (p != null) {
                    JOptionPane.showMessageDialog(null, p.toString());
                } else {
                    JOptionPane.showMessageDialog(null,
                            "No existe un producto con ese ID");
                }
            }
        });


        // BOTÓN: BUSCAR POR NOMBRE

        btnBuscarNombre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nombre = txtNombreBusqueda.getText();
                Producto p = tienda.buscarPorNombre(nombre);

                if (p != null) {
                    JOptionPane.showMessageDialog(null, p.toString());
                } else {
                    JOptionPane.showMessageDialog(null,
                            "No existe un producto con ese nombre");
                }
            }
        });


        // BOTÓN: MOSTRAR LISTA Y RESUMEN

        btnMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarProductosEnLista();
                actualizarResumen();
            }
        });
    }

    // Llena el JList con los productos de la tienda
    private void mostrarProductosEnLista() {
        DefaultListModel dlm = new DefaultListModel();
        List<Producto> productos = tienda.todos();

        for (Producto p : productos) {
            dlm.addElement(p.toString());
        }
        lstProductos.setModel(dlm);
    }

    // Muestra la sumatoria total de ventas y el conteo de productos
    private void actualizarResumen() {
        int totalVentas = tienda.sumarVentasTotales(0);
        int cantidadProductos = tienda.conteo(0);

        lblSumatoriaVentas.setText("VENTAS TOTALES (3 meses): " + totalVentas);
        lblConteoProductos.setText("CANTIDAD DE PRODUCTOS: " + cantidadProductos);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ventana");
        frame.setContentPane(new Ventana().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
