# Taller Semana 6 — Programación III  

## 📌 Descripción del ejercicio  
El programa implementa una tienda en línea que administra hasta **tres productos**.  
Cada producto contiene: ID, nombre, precio y registro de ventas de los últimos tres meses.  

El sistema permite:  
- Registrar productos (máximo tres).  
- Actualizar el precio de un producto existente.  
- Registrar ventas mensuales.  
- Buscar productos por **ID** mediante búsqueda binaria.  
- Buscar productos por **nombre** mediante búsqueda lineal.  
- Visualizar un resumen general de ventas utilizando métodos recursivos.  

---

## 👥 Integrantes del grupo  
- **Emily Mullo**  
- **Lucas Karlsson**  
- **David Morales**  
- **Johan Gamboa**  

---

## 🧠 Explicación de métodos utilizados  

### 🔹 1. Búsqueda Binaria (por ID)  
Se aplica cuando se busca un producto por su identificador.  
Como los productos solo se pueden ingresar en **orden creciente**, la lista siempre se mantiene ordenada, permitiendo usar búsqueda binaria.  

Métodos que usan este enfoque:  
- `buscarPorId(int id)`  
- `editarPorId(int id, Producto dato)`  

La búsqueda binaria reduce el tiempo de búsqueda dividiendo la lista en mitades sucesivamente hasta localizar el ID.

---

### 🔹 2. Búsqueda Lineal (por nombre)  
Para buscar un producto por nombre, se recorre la lista elemento por elemento hasta encontrar una coincidencia.  
Esta técnica se usa porque no se exige ordenamiento alfabético en los nombres.

Método:  
- `buscarPorNombre(String nombre)`

---

### 🔹 3. Métodos Recursivos  
El programa utiliza recursividad para operaciones de conteo y cálculo:

- `sumarVentasTotales(int i)` → suma recursivamente todas las ventas del arreglo.  
- `conteo(int i)` → cuenta cuántos productos existen en la lista.  

---

