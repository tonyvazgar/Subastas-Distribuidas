package Server;

import Cliente.*;

import Cliente.View.*;
import Model.*;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luisluna
 */
public class Server implements Remote, ServidorMetodos {

    public LinkedList<UsuarioInt> users = new LinkedList<>();
    public LinkedList<SubastaInt> subastas = new LinkedList<>();

    public LinkedList<ClienteMetodos> clientes = new LinkedList<>();

    ///
    /*
         * Codigo para hacer la conexion con el servidor
         *
     */
    public static void main(String[] argumentos) {
        //System.setSecurityManager(new SecurityManager());

        //Conectar con rmi registry
        Registry registroNombres = null;

        try {
            Server server = new Server();
            /*
            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingresa tu nombre de usuario: ");
            String username = new Scanner(System.in).nextLine();
            System.out.println("Ingresa tu nombre: ");
            String name = new Scanner(System.in).nextLine();
            System.out.println("Ingresa tu nombre de contraseña: ");
            String pass = new Scanner(System.in).nextLine();
            Usuario u = new Usuario(name, username, pass, "");
            u.setDireccion("");
            server.users.add(u);    
            */
            Usuario u1 = new Usuario("Luis", "luis", "123", "luis@udlap.mx");
            u1.setDireccion("10B Sur");
            server.users.add(u1);

            Usuario u2 = new Usuario("Mónica Perez Paredes", "moni", "123", "monica.perezps@udlap.mx");
            u2.setDireccion("Rosas");
            server.users.add(u2);

            Usuario u3 = new Usuario("Victor Pulido Contreras", "vic", "123", "victor.pulidocs@udlap.mx");
            u3.setDireccion("Fortin");
            server.users.add(u3);
            
            Usuario u4 = new Usuario("Tony Vazgar", "tony", "123", "luis.vazquezga@udlap.mx");
            u4.setDireccion("Puebla");
            server.users.add(u4);
            
            Usuario u5 = new Usuario("Sara Fernanda", "sara", "123", "sara@udlap.mx");
            u5.setDireccion("Puebla");
            server.users.add(u5);
            
            Producto p1 = new Producto("iPhone", u1, 2000.00);
            u1.agregarProductosComprados(p1);
            u1.agregarProductosEnVenta(p1);

            Producto p2 = new Producto("Perros", u2, 750.40);
            u2.agregarProductosComprados(p2);
            u2.agregarProductosEnVenta(p2);
            server.crearSubastas(new Subasta(200.00, p2, new Date()));

            registroNombres = LocateRegistry.createRegistry(2345);
            
            
            /*
             * En las siguientes lineas vamos a ingresar entre:
             * localhost y la dirección ip actual de tu dispositivo (ejemplo: 140.148.207.207)
             */

            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingresa la palabra 'localhost' o  tu IP actual (ejemplo: 140.148.207.207): ");
            System.out.print("==>");
            String line = new Scanner(System.in).nextLine();
            
            //System.setProperty("java.rmi.server.hostname","140.148.195.188");
            System.setProperty("java.rmi.server.hostname", line);
            ServidorMetodos stub = (ServidorMetodos) UnicastRemoteObject.exportObject(server, 2345);

            registroNombres.bind("UBay", stub);
            System.out.println("Server Ready");

        } catch (Exception e) {

            System.err.println(e.getCause() + "\n" + e.getMessage());
        }

        //Cargar objetos
    }

    private void getHost() {
    
    }
    
    public void decirHola() {
        System.out.println("El cliente se conecto y me dijo hola !!!");
    }

    public void registrarUsuario(Usuario u) throws RemoteException {
        Server server = new Server();
        server.users.add(u);
        System.out.println(users.getLast().getNombre());
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       
    }

    @Override
    public UsuarioInt inciarSesion(String user, String pass) throws RemoteException {
        UsuarioInt userObj = null;
        boolean ok = false;
        for (UsuarioInt u : this.users) {
            if (u.getUsuario().equals(user) && u.getContrasena().equals(pass)) { //Inicio correcto
                userObj = u;
                break;
            }
        }
        if (userObj != null) {
            userObj.setEnLinea(true);
            UsuarioInt stub = (UsuarioInt) UnicastRemoteObject.exportObject(userObj, 0);

            Registry reg = LocateRegistry.getRegistry(2345);
            try {
                reg.bind(userObj.getUsuario(), stub);
                this.mandarActualizacionAClientes();

            } catch (Exception ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return userObj;

    }

    @Override
    public void productos() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<UsuarioInt> getUsuarios() throws RemoteException {
        LinkedList<UsuarioInt> usuariosActivos = new LinkedList<>();
        for (UsuarioInt u : this.users) {
            if (u.getEnLinea()) {
                usuariosActivos.add(u);
            }
        }
        return usuariosActivos;
    }

    @Override
    public LinkedList<SubastaInt> subastas() throws RemoteException {
        return this.subastas;

    }

    @Override
    public void crearSubastas(SubastaInt sub) {
        subastas.add(sub);
        //Notificar Clientes
        this.mandarActualizacionAClientes();
    }

    @Override
    public void agregarOfertas(Oferta o, SubastaInt s) throws RemoteException {
        SubastaInt laSubasta = null;
        for (SubastaInt subasta : this.subastas) {
            if (subasta.getProducto().getNombre().equals(s.getProducto().getNombre())) {
                laSubasta = subasta;
                break;
            }
        }

        if (laSubasta != null) {

            laSubasta.agregarOferta(o);
            this.mandarActualizacionAClientes();

        }

    }

    @Override
    public void registrarCliente(ClienteMetodos cliente) throws RemoteException {
        //Checar que no este registrado
        if (!clientes.contains(cliente)) {
            clientes.add(cliente);
        }
        System.out.println("Clientes registrados: " + clientes.size());
    }

    @Override
    public void eliminarCliente(ClienteMetodos cliente) throws RemoteException {
        if (clientes.contains(cliente)) {
            clientes.remove(cliente);
        }
    }

    @Override
    public void cerrarSesion(String user) throws RemoteException {
        UsuarioInt userObj = null;
        for (UsuarioInt u : this.users) {
            if (u.getUsuario().equals(user)) {
                u.setEnLinea(false);
                System.out.println(u.getNombre() + " Esta en linea = " + u.getEnLinea());
                userObj = u;
                break;
            }
        }
        if (userObj != null) {

            Registry reg = LocateRegistry.getRegistry(2345);
            try {
                UnicastRemoteObject.unexportObject(userObj, true);
                reg.unbind(userObj.getUsuario());
                this.mandarActualizacionAClientes();

            } catch (Exception ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);

            }
        }

    }

    @Override
    public void mandarActualizacionAClientes() {
        System.out.println("Voy a notificar a los clientes...");
        for (ClienteMetodos c : this.clientes) {
            try {
                c.actualizarVentana();
            } catch (RemoteException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public void comprarProducto(ProductoInt producto, UsuarioInt comprador) {
        try {
            comprador.comprar(producto);
            this.mandarActualizacionAClientes();
        } catch (RemoteException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void vender(ProductoInt producto, UsuarioInt vendedor) {
        try {

            vendedor.vender(producto);
            this.mandarActualizacionAClientes();
        } catch (RemoteException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void eliminarSubasta(SubastaInt subasta) {
        int i = 0;
        for (SubastaInt s : this.subastas) {
            try {
                if (s.getProducto().getNombre().equals(subasta.getProducto().getNombre())) {
                    this.subastas.remove(i);

                }
            } catch (RemoteException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

            i++;
        }
        System.out.println("Numero de subastas en el servidor: " + this.subastas.size());

    }

}
