# language: es
@compra_invitado
Característica: Compra de productos como invitado en OpenCart
  Como cliente que no tiene cuenta en la tienda
  Quiero comprar productos usando el checkout de invitado
  Para recibir mi pedido sin necesidad de registrarme

  Antecedentes:
    Dado que "Cliente" está en la tienda OpenCart

  @e2e @smoke
  Escenario: Comprar dos productos con Guest Checkout hasta la confirmación del pedido
    Cuando agrega al carrito los productos
      | iPhone  |
      | MacBook |
    Y visualiza el carrito de compras
    Entonces debe ver en el carrito los productos
      | iPhone  |
      | MacBook |
    Cuando finaliza la compra como invitado con los datos
      | nombre | apellido | telefono   | direccion        | ciudad | codigoPostal | pais     | region      |
      | Ana    | Gomez    | 3001234567 | Calle 10 # 20-30 | Bogota | 110111       | Colombia | Bogota D.C. |
    Entonces debe ver el mensaje de confirmación "Your order has been placed!"
