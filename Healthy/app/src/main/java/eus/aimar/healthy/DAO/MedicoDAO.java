package eus.aimar.healthy.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eus.aimar.healthy.Model.Medico;

@Dao
public interface MedicoDAO {
    @Query("SELECT * FROM Medico")
    LiveData<List<Medico>> getMedicos();

    @Query("SELECT * FROM Medico WHERE user = :username")
    public LiveData<List<Medico>> getMedicosByUsername(String username);



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Medico medico);

    @Update
    void update(Medico medico);

    @Delete
    void delete(Medico medico);
}