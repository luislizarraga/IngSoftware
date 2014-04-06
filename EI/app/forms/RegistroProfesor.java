package forms;

public class RegistroProfesor {

	public String nombre;
    
    public String apellidoPaterno;

    public String apellidoMaterno;

    public String correoElectronico;

    public String contrasena;

    public String confContrasena;

    public String constancia;

    public String video;


    public List<ValidationError> validate() {
        List<ValidationError> errores = new ArrayList<ValidationError>();
        if (correoElectronico.equals("")) {
            errores.add(new ValidationError("correoElectronico", "Este campo es requerido."));
        } else {
            Profesor p = Profesor.find.where().eq("correoElectronico", correoElectronico).findUnique();
            if (p != null) {
                errores.add(new ValidationError("correoElectronico", "Este correo ya se encuentra registrado."));
            }
            System.out.println(correoElectronico.matches("[a-zA-Z0-9-\\+]+@[a-zA-Z0-9-\\+]+(.[a-zA-Z0-9-\\+]+)+"));
            if (!correoElectronico.matches("[a-zA-Z0-9-\\+]+@[a-zA-Z0-9-\\+]+(.[a-zA-Z0-9-\\+]+)+")) {
                errores.add(new ValidationError("correoElectronico", "Correo electrónico inválido."));
            }
        }
        if (contrasena.equals("") || confContrasena.equals("")) {
            errores.add(new ValidationError("contrasena", "Este campo es requerido."));
            errores.add(new ValidationError("confContrasena", "Este campo es requerido."));
        } else if (!contrasena.equals(confContrasena)) {
            errores.add(new ValidationError("contrasena", "Las contraseñas no coinciden."));
        }
        if (nombre.equals("")) {
            errores.add(new ValidationError("nombre", "Este campo es requerido."));
        }
        if (apellidoPaterno.equals("")) {
            errores.add(new ValidationError("apellidoPaterno", "Este campo es requerido."));
        }

        if (constancia.equals("")) {
            errores.add(new ValidationError("constancia", "Este campo es requerido."));
        }

        if (video.equals("")) {
            errores.add(new ValidationError("video", "Este campo es requerido."));
        }

        return errores.isEmpty() ? null : errores;
    }



}