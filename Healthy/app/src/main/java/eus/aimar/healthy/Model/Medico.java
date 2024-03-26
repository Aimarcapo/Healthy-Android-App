package eus.aimar.healthy.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.checkerframework.common.aliasing.qual.Unique;

@Entity(tableName = "Medico",indices = @Index(value = {"id"}, unique = true))
public class Medico {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "apellidos")
    private String apellidos;
    @Unique
    @ColumnInfo(name = "user")
    private  String user;
    @ColumnInfo(name = "password")
    private  String password;

    public Medico() {

    }

    public Medico(int id,String nombre,String apellidos,String user,String password) {
        this.id = id;
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.user=user;
        this.password=password;

    }
    public Medico(String nombre,String apellidos,String user,String password) {
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.user=user;
        this.password=password;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
