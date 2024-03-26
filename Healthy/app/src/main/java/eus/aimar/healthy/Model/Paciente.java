package eus.aimar.healthy.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
@Entity(tableName = "Paciente",indices = @Index(value = {"id"}, unique = true))
public class Paciente {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "apellidos")
    private String apellidos;
    @ColumnInfo(name = "medico")
private  int  medico;
    @ColumnInfo(name = "medicamento")
 private String medicamento;
    public Paciente() {

    }

    public Paciente(int id, int medico, String nombre, String apellidos,String medicamento) {
        this.id = id;
        this.medico = medico;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.medicamento=medicamento;

    }
    public Paciente( int medico, String nombre, String apellidos,String medicamento) {
        this.medico = medico;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.medicamento=medicamento;

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

    public int getMedico() {
        return medico;
    }

    public void setMedico(int medico) {
        this.medico = medico;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }


}
