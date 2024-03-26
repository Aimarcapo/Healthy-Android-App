package eus.aimar.healthy.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eus.aimar.healthy.Model.Paciente;
@Dao
public interface PacienteDAO {
    @Query("SELECT * FROM Paciente")
    LiveData<List<Paciente>> getPacientes();
    @Query("SELECT * FROM Paciente WHERE id=:id_paciente")
    LiveData<Paciente> getPaciente(int id_paciente);
    @Query("SELECT * FROM Paciente WHERE medico=:id_medico")
    LiveData<Paciente> getPacienteByMedico(int id_medico);
    @Query("SELECT * FROM Paciente WHERE medico=:medico")
    LiveData<List<Paciente>> getPacientee(int medico);
    @Query("DELETE  FROM Paciente WHERE id=:id_paciente")
    void deletePaciente(int id_paciente);
    @Insert
    void insert(Paciente paciente);

    @Update
    void update(Paciente paciente);

    @Delete
    void delete(Paciente paciente);
   // @Query("delete from Paciente")
   // void deleteAllDataPaciente();
}
