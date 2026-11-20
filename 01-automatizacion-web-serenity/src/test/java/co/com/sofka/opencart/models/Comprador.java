package co.com.sofka.opencart.models;

/**
 * Datos personales y de facturación de un comprador que realiza el checkout como invitado.
 * Es inmutable: se construye una sola vez a partir de los datos de prueba.
 */
public final class Comprador {

    private final String nombre;
    private final String apellido;
    private final String correo;
    private final String telefono;
    private final String direccion;
    private final String ciudad;
    private final String codigoPostal;
    private final String pais;
    private final String region;

    private Comprador(Builder builder) {
        this.nombre = builder.nombre;
        this.apellido = builder.apellido;
        this.correo = builder.correo;
        this.telefono = builder.telefono;
        this.direccion = builder.direccion;
        this.ciudad = builder.ciudad;
        this.codigoPostal = builder.codigoPostal;
        this.pais = builder.pais;
        this.region = builder.region;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public String getPais() {
        return pais;
    }

    public String getRegion() {
        return region;
    }

    public static final class Builder {
        private String nombre;
        private String apellido;
        private String correo;
        private String telefono;
        private String direccion;
        private String ciudad;
        private String codigoPostal;
        private String pais;
        private String region;

        private Builder() {
        }

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder apellido(String apellido) {
            this.apellido = apellido;
            return this;
        }

        public Builder correo(String correo) {
            this.correo = correo;
            return this;
        }

        public Builder telefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public Builder direccion(String direccion) {
            this.direccion = direccion;
            return this;
        }

        public Builder ciudad(String ciudad) {
            this.ciudad = ciudad;
            return this;
        }

        public Builder codigoPostal(String codigoPostal) {
            this.codigoPostal = codigoPostal;
            return this;
        }

        public Builder pais(String pais) {
            this.pais = pais;
            return this;
        }

        public Builder region(String region) {
            this.region = region;
            return this;
        }

        public Comprador build() {
            return new Comprador(this);
        }
    }
}
